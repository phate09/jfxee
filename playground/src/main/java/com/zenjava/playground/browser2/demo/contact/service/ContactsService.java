package com.zenjava.playground.browser2.demo.contact.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class ContactsService
{
    private int nextId = 0;
    private List<Contact> contacts;

    public ContactsService()
    {
        this.contacts = new ArrayList<Contact>();
        this.contacts.add(new Contact(nextId++, "Richard", "Bair", "Oracle"));
        this.contacts.add(new Contact(nextId++, "Nicolas", "Lorain", "Oracle"));
        this.contacts.add(new Contact(nextId++, "Jonathan", "Giles", "Oracle"));
        this.contacts.add(new Contact(nextId++, "Jasper", "Potts", "Oracle"));
        this.contacts.add(new Contact(nextId++, "Daniel", "Zwolenski", "Zen Java"));
        this.contacts.add(new Contact(nextId++, "David", "Zwolenski", "Nova"));

        Collections.sort(this.contacts, new Comparator<Contact>()
        {
            public int compare(Contact o1, Contact o2)
            {
                return o1.getLastName().compareTo(o2.getLastName());
            }
        });
    }

    public List<Contact> searchContacts(String... keywords)
    {
        List<Contact> matches = new ArrayList<Contact>();
        outer: for (Contact contact : contacts)
        {
            for (String keyword : keywords)
            {
                keyword = keyword.toLowerCase();
                if (! (contact.getFirstName().toLowerCase().contains(keyword)
                        || contact.getLastName().toLowerCase().contains(keyword)
                        || contact.getCompany().toLowerCase().contains(keyword)))
                {
                    // not a match - does not contain keyword
                    continue outer;
                }
            }
            matches.add(contact);
        }
        return matches;
    }

    public Contact getContact(long contactId)
    {
        for (Contact contact : contacts)
        {
            if (contact.getContactId() == contactId)
            {
                return contact;
            }
        }
        return null;
    }
}
