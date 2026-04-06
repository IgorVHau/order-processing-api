<h1 align="center"> order-processing-api </h1>

<p align="center">
Event-driven microservices for order processing using Spring Boot, AWS SQS and Docker.<br>
Focused on asynchronous communication and distributed systems concepts.
</p>

<p align="center">
    <img src="https://img.shields.io/badge/Java-21-red?labelColor=blue"/>
	<img src="https://img.shields.io/badge/Spring_Boot-4.x-brightgreen">
</p>
<p align="center">
    <img src="https://img.shields.io/badge/Status-In_Progress-orange"/>
</p>


<details>
    <summary>📚 <b> Table of Contents </b> </summary>

- [Tech stack](#tech-stack)
- [Architecture](#architecture)
- [Features](#features)
- [Reliability](#reliability)
- [Trade-offs](#trade-offs)
- [AWS Configuration](#aws-configuration)
- [Running locally](#running-locally)
- [Roadmap](#roadmap)
- [Goal](#goal)

</details>


## Tech Stack
- Java 21
- Spring Boot
- AWS SQS
- Docker / Docker Compose
- H2 Database

## Architecture

```mermaid
flowchart LR

A[<b>order-service</b>]:::app
B([SQS]):::aws
C[<b>payment-service</b>]:::app
D[(processed_events)]:::db
E[Business Logic]:::app
F([DLQ]):::aws

A e1@-->|Outbox<br> Publisher| B
B e2@-->|SqsListener| C
C -->|Idempotency<br> Check| D
C -->|Process<br> Payment| E
E e3@-->|Fail after<br> retries| F

click A "https://github.com/IgorVHau/order-processing-api/tree/main/order-service" _blank
click C "https://github.com/IgorVHau/order-processing-api/tree/main/payment-service" _blank

classDef app stroke: #ededed, stroke-width: 2px, color: #ededed, fill: #232f3e
classDef aws stroke: #ff9900, stroke-width: 2px
classDef db stroke: #3b48cc,stroke-width: 2px

linkStyle 0,1,2,3 font-size: 14px
linkStyle 4 stroke:#ff0000, stroke-width: 2px, font-size: 14px

e1@{ animation: fast }
e2@{ animation: fast }
e3@{ animation: slow }

```

### Flow

1. Client sends a request to order-service
2. Order is created and persisted
3. An OrderCreatedEvent is published to SQS
4. payment-service consumes the event
5. Payment is processed asynchronously by payment-service

## Features

- Asynchronous communication via AWS SQS
- Microservices architecture (order-service and payment-service)
- Idempotent message processing
- Dockerized services with docker-compose
- End-to-end event flow validated

## Reliability

- Transactional Outbox Pattern
- Idempotent consumer with processed_events table
- Retry with exponential backoff
- Dead Letter Queue (DLQ) for failed message handling after max retries

## Trade-offs

- H2 database is used for simplicity in local development (not production-ready)
- Payment processing is simulated (no real external integration)
- DLQ messages are not automatically reprocessed yet (manual intervention required)

## AWS Configuration

This project requires AWS credentials to access SQS. 

Make sure you have [AWS CLI](https://docs.aws.amazon.com/pt_br/cli/latest/userguide/getting-started-quickstart.html) installed, then run: 

```bash
aws configure
```

Provide the following information when prompted:

- AWS Access Key ID: `your_access_key`
- AWS Secret Access Key: `your_secret_key`
- Default region name: `sa-east-1`
- Default output format: `json`

> [!NOTE]
> Credentials must be stored under the `default` profile to be automatically detected.


##  Running locally

### Requirements

- Docker (Docker Desktop recommended for Windows/Mac)
- AWS credentials configured [(see AWS Configuration section)](#aws-configuration)

### Start services


```bash
docker compose up --build
```

> [!TIP]
> To run in the background and keep your terminal free, use `docker compose up -d --build`.

After starting, services will be available at:

- order-service → http://localhost:8081
- payment-service → (no public HTTP endpoints)

Orders can be created using curl or an API Client.

### Using curl

```bash
curl -X POST http://localhost:8081/orders \
-H "Content-Type: application/json" \
-d '{"customerName":"John Doe","amount":100}'
```

### Using API Client

- *URL:* `http://localhost:8081/orders`
- *HTTP Method:* `POST`
- *Content-Type:* `application/json`
- *Request body (example):*
```json
	{
	    "customerName": "John Doe",
	    "amount": 100
	}
```

> [!WARNING] 
> This project intentionally simulates failures for demonstration purposes. 
> 
> If "amount > 100", the payment-service will throw an exception.
> The message will be retried 3 times with exponential backoff.
> After that, it will be sent to the Dead Letter Queue (DLQ).

> [!TIP]
> Try sending different values to check the system behavior:
>
>
> `amount <= 100` → processed successfully
>
> `amount > 100` → retries + DLQ


## Roadmap

See [ROADMAP.md](./ROADMAP.md)

## Goal

Study and implement event-driven architecture concepts using AWS and Spring ecosystem.
