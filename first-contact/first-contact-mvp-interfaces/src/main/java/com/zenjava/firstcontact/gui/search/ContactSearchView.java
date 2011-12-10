package com.zenjava.firstcontact.gui.search;

import com.zenjava.firstcontact.service.Contact;
import javafx.scene.Node;

import java.util.List;

public interface ContactSearchView
{
    Node getNode();

    void setPresenter(ContactSearchPresenter presenter);

    String getSearchPhrase();

    void setSearchResults(List<Contact> searchResults);
}
