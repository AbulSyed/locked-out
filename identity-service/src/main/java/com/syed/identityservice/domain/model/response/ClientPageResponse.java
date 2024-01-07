package com.syed.identityservice.domain.model.response;

import lombok.*;

import java.util.List;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class ClientPageResponse {

    private List<ClientResponse> clients;
    private int page;
    private int size;
    private long totalElements;
    private int totalPages;
    private boolean lastPage;
}
