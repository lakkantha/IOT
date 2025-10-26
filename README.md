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
- H2 Database (for development)
- Maven
- Lombok

## Prerequisites

- JDK 17 or later
- Maven 3.6 or later
- Apache Kafka 3.4.1
- Docker (optional, for containerization)

## Getting Started

1. Clone the repository:
   ```bash
   git clone https://github.com/lakkantha/IOT.git
   cd IOT
   ```

2. Start Kafka (assuming Kafka is installed locally):
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
- Caching mechanisms
- Database sharding capabilities
- Load balancing support

## Monitoring and Management

- Health checks via Spring Actuator
- Metrics collection
- Performance monitoring
- Alert system for anomalies

## Contributing

Please read CONTRIBUTING.md for details on our code of conduct and the process for submitting pull requests.

## License

This project is licensed under the MIT License - see the LICENSE.md file for details