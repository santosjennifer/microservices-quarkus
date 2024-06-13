package com.github.ifood.resource;

import java.util.List;

import com.github.ifood.dto.AdicionarRestauranteDto;
import com.github.ifood.dto.PratoDto;
import com.github.ifood.mapper.PratoMapper;
import com.github.ifood.mapper.RestauranteMapper;
import com.github.ifood.model.Restaurante;
import com.github.ifood.service.PratoService;
import com.github.ifood.service.RestauranteService;

import io.quarkus.hibernate.reactive.panache.common.WithSession;
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

@Path("/restaurantes")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class RestauranteResource {
	
    PratoMapper pratoMapper;
    RestauranteService restauranteService;
    PratoService pratoService;
    RestauranteMapper restauranteMapper;

    @Inject
    public RestauranteResource(PratoMapper pratoMapper,
                               RestauranteService restauranteService,
                               PratoService pratoService,
                               RestauranteMapper restauranteMapper) {
        this.pratoMapper = pratoMapper;
        this.restauranteService = restauranteService;
        this.pratoService = pratoService;
        this.restauranteMapper = restauranteMapper;
    }

    @GET
    @WithSession
    @Path("/{idRestaurante}/pratos")
    public Uni<List<PratoDto>> buscarPratosByRestaurante(@PathParam("idRestaurante") Long idRestaurante) {
        return restauranteService.getRestauranteById(idRestaurante)
            .flatMap(restaurante -> 
                pratoService.getPratoByRestaurante(restaurante)
                    .map(pratoMapper::toDto)
                    .collect().asList()
            );
    }

    @POST
    @WithSession
    public Uni<Response> salvarRestaurante(AdicionarRestauranteDto adicionarRestauranteDto) {
        Restaurante restaurante = restauranteMapper.toRestaurante(adicionarRestauranteDto);
        return restauranteService.saveRestaurante(restaurante)
        		.onItem().transform(saved -> Response.status(Response.Status.CREATED).entity(saved).build());
    }

    @GET
    @WithSession
    public Uni<List<Restaurante>> retornarRestaurantes() {
        return Restaurante.listAll();
    }
}
