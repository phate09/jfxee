package com.zenjava.firstcontact.gui.search;

import com.zenjava.firstcontact.service.Contact;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class ContactSearchModel
{
    private StringProperty searchPhrase;
    private ObservableList<Contact> searchResults;

    public ContactSearchModel()
    {
        searchPhrase = new SimpleStringProperty("");
        searchResults = FXCollections.observableArrayList();
    }

    public StringProperty searchPhraseProperty()
    {
        return searchPhrase;
    }

    public String getSearchPhrase()
    {
        return searchPhrase.get();
    }

    public void setSearchPhrase(String searchPhrase)
    {
        this.searchPhrase.set(searchPhrase);
    }

    public ObservableList<Contact> getSearchResults()
    {
        return searchResults;
    }
}
