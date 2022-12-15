package com.thebeans.models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Table(name="task")
public class Task {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long task_id;
	
	private Long user_id;
	
	@NotBlank(message="Please enter your task\'s title")
	@Size(max = 20, message="Please enter a title with 20 characters or less")
	private String title;
	
	@Size(max = 30, message="Descriptions can be up to 30 characters maximum")
	private String description;
	
	@DateTimeFormat(style="M-")
	private String dueDate;
	
	@NotBlank(message="Please enter the day")
	private String status;
	
	@NotBlank(message="Please select a priority")
	private String priority;
	
	@Size(max = 20, message="Category names must be 20 characters or less")
	private String category;

	public Long getTask_id() {
		return task_id;
	}

	public void setTask_id(Long task_id) {
		this.task_id = task_id;
	}

	public Long getUser_id() {
		return user_id;
	}

	public void setUser_id(Long user_id) {
		this.user_id = user_id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getDueDate() {
		return dueDate;
	}

	public void setDueDate(String dueDate) {
		this.dueDate = dueDate;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getPriority() {
		return priority;
	}

	public void setPriority(String priority) {
		this.priority = priority;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}
	
	
	

}
