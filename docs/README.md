# IoT Data Processing System - Diagrams

This folder contains architecture and data flow diagrams for the IoT Data Processing System.

- `architecture.png`: High-level system architecture
- `dataflow.png`: Data flow from device to analytics

use  https://mermaid.live/ for digram created.

---

## Deployment Customization

- **Ports**: Change exposed ports in `docker-compose.yml` and `application.properties` as needed (e.g., 8080 for app, 9092 for Kafka).
- **Environment Variables**: Set custom Kafka or DB connection strings in `docker-compose.yml` or pass them to the app container.
- **Database**: For production, replace H2 with PostgreSQL/MySQL and update `application.properties` accordingly.
- **Scaling**: Adjust Kafka partitions/replicas in `KafkaConfig.java` and `docker-compose.yml` for higher throughput.
- **Scripts**: Use `run-local.ps1` for Windows or `run-local.sh` for Linux/Mac to build and run locally.

---


---

## Cloud Deployment & Automation

- **GitHub Actions**: See `.github/workflows/ci-cd.yml` for automated build, test, and Docker image creation.
- **Kubernetes**: See `k8s/iot-app-deployment.yaml` for scalable cloud-native deployment.
- **AWS/Azure/GCP**: Use managed Kubernetes (EKS, AKS, GKE) or cloud container services. Update image repository and environment variables as needed.
- **Secrets & Config**: Use cloud secret managers or Kubernetes secrets for sensitive data.
- **Scaling**: Adjust replica count and resource limits in Kubernetes manifests for production workloads.

For more details, see the main `README.md` and `DESIGN.md` files.

---

**Sample Diagram Descriptions:**

- `architecture.png`: Shows IoT devices, Kafka, Spring Boot app, database, and API clients.
- `dataflow.png`: Illustrates the flow of data from device registration and sensor data ingestion through Kafka, processing, storage, and analytics retrieval.
