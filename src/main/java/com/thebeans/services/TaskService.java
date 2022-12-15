package com.thebeans.services;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.thebeans.models.Task;
import com.thebeans.repositories.TaskRepository;

@Service
@Transactional
public class TaskService {
	
	@Autowired
	private TaskRepository repo;
	
	//get all
	public List<Task> listAll(){
		return repo.findAll();
	}
	
	//get by id
	public Task get(long id) {
		return repo.findById(id).get();
	}
	
	//create
	public void save(Task task) {
		repo.save(task);
	}
	
	//edit
	public void edit(Task task) {
		repo.save(task);
	}
	
	//delete
	public void delete(long id) {
		repo.deleteById(id);
	}
	
	//update
	public void update(Task task) {
		repo.findById(task.getTask_id()).get().setTitle(task.getTitle());
	}
}
