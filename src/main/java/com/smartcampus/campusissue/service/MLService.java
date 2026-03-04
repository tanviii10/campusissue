package com.smartcampus.campusissue.service;

import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.InputStreamReader;

@Service
public class MLService {

    public String getPrediction(String description, String severity) {
        try {

        	ProcessBuilder processBuilder = new ProcessBuilder(
        	        "python",
        	        "CampusML/predict_issue.py",
        	        description,
        	        severity
        	);

            processBuilder.redirectErrorStream(true);

            Process process = processBuilder.start();

            BufferedReader reader =
                    new BufferedReader(new InputStreamReader(process.getInputStream()));

            String line;
            StringBuilder output = new StringBuilder();

            while ((line = reader.readLine()) != null) {
                output.append(line);
            }

            process.waitFor();

            return output.toString();

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}