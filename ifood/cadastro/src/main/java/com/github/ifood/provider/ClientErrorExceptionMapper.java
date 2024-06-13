package com.github.ifood.provider;

import com.github.ifood.exception.CustomClientErrorException;

import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

@Provider
public class ClientErrorExceptionMapper implements ExceptionMapper<CustomClientErrorException> {

    @Override
    public Response toResponse(CustomClientErrorException exception) {
        return Response.status(exception.getResponse().getStatus())
                       .entity(exception.getMessage())
                       .build();
    }

}
