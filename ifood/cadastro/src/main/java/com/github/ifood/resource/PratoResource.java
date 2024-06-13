package com.github.ifood.resource;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.eclipse.microprofile.metrics.annotation.Counted;
import org.eclipse.microprofile.metrics.annotation.SimplyTimed;
import org.eclipse.microprofile.metrics.annotation.Timed;
import org.eclipse.microprofile.openapi.annotations.enums.SecuritySchemeType;
import org.eclipse.microprofile.openapi.annotations.security.OAuthFlow;
import org.eclipse.microprofile.openapi.annotations.security.OAuthFlows;
import org.eclipse.microprofile.openapi.annotations.security.SecurityRequirement;
import org.eclipse.microprofile.openapi.annotations.security.SecurityRequirements;
import org.eclipse.microprofile.openapi.annotations.security.SecurityScheme;
import org.eclipse.microprofile.openapi.annotations.security.SecuritySchemes;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

import com.github.ifood.dto.prato.AdicionarPratoDto;
import com.github.ifood.dto.prato.AtualizarPratoDto;
import com.github.ifood.dto.prato.PratoDto;
import com.github.ifood.mapper.PratoMapper;
import com.github.ifood.model.Prato;
import com.github.ifood.model.Restaurante;

import jakarta.annotation.security.RolesAllowed;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.NotFoundException;
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
public class PratoResource {

	@Inject
	PratoMapper pratoMapper;

	private static Optional<Restaurante> validaRestaurante(Long idRestaurante) {
		Optional<Restaurante> opRestaurante = Restaurante.findByIdOptional(idRestaurante);
		if (opRestaurante.isEmpty())
			throw new NotFoundException("Restaurante não encontrado");
		return opRestaurante;
	}

	@Counted(name = "Quantidade buscas de prato por restaurante")
	@SimplyTimed(name = "Tempo simples de busca por prato")
	@Timed(name = "Tempo completo de busca do prato")
	@GET
	@Tag(name = "Prato")
	@Path("/{idRestaurante}/pratos")
	public List<PratoDto> todosPratos(@PathParam("idRestaurante") Long idRestaurante) {
		Restaurante restaurante = validaRestaurante(idRestaurante).get();

		return Prato.<Prato>stream("restaurante", restaurante).map(this.pratoMapper::toDto)
				.collect(Collectors.toList());
	}

	@POST
	@Transactional
	@Tag(name = "Prato")
	@Path("/{idRestaurante}/pratos")
	public void adicionarPrato(@PathParam("idRestaurante") Long idRestaurante, AdicionarPratoDto dto) {
		Optional<Restaurante> opRestaurante = validaRestaurante(idRestaurante);

		Prato prato = this.pratoMapper.toPrato(dto);
		prato.restaurante = opRestaurante.get();

		prato.persist();
		Response.status(Response.Status.CREATED).build();
	}

	@PUT
	@Transactional
	@Tag(name = "Prato")
	@Path("/{idRestaurante}/prato/{idPrato}")
	public void atualizarPrato(@PathParam("idRestaurante") Long idRestaurante, @PathParam("idPrato") Long idPrato,
			AtualizarPratoDto dto) {
		validaRestaurante(idRestaurante);

		Optional<Prato> opPrato = Prato.findByIdOptional(idPrato);
		if (opPrato.isEmpty())
			throw new NotFoundException();

		Prato manager = opPrato.get();
		this.pratoMapper.toPrato(dto, manager);

		manager.persist();
	}

	@DELETE
	@Transactional
	@Tag(name = "Prato")
	@Path("{idRestaurante}/prato/{id}")
	public void excluirPrato(@PathParam("idRestaurante") Long idRestaurante, @PathParam("id") Long idPrato) {
		validaRestaurante(idRestaurante);
		Optional<Prato> opPrato = Prato.findByIdOptional(idPrato);
		opPrato.ifPresentOrElse(Prato::delete, () -> {
			throw new NotFoundException();
		});
	}
}
