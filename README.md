# IoT Data Processing System

Maintainer: Lakkantha

A scalable system for processing and analyzing data from IoT devices, built with Spring Boot and Apache Kafka.

## System Architecture

This system is designed to handle data from over 100,000 IoT devices with the following components:

1. **Data Ingestion Layer**
   - Apache Kafka for high-throughput data streaming
   - Scalable message consumers for parallel processing
   - Device authentication and validation

2. **Processing Layer**
   - Real-time data processing and analysis
   - Batch processing capabilities
   - Data aggregation and statistical analysis

3. **Storage Layer**
   - Scalable database architecture
   - Data partitioning for efficient querying
   - Historical data management

4. **API Layer**
   - RESTful endpoints for data access
   - Device management interface
   - Real-time monitoring and metrics

## Technologies Used

- Java 17
- Spring Boot 3.1.5
- Apache Kafka
- Spring Data JPA
- Spring Async (ThreadPoolTaskExecutor, @Async)
- H2 Database (for development)
- Maven
- Lombok

## Prerequisites

- JDK 17 (exact); Maven toolchains configured to use JDK 17
- Maven 3.6 or later
- Apache Kafka 3.4.1 (or use docker-compose provided)
- Docker (optional, for containerization)

### Maven Toolchains (required)
This project enforces JDK 17 via Maven Toolchains. Ensure you have a JDK 17 installed and a toolchains file at `%UserProfile%\.m2\toolchains.xml` similar to:

```
<toolchains>
   <toolchain>
      <type>jdk</type>
      <provides>
         <version>[17,18)</version>
         <vendor>any</vendor>
      </provides>
      <configuration>
         <jdkHome>C:\Program Files\Eclipse Adoptium\jdk-17.0.x-hotspot</jdkHome>
      </configuration>
   </toolchain>
   </toolchains>
```

## Getting Started

1. Clone the repository:
   ```bash
   git clone https://github.com/lakkantha/IOT.git
   cd IOT
   ```

2. Start Kafka (choose one):

   - Using Docker Compose (recommended):
     - `docker compose up -d` to start Zookeeper, Kafka, and the app containerized
     - Or start only infra: `docker compose up -d zookeeper kafka`

   - Installed locally:
   ```bash
   # Start Zookeeper
   zookeeper-server-start.bat config/zookeeper.properties

   # Start Kafka
   kafka-server-start.bat config/server.properties
   ```

3. Build the application:
   ```bash
   mvn clean install
   ```

4. Run the application:
   ```bash
   mvn spring-boot:run
   ```

   On Windows PowerShell, you can also use the helper script:

   ```powershell
   ./run-local.ps1
   ```

## Tuning Async Executor
The async executor is configurable via application.properties. Defaults are reasonable for local dev:

```
app.async.core-pool-size=8
app.async.max-pool-size=32
app.async.queue-capacity=1000
app.async.thread-name-prefix=io-exec-
app.async.await-termination-seconds=30
app.async.wait-for-tasks-to-complete-on-shutdown=true
```

## Project Structure

```
src/main/java/com/iot/
├── config/         # Configuration classes
├── controller/     # REST controllers
├── model/         # Domain models and DTOs
├── repository/    # Data access layer
└── service/      # Business logic and services
```

## API Documentation

### Device Registration
```
POST /api/devices
Content-Type: application/json

{
    "deviceId": "string",
    "deviceType": "string",
    "location": {
        "latitude": number,
        "longitude": number
    }
}
```
Responses:
- 200 OK with created device
- 409 Conflict if deviceId already exists

### Update Device Status
```
PUT /api/devices/{deviceId}/status
Query Parameters:
   status=ACTIVE|INACTIVE|MAINTENANCE|ERROR

Responses:
   200 OK
   404 Not Found (if device doesn't exist)
```

### Data Ingestion
```
POST /api/data
Content-Type: application/json

{
    "deviceId": "string",
    "timestamp": "string",
    "measurements": {
        "key": "value"
    }
}
```

## Scalability Features

- Horizontal scaling through Kafka partitioning
- Stateless application design
- Asynchronous processing via dedicated thread pool (@Async)
- Caching mechanisms
- Database sharding capabilities
- Load balancing support

## Concurrency & Consistency

- Async execution: `@EnableAsync` with `ThreadPoolTaskExecutor` bean `ioTaskExecutor`.
- Non-blocking publish: `AsyncNotificationService` uses `@Async("ioTaskExecutor")` to publish Kafka messages.
- Optimistic locking: `@Version` field added in `BaseEntity` to prevent lost updates under contention.
- Duplicate registrations: Unique constraint on `Device.deviceId` mapped to HTTP 409 Conflict.

## Monitoring and Management

- Health checks via Spring Actuator
- Metrics collection
- Performance monitoring
- Alert system for anomalies

## Contributing

Please read CONTRIBUTING.md for details on our code of conduct and the process for submitting pull requests.

## License

This project is licensed under the MIT License - see the LICENSE.md file for details