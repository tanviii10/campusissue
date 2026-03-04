package com.smartcampus.campusissue.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "issues")
public class Issue {

    @Id
    private String id;

    private String description;
    private String severity;
    private String category;
    private String priority;
    private double predictedResolutionTimeHours;
    private String status;

    public Issue() {}

    public String getId() { return id; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public String getSeverity() { return severity; }
    public void setSeverity(String severity) { this.severity = severity; }

    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }

    public String getPriority() { return priority; }
    public void setPriority(String priority) { this.priority = priority; }

    public double getPredictedResolutionTimeHours() { return predictedResolutionTimeHours; }
    public void setPredictedResolutionTimeHours(double t) { this.predictedResolutionTimeHours = t; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
}