package com.titmouse.anton.todo.ui;

import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.Notification;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hannesdorfmann.mosby3.mvp.MvpFragment;
import com.titmouse.anton.todo.R;
import com.titmouse.anton.todo.database.entity.TodoEntity;
import com.titmouse.anton.todo.oth.NotificationHelper;
import com.titmouse.anton.todo.oth.NotificationPublisher;
import com.titmouse.anton.todo.oth.RecyclerTouchListener;
import com.titmouse.anton.todo.todolist.adapter.SwipeController;
import com.titmouse.anton.todo.todolist.adapter.SwipeControllerActions;
import com.titmouse.anton.todo.todolist.adapter.TodoAdapter;
import com.titmouse.anton.todo.todolist.presenter.TodoPresenter;
import com.titmouse.anton.todo.todolist.presenter.TodoPresenterImpl;
import com.titmouse.anton.todo.todolist.view.TodoView;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;


public class TodoFragment extends MvpFragment<TodoView, TodoPresenter> implements TodoView, RecyclerTouchListener.ClickListener, FragmentLifecycle {
	
	private static int sIdNotification = 1;

	private TodoAdapter mAdapter;
	private TextView mEmptyText;
	private RecyclerView mRecyclerView;
	private View mAddTodoButton;
	private NotificationHelper mNotificationHelper;
	
	@NonNull
	@Override
	public TodoPresenter createPresenter() {
		return new TodoPresenterImpl();
	}
	
	
	public TodoFragment() {
	}
	
	@Override
	public void onCreate(final Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}
	
	@Override
	public void onResume() {
		presenter.onResume();
		super.onResume();
	}
	
	@Override
	public View onCreateView(@NonNull final LayoutInflater inflater, final ViewGroup container, final Bundle savedInstanceState) {
		super.onCreateView(inflater, container, savedInstanceState);
		final View rootView = inflater.inflate(R.layout.todo_fragment, container, false);
		mAddTodoButton = rootView.findViewById(R.id.add_todo_fab);
		mRecyclerView = rootView.findViewById(R.id.todo_recyclerview);
		mEmptyText = rootView.findViewById(R.id.empty_text);
		
		mAddTodoButton.setOnClickListener(this::showAddTodoDialog);
		mAdapter = new TodoAdapter(
			position -> presenter.changeDoneTodo(position),
			position -> showAddTodoNotificationDialog(mAdapter.getTodo(position)));
		mRecyclerView.setAdapter(mAdapter);
		mRecyclerView.setItemAnimator(new DefaultItemAnimator());
		mRecyclerView.addOnItemTouchListener(new RecyclerTouchListener(getContext(), mRecyclerView, this));
		
		final SwipeController swipeController = new SwipeController(new SwipeControllerActions() {
			
			@Override
			public void onLeftSwiped(final int position) {
				presenter.deleteTodo(position);
			}
			
			@Override
			public void onRightSwiped(final int position) {
				presenter.deleteTodo(position);
			}
		});
		
		final ItemTouchHelper itemTouchhelper = new ItemTouchHelper(swipeController);
		itemTouchhelper.attachToRecyclerView(mRecyclerView);
		
		final RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());
		mRecyclerView.setLayoutManager(mLayoutManager);
		mRecyclerView.setNestedScrollingEnabled(true);
		
		mNotificationHelper = new NotificationHelper(getContext());
		
		return rootView;
	}
	
	private void showAddTodoNotificationDialog(final TodoEntity todo) {
		final Calendar calendar = Calendar.getInstance();
		
		
		final DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(),
			(view, year, monthOfYear, dayOfMonth) -> {
				final Calendar newCalendar = new GregorianCalendar(year, monthOfYear, dayOfMonth);
				showAddTodoNotificationTimeDialog(newCalendar, todo);
			}, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
		datePickerDialog.show();
	}
	
	private void showAddTodoNotificationTimeDialog(final Calendar calendar, final TodoEntity todo) {
		
		final TimePickerDialog timePickerDialog = new TimePickerDialog(getContext(),
			(view, hourOfDay, minute) -> {
				calendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
				calendar.set(Calendar.MINUTE, minute);
				calendar.set(Calendar.SECOND, 0);
				calendar.set(Calendar.MILLISECOND, 0);
				
				scheduleNotification(mNotificationHelper.createNotification(getResources().getString(R.string.notification_title), todo.getText()), calendar.getTimeInMillis());
			}, calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE), false);
		timePickerDialog.show();
	}
	
	private void showAddTodoDialog(final View _) {
		final AlertDialog.Builder alertDialog = new AlertDialog.Builder(getContext());
		alertDialog.setTitle(R.string.dialog_add_todo_title);
		final EditText input = new EditText(getContext());
		final LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
			LinearLayout.LayoutParams.MATCH_PARENT,
			LinearLayout.LayoutParams.MATCH_PARENT);
		input.setLayoutParams(lp);
		alertDialog.setView(input);
		alertDialog.setIcon(android.R.drawable.ic_input_add);
		alertDialog.setPositiveButton(R.string.dialog_add_todo_positive_button,
			(dialog, which) -> presenter.addTodo(
				new TodoEntity(mAdapter.getItemCount() + 1, input.getEditableText().toString())
			)
		);
		alertDialog.setNegativeButton(R.string.dialog_add_todo_negative_button,
			(dialog, which) -> dialog.cancel());
		alertDialog.show();
	}
	
	
	private void showEditTodoDialog(final TodoEntity todo) {
		final AlertDialog.Builder alertDialog = new AlertDialog.Builder(getContext());
		alertDialog.setTitle(R.string.dialog_add_todo_title);
		final EditText input = new EditText(getContext());
		final LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
			LinearLayout.LayoutParams.MATCH_PARENT,
			LinearLayout.LayoutParams.MATCH_PARENT);
		input.setLayoutParams(lp);
		input.setText(todo.getText());
		alertDialog.setView(input);
		alertDialog.setIcon(android.R.drawable.ic_input_add);
		alertDialog.setPositiveButton(R.string.dialog_add_todo_save_button,
			(dialog, which) -> presenter.changeTodo(
				todo,
				new TodoEntity(todo.getId(), input.getEditableText())
			)
		);
		alertDialog.setNegativeButton(R.string.dialog_add_todo_negative_button,
			(dialog, which) -> dialog.cancel());
		alertDialog.show();
	}
	
	@Override
	public void showDataList(final List<TodoEntity> todoList) {
		mAdapter.updateList(todoList);
	}
	
	@Override
	public void hasData(final boolean hasData) {
		mEmptyText.setVisibility(hasData ? View.GONE : View.VISIBLE);
	}
	
	@Override
	public void onClick(final View view, final int position) {
		final Animation animShake = AnimationUtils.loadAnimation(getContext(), R.anim.shake_view);
		view.startAnimation(animShake);
	}
	
	@Override
	public void onLongClick(final View view, final int position) {
		if (position >= 0) {
			showEditTodoDialog(mAdapter.getTodo(position));
		}
	}
	
	@Override
	public void onOpenFragment() {
		if (presenter != null) {
			presenter.onResume();
		}
	}
	
	private void scheduleNotification(final Notification notification, final long delay) {
		
		final Intent notificationIntent = new Intent(getContext(), NotificationPublisher.class);
		notificationIntent.putExtra(NotificationPublisher.NOTIFICATION_ID, sIdNotification);
		notificationIntent.putExtra(NotificationPublisher.NOTIFICATION, notification);
		final PendingIntent pendingIntent =
			PendingIntent.getBroadcast(getContext(), sIdNotification++, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);
		final AlarmManager alarmManager = (AlarmManager) getActivity().getSystemService(Context.ALARM_SERVICE);
		
		alarmManager.set(AlarmManager.RTC_WAKEUP, delay, pendingIntent);
	}
	
}
