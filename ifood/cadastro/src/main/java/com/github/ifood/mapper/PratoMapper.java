package com.github.ifood.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import com.github.ifood.dto.prato.AdicionarPratoDto;
import com.github.ifood.dto.prato.AtualizarPratoDto;
import com.github.ifood.dto.prato.PratoDto;
import com.github.ifood.model.Prato;

@Mapper(componentModel = "cdi")
public interface PratoMapper {
	
    PratoDto toDto(Prato prato);
    Prato toPrato(AdicionarPratoDto dto);
    void toPrato(AtualizarPratoDto dto, @MappingTarget Prato prato);

}
