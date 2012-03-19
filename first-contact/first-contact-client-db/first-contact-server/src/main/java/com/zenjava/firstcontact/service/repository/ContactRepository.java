package com.zenjava.firstcontact.service.repository;

import com.zenjava.firstcontact.service.Contact;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ContactRepository extends CrudRepository<Contact, Long>
{
    List<Contact> findByFirstNameLikeOrLastNameLike(String firstName, String lastName);
}
