package com.example.mvcSpring.repository;

import com.example.mvcSpring.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository <User ,Long> {

}