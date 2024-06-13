package com.github.ifood.dto;

import java.math.BigDecimal;

import com.github.ifood.model.Restaurante;

public class PratoDto {
	
    public Long id;
    public String nome;
    public String descricao;
    public Restaurante restaurante;
    public BigDecimal preco;
    
    public PratoDto() {}

    public PratoDto(Long id, String nome, String descricao, Restaurante restaurante, BigDecimal preco) {
        this.id = id;
        this.nome = nome;
        this.descricao = descricao;
        this.restaurante = restaurante;
        this.preco = preco;
    }

}
