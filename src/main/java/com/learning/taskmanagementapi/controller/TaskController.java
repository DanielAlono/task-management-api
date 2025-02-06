package com.learning.taskmanagementapi.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.learning.taskmanagementapi.exception.TaskNotFoundException;
import com.learning.taskmanagementapi.model.entity.Task;
import com.learning.taskmanagementapi.service.TaskService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/tasks")
public class TaskController {
	
	public static final String TASK_NOT_FOUND_MESSAGE = "Task with ID %d not found.";

	@Autowired
	TaskService taskService;
	
	@GetMapping
	public List<Task> findAllTask(){
		return taskService.findAll();
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Task> findTaskById(@PathVariable Long id) {
	    Task task = taskService.findById(id)
	        .orElseThrow(() -> new TaskNotFoundException(String.format(TASK_NOT_FOUND_MESSAGE, id)));
	    
	    return ResponseEntity.ok(task);
	}

	
	@PostMapping
	public ResponseEntity<?> createTask(@Valid @RequestBody Task task, BindingResult result) {
		if(result.hasErrors())
			return validation(result);
		
		return ResponseEntity.status(HttpStatus.CREATED).body(taskService.createTask(task));
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<?> updateTask(@Valid @RequestBody Task task, BindingResult result, @PathVariable Long id){
		if (result.hasErrors())
			return validation(result);
		
		Optional<Task> taskOptional = taskService.updateTask(id, task);
		if (taskOptional.isPresent())
			return ResponseEntity.status(HttpStatus.OK).body(taskOptional.get());
		
		throw new TaskNotFoundException(String.format(TASK_NOT_FOUND_MESSAGE, id));
				
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteTask(@PathVariable Long id) {
	    Task task = taskService.findById(id)
	        .orElseThrow(() -> new TaskNotFoundException(String.format(TASK_NOT_FOUND_MESSAGE, id)));
	    
	    taskService.deleteTask(task.getId());
	    return ResponseEntity.noContent().build();
	}

	
	@PatchMapping("/{id}")
	public ResponseEntity<?> partialUpdateTask(@Valid @RequestBody Task task, BindingResult result, @PathVariable Long id) {
		if (result.hasErrors())
			return validation(result);
		
		return taskService.findById(id)
				.map(existingTask -> {
					Optional.ofNullable(task.getTittle()).ifPresent(existingTask::setTittle);
					Optional.ofNullable(task.getDescription()).ifPresent(existingTask::setDescription);
					Optional.ofNullable(task.getStatus()).ifPresent(existingTask::setStatus);
					Optional.ofNullable(task.getCreateDate()).ifPresent(existingTask::setCreateDate);
					
					taskService.updateTask(id, existingTask);
					return ResponseEntity.status(HttpStatus.OK).body(existingTask);
				}).orElseThrow(() -> new TaskNotFoundException(String.format(TASK_NOT_FOUND_MESSAGE, id)));
	}
	
	private ResponseEntity<Map<String, String>> validation(BindingResult result) {
	    Map<String, String> errors = new HashMap<>();
	    
	    result.getFieldErrors().forEach(err ->
	        errors.put(err.getField(), err.getDefaultMessage())
	    );
	    
	    return ResponseEntity.badRequest().body(errors);
	}

}
