package com.github.ifood.dto;

import java.util.List;

import com.github.ifood.model.Localizacao;

public class PedidoDto {

    public String cliente;
    public List<PratoPedidoDto> pratos;
    public String entregador;
    public Localizacao localizacaoEntregador;
    
}
