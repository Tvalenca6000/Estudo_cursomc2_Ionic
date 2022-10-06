package com.tvalenca.cursomc.services;

import java.util.Date;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tvalenca.cursomc.domain.Item_Pedido;
import com.tvalenca.cursomc.domain.Pagamento_Com_Boleto;
import com.tvalenca.cursomc.domain.Pedido;
import com.tvalenca.cursomc.domain.enums.Estado_Pagamento;
import com.tvalenca.cursomc.repositories.Item_Pedido_Repository;
import com.tvalenca.cursomc.repositories.Pagamento_Repository;
import com.tvalenca.cursomc.repositories.Pedido_Repository;
import com.tvalenca.cursomc.services.exceptions.ObjectNotFoundException;

@Service
public class Pedido_Service {
    
    @Autowired
    private Pedido_Repository repo;

    @Autowired
    private Boleto_Service boleto_Service;

    @Autowired
    private Pagamento_Repository pagamento_Repository;

    @Autowired
    private Item_Pedido_Repository item_Pedido_Repository;

    @Autowired
    private Produto_Service produto_Service;

    @Autowired
    private Cliente_Service cliente_Service;

	@Autowired
	private Email_Service email_Service;

    public Pedido buscar(Integer id){
        Optional<Pedido> obj = repo.findById(id); 
        return obj.orElseThrow(()-> new ObjectNotFoundException("Objeto não encontrado! Id: " + id + ", Tipo: "
                                     + Pedido.class.getName()));
    }

    @Transactional
    public Pedido insert(Pedido obj) {
		obj.setId(null);
		obj.setInstante(new Date());
		obj.setCliente(cliente_Service.buscar(obj.getCliente().getId()));
		obj.getPagamento().setEstado(Estado_Pagamento.PENDENTE);
		obj.getPagamento().setPedido(obj);
		if (obj.getPagamento() instanceof Pagamento_Com_Boleto) {
			Pagamento_Com_Boleto pagto = (Pagamento_Com_Boleto) obj.getPagamento();
			boleto_Service.preencherPagamentoComBoleto(pagto, obj.getInstante());
		}
		obj = repo.save(obj);
		pagamento_Repository.save(obj.getPagamento());
		for (Item_Pedido ip : obj.getItens()) {
			ip.setDesconto(0.0);
			ip.setProduto(produto_Service.buscar(ip.getProduto().getId()));
			ip.setPreco(ip.getProduto().getPreco());
			ip.setPedido(obj);
		}
		item_Pedido_Repository.saveAll(obj.getItens());
		// Verificando ToString System.out.println(obj);
		email_Service.send_OrderConfirmationEmail(obj);
		return obj;
	}
}