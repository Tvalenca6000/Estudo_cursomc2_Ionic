package com.tvalenca.cursomc.services;

import org.springframework.mail.SimpleMailMessage;

import com.tvalenca.cursomc.domain.Pedido;

public interface Email_Service {

    void send_OrderConfirmationEmail(Pedido obj);

    void send_Email(SimpleMailMessage msg);
    
}
