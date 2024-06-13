package com.github.ifood.model;

import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;

import io.quarkus.hibernate.reactive.panache.PanacheEntityBase;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "prato_carrinho")
public class PratoCarrinho extends PanacheEntityBase {
	
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;

    public Long prato;

    public String cliente;
    
    @CreationTimestamp
    public LocalDateTime createdAt;

    public LocalDateTime finallyAt;
    
    public Boolean finalized = false;

    public PratoCarrinho() {}

    public PratoCarrinho(Long prato, String cliente) {
        this.prato = prato;
        this.cliente = cliente;
    }

}
