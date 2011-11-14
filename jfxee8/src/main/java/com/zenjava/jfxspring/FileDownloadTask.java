package com.zenjava.jfxspring;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.Task;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.logging.Level;
import java.util.logging.Logger;

public class FileDownloadTask extends Task<File>
{
    private final static Logger log = Logger.getLogger(FileDownloadTask.class.getName());

    private static final int DEFAULT_BUFFER_SIZE = 1024;

    private HttpClient httpClient;
    private String remoteUrl;
    private File localFile;
    private int bufferSize;

    public FileDownloadTask(String remoteUrl, File localFile)
    {
        this(new DefaultHttpClient(), remoteUrl, localFile, DEFAULT_BUFFER_SIZE);
    }

    public FileDownloadTask(HttpClient httpClient, String remoteUrl, File localFile, int bufferSize)
    {
        this.httpClient = httpClient;
        this.remoteUrl = remoteUrl;
        this.localFile = localFile;
        this.bufferSize = bufferSize;

        stateProperty().addListener(new ChangeListener<State>()
        {
            public void changed(ObservableValue<? extends State> source, State oldState, State newState)
            {
                if (newState.equals(State.SUCCEEDED))
                {
                    onSuccess();
                }
                else if (newState.equals(State.FAILED))
                {
                    onFailed();
                }
            }
        });
    }

    public String getRemoteUrl()
    {
        return remoteUrl;
    }

    public File getLocalFile()
    {
        return localFile;
    }

    protected File call() throws Exception
    {
        log.info(String.format("Downloading file %s to %s", remoteUrl, localFile.getAbsolutePath()));

        HttpGet httpGet = new HttpGet(this.remoteUrl);
        HttpResponse response = httpClient.execute(httpGet);
        InputStream remoteContentStream = response.getEntity().getContent();
        OutputStream localFileStream = null;
        try
        {
            long fileSize = response.getEntity().getContentLength();
            log.fine(String.format("Size of file to download is %s", fileSize));

            File dir = localFile.getParentFile();
            dir.mkdirs();

            localFileStream = new FileOutputStream(localFile);
            byte[] buffer = new byte[bufferSize];
            int sizeOfChunk;
            int amountComplete = 0;
            while ((sizeOfChunk = remoteContentStream.read(buffer)) != -1)
            {
                localFileStream.write(buffer, 0, sizeOfChunk);
                amountComplete += sizeOfChunk;
                updateProgress(amountComplete, fileSize);
                log.info(String.format("Downloaded %s of %s bytes (%d) for file",
                        amountComplete, fileSize, (int)((double)amountComplete / (double)fileSize * 100.0)));
            }
            log.info(String.format("Downloading of file %s to %s completed successfully", remoteUrl, localFile.getAbsolutePath()));
            return localFile;
        }
        finally
        {
            remoteContentStream.close();
            if (localFileStream != null)
            {
                localFileStream.close();
            }
        }
    }


    private void onFailed()
    {
        log.log(Level.SEVERE, "File download failed: " + getException().getMessage(), getException());
    }

    private void onSuccess()
    {
    }
}



/*
I had to implement a File Download service for something I'm working on this week and thought I'd share.

File downloading is pretty easy these days if we use something like Apache's HTTPClient. This can do all sorts of stuff,
like proxy-support, retry-on-error and HTTP Basic Authentication. I won't go into too much detail
on this as we only need the basics here, but you can find a lot of good information about it on the web (check out their tutorial for a good starting point).

The trick to getting it working in JavaFX is the threading. Downloading a file is obviously a slow operation, the kind
of thing we want to do on a background thread so we're not blocking our GUI. Using JavaFX's threading Worker framework
we can create a fairly nifty little re-usable class for this sort of Downloading.

Have a look at the full source code for the FileDownloadTask first. As you can see, the main work happens in the 'call' method, so we'll step through that in detail.

First we open a connection to our download URL. Using HttpClient this is ridiculously easy:

[sourcecode language="java"]
HttpGet httpGet = new HttpGet(this.remoteUrl);
HttpResponse response = httpClient.execute(httpGet);
[/sourcecode]

There are a number of different ways we could access our response, we could get the whole thing as a String or a byte array for example,
but for downloading files we want to use streams. This is as easy as:

[sourcecode language="java"]
InputStream remoteContentStream = response.getEntity().getContent();
[/sourcecode]

Now we have our stream, we can use simple Java streaming to copy from our remote stream to a local file stream, but
since we're JavaFX programmers and we want to make nice, friendly GUIs for our users were going to add some support
for tracking how much of the file we've downloaded and how much we have left to do.

First, we find out just how big our file is, using this command:

[sourcecode language="java"]
long fileSize = response.getEntity().getContentLength();
[/sourcecode]

Now we can create our local file and get it ready for writing to:

[sourcecode language="java"]
File dir = localFile.getParentFile();
dir.mkdirs();
localFileStream = new FileOutputStream(localFile);
[/sourcecode]

We'll use a buffer to read chunks of data from the remote fiel and push this into our local file, as we go we'll keep
track of how many bytes we've downloaded:

[sourcecode language="java"]
int sizeOfChunk;
int amountComplete = 0;
while ((sizeOfChunk = remoteContentStream.read(buffer)) != -1)
{
    localFileStream.write(buffer, 0, sizeOfChunk);
    amountComplete += sizeOfChunk;
    updateProgress(amountComplete, fileSize);
}
[/sourcecode]

Notice, the call to 'updateProgress', this is a method provided by Task that allows us to update the 'progress; property
of our task in a thread safe way. UI Controls (such as the ProgressBar) can bind to this property to monitor the progress
of the download without having to do any special thread handling on their end.

That's more or less all there is to it from the service perspective, to use this service anywhere in your code you just do the following:

[sourcecode language="java"]
FileDownloadTask fileDownloadTask = new FileDownloadTask("http://www.tagg.org/pdftest.pdf", "c:/temp/downloaded.pdf");
[/sourcecode]

Generally, when you use this class there are a few tips and tricks that can be helpful.

If you want the user to choose where the download should be saved, you can use JavaFx's FileChooser like so:

[sourcecode language="java"]
String remoteUrl = "http://media.beyondzeroemissions.org/ZCA2020_Stationary_Energy_Report_v1.pdf";
FileChooser chooser = new FileChooser();
File file = chooser.showSaveDialog(stage);
if (file != null)
{
    FileDownloadTask fileDownloadTask = new FileDownloadTask(remoteUrl, file);
    new Thread(fileDownloadTask).start();
}
[/sourcecode]


To create a Progress Bar and have it display the progress of your download do the following:

[sourcecode language="java"]
ProgressBar progressBar = new ProgressBar();
progressBar.progressProperty().bind(fileDownloadTask.progressProperty());
progressBar.visibleProperty().bind(fileDownloadTask.runningProperty()); // optionally hide the progress bar when not loading
[/sourcecode]

To create a launch button that is only enabled when the file is successfully download you can do this:

[sourcecode language="java"]
Hyperlink link = new Hyperlink();
link.setText(fileDownloadTask.getLocalFile().getName());
link.disableProperty().bind(fileDownloadTask.stateProperty().isNotEqualTo(Worker.State.SUCCEEDED));
link.setOnAction(new EventHandler<ActionEvent>()
{
    public void handle(ActionEvent event)
    {
        try
        {
            Desktop.getDesktop().open(fileDownloadTask.getLocalFile());
        }
        catch (IOException e)
        {
            // todo handle this by showing an error message
            e.printStackTrace();
        }
    }
});
[/sourcecode]


I won't bore you with the details of it all, but you can see a simple little demo of this all in action using this
code found at:














http://hc.apache.org/httpcomponents-client-ga/tutorial/pdf/httpclient-tutorial.pdf
                                  http://hc.apache.org/httpcomponents-client-ga/

 */