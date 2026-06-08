package com.rkeblitis.contact_aggregator.service;

import com.rkeblitis.contact_aggregator.client.KenectLabsClient;
import com.rkeblitis.contact_aggregator.client.PageResult;
import com.rkeblitis.contact_aggregator.model.Contact;
import com.rkeblitis.contact_aggregator.model.KenectLabsContact;
import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.never;

class ContactServiceTest {
    @Test
    void mapsKenectLabsContactToContactAndSetsSource() {
        KenectLabsClient client = mock(KenectLabsClient.class);

        KenectLabsContact external = new KenectLabsContact(
            1L,
            "Mia Mia",
            "mia@mia.com",
            Instant.parse("2021-02-05T11:10:09.987Z"),
            Instant.parse("2026-06-08T15:27:17.547Z")
        );

        when(client.fetchPage(1)).thenReturn(new PageResult(List.of(external), 1));

        ContactService service = new ContactService(client);

        List<Contact> result = service.getAllContacts();

        assertThat(result).hasSize(1);
        Contact contact = result.get(0);
        assertThat(contact.id()).isEqualTo(1L);
        assertThat(contact.name()).isEqualTo("Mia Mia");
        assertThat(contact.email()).isEqualTo("mia@mia.com");
        assertThat(contact.source()).isEqualTo("KENECT_LABS");   
        assertThat(contact.createdAt()).isEqualTo(Instant.parse("2021-02-05T11:10:09.987Z"));
        assertThat(contact.updatedAt()).isEqualTo(Instant.parse("2026-06-08T15:27:17.547Z"));
    }

    @Test
    void fetchesAllPagesAndCombinesThem() {
        KenectLabsClient client = mock(KenectLabsClient.class);

        KenectLabsContact one = new KenectLabsContact(
            1L, 
            "Page one Person", 
            "one@example.com",
            Instant.parse("2021-01-01T00:00:00.000Z"),
            Instant.parse("2021-01-01T00:00:00.000Z"));

        KenectLabsContact two = new KenectLabsContact(
            2L, 
            "Page Two Person", 
            "two@example.com",
            Instant.parse("2021-02-02T00:00:00.000Z"),
            Instant.parse("2021-02-02T00:00:00.000Z"));

        when(client.fetchPage(1)).thenReturn(new PageResult(List.of(one), 2));
        when(client.fetchPage(2)).thenReturn(new PageResult(List.of(two), 2));

        ContactService service = new ContactService(client);

        List<Contact> result = service.getAllContacts();

        assertThat(result).hasSize(2);
        assertThat(result).extracting(Contact::id).containsExactly(1L, 2L);

        verify(client).fetchPage(1);
        verify(client).fetchPage(2);
    }

    @Test
    void doesNotFetchBeyondTheLastPage() {
        KenectLabsClient client = mock(KenectLabsClient.class);

        KenectLabsContact only = new KenectLabsContact(
            1L, 
            "Only Person", 
            "only@example.com",
            Instant.parse("2021-01-01T00:00:00.000Z"),
            Instant.parse("2021-01-01T00:00:00.000Z"));

        when(client.fetchPage(1)).thenReturn(new PageResult(List.of(only), 1));

        ContactService service = new ContactService(client);

        List<Contact> result = service.getAllContacts();

        assertThat(result).hasSize(1);
        verify(client).fetchPage(1);
        verify(client, never()).fetchPage(2);
    }

    @Test
    void throwsErrorWhenClientFails() {
        KenectLabsClient client = mock(KenectLabsClient.class);

        when(client.fetchPage(1)).thenThrow(new RuntimeException("downstream unavailable"));

        ContactService service = new ContactService(client);

        assertThatThrownBy(() -> service.getAllContacts())
                .isInstanceOf(RuntimeException.class);
    }

    @Test
    void returnsEmptyListWhenNoContacts() {
        KenectLabsClient client = mock(KenectLabsClient.class);

        when(client.fetchPage(1)).thenReturn(new PageResult(List.of(), 1));

        ContactService service = new ContactService(client);

        List<Contact> result = service.getAllContacts();

        assertThat(result).isEmpty();
    }
}
