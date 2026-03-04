package com.smartcampus.campusissue.repository;

import com.smartcampus.campusissue.model.Issue;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface IssueRepository extends MongoRepository<Issue, String> {
}