package com.titmouse.anton.todo.todolist.presenter;

import com.hannesdorfmann.mosby3.mvp.MvpBasePresenter;
import com.titmouse.anton.todo.database.entity.TodoEntity;
import com.titmouse.anton.todo.todolist.model.TodoModelImpl;
import com.titmouse.anton.todo.todolist.view.TodoView;

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
	public void addTodo(final TodoEntity todo) {
		if (isViewAttached()) {
			mModel.addTodo(todo);
			checkItemsAndShow();
		}
	}

	@Override
	public void changeTodo(final TodoEntity oldTodo, final TodoEntity newTodo) {
		if (isViewAttached()) {
			mModel.editTodo(oldTodo, newTodo);
			checkItemsAndShow();
		}
	}

	@Override
	public void changeNotificationTodo(final int position) {
		if (isViewAttached()) {
			mModel.getTodo(position).ifPresent(oldTodo -> {
				final TodoEntity newTodo = oldTodo;
				mModel.changeNotification(oldTodo, newTodo);
			});
			
			checkItemsAndShow();
		}
	}
	
	@Override
	public void changeDoneTodo(final int pos) {
		if (isViewAttached()) {
			mModel.getTodo(pos)
				.ifPresent(mModel::changeDoneTodo);
			checkItemsAndShow();
		}
	}
}
