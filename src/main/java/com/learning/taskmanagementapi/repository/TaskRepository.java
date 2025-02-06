package com.learning.taskmanagementapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.learning.taskmanagementapi.model.entity.Task;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long>{

}
