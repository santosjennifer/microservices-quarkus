package com.github.ifood.service;

import java.time.LocalDateTime;

import com.github.ifood.model.PratoCarrinho;

import io.quarkus.hibernate.reactive.panache.Panache;
import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.PersistenceException;
import jakarta.ws.rs.NotFoundException;

@ApplicationScoped
public class CarrinhoService {

	public Uni<PratoCarrinho> save(PratoCarrinho pratoCarrinho) {
		return Panache.<PratoCarrinho>withTransaction(pratoCarrinho::persist).onItem().ifNull()
				.failWith(() -> new PersistenceException("Erro ao salvar prato no carrinho: " + pratoCarrinho));
	}

	public Uni<PratoCarrinho> findByIdPedidoCliente(Long idPedido, String cliente) {
		return PratoCarrinho.<PratoCarrinho>find("id = ?1 and cliente = ?2", idPedido, cliente).firstResult().onItem()
				.ifNull().failWith(() -> new NotFoundException("Pedido não encontrado: " + idPedido));
	}
	
	public Multi<PratoCarrinho> findAllOpenPedidoCliente(String cliente) {
		return PratoCarrinho.<PratoCarrinho>list("finalized = false and cliente = ?1", cliente).onItem()
				.transformToMulti(pratos -> {
					if (pratos.isEmpty()) {
						throw new NotFoundException("Não há prato no carrinho para o cliente " + cliente);
					} else {
						return Multi.createFrom().iterable(pratos);
					}
				});
	}

	public Uni<PratoCarrinho> finish(PratoCarrinho pratoCarrinho) {
		pratoCarrinho.finallyAt = LocalDateTime.now();
		pratoCarrinho.finalized = true;
		return save(pratoCarrinho);
	}

}
