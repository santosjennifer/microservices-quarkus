package com.github.ifood.service;

import java.util.UUID;

import org.elasticsearch.client.Request;
import org.elasticsearch.client.Response;
import org.elasticsearch.client.ResponseListener;
import org.elasticsearch.client.RestClient;
import org.jboss.logging.Logger;

import com.github.ifood.dto.PedidoRealizadoDto;

import io.vertx.core.json.JsonObject;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

@ApplicationScoped
public class ElasticSearchService {

	private static final Logger LOG = Logger.getLogger(ElasticSearchService.class);

	@Inject
	RestClient restClient;

	public void index(PedidoRealizadoDto pedidoRealizadoDto) {
		String uui = UUID.randomUUID().toString();
		Request request = new Request("PUT", "pedidos/_doc/" + uui);
		request.setJsonEntity(JsonObject.mapFrom(pedidoRealizadoDto).toString());
		this.restClient.performRequestAsync(request, getResponseListener());
	}

	private ResponseListener getResponseListener() {
		return new ResponseListener() {
			@Override
			public void onSuccess(Response response) {
				LOG.info("Sucesso ao enviar dados para o Elastic Search");
			}

			@Override
			public void onFailure(Exception e) {
				LOG.error("Erro ao enviar dados para o Elastic Search: " + e.getMessage(), e.getCause());
			}
		};
	}

}
