package com.github.ifood.dto.prato;

import java.math.BigDecimal;

import com.github.ifood.dto.restaurante.RestauranteDto;

public class PratoDto {

    public Long id;
    public String nome;
    public String descricao;
    public RestauranteDto restaurante;
    public BigDecimal preco;
    
}
