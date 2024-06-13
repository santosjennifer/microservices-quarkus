package com.github.ifood.exception;

import jakarta.ws.rs.WebApplicationException;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

public class PedidoNotFoundException extends WebApplicationException {
	
	private static final long serialVersionUID = -6052905565497053710L;

	public PedidoNotFoundException(String message) {
        super(Response.status(Response.Status.NOT_FOUND)
                      .entity(message)
                      .type(MediaType.TEXT_PLAIN)
                      .build());
    }

}
