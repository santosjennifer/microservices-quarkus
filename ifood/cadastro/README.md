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
### Configurações para o primeiro acesso:

```
- Após subir os containers, acessar o Keycloak http://localhost:8081/, usuário e senha admin.
- No console, selecionar "ifood".
- Em Users, Create a New User, informar um username e firstname.
- Na aba Credentiais, criar uma senha para o usuário.
- Na aba Role mapping, clicar em Assign role e selecionar a role "proprietario".

- No link http://localhost:8081/realms/ifood, copiar a publicKey.
- Em application.yml do projeto, substituir pela nova publicKey.

- Acessar o Swagger http://localhost:8080/q/swagger-ui.
- Clicar em Autorize.
- Informar o usuário e senha criado no Keycloak, e no campo client_id informar "front-web-cadastro".
```

### Links Úteis

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
