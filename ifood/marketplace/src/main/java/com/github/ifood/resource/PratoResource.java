package com.github.ifood.resource;

import java.util.List;
import java.util.stream.Collectors;

import org.eclipse.microprofile.openapi.annotations.enums.SchemaType;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;

import com.github.ifood.dto.PratoDto;
import com.github.ifood.mapper.PratoMapper;
import com.github.ifood.service.PratoService;

import io.quarkus.hibernate.reactive.panache.common.WithSession;
import io.smallrye.mutiny.Uni;
import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

@Path("/pratos")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class PratoResource {

	PratoMapper pratoMapper;
	PratoService pratoService;

	@Inject
	public PratoResource(PratoMapper pratoMapper, PratoService pratoService) {
		this.pratoMapper = pratoMapper;
		this.pratoService = pratoService;
	}

	@GET
	@WithSession
	@APIResponse(responseCode = "200", content = @Content(schema = @Schema(type = SchemaType.ARRAY, implementation = PratoDto.class)))
	public Uni<List<PratoDto>> buscarPratos() {
	    return pratoService.getPratoAll()
	        .flatMap(pratos -> Uni.createFrom().item(() ->
	            pratos.stream()
	                .map(pratoMapper::toDto)
	                .collect(Collectors.toList())
	        ));
	}

}
