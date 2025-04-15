package com.greenpulse.ai.ai_analytics_service.handler;

import com.greenpulse.ai.ai_analytics_service.dto.SensorData;
import com.greenpulse.ai.ai_analytics_service.event.SensorDataEvent;
import com.greenpulse.ai.ai_analytics_service.service.PredictionService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
@RequiredArgsConstructor
public class SensorDataEventHandler {

    private final PredictionService predictionService;

    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());


    @KafkaListener(topics = "sensor-data-event-topic", groupId = "sensor-data-events", containerFactory = "kafkaListenerContainerFactory")
    public void handleEvent(SensorDataEvent event) {
        LOGGER.info("Received SensorDataEvent: {}", event.getCity(), event.getTemp(), event.getPres(), event.getDewp(), event.getRain(), event.getWindSpeed(), event.isWd_NE(), event.isWd_NW(), event.isWd_SE());

        // Преобразуем в формат, который принимает модель
        SensorData data = new SensorData(
                event.getTemp(),
                event.getPres(),
                event.getDewp(),
                event.getRain(),
                event.getWindSpeed(),
                event.isWd_NE(),
                event.isWd_NW(),
                event.isWd_SE()
        );

        try {
            double prediction = predictionService.predict(data);
            LOGGER.info("Prediction for {} → PM2.5 = {}", event.getCity(), prediction);
            // TODO: сохранить результат в Redis
        } catch (Exception e) {
            LOGGER.error("Error during prediction: {}", e.getMessage(), e);
        }

    }
}
