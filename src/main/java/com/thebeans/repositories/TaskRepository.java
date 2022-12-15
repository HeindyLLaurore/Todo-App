package com.thebeans.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.thebeans.models.Task;

public interface TaskRepository extends JpaRepository<Task, Long>{
	@Query("SELECT t FROM Task t INNER JOIN User u on u.user_id = t.task_id")
	Task findBytask_id(String task_id);
}
