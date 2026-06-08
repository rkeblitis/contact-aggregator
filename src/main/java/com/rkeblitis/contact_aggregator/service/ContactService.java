package com.rkeblitis.contact_aggregator.service;

import com.rkeblitis.contact_aggregator.client.KenectLabsClient;
import com.rkeblitis.contact_aggregator.model.Contact;
import com.rkeblitis.contact_aggregator.model.KenectLabsContact;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ContactService {
    
    private static final String SOURCE = "KENECT_LABS";

    private final KenectLabsClient kenectLabsClient;

    public ContactService(KenectLabsClient kenectLabsClient) {
        this.kenectLabsClient = kenectLabsClient;
    }

    public List<Contact> getAllContacts() {
        List<KenectLabsContact> externalContacts = kenectLabsClient.fetchPage(1);

        return externalContacts.stream()
            .map(this::toContact)
            .toList();
    }

    private Contact toContact(KenectLabsContact external) {
        return new Contact(
            external.id(),
            external.name(),
            external.email(),
            SOURCE,
            external.createdAt(),
            external.updatedAt()
        );
    }
}