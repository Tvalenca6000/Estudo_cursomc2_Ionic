package com.tvalenca.cursomc.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;

public class SmtpEmailService extends Abstract_EmailService {

    @Autowired
    private MailSender mailSender;

    private static final Logger LOG = LoggerFactory.getLogger(SmtpEmailService.class);

    @Override
    public void send_Email(SimpleMailMessage msg) {
        LOG.info("Enviando email....");
        mailSender.send(msg);
        LOG.info("Email enviado");
    }
    
}
