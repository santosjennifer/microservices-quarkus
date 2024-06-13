package com.github.ifood.provider;

import java.io.Serializable;
import java.util.Arrays;
import java.util.stream.Collectors;

import org.eclipse.microprofile.openapi.annotations.media.Schema;

import jakarta.validation.ConstraintViolation;

public class ConstraintViolationImpl implements Serializable {

	private static final long serialVersionUID = -7149990696969602696L;

	@Schema(description = "Path do atributo, ex: dataInicio, pessoa.endereco.numero", required = false)
	private final String atributo;

	@Schema(description = "Mensagem descritiva do erro possivelmente associado ao path ou o Json", required = true)
	private final String mensagem;

	public ConstraintViolationImpl(ConstraintViolation<?> violation) {
		this.mensagem = violation.getMessage();
		this.atributo = getAtributo(violation.getPropertyPath().toString());
	}

	public ConstraintViolationImpl(String atributo, String mensagem) {
		this.atributo = atributo;
		this.mensagem = mensagem;
	}

	public static ConstraintViolationImpl of(ConstraintViolation<?> violation) {
		return new ConstraintViolationImpl(violation);
	}

	public static ConstraintViolationImpl of(String violation) {
		return new ConstraintViolationImpl(null, violation);
	}

	private String getAtributo(String text) {
		return Arrays.stream(text.split("\\.")).skip(2).collect(Collectors.joining("."));
	}

	public String getAtributo() {
		return atributo;
	}

	public String getMensagem() {
		return mensagem;
	}

}
