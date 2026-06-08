package com.rkeblitis.contact_aggregator.client;

import com.rkeblitis.contact_aggregator.model.KenectLabsContact;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.web.client.RestClient;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.header;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.method;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;
import static org.springframework.http.HttpMethod.GET;

class KenectLabsClientTest {
    private RestClient.Builder builder;
    private MockRestServiceServer server;
    private KenectLabsClient client;

    @BeforeEach
    void setUp() {
        builder = RestClient.builder();
        server = MockRestServiceServer.bindTo(builder).build();
        client = new KenectLabsClient(builder.build());
    }

    @Test
    void fetchesPageOneWithAuthHeaderAndReadsTotalPages() {
        String body = """
                [
                  {
                    "id": 1,
                    "name": "Mia Mia",
                    "email": "mia@mia.com",
                    "createdAt": "2021-02-05T11:10:09.987Z",
                    "updatedAt": "2026-06-08T15:27:17.547Z"
                  }
                ]
                """;

        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.add("total-pages", "2");

        server.expect(requestTo("/api/v1/contacts?page=1"))
                .andExpect(method(GET))
                .andRespond(withSuccess(body, MediaType.APPLICATION_JSON).headers(responseHeaders));

        PageResult result = client.fetchPage(1);

        assertThat(result.totalPages()).isEqualTo(2);
        assertThat(result.contacts()).hasSize(1);
        assertThat(result.contacts().get(0).email()).isEqualTo("mia@mia.com");

        server.verify();
    }

}
