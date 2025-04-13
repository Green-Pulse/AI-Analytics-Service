package com.greenpulse.ai.ai_analytics_service.controller;

import ai.onnxruntime.OrtException;
import com.greenpulse.ai.ai_analytics_service.dto.SensorData;
import com.greenpulse.ai.ai_analytics_service.service.PredictionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/ai")
@RequiredArgsConstructor
public class PredictionController {

    private final PredictionService predictionService;

    @PostMapping("/predict")
    public ResponseEntity<Double> predict(@RequestBody SensorData input) throws OrtException {
        double pm25 = predictionService.predict(input);
        return ResponseEntity.ok(pm25);
    }
}
