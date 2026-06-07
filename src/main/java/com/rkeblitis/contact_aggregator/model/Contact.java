package com.rkeblitis.contact_aggregator.model;

import java.time.Instant;

public record Contact(
    Long id,
    String name,
    String email,
    String source,
    Instant createdAt,
    Instant updatedAt
) {}