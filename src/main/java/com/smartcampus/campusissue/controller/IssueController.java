package com.smartcampus.campusissue.controller;

import com.smartcampus.campusissue.model.Issue;
import com.smartcampus.campusissue.repository.IssueRepository;
import com.smartcampus.campusissue.service.MLService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/issues")
public class IssueController {

    @Autowired
    private MLService mlService;

    @Autowired
    private IssueRepository issueRepository;

    @GetMapping("/create")
    public Issue createIssue(@RequestParam String description,
                             @RequestParam String severity) {

        String prediction = mlService.getPrediction(description, severity);

        System.out.println("ML Output: " + prediction);

        // simple JSON parsing
        String category = prediction.split("\"category\": \"")[1].split("\"")[0];
        String priority = prediction.split("\"priority\": \"")[1].split("\"")[0];
        double predictedTime = Double.parseDouble(
                prediction.split("\"predictedResolutionTimeHours\": ")[1]
                        .replace("}", "")
        );

        Issue issue = new Issue();
        issue.setDescription(description);
        issue.setSeverity(severity);
        issue.setCategory(category);
        issue.setPriority(priority);
        issue.setPredictedResolutionTimeHours(predictedTime);
        issue.setStatus("Pending");

        return issueRepository.save(issue);
    }
}