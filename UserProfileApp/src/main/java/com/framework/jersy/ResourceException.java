package com.framework.jersy;

import com.sun.jersey.api.client.ClientResponse;

public class ResourceException extends RuntimeException {
	private static final long serialVersionUID = 5186901780596056951L;
	private ClientResponse clientResponse;
	
	public ResourceException(ClientResponse clientResponse) {
		this.clientResponse = clientResponse;
	}
	
	public ClientResponse getClientResponse() {
		return clientResponse;
	}
}
