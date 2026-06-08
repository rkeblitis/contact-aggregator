package com.rkeblitis.contact_aggregator.controller;

import com.rkeblitis.contact_aggregator.model.Contact;
import com.rkeblitis.contact_aggregator.service.ContactService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.time.Instant;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ContactController.class)
class ContactControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private ContactService contactService;

    @Test
    void getContactsReturnsJsonMatchingSchema() throws Exception {
        Contact contact = new Contact(
            1L, 
            "Mia Mia", 
            "mia@mia.com", 
            "KENECT_LABS",
            Instant.parse("2021-02-05T11:10:09.987Z"),
            Instant.parse("2026-06-08T15:27:17.547Z"));

        when(contactService.getAllContacts()).thenReturn(List.of(contact));

        mockMvc.perform(get("/contacts"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$[0].id").value(1))
            .andExpect(jsonPath("$[0].name").value("Mia Mia"))
            .andExpect(jsonPath("$[0].email").value("mia@mia.com"))
            .andExpect(jsonPath("$[0].source").value("KENECT_LABS"));
    }
}