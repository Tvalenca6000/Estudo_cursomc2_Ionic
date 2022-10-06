package com.tvalenca.cursomc.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mail.SimpleMailMessage;


public class MockEmailService extends Abstract_EmailService {

    private static final Logger LOG = LoggerFactory.getLogger(MockEmailService.class);
    
    @Override
    public void send_Email(SimpleMailMessage msg) {
        LOG.info("Simulando envio de email....");
        LOG.info(msg.toString());
        LOG.info("Email enviado");
        
    }
}
