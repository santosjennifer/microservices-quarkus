# Cadastro API
API para cadastro de restaurantes e pratos.

### Tecnologia
- Quarkus 3.11.1
- Flyway
- PostgreSQL
- Jaeger
- ActiveMQ Artemis
- Keycloak
- Prometheus
- Grafana

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
`http://localhost:8083/q/swagger-ui/`

#### Jaeger
`http://localhost:16686/search`

#### ActiveMQ Artemis
`http://localhost:8161/`

#### Keycloak
`http://localhost:8081/`

#### Prometheus
`http://localhost:9090/graph`

#### Grafana
`http://localhost:3000/`
