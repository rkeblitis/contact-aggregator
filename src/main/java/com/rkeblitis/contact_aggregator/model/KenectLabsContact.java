package com.rkeblitis.contact_aggregator.model;

import java.time.Instant;

public record KenectLabsContact(
    Long id,
    String name,
    String email,
    Instant createdAt,
    Instant updatedAt
) {}