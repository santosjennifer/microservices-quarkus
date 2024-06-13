package com.github.ifood.provider;

import jakarta.ws.rs.ClientErrorException;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

@Provider
public class ClientErrorExceptionMapper implements ExceptionMapper<ClientErrorException> {

	@Override
	public Response toResponse(ClientErrorException exception) {
		return Response.status(Response.Status.FORBIDDEN).entity(exception.getMessage()).build();
	}

}
