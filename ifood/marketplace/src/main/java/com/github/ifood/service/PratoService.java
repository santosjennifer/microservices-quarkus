package com.github.ifood.service;

import java.util.List;

import com.github.ifood.model.Prato;
import com.github.ifood.model.Restaurante;

import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.ws.rs.NotFoundException;


@ApplicationScoped
public class PratoService {
	
	public Uni<List<Prato>> getPratoAll() {
	    return Prato.<Prato>listAll();
	}

	public Multi<Prato> getPratoByRestaurante(Restaurante restaurante) {
	    return Prato.<Prato>list("restaurante", restaurante)
	            .onItem().transformToMulti(pratos -> Multi.createFrom().iterable(pratos));
	}
	
    public Uni<Prato> findPratoById(Long idPrato) {
        return Prato.findById(idPrato)
                    .onItem().ifNotNull().transform(prato -> (Prato) prato)
                    .onItem().ifNull().failWith(() -> new NotFoundException("Prato não encontrado: " + idPrato));
    }
}
