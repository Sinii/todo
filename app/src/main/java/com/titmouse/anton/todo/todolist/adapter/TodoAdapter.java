package com.titmouse.anton.todo.todolist.adapter;

import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.titmouse.anton.todo.R;
import com.titmouse.anton.todo.database.entity.TodoEntity;

import java.util.List;


public class TodoAdapter extends RecyclerView.Adapter<TodoAdapter.TodoViewHolder> {
	
	private final ItemClickListener mChangeDoneClickListener;
	private final ItemClickListener mChangeNotificationClickListener;
	
	private List<TodoEntity> mTodoList;
	
	
	public TodoAdapter(
		final ItemClickListener changeDoneClickListener,
		final ItemClickListener changeNotificationListener
	) {
		mChangeDoneClickListener = changeDoneClickListener;
		mChangeNotificationClickListener = changeNotificationListener;
		
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
		final TodoEntity todo = mTodoList.get(position);
		if (todo != null) {
			
			holder.textView.setText(todo.getText());
			holder.textView.setOnClickListener(v -> mChangeDoneClickListener.onClick(position));
			holder.notification.setOnClickListener(v -> mChangeNotificationClickListener.onClick(position));
			
			if (todo.getDone()) {
				holder.layout.setBackgroundColor(Color.GRAY);
			} else {
				holder.layout.setBackgroundColor(Color.parseColor("#ff669900"));
			}
		}
	}
	
	@Override
	public int getItemCount() {
		return mTodoList.size();
	}
	
	static class TodoViewHolder extends RecyclerView.ViewHolder {
		
		private final TextView textView;
		private final View notification;
		private final ConstraintLayout layout;
		
		private TodoViewHolder(final View v) {
			super(v);
			
			textView = v.findViewById(R.id.item_text);
			notification = v.findViewById(R.id.notification);
			layout = v.findViewById(R.id.parent_layout);
		}
	}
	
	public interface ItemClickListener {
		
		void onClick(final int position);
	}
}