package com.titmouse.anton.todo.todolist.presenter;

import com.hannesdorfmann.mosby3.mvp.MvpPresenter;
import com.titmouse.anton.todo.todolist.view.TodoView;
import com.titmouse.anton.todo.database.entity.TodoEntity;


public interface TodoPresenter extends MvpPresenter<TodoView> {
	
	void onResume();
	
	void deleteTodo(int position);

	void addTodo(TodoEntity todo);

	void changeTodo(TodoEntity oldTodo, TodoEntity newTodo);

	void addNotificationTodo(TodoEntity todo);

}
