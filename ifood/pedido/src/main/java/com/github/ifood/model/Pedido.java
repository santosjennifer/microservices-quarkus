package com.github.ifood.model;

import java.util.List;

import io.quarkus.mongodb.panache.PanacheMongoEntity;
import io.quarkus.mongodb.panache.common.MongoEntity;

@MongoEntity(collection = "pedidos", database = "pedido")
public class Pedido extends PanacheMongoEntity {
	
    public String cliente;
    public List<Prato> pratos;
    public String entregador;
    public Localizacao localizacaoEntregador;

}
