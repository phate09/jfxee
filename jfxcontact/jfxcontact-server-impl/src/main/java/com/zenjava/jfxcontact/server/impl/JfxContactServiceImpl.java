package com.zenjava.jfxcontact.server.impl;

import com.zenjava.jfxcontact.server.Contact;
import com.zenjava.jfxcontact.server.JfxContactService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service("jfxContactServiceImpl")
public class JfxContactServiceImpl implements JfxContactService
{
    public List<Contact> searchContacts(String... keywords)
    {
        // we will change this to use hibernate later
        List<Contact> results = new ArrayList<Contact>();
        results.add(new Contact("Homer", "Simpson"));
        results.add(new Contact("Marge", "Simpson"));
        results.add(new Contact("Lisa", "Simpson"));
        results.add(new Contact("Bart", "Simpson"));
        results.add(new Contact("Maggie", "Simpson"));
        return results;
    }
}
