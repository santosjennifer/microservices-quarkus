package com.github.ifood.deserializer;

import com.github.ifood.dto.PedidoRealizadoDto;

import io.quarkus.kafka.client.serialization.ObjectMapperDeserializer;

public class PedidoDeserializer extends ObjectMapperDeserializer<PedidoRealizadoDto> {

    public PedidoDeserializer() {
        super(PedidoRealizadoDto.class);
    }
    
}
