package com.github.ifood.message;

import org.eclipse.microprofile.reactive.messaging.Incoming;
import org.jboss.logging.Logger;

import com.github.ifood.dto.AdicionarRestauranteDto;
import com.github.ifood.mapper.RestauranteMapper;
import com.github.ifood.model.Restaurante;
import com.github.ifood.service.RestauranteService;

import io.vertx.core.Vertx;
import io.vertx.core.json.JsonObject;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

@ApplicationScoped
public class RestauranteIncoming {
	
	private static final Logger LOG = Logger.getLogger(RestauranteIncoming.class);
	
    RestauranteService restauranteService;
    RestauranteMapper restauranteMapper;

    @Inject
    public RestauranteIncoming(RestauranteMapper restauranteMapper, RestauranteService restauranteService) {
        this.restauranteMapper = restauranteMapper;
        this.restauranteService = restauranteService;
    }
    
    @Transactional
    @Incoming("restaurantes")
    public void receiveRestaureante(JsonObject json) {
        try {
            LOG.info("Registro recebido: " + json);

            AdicionarRestauranteDto adicionarRestaurante = json.mapTo(AdicionarRestauranteDto.class);
            Restaurante restaurante = restauranteMapper.toRestaurante(adicionarRestaurante);

            Vertx.currentContext().runOnContext(v -> {
                restauranteService.saveRestaurante(restaurante)
                                  .subscribe().with(
                                      result -> LOG.info("Restaurante salvo com sucesso"),
                                      failure -> LOG.error("Erro ao salvar restaurante: ", failure)
                                  );
            });
        } catch (Exception e) {
            LOG.error("Erro ao processar restaurante: ", e);
        }
    }

}
