package com.github.ifood.resource;

import java.io.IOException;
import java.util.List;

import org.bson.types.ObjectId;

import com.github.ifood.exception.PedidoNotFoundException;
import com.github.ifood.model.Localizacao;
import com.github.ifood.model.Pedido;

import io.quarkus.mongodb.panache.PanacheMongoEntityBase;
import jakarta.json.bind.JsonbBuilder;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

@Path("/pedidos")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class PedidoResource {
	
	@GET
	public List<PanacheMongoEntityBase> listaPedidos() {
		return Pedido.listAll();
	}
	
    @POST
    @Path("{idPedido}/localizacao")
    public Pedido novaLocalizacao(@PathParam("idPedido") String idPedido, Localizacao localizacao) throws IOException {
        Pedido pedido = Pedido.findById(new ObjectId(idPedido));
        if (pedido == null) {
            throw new PedidoNotFoundException("Pedido não encontrado");
        }

        pedido.localizacaoEntregador = localizacao;
        pedido.persistOrUpdate();

        String mensagem = JsonbBuilder.create().toJson(localizacao);
        LocalizacaoWebSocket.enviarMensagem(mensagem);

        return pedido;
    }

}
