package com.github.ifood.message;

import java.util.ArrayList;

import org.eclipse.microprofile.reactive.messaging.Incoming;
import org.jboss.logging.Logger;

import com.github.ifood.dto.PedidoRealizadoDto;
import com.github.ifood.dto.PratoPedidoDto;
import com.github.ifood.dto.RestauranteDto;
import com.github.ifood.model.Pedido;
import com.github.ifood.model.Prato;
import com.github.ifood.model.Restaurante;
import com.github.ifood.service.ElastichSearchService;

import jakarta.inject.Inject;

public class PedidoRealizadoIncoming {
	
	private static final Logger LOG = Logger.getLogger(PedidoRealizadoIncoming.class);
	
    @Inject
    ElastichSearchService elastichSearchService;

	@Incoming("pedidos")
	public void lerPedidos(PedidoRealizadoDto pedidoRealizado) {
		try {
			LOG.info("Pedido recebido do cliente: " + pedidoRealizado.cliente);
			
			Pedido pedido = new Pedido();
			pedido.cliente = pedidoRealizado.cliente;
			pedido.pratos = new ArrayList<>();
			
            for (PratoPedidoDto pratoDto : pedidoRealizado.pratos) {
                Prato prato = from(pratoDto);
                pedido.pratos.add(prato);
                
                Restaurante restaurante = from(pratoDto.restaurante);
                prato.restaurante = restaurante;
            }
			
			elastichSearchService.index(pedidoRealizado);
			
			pedido.persist();
			LOG.info("Pedido salvo: " + pedido.id);
		} catch (Exception e) {
			LOG.error("Erro ao receber pedido: " + e);
		}
	}

	
    private Prato from(PratoPedidoDto pratoPedido) {
        Prato prato = new Prato();
        prato.descricao = pratoPedido.descricao;
        prato.nome = pratoPedido.nome;
        prato.preco = pratoPedido.preco;
        return prato;
    }
    
    private Restaurante from(RestauranteDto restauranteDto) {
        Restaurante restaurante = new Restaurante();
        restaurante.cnpj = restauranteDto.cnpj;
        restaurante.id = restauranteDto.id;
        restaurante.nome = restauranteDto.nome;
        return restaurante;
    }
	
}
