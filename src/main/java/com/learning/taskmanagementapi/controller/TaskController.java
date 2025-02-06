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

import com.learning.taskmanagementapi.model.entity.Task;
import com.learning.taskmanagementapi.service.TaskService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/tasks")
public class TaskController {

	@Autowired
	TaskService taskService;
	
	@GetMapping
	public List<Task> findAllTask(){
		return taskService.findAll();
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Task> findTaskById(@PathVariable Long id) {
		Optional<Task> taskOptional = taskService.findById(id);
		
		if(taskOptional.isPresent())
			return ResponseEntity.ok(taskOptional.orElseThrow());
		
		return ResponseEntity.notFound().build();
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
		
		return ResponseEntity.notFound().build();
				
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Task> deleteTask(@PathVariable Long id){
		Optional<Task> taskOptional = taskService.findById(id);
		if(taskOptional.isPresent()) {
			taskService.deleteTask(id);
			return ResponseEntity.noContent().build();
		}
		
		return ResponseEntity.notFound().build();
	}
	
	@PatchMapping("/{id}")
	public ResponseEntity<?> partialUpdateTask(@Valid @RequestBody Task task, BindingResult result, @PathVariable Long id) {
		if (result.hasErrors())
			return validation(result);
		
		Optional<Task> taskOptional = taskService.findById(id);
		
		if(taskOptional.isPresent()) {
			Task taskToUpdate = taskOptional.get();
			
			if (task.getTittle() != null)
				taskToUpdate.setTittle(task.getTittle());
			
			if (task.getDescription() != null)
				taskToUpdate.setDescription(task.getDescription());
			
			if (task.getStatus() != null)
				taskToUpdate.setStatus(task.getStatus());
			
			if (task.getCreateDate() != null)
				taskToUpdate.setCreateDate(task.getCreateDate());
			
			taskService.updateTask(id, taskToUpdate);
			return ResponseEntity.status(HttpStatus.OK).body(taskToUpdate);
		}
		
		return ResponseEntity.notFound().build();
	}
	
	private ResponseEntity<?> validation(BindingResult result) {
		Map<String, String> errors = new HashMap<>();
		
		result.getFieldErrors().forEach(err -> {
			errors.put(err.getField(), "The field " + err.getField() + " " + err.getDefaultMessage());
		});
		
		return ResponseEntity.badRequest().body(errors);
		
	}
}
