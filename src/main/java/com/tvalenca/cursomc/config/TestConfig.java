package com.tvalenca.cursomc.config;

import java.text.ParseException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import com.tvalenca.cursomc.services.DB_Service;
import com.tvalenca.cursomc.services.Email_Service;
import com.tvalenca.cursomc.services.MockEmailService;

@Configuration
@Profile("test")
public class TestConfig {

    @Autowired
    private DB_Service dbService;

    @Bean
    public boolean instantiateDatabase() throws ParseException{
        dbService.instantiateTestDatabase();
        return true;
    }
    
    @Bean
    public Email_Service email_Service(){
        return new MockEmailService();
    }
}
