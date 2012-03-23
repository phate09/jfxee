package com.zenjava.firstcontact.service;

import com.zenjava.firstcontact.service.repository.ContactRepository;
import com.zenjava.firstcontact.service.repository.ContactSpecifications;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import java.util.List;


@Service
@Transactional(readOnly = true)
public class ContactServiceImpl implements ContactService
{
    private static final Logger log = LoggerFactory.getLogger(ContactServiceImpl.class);

    @Inject private ContactRepository contactRepository;

    public List<Contact> searchContacts(String[] keywords)
    {
        log.info("Searching Contacts, keywords = '{}'", keywords);
        List<Contact> contacts = contactRepository.findAll(ContactSpecifications.searchContacts(keywords));
        log.debug("Found {} matching Contacts", contacts.size());
        return contacts;
    }

    public Contact getContact(Long contactId)
    {
        log.info("Finding details for Contacts with ID '{}'", contactId);
        Contact contact = contactRepository.findOne(contactId);
        if (contact != null)
        {
            log.debug("Found '{} {}' for Contact ID '{}'", new Object[] {
                    contact.getFirstName(), contact.getLastName(), contactId
            });
        }
        else
        {
            log.debug("No Contact found for ID '{}'", contactId);
        }
        return contact;
    }

    @Transactional(readOnly = false)
    public Contact updateContact(Contact updatedContact)
    {
        log.info("Updating details for Contact with ID '{}', firstName='{}', lastName='{}'", new Object[] {
                updatedContact.getId(), updatedContact.getFirstName(), updatedContact.getLastName()
        });
        return contactRepository.save(updatedContact);
    }
}
