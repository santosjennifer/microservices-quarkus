package com.github.ifood.service;

import com.github.ifood.exception.ClientErrorException;
import com.github.ifood.model.Restaurante;

import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class RestauranteService {

	public Restaurante findRestauranteById(Long id, String proprietario) {
		return Restaurante.<Restaurante>find("id = ?1 and proprietario = ?2", id, proprietario).firstResultOptional()
				.orElseThrow(() -> new ClientErrorException(String.format(
						"Restaurante %s não encontrado ou %s inválido para acessar o restraurante", id, proprietario)));
	}

}
