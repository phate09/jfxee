package com.zenjava.firstcontact.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SimpleContactService implements ContactService
{
    private long nextId = 0;
    private Map<Long, Contact> contacts;

    public SimpleContactService()
    {
        this.contacts = new HashMap<Long, Contact>();
        createContact("Cathy", "Freeman");
        createContact("Albert", "Namatjira");
        createContact("Noel", "Pearson");
        createContact("Oodgeroo", "Nooncal");
        createContact("Neville", "Bonner");
        createContact("Pat", "O'Shane");
        createContact("Ernie", "Dingo");
        createContact("Adam", "Goodes");
        createContact("David", "Gulpilil");
        createContact("Yvonne", "Goolagong-Cawley");
    }

    public Contact createContact(String firstName, String lastName)
    {
        Contact contact = new Contact(nextId++, firstName, lastName);
        contacts.put(contact.getId(), contact);
        return contact;
    }

    public List<Contact> searchContacts(String[] keywords)
    {
        List<Contact> matches = new ArrayList<Contact>();
        if (keywords != null && keywords.length > 0)
        {
            outer:
            for (Contact contact : contacts.values())
            {
                for (String keyword : keywords)
                {
                    keyword = keyword.toLowerCase();
                    if (!(contact.getFirstName().toLowerCase().contains(keyword)
                            || contact.getLastName().toLowerCase().contains(keyword)))
                    {
                        // keyword not found on contact
                        continue outer;
                    }
                }
                matches.add(contact);
            }
        }
        return matches;
    }

    public Contact getContact(Long contactId)
    {
        return contacts.get(contactId);
    }
}
