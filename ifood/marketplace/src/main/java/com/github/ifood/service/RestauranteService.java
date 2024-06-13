package com.github.ifood.service;

import org.jboss.logging.Logger;

import com.github.ifood.model.Restaurante;

import io.quarkus.hibernate.reactive.panache.Panache;
import io.quarkus.hibernate.reactive.panache.common.WithSession;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class RestauranteService {

	private static final Logger LOG = Logger.getLogger(RestauranteService.class);

	@WithSession
	public Uni<Restaurante> getRestauranteById(Long id) {
		return Restaurante.findById(id);
	}
	
	public Uni<Restaurante> saveRestaurante(Restaurante restaurante) {
	    try {
	        restaurante.cnpj = restaurante.cnpj.replaceAll("\\x2E|\\x2F|\\x2D", "");

	        return Panache.withTransaction(() -> restaurante.persist().replaceWith(restaurante))
	                      .onItem().invoke(persistedRestaurante -> 
	                          LOG.info("Restaurante persistido com sucesso")
	                      ).onFailure().invoke(e -> 
	                          LOG.error("Falha ao persitir restaurante: ", e)
	                      );
	    } catch (Exception e) {
	        LOG.error("Exceção ao persistir restaurante na base: ", e);
	        return Uni.createFrom().failure(e);
	    }
	}

}
