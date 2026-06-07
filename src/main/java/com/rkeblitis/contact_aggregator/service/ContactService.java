package com.rkeblitis.contact_aggregator.service;

import com.rkeblitis.contact_aggregator.model.Contact;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;

@Service
public class ContactService {
    public List<Contact> getAllContacts() {
        return List.of(
            new Contact(
                1L, 
                "Mrs. Willian Bradtke", 
                "jerold@example.net", 
                "KENECT_LABS",
                Instant.parse("2020-06-24T19:37:16.688Z"),
                Instant.parse("2020-06-24T19:37:16.688Z")),
            new Contact(
                2L,
                "John Doe", 
                "johndoe@example.net", 
                "KENECT_LABS",
                Instant.parse("2021-02-10T11:10:09.987Z"),
                Instant.parse("2022-05-05T15:27:17.547Z"))
        );
    }
}