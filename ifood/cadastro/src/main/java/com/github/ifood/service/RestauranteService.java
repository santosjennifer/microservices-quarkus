package com.github.ifood.service;

import com.github.ifood.exception.CustomClientErrorException;
import com.github.ifood.model.Restaurante;

import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class RestauranteService {

    public Restaurante findRestauranteById(Long id, String proprietario) {
        return Restaurante.<Restaurante>find("id = ?1 and proprietario = ?2", id, proprietario)
                .firstResultOptional()
                .orElseThrow(() -> new CustomClientErrorException(String.format(
                        "Restaurante %s não encontrado ou proprietário %s inválido para acessar o restaurante.", id, proprietario)));
    }

}
