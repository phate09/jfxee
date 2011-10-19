package com.zenjava.jfxcontact.client.search;

import com.zenjava.jfxcontact.server.Contact;
import com.zenjava.jfxcontact.server.JfxContactService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class SearchController
{
    private SearchView view;


    @Autowired
    private JfxContactService jfxContactService;

    public SearchController()
    {
        view = new SearchView(this);
    }

    public SearchView getView()
    {
        return view;
    }

    public void search(String[] terms)
    {
        List<Contact> contacts = jfxContactService.searchContacts(terms);
        view.setSearchResults(contacts);
    }
}
