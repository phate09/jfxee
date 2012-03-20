package com.zenjava.firstcontact.service.repository;

import com.zenjava.firstcontact.service.Contact;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;

public interface ContactRepository extends CrudRepository<Contact, Long>, JpaSpecificationExecutor<Contact>
{
}
