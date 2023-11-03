package com.syed.authservice.filter;

import com.syed.authservice.domain.model.response.ClientResponse;

public class ClientContextHolder {

    private static final ThreadLocal<ClientResponse> clientResponseHolder = new ThreadLocal<>();

    public static void setClientResponse(ClientResponse clientResponse) {
        clientResponseHolder.set(clientResponse);
    }

    public static ClientResponse getClientResponse() {
        return clientResponseHolder.get();
    }
}
