# How to Create Architecture and Data Flow Diagrams

## Option 1: Using Online Mermaid Renderer (Quickest)

1. Visit https://mermaid.live/
2. Copy the content from `architecture.mmd` or `dataflow.mmd`
3. Paste into the editor
4. Click "Download PNG" or "Download SVG"
5. Save as `architecture.png` or `dataflow.png`

## Option 2: Using VS Code Extension

1. Install "Markdown Preview Mermaid Support" extension
2. Create a markdown file and embed the mermaid code:
   ```markdown
   ```mermaid
   [paste content from .mmd file]
   ```
   ```
3. Right-click the preview and save as image

## Option 3: Using Lucidchart (Professional Quality)

### Architecture Diagram (`architecture.png`)

**Layers (Top to Bottom):**

1. **IoT Devices Layer**
   - Add 3 rectangles: "IoT Device 1", "IoT Device 2", "IoT Device N..."
   - Use device/sensor icons

2. **Message Queue Layer**
   - Add Kafka cluster (use database cylinder icon)
   - Add 2 queue icons: "sensor-data topic", "device-registration topic"

3. **Spring Boot Application** (Group in container)
   
   a. **API Layer - Facade Pattern** (group)
      - Rectangle: "IoTFacade" (highlight in red/pink)
      - Rectangles: "DeviceController", "SensorDataController", "AnalyticsController"
   
   b. **Service Layer** (group)
      - Rectangles: "DeviceService", "SensorDataProcessingService"
      - Rectangles: "KafkaProducerService", "KafkaConsumerService"
   
   c. **Configuration - Singleton Pattern** (group)
      - Rectangle: "ApplicationConfig (Singleton)" (highlight in green)
      - Rectangle: "KafkaConfig"
   
   d. **Repository Layer** (group)
      - Rectangles: "DeviceRepository", "SensorDataRepository"
   
   e. **Domain Models - Builder Pattern** (group)
      - Rectangle: "Device (Builder Pattern)" (highlight in blue)
      - Rectangle: "SensorData"

4. **Database Layer**
   - Cylinder: "H2/PostgreSQL Database"

**Connections:**
- Devices → Kafka (solid arrows, label: "Send Data")
- Kafka → Topics (contained within)
- Controllers → Facade (solid arrows)
- Facade → Services (solid arrows)
- KafkaConsumer → Services (solid arrows from topics)
- KafkaProducer → Kafka (solid arrows)
- Services → Repositories (solid arrows)
- Repositories → Database (solid arrows)
- Services → Config (dashed arrows, label: "Uses")
- Services → Models (dashed arrows, label: "Creates")

**Colors:**
- Facade: Light Red/Pink (#ff9999)
- Singleton Config: Light Green (#99ff99)
- Builder Pattern: Light Blue (#9999ff)
- Kafka: Light Orange (#ffcc99)
- Database: Light Cyan (#99ccff)

### Data Flow Diagram (`dataflow.png`)

**Create a Sequence Diagram:**

**Participants (Left to Right):**
1. IoT Device
2. REST API (Controller)
3. IoTFacade (Facade Pattern)
4. KafkaProducer
5. Kafka Cluster
6. KafkaConsumer
7. Processing Service
8. ApplicationConfig (Singleton)
9. Device Builder (Builder Pattern)
10. Repository
11. Database

**Flow 1: Device Registration**
1. Device → API: POST /api/devices
2. API → Facade: registerNewDevice(dto)
3. Facade → Builder: Device.builder()...build()
4. Builder → Facade: validated Device
5. Facade → Service: registerDevice(dto)
6. Service → Repo: save(device)
7. Repo → DB: INSERT device
8. DB → Repo: device saved
9. Repo → Service: Device entity
10. Service → Facade: Device
11. Facade → Producer: sendDeviceRegistration(dto)
12. Producer → Kafka: publish to topic
13. Facade → API: Device
14. API → Device: 200 OK

**Flow 2: Sensor Data Processing**
1. Device → API: POST /api/data
2. API → Facade: processSensorData(dto)
3. Facade → Service: validateSensorData(dto)
4. Service → Facade: validated
5. Facade → Service: getDeviceById()
6. Service → Repo: findByDeviceId()
7. Repo → DB: SELECT device
8. DB → Repo → Service → Facade: Device
9. Facade → Service: processSensorData()
10. Service → Config: getDeviceBatchSize()
11. Config → Service: batch size
12. Service → Service: assessDataQuality()
13. Service → Facade: SensorData
14. Facade → Producer: sendSensorData()
15. Producer → Kafka: publish to topic
16. Facade → API → Device: 202 Accepted

**Flow 3: Async Processing**
1. Kafka → Consumer: consume batch
2. Consumer → Service: processSensorData()
3. Service → Repo: saveAll()
4. Repo → DB: BATCH INSERT
5. DB → Repo → Service → Consumer: success

## Option 4: Using PlantUML

1. Install PlantUML extension in VS Code
2. Use the `.puml` files provided
3. Right-click and select "Export Current Diagram"
4. Choose PNG format

## Option 5: Using Draw.io / diagrams.net

1. Visit https://app.diagrams.net/
2. Use the instructions from Option 3 (Lucidchart)
3. Draw.io has similar shapes and connectors
4. Export as PNG when done

## Recommended Approach

For **quick preview**: Use Option 1 (Mermaid Live)
For **professional diagrams**: Use Option 3 (Lucidchart) or Option 5 (Draw.io)
For **embedding in docs**: Keep the `.mmd` files and render on-demand

## File Locations

After creating the diagrams, save them as:
- `docs/architecture.png`
- `docs/dataflow.png`

The DESIGN.md already references these paths.
