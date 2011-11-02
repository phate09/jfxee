package com.zenjava.jfxee7;

import com.caucho.hessian.server.HessianServlet;

public class HelloServiceImpl extends HessianServlet implements HelloService
{
    public String sayHello(Person person)
    {
        System.out.println(String.format("The server received a request from %s %s",
                person.getFirstName(), person.getLastName()));

        return String.format("The server says hello %s %s!",
                person.getFirstName(), person.getLastName());
    }
}
