package com.github.ifood.model;

import io.quarkus.hibernate.reactive.panache.PanacheEntityBase;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "restaurante")
public class Restaurante extends PanacheEntityBase {
	
	@Id
	public Long id;
	
	public String nome;
	
    @OneToOne(cascade = CascadeType.ALL)
    public Localizacao localizacao;
    
    public String cnpj;

}
