package com.titmouse.anton.todo.history.presenter;

import com.hannesdorfmann.mosby3.mvp.MvpBasePresenter;
import com.titmouse.anton.todo.database.entity.TodoEntity;
import com.titmouse.anton.todo.history.model.TodoModelImpl;
import com.titmouse.anton.todo.history.view.TodoView;

import java.util.List;


public class TodoPresenterImpl extends MvpBasePresenter<TodoView> implements TodoPresenter {
	
	private final TodoModelImpl mModel;
	
	public TodoPresenterImpl() {
		mModel = new TodoModelImpl();
	}
	
	
	@Override
	public void onResume() {
		if (isViewAttached()) {
			checkItemsAndShow();
		}
	}

	private void checkItemsAndShow() {
		if (isViewAttached()) {
			final List<TodoEntity> items = mModel.getTodoList();
			getView().showDataList(items);
			getView().hasData(items != null && items.size() > 0);
		}
	}

	@Override
	public void deleteTodo(final int position) {
		if (isViewAttached()) {
			mModel.deleteTodo(position);
			checkItemsAndShow();
		}
	}

	@Override
	public void addTodo(TodoEntity todo) {
		if (isViewAttached()) {
			mModel.addTodo(todo);
			checkItemsAndShow();
		}
	}

	@Override
	public void changeTodo(TodoEntity todo) {
		if (isViewAttached()) {
			// TODO: 09.09.2018
		}
	}

	@Override
	public void addNotificationTodo(TodoEntity todo) {
		if (isViewAttached()) {
			mModel.addNotification(todo);
			checkItemsAndShow();
		}
	}
}
