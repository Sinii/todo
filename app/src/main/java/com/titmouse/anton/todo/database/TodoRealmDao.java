package com.titmouse.anton.todo.database;

import com.titmouse.anton.todo.database.entity.TodoEntity;

import java.util.List;


public interface TodoRealmDao {
	
	void setTodo(TodoEntity todoEntity);
	
	List<TodoEntity> getToDo();
	
	List<TodoEntity> deleteToDo(TodoEntity todoEntity);
	
	void deleteAll();
}
