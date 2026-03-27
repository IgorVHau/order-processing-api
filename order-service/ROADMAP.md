# Project Roadmap

## Phase 1 — Service Foundation
✔ Configure Spring Boot service structure  
✔ Implement layered architecture (Controller, Service, Repository)  
✔ Define Order domain model

## Phase 2 — Containerization
✔ Create Dockerfile  
✔ Document how to run the service with Docker

## Phase 3 — Event-Driven Architecture
✔ Introduce domain events (e.g., OrderCreatedEvent)  
✔ Publish events to message broker (SQS)

## Phase 4 — AWS Integration
✔ Integrate with AWS SQS 
✔ Implement event producer

## Phase 5 — Resilience & Reliability
⬜ Implement retry with backoff  
⬜ Configure Dead Letter Queue (DLQ)  
⬜ Improve error handling and logging  
⬜ Ensure idempotent consumer behavior
