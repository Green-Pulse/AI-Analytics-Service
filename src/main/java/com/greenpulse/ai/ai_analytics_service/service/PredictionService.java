package com.greenpulse.ai.ai_analytics_service.service;

import ai.onnxruntime.*;
import com.greenpulse.ai.ai_analytics_service.dto.SensorData;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.*;

@Service
public class PredictionService {

    private final OrtEnvironment env;
    private final OrtSession session;

    public PredictionService() throws Exception {
        env = OrtEnvironment.getEnvironment();
        session = env.createSession(new File("model/pm25_predictor.onnx").getAbsolutePath(),
                new OrtSession.SessionOptions());
    }

    public double predict(SensorData input) throws OrtException {
        float[] features = new float[] {
                (float) input.getTemp(),
                (float) input.getPres(),
                (float) input.getDewp(),
                (float) input.getRain(),
                (float) input.getWindSpeed(),
                input.isWd_NE() ? 1f : 0f,
                input.isWd_NW() ? 1f : 0f,
                input.isWd_SE() ? 1f : 0f
        };

        OnnxTensor tensor = OnnxTensor.createTensor(env, new float[][] { features });
        try (OrtSession.Result result = session.run(Collections.singletonMap("float_input", tensor))) {
            float[][] output = (float[][]) result.get(0).getValue();
            return output[0][0]; // PM2.5 prediction
        } catch (OrtException e) {
            throw new RuntimeException("Failed to run prediction", e);
        }
    }
}
