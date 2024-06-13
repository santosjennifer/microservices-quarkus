package com.github.ifood.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Mappings;

import com.github.ifood.dto.restaurante.AdicionarRestauranteDto;
import com.github.ifood.dto.restaurante.AtualizarRestauranteDto;
import com.github.ifood.dto.restaurante.RestauranteDto;
import com.github.ifood.model.Restaurante;

@Mapper(componentModel = "cdi")
public interface RestauranteMapper {

    @Mappings({
            @Mapping(target = "nome", source = "nome"),
            @Mapping(target = "id", ignore = true),
            @Mapping(target = "dataCriacao", ignore = true),
            @Mapping(target = "dataAtualizacao", ignore = true),
            @Mapping(target = "localizacao.id", ignore = true)
    })
    Restaurante toRestaurante(AdicionarRestauranteDto dto);

    @Mappings({
            @Mapping(source = "nome", target = "nome"),
            @Mapping(target = "dataCriacao", dateFormat = "dd/MM/yyyy HH:mm:ss")
    })
    RestauranteDto toRestauranteDto(Restaurante restaurante);

    @Mappings({
            @Mapping(target = "nome", source = "nome"),
    })
    public void toRestaurante(AtualizarRestauranteDto dto, @MappingTarget Restaurante restaurante);

}
