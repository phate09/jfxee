package com.zenjava.firstcontact.service;

import com.zenjava.firstcontact.service.repository.ContactRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import java.util.List;


@Service
@Transactional(readOnly = true)
public class ContactServiceImpl implements ContactService
{
    @Inject private ContactRepository contactRepository;

    public List<Contact> searchContacts(String[] keywords)
    {
        String searchTerm = keywords != null && keywords.length > 0 && keywords[0].trim().length() > 0
                ? "%" + keywords[0] + "%" : "%";
        return contactRepository.findByFirstNameLikeOrLastNameLike(searchTerm, searchTerm);
    }

    public Contact getContact(Long contactId)
    {
        return contactRepository.findOne(contactId);
    }

    @Transactional(readOnly = false)
    public Contact updateContact(Contact updatedContact)
    {
        return contactRepository.save(updatedContact);
    }
}
