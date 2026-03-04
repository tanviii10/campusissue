package com.smartcampus.campusissue.repository;

import com.smartcampus.campusissue.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserRepository extends MongoRepository<User, String> {

    User findByEmail(String email);
}