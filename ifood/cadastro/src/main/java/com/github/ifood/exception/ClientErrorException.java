package com.github.ifood.exception;

import jakarta.ws.rs.WebApplicationException;

public class ClientErrorException extends WebApplicationException {

	private static final long serialVersionUID = 6448682634753330420L;

    public ClientErrorException(String message) {
        super(message);
    }
    
}
