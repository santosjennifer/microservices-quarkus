package com.github.ifood.mapper;

import com.github.ifood.dto.PratoPedidoDto;
import com.github.ifood.model.Prato;
import com.github.ifood.model.PratoCarrinho;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(componentModel = "cdi")
public interface PratoCarrinhoMapper {

	@Mappings({ @Mapping(source = "id", target = "prato") })
	PratoCarrinho to(Prato prato);

	@Mappings({ 
			@Mapping(source = "nome", target = "nome"), 
			@Mapping(source = "descricao", target = "descricao"),
			@Mapping(source = "preco", target = "preco"),
			@Mapping(source = "restaurante.nome", target = "restaurante.nome") })
	PratoPedidoDto fromPratoPedidoDto(Prato prato);

}
