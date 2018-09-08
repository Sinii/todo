package com.titmouse.anton.todo.database.entity;

import io.realm.RealmObject;

public class TodoEntity extends RealmObject {
	
	private Long id;
	private boolean done;
	private String text;
	private int error;

	public TodoEntity() {

	}

	public TodoEntity(final Long id, final boolean done, final String text, final int error) {
		this.id = id;
		this.done = done;
		this.text = text;
		this.error = error;
	}

	public TodoEntity(final int id, final String text) {
		this.id = (long) id;
		this.done = false;
		this.text = text;
		this.error = 0;
	}
	
	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}

	public boolean getDone() {
		return done;
	}
	
	public void setDone(boolean done) {
		this.done = done;
	}
	
	public String getText() {
		return text;
	}
	
	public void setText(String text) {
		this.text = text;
	}
	
	public int getError() {
		return error;
	}
	
	public void setError(int error) {
		this.error = error;
	}
}
