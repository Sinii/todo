package com.titmouse.anton.todo.history.presenter;

import com.hannesdorfmann.mosby3.mvp.MvpPresenter;
import com.titmouse.anton.todo.history.view.TodoView;
import com.titmouse.anton.todo.database.entity.TodoEntity;


public interface TodoPresenter extends MvpPresenter<TodoView> {
	
	void onResume();
	
	void deleteTodo(int position);

	void addTodo(TodoEntity todo);

	void changeTodo(TodoEntity todo);

	void addNotificationTodo(TodoEntity todo);

}
