CREATE TABLE restaurante (
	id int8 NOT NULL GENERATED BY DEFAULT AS IDENTITY,
	cnpj varchar(255) NULL,
	dataatualizacao timestamp NULL,
	datacriacao timestamp NULL,
	nome varchar(255) NULL,
	proprietario varchar(255) NULL,
	localizacao_id int8 NULL,
	CONSTRAINT restaurante_pkey PRIMARY KEY (id)
);

ALTER TABLE restaurante ADD CONSTRAINT fkdfbggt9ievc4ev74wl3tdnscl FOREIGN KEY (localizacao_id) REFERENCES localizacao(id);
