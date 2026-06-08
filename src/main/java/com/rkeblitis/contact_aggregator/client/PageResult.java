package com.rkeblitis.contact_aggregator.client;

import com.rkeblitis.contact_aggregator.model.KenectLabsContact;

import java.util.List;

public record PageResult(List<KenectLabsContact> contacts, int totalPages) {}