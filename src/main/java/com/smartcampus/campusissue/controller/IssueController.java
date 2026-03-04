package com.smartcampus.campusissue.controller;

import com.smartcampus.campusissue.model.Issue;
import com.smartcampus.campusissue.repository.IssueRepository;
import com.smartcampus.campusissue.service.MLService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
                             @RequestParam String severity,
                             @RequestParam String location) {

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
        issue.setLocation(location);

        return issueRepository.save(issue);
    }
    @GetMapping("/all")
    public List<Issue> getAllIssues() {
        return issueRepository.findAll();
    }
    
    @PutMapping("/updateStatus/{id}")
    public Issue updateStatus(@PathVariable String id,
                              @RequestParam String status) {

        Issue issue = issueRepository.findById(id).orElse(null);

        if(issue != null){
            issue.setStatus(status);
            return issueRepository.save(issue);
        }

        return null;
    }
    
    @GetMapping("/stats")
    public Map<String, Object> getStats() {

        List<Issue> issues = issueRepository.findAll();

        int total = issues.size();
        int pending = 0;
        int resolved = 0;
        int highPriority = 0;

        Map<String,Integer> categoryCount = new HashMap<>();

        for(Issue issue : issues){

            if(issue.getStatus().equalsIgnoreCase("Pending"))
                pending++;

            if(issue.getStatus().equalsIgnoreCase("Resolved"))
                resolved++;

            if(issue.getPriority().equalsIgnoreCase("High"))
                highPriority++;

            String cat = issue.getCategory();
            categoryCount.put(cat, categoryCount.getOrDefault(cat,0)+1);
        }

        String mostCommonCategory = "";
        int max = 0;

        for(String key : categoryCount.keySet()){
            if(categoryCount.get(key) > max){
                max = categoryCount.get(key);
                mostCommonCategory = key;
            }
        }

        Map<String,Object> stats = new HashMap<>();

        stats.put("totalIssues", total);
        stats.put("pendingIssues", pending);
        stats.put("resolvedIssues", resolved);
        stats.put("highPriorityIssues", highPriority);
        stats.put("mostCommonCategory", mostCommonCategory);

        return stats;
    }
}