# Marketplace API
API para adicionar pratos ao carrinho e realizar pedidos.

### Tecnologia
- Quarkus 3.11.1
- Flyway
- PostgreSQL
- Kafka
- ActiveMQ Artemis

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
`http://localhost:8084/q/swagger-ui/`

#### ActiveMQ Artemis
`http://localhost:8161/`


