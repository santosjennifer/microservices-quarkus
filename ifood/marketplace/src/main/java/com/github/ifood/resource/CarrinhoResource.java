package com.github.ifood.resource;

import org.eclipse.microprofile.reactive.messaging.Channel;
import org.eclipse.microprofile.reactive.messaging.Emitter;

import com.github.ifood.dto.PedidoRealizadoDto;
import com.github.ifood.mapper.PratoCarrinhoMapper;
import com.github.ifood.model.PratoCarrinho;
import com.github.ifood.service.CarrinhoService;
import com.github.ifood.service.PratoService;

import io.quarkus.hibernate.reactive.panache.common.WithSession;
import io.quarkus.hibernate.reactive.panache.common.WithTransaction;
import io.smallrye.mutiny.Uni;
import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/carrinho")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class CarrinhoResource {

	private final String cliente = "Cliente A";

	@Inject
	PratoService pratoService;

	@Inject
	CarrinhoService carrinhoService;

	@Inject
	PratoCarrinhoMapper pratoCarrinhoMapper;

	@Inject
	@Channel("pedidos")
	Emitter<PedidoRealizadoDto> pedidoRealizadoDtoEmitter;

	@GET
	@WithTransaction
	public Uni<PratoCarrinho> findCarrinhoCliente() {
		return carrinhoService.findAllOpenPedidoCliente(cliente).collect().first();
	}

	@POST
	@Path("/prato/{idPrato}")
	public Uni<Response> adicionarPratoCarrinho(@PathParam("idPrato") Long idPrato) {
		return pratoService.findPratoById(idPrato).map(p -> new PratoCarrinho(p.id, cliente))
				.chain(this.carrinhoService::save).onItem()
				.transform(pc -> Response.status(Response.Status.CREATED).entity(pc).build());
	}

	@GET
	@Path("/details/{idPedido}")
	public Uni<Response> findPedidoCliente(@PathParam("idPedido") Long idPedido) {
		return this.carrinhoService.findByIdPedidoCliente(idPedido, cliente).onItem()
				.transform(p -> Response.ok(p).build());
	}

	@POST
	@WithSession
	@Path("realizarPedido")
	public Uni<Response> finalizarPedidoCliente() {
		return this.carrinhoService.findAllOpenPedidoCliente(cliente).onItem()
				.transformToUniAndMerge(this.carrinhoService::finish).onItem()
				.transformToUniAndMerge(pratoCarrinho -> this.pratoService.findPratoById(pratoCarrinho.prato))
				.map(this.pratoCarrinhoMapper::fromPratoPedidoDto).collect().asList()
				.map(pratoPedido -> new PedidoRealizadoDto(pratoPedido, cliente))
				.invoke(this::sendMessageKafka).onItem()
				.transform(pedidoRealizado -> Response.accepted(pedidoRealizado).build());
	}

	private void sendMessageKafka(PedidoRealizadoDto pedidoRealizado) {
		this.pedidoRealizadoDtoEmitter.send(pedidoRealizado);
	}

}
