package com.titmouse.anton.todo.todolist.model;

import com.titmouse.anton.todo.database.entity.TodoEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


public class TodoModelImpl implements TodoModel {
	
	private List<TodoEntity> mCachedTodoList = new ArrayList<>();
	
	@Override
	public List<TodoEntity> getTodoList() {
		// TODO: 08.09.2018 add database
		if (mCachedTodoList.size() == 0) {
			mCachedTodoList = getMockData();
		}
		return mCachedTodoList;
	}
	
	@Override
	public Optional<TodoEntity> getTodo(final int position) {
		return Optional.of(mCachedTodoList.get(position));
	}
	
	@Override
	public void deleteTodo(final int position) {
		mCachedTodoList.remove(position);
	}
	
	@Override
	public void addTodo(final TodoEntity todoEntity) {
		mCachedTodoList.add(mCachedTodoList.size(), todoEntity);
	}
	
	@Override
	public void editTodo(final TodoEntity oldTodo, final TodoEntity newTodo) {
		final int index = mCachedTodoList.indexOf(oldTodo);
		if (index >= 0) {
			mCachedTodoList.set(index, newTodo);
		} else {
			// TODO: 16.09.2018 notify server about exception
		}
	}
	
	@Override
	public void changeNotification(final TodoEntity oldTodo, final TodoEntity newTodo) {
	
	
	}
	
	@Override
	public void changeDoneTodo(final TodoEntity todoEntity) {
		final int index = mCachedTodoList.indexOf(todoEntity);
		if (index >= 0) {
			todoEntity.setDone(!todoEntity.getDone());
			mCachedTodoList.set(index, todoEntity);
		}
	}
	
	private static List<TodoEntity> getMockData() {
		//noinspection unchecked
		final List<TodoEntity> list = new ArrayList<>();
		list.add(new TodoEntity(0L, false, "Антон, найди время меня сделать", 0));
		list.add(new TodoEntity(1L, false, "Удали меня свайпом", 0));
		list.add(new TodoEntity(2L, false, "Удали меня полностью", 0));
		return list;
//		return
//			new ArrayList() {{
//				new TodoEntity(0L, false, "Антон, найди время меня сделать", 0);
//				new TodoEntity(1L, true, "Удали меня свайпом", 0);
//				new TodoEntity(2L, false, "Удали меня полностью", 0);
//			}};
	}
}
