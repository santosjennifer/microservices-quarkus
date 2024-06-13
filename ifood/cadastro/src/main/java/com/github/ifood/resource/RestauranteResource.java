package com.github.ifood.resource;

import java.util.List;
import java.util.stream.Collectors;

import org.eclipse.microprofile.jwt.Claim;
import org.eclipse.microprofile.jwt.Claims;
import org.eclipse.microprofile.metrics.annotation.Counted;
import org.eclipse.microprofile.metrics.annotation.SimplyTimed;
import org.eclipse.microprofile.metrics.annotation.Timed;
import org.eclipse.microprofile.openapi.annotations.enums.SecuritySchemeType;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponses;
import org.eclipse.microprofile.openapi.annotations.security.OAuthFlow;
import org.eclipse.microprofile.openapi.annotations.security.OAuthFlows;
import org.eclipse.microprofile.openapi.annotations.security.SecurityRequirement;
import org.eclipse.microprofile.openapi.annotations.security.SecurityRequirements;
import org.eclipse.microprofile.openapi.annotations.security.SecurityScheme;
import org.eclipse.microprofile.openapi.annotations.security.SecuritySchemes;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;
import org.eclipse.microprofile.reactive.messaging.Channel;
import org.eclipse.microprofile.reactive.messaging.Emitter;

import com.github.ifood.dto.restaurante.AdicionarRestauranteDto;
import com.github.ifood.dto.restaurante.AtualizarRestauranteDto;
import com.github.ifood.dto.restaurante.RestauranteDto;
import com.github.ifood.mapper.RestauranteMapper;
import com.github.ifood.model.Restaurante;
import com.github.ifood.provider.ConstraintViolationResponse;
import com.github.ifood.service.RestauranteService;

import jakarta.annotation.security.RolesAllowed;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@RequestScoped
@Path("/restaurantes")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@RolesAllowed("proprietario")
@SecuritySchemes({
		@SecurityScheme(securitySchemeName = "ifood-oauth", type = SecuritySchemeType.OAUTH2, flows = @OAuthFlows(password = @OAuthFlow(tokenUrl = "http://localhost:8081/realms/ifood/protocol/openid-connect/token"))) })
@SecurityRequirements({ @SecurityRequirement(name = "ifood-oauth", scopes = {}) })
public class RestauranteResource {

	@Inject
	RestauranteMapper restauranteMapper;

	@Inject
	@Claim(standard = Claims.given_name)
	String jwtSubProprietario;

	@Inject
	@Channel("restaurantes")
	Emitter<Restaurante> restauranteEmitter;

	@Inject
	RestauranteService restauranteService;

	@Counted(name = "Quantidade buscas todos restaurante")
	@SimplyTimed(name = "Tempo simples de busca dos restaurantes")
	@Timed(name = "Tempo completo de busca dos restaurantes")
	@GET
	@Tag(name = "Restaurante")
	public List<RestauranteDto> todosRestaurantes() {
		return Restaurante.<Restaurante>streamAll().map(this.restauranteMapper::toRestauranteDto)
				.collect(Collectors.toList());
	}

	@POST
	@Transactional
	@Tag(name = "Restaurante")
	@APIResponses({
		    @APIResponse(responseCode = "201", description = "Restaurante cadastrado com successo"),
			@APIResponse(responseCode = "400", description = "Erro ao cadastrar restaurante", content = @Content(schema = @Schema(allOf = ConstraintViolationResponse.class)))
	})
	public Response adicionarRestaurante(@Valid AdicionarRestauranteDto dto) {
		dto.cnpj = dto.replaceCnpj();
		Restaurante restaurante = restauranteMapper.toRestaurante(dto);
		restaurante.proprietario = jwtSubProprietario;
		restaurante.persist();
	
		restauranteEmitter.send(restaurante);
		
		return Response.status(Response.Status.CREATED).build();
	}

	@PUT
	@Path("/{id}")
	@Transactional
	@Tag(name = "Restaurante")
	public Response atualizarRestaurante(@PathParam("id") Long id, AtualizarRestauranteDto dto) {
		Restaurante restaurante = restauranteService.findRestauranteById(id, jwtSubProprietario);
		restauranteMapper.toRestaurante(dto, restaurante);
		restaurante.persist();
		return Response.noContent().build();
	}

	@DELETE
	@Path("/{id}")
	@Transactional
	@Tag(name = "Restaurante")
	public Response excluirRestaurante(@PathParam("id") Long id) {
		Restaurante restaurante = restauranteService.findRestauranteById(id, jwtSubProprietario);
		restaurante.delete();
		return Response.noContent().build();
	}

}