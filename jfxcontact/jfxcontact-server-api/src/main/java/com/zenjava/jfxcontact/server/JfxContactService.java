package com.zenjava.jfxcontact.server;

import java.util.List;

public interface JfxContactService
{
    List<Contact> searchContacts(String... keywords);
}
