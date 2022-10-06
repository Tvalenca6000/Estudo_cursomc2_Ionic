package com.tvalenca.cursomc.services;

import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;

import com.tvalenca.cursomc.domain.Pedido;

public abstract class Abstract_EmailService implements Email_Service{
    
    @Value("${default.sender}")
    private String sender;

    @Override
    public void send_OrderConfirmationEmail(Pedido obj) {
        SimpleMailMessage sm = prepare_SimpleMailMessageFromPedido(obj);
        send_Email(sm);
        
    }

    protected SimpleMailMessage prepare_SimpleMailMessageFromPedido(Pedido obj) {
        SimpleMailMessage sm = new SimpleMailMessage();
        sm.setTo(obj.getCliente().getEmail());
        sm.setFrom(sender);
        sm.setSubject("Pedido Confirmado! Codigo: " + obj.getId());
        sm.setSentDate(new Date(System.currentTimeMillis()));
        sm.setText(obj.toString());
        return sm;
    }
    
}
