package com.learning.taskmanagementapi.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.learning.taskmanagementapi.model.entity.Task;
import com.learning.taskmanagementapi.repository.TaskRepository;

@Service
public class TaskServiceImpl implements TaskService {
	
	@Autowired
	private TaskRepository taskRepository;

	@Override
	public List<Task> findAll() {		
		return taskRepository.findAll();
	}

	@Override
	public Optional<Task> findById(Long id) {
		return taskRepository.findById(id);
	}

	@Override
	public Task createTask(Task task) {
		return taskRepository.save(task);
	}

	@Override
	public Optional<Task> updateTask(Long id, Task task) {
		Optional<Task> taskOptional = taskRepository.findById(id);
		if(taskOptional.isPresent()) {
			Task taskToUpdate = taskOptional.get();
			taskToUpdate.setTitle(task.getTitle());
			taskToUpdate.setDescription(task.getDescription());
			taskToUpdate.setStatus(task.getStatus());
			taskToUpdate.setCreateDate(task.getCreateDate());
			
			return Optional.of(taskRepository.save(taskToUpdate));
		}
		return Optional.empty();
	}

	@Override
	public void deleteTask(Long id) {
		taskRepository.deleteById(id);
	}

}
