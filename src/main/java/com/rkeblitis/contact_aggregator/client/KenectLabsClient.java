package com.rkeblitis.contact_aggregator.client;

import com.rkeblitis.contact_aggregator.model.KenectLabsContact;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.ResponseEntity;

import java.util.List;

@Component
public class KenectLabsClient {

    private final RestClient restClient;

    public KenectLabsClient(RestClient restClient) {
        this.restClient = restClient;
    }

    public PageResult fetchPage(int page) {
        ResponseEntity<List<KenectLabsContact>> response = restClient.get()
            .uri("/api/v1/contacts?page={page}", page)
            .retrieve()
            .toEntity(new ParameterizedTypeReference<List<KenectLabsContact>>() {}); 

        List<KenectLabsContact> contacts = response.getBody();
        int totalPages = Integer.parseInt(response.getHeaders().getFirst("total-pages"));

        return new PageResult(contacts, totalPages);     
    }
}