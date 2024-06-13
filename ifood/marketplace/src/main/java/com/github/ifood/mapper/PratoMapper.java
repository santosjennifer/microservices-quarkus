package com.github.ifood.mapper;

import org.mapstruct.Mapper;

import com.github.ifood.dto.PratoDto;
import com.github.ifood.model.Prato;

@Mapper(componentModel = "cdi")
public interface PratoMapper {

	PratoDto toDto(Prato prato);
	
}
