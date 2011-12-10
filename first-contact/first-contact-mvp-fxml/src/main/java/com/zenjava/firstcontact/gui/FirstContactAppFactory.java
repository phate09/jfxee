package com.zenjava.firstcontact.gui;

import com.zenjava.firstcontact.gui.detail.ContactDetailPresenter;
import com.zenjava.firstcontact.gui.main.MainPresenter;
import com.zenjava.firstcontact.gui.search.ContactSearchPresenter;
import com.zenjava.firstcontact.service.ContactService;
import com.zenjava.firstcontact.service.SimpleContactService;
import javafx.fxml.FXMLLoader;

import java.io.IOException;

public class FirstContactAppFactory
{
    private MainPresenter mainPresenter;
    private ContactSearchPresenter contactSearchPresenter;
    private ContactDetailPresenter contactDetailPresenter;
    private ContactService contactService;

    public MainPresenter getMainPresenter()
    {
        if (mainPresenter == null)
        {
            try
            {
                FXMLLoader loader = new FXMLLoader();
                loader.load(getClass().getResourceAsStream("/fxml/Main.fxml"));
                mainPresenter = (MainPresenter) loader.getController();
                mainPresenter.setContactDetailPresenter(getContactDetailPresenter());
                mainPresenter.setContactSearchPresenter(getContactSearchPresenter());
            }
            catch (IOException e)
            {
                throw new RuntimeException("Unable to load Main.fxml", e);
            }
        }
        return mainPresenter;
    }

    public ContactSearchPresenter getContactSearchPresenter()
    {
        if (contactSearchPresenter == null)
        {
            try
            {
                FXMLLoader loader = new FXMLLoader();
                loader.load(getClass().getResourceAsStream("/fxml/ContactSearch.fxml"));
                contactSearchPresenter = (ContactSearchPresenter) loader.getController();
                contactSearchPresenter.setContactService(getContactService());
                contactSearchPresenter.setMainPresenter(getMainPresenter());
            }
            catch (IOException e)
            {
                throw new RuntimeException("Unable to load ContactSearch.fxml", e);
            }
        }
        return contactSearchPresenter;
    }

    public ContactDetailPresenter getContactDetailPresenter()
    {
        if (contactDetailPresenter == null)
        {
            try
            {
                FXMLLoader loader = new FXMLLoader();
                loader.load(getClass().getResourceAsStream("/fxml/ContactDetail.fxml"));
                contactDetailPresenter = (ContactDetailPresenter) loader.getController();
                contactDetailPresenter.setContactService(getContactService());
                contactDetailPresenter.setMainPresenter(getMainPresenter());
            }
            catch (IOException e)
            {
                throw new RuntimeException("Unable to load ContactDetail.fxml", e);
            }
        }
        return contactDetailPresenter;
    }

    public ContactService getContactService()
    {
        if (contactService == null)
        {
            contactService = new SimpleContactService();
        }
        return contactService;
    }
}
