# Pedido API
API que registra os pedidos recebidos do marketplace.

### Tecnologia
- Quarkus 3.11.1
- MongoDB
- Kafka
- ElasticSearch
- Kibana

### Iniciando a aplicação:

Para instalar as dependências:

```shell script
docker-compose up
```

Para compilar a aplicação:

```shell script
mvn compile
```
Para iniciar a aplicação:

```shell script
mvn quarkus:dev
```

#### Swagger
`http://localhost:8085/q/swagger-ui/`

#### Kibana
`http://localhost:5601`