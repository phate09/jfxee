package com.zenjava.jfxspring;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SampleAppFactory
{
    @Bean
    public Person person()
    {
        return new Person("Richard");
    }

    @Bean
    public SampleController sampleController()
    {
        return new SampleController();
    }
}
