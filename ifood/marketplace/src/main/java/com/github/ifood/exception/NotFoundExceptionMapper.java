package com.github.ifood.exception;

import jakarta.ws.rs.NotFoundException;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.UriInfo;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

@Provider
public class NotFoundExceptionMapper implements ExceptionMapper<NotFoundException> {

	@Context
	UriInfo uriInfo;

	@Override
	@Produces(MediaType.APPLICATION_JSON)
	public Response toResponse(NotFoundException e) {
		StandardHandler standardHandler = new StandardHandler(
				e.getMessage(), 
				uriInfo.getPath(),
				System.currentTimeMillis(), 
				Response.Status.NOT_FOUND.getStatusCode());

		return Response.status(Response.Status.NOT_FOUND).entity(standardHandler).build();
	}

}
