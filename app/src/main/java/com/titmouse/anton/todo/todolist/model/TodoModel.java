package com.titmouse.anton.todo.todolist.model;

import com.titmouse.anton.todo.database.entity.TodoEntity;

import java.util.List;


public interface TodoModel {
	
	List<TodoEntity> getTodoList();
	
	void deleteTodo(int position);
	
	void addTodo(TodoEntity todoEntity);
	
	void editTodo(TodoEntity oldTodo, TodoEntity newTodo);
	
	void addNotification(TodoEntity todoEntity);
}
