package com.github.ifood.dto;

import java.util.List;

public class PedidoRealizadoDto {
	
    public List<PratoPedidoDto> pratos;
    public String cliente;

    public PedidoRealizadoDto() {}

    public PedidoRealizadoDto(List<PratoPedidoDto> pratos, String cliente) {
        this.pratos = pratos;
        this.cliente = cliente;
    }

}
