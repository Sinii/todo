package com.titmouse.anton.todo.history.view;

import com.hannesdorfmann.mosby3.mvp.MvpView;
import com.titmouse.anton.todo.database.entity.TodoEntity;

import java.util.List;


public interface TodoView extends MvpView {
	
	void showDataList(List<TodoEntity> todoList);

	void hasData(boolean hasData);

}
