# API Documentation

## Device Management

### Register Device
- **POST** `/api/devices`
- **Request Body:**
```json
{
  "deviceId": "string",
  "deviceType": "string",
  "location": {
    "latitude": 12.34,
    "longitude": 56.78,
    "address": "string"
  },
  "firmware": "string"
}
```
- **Response:** `202 Accepted`

### Get Device
- **GET** `/api/devices/{deviceId}`
- **Response:**
```json
{
  "id": 1,
  "deviceId": "string",
  "deviceType": "string",
  "location": {
    "latitude": 12.34,
    "longitude": 56.78,
    "address": "string"
  },
  "status": "ACTIVE",
  "firmware": "string"
}
```

### Delete Device
- **DELETE** `/api/devices/{deviceId}`
- **Response:** `204 No Content`

---

## Sensor Data

### Ingest Sensor Data
- **POST** `/api/data`
- **Request Body:**
```json
{
  "deviceId": "string",
  "timestamp": "2025-10-19T12:00:00Z",
  "measurements": {
    "temperature": "22.5",
    "humidity": "60"
  }
}
```
- **Response:** `202 Accepted`

---

## Analytics

### Get Device Analytics
- **GET** `/api/analytics/device/{deviceId}`
- **Response:**
```json
{
  "deviceId": "string",
  "totalMeasurements": 0,
  "averageValues": {},
  "maxValues": {},
  "minValues": {},
  "dataQualityScore": 0.0
}
```

---

## Health & Monitoring
- **GET** `/actuator/health`
- **GET** `/actuator/metrics`

---

For more details, see the main `README.md` and `DESIGN.md` files.