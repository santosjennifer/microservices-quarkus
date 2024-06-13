package com.github.ifood.dto;

import java.math.BigDecimal;

public class PratoPedidoDto {

    public String nome;
    public String descricao;
    public BigDecimal preco;
    public RestauranteDto restaurante;

    public PratoPedidoDto() {}

    public PratoPedidoDto(String nome, String descricao, BigDecimal preco, RestauranteDto restaurante) {
        this.nome = nome;
        this.descricao = descricao;
        this.preco = preco;
        this.restaurante = restaurante;
    }
    
}
