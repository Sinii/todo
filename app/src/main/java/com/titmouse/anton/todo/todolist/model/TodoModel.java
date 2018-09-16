package com.titmouse.anton.todo.todolist.model;

import com.titmouse.anton.todo.database.entity.TodoEntity;

import java.util.List;
import java.util.Optional;


public interface TodoModel {
	
	List<TodoEntity> getTodoList();
	
	Optional<TodoEntity> getTodo(int position);
	
	void deleteTodo(int position);
	
	void addTodo(TodoEntity todoEntity);
	
	void editTodo(TodoEntity oldTodo, TodoEntity newTodo);
	
	void changeNotification(TodoEntity oldTodo, TodoEntity newTodo);
	
	void changeDoneTodo(TodoEntity todoEntity);
	
}
