#  AI Analytics Service

The **AI Analytics Service** is responsible for analyzing environmental sensor data (e.g., temperature, pressure, humidity, wind speed) and predicting **PM2.5 air pollution levels** using a pre-trained ML model.

---

##  Purpose

This microservice uses an ML model (trained in Python and exported as `.onnx`) to:
- Predict air quality (PM2.5 levels)
- Help make proactive environmental decisions
- Support data ingestion and visualization in Grafana

---

##  Machine Learning Model

- **Trained in Python** using `scikit-learn`
- **Exported to ONNX format** for Java compatibility
- Input: Meteorological data (TEMP, PRES, DEWP, WIND_SPEED, etc.)
- Output: Predicted PM2.5 value (µg/m³)

Example input:
```json
{
  "temp": 20.5,
  "pres": 1015,
  "dewp": 5.2,
  "rain": 0.0,
  "windSpeed": 3.4,
  "wd_NE": true,
  "wd_NW": false,
  "wd_SE": false
}
```

---

##  Tech Stack

- Java 21  
- Spring Boot 3.4.4  
- ONNX Runtime Java  
- Prometheus (metrics)  
- Kafka (optional future integration)  
- Grafana (visualization)

---

##  Endpoint

| Method | Endpoint              | Description              |
|--------|-----------------------|--------------------------|
| POST   | `/api/ai/predict`     | Predict PM2.5 from input |

---

##  Sample Request

```
POST /predict
Content-Type: application/json

{
  "temp": 20.5,
  "pres": 1015,
  "dewp": 5.2,
  "rain": 0.0,
  "windSpeed": 3.4,
  "wd_NE": true,
  "wd_NW": false,
  "wd_SE": false
}
```

Response:
```json
{
  "prediction": 51.06
}
```

---


##  TODOs

###  MVP Done
- [x] Accept sensor input JSON
- [x] Load `.onnx` model
- [x] Predict PM2.5 level
- [x] Return prediction via REST

###  Short-Term Enhancements
- [ ] Add Kafka consumer to handle real-time sensor streams
- [ ] Log each prediction to a database (PostgreSQL or Redis)
- [ ] Add Prometheus custom metrics (e.g., prediction count, average pollution level)
- [ ] Add error handling for bad input or model failures
- [ ] Return additional metadata in prediction response

###  Integration Tasks
- [ ] Expose predictions to Grafana dashboard
- [ ] Send alerts to Notification Service if PM2.5 > threshold
- [ ] Secure endpoints via JWT or API Key
- [ ] Document API with Swagger/OpenAPI

###  Advanced Features
- [ ] Replace linear regression with LSTM or XGBoost model
- [ ] Use model versioning and automatic re-training pipeline
- [ ] Create ML pipeline with Airflow or Prefect
- [ ] Create a dashboard for visualizing model accuracy
- [ ] Integrate with a weather API for real-time external data
- [ ] Add batch prediction endpoint (for multiple sensor records)
- [ ] Basic monitoring via Prometheus

---

##  References

- [ONNX Runtime Java Docs](https://onnxruntime.ai/docs/get-started/with-java.html)
- [Prometheus + Spring Boot](https://micrometer.io/docs/registry/prometheus)
- [Air Quality Dataset Source](https://archive.ics.uci.edu/datasets)
- [Grafana with Prometheus Guide](https://grafana.com/docs/grafana/latest/getting-started/getting-started-prometheus/)
