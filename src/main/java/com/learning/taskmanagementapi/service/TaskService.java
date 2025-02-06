package com.learning.taskmanagementapi.service;

import java.util.List;
import java.util.Optional;

import com.learning.taskmanagementapi.model.entity.Task;

public interface TaskService {

	List<Task> findAll();
	Optional<Task> findById(Long id);
	Task createTask(Task task);
	Optional<Task> updateTask(Long id, Task task);
	void deleteTask(Long id);
}
