package com.titmouse.anton.todo.history.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import com.titmouse.anton.todo.R;
import com.titmouse.anton.todo.database.entity.TodoEntity;

import java.util.List;


public class TodoAdapter extends RecyclerView.Adapter<TodoAdapter.TodoViewHolder> {
	
	private List<TodoEntity> mTodoList;
	
	
	public TodoAdapter() {
	}
	
	public void updateList(final List<TodoEntity> todoEntities) {
		mTodoList = todoEntities;
		notifyDataSetChanged();
	}

	public TodoEntity getTodo(final int pos) {
		Log.i("anton", "getTodo: " + pos + " " + mTodoList.get(pos).getId());
		return mTodoList.get(pos);
	}
	
	@NonNull
	@Override
	public TodoViewHolder onCreateViewHolder(@NonNull final ViewGroup parent, final int viewType) {
		final View itemView = LayoutInflater.from(parent.getContext())
			.inflate(R.layout.todo_item, parent, false);
		
		return new TodoViewHolder(itemView);
	}
	
	@Override
	public void onBindViewHolder(@NonNull final TodoViewHolder holder, final int position) {
		assert mTodoList != null;

		Log.i("anton", "onBindViewHolder: " + mTodoList.size() + "  " + position);
		final TodoEntity todoModel = mTodoList.get(position);
		if (todoModel != null) {
			holder.textView.setText(todoModel.getText());
		}
	}
	
	
	@Override
	public int getItemCount() {
		return mTodoList.size();
	}
	
	static class TodoViewHolder extends RecyclerView.ViewHolder {
		
		private final TextView textView;
		private final CheckBox checkBox;

		private TodoViewHolder(final View v) {
			super(v);
			
			textView = v.findViewById(R.id.item_text);
			checkBox = v.findViewById(R.id.check_box);
		}
	}
}