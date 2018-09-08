package com.titmouse.anton.todo.history.model;

import com.titmouse.anton.todo.database.entity.TodoEntity;

import java.util.List;


public interface TodoModel {

	List<TodoEntity> getTodoList();
	
	void deleteTodo(int position);

	void addTodo(TodoEntity todoEntity);

	void addNotification(TodoEntity todoEntity);
}
