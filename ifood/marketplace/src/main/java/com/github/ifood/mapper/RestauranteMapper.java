package com.github.ifood.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import com.github.ifood.dto.AdicionarRestauranteDto;
import com.github.ifood.model.Restaurante;

@Mapper(componentModel = "cdi")
public interface RestauranteMapper {

	@Mappings({ 
		    @Mapping(source = "id", target = "id"), 
		    @Mapping(source = "nome", target = "nome"),
			@Mapping(source = "cnpj", target = "cnpj"),
			@Mapping(source = "localizacao.longitude", target = "localizacao.longitude"),
			@Mapping(source = "localizacao.latitude", target = "localizacao.latitude") })
	Restaurante toRestaurante(AdicionarRestauranteDto restauranteDto);
}
