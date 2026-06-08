# Contact Aggregator

A read-only service that collects all contacts from the Kenect Labs API, normalizes them to a common model, and serves them at `GET /contacts`.

## Running it

Run: `./mvnw spring-boot:run`

Call it: `curl http://localhost:8080/contacts`

- Requires JDK 21
- The auth token reads from the `KENECT_AUTH_TOKEN` env var, with a default so it runs with no setup

## Design

Layered flow: `GET /contacts` → controller → service → Kenect Labs client.

Two models: `KenectLabsContact` matches the upstream API's schema; `Contact` is what `/contacts` returns. A mapper translates between them.

Pagination: the service pages through all results and combines them into one list.

## Testing

Run tests with `./mvnw test`. Service logic is unit-tested, the client is tested against a mock HTTP server, and a controller test covers the JSON contract.

## A note on pagination

The spec describes RFC8288 Link headers, but I tested the live endpoint and it returns Current-Page/Total-Pages with no Link header, so I implemented against the live behavior.
