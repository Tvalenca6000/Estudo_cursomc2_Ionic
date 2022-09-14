package com.tvalenca.cursomc.services;

import java.util.Calendar;
import java.util.Date;

import org.springframework.stereotype.Service;

import com.tvalenca.cursomc.domain.Pagamento_Com_Boleto;

@Service
public class Boleto_Service {
    
    public void preencherPagamentoComBoleto(Pagamento_Com_Boleto pagto, Date instanteDoPedido){
        Calendar cal = Calendar.getInstance();
        cal.setTime(instanteDoPedido);
        cal.add(Calendar.DAY_OF_MONTH, 7);
        pagto.setDataVencimento(cal.getTime());
    }
}
