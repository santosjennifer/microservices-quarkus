package com.github.ifood.exception;

import jakarta.ws.rs.ClientErrorException;
import jakarta.ws.rs.core.Response;

public class CustomClientErrorException extends ClientErrorException {
	
	private static final long serialVersionUID = 1L;

	public CustomClientErrorException(String message) {
        super(message, Response.Status.BAD_REQUEST);
    }
    
}
