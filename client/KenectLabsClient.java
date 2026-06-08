package com.rkeblitis.contact_aggregator.client;

import com.rkeblitis.contact_aggregator.model.KenectLabsContact;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;
import org.springframework.core.ParameterizedTypeReference;

import java.util.List;

@Component
public class KenectLabsClient {

    private final RestClient restClient;

    public KenectLabsClient(RestClient restClient) {
        this.restClient = restClient;
    }

    public <List>KenectLabsContact> fetchPage(int page) {
        return restClient.get()
            .uri("/api/vi/contacts?page={page}", page)
            .retrieve()
            .body(new ParameterizedTypeReference<List<KenectLabsContact>>() {});
    }
}