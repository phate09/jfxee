package com.zenjava.firstcontact.gui.detail;

import com.zenjava.firstcontact.service.Contact;
import javafx.scene.Node;

public interface ContactDetailView
{
    Node getNode();

    void setPresenter(ContactDetailPresenter presenter);

    Contact getContact();

    void setContact(Contact contact);
}
