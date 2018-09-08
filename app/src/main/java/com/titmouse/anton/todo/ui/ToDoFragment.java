package com.titmouse.anton.todo.ui;

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
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hannesdorfmann.mosby3.mvp.MvpFragment;
import com.titmouse.anton.todo.R;
import com.titmouse.anton.todo.database.entity.TodoEntity;
import com.titmouse.anton.todo.history.adapter.SwipeController;
import com.titmouse.anton.todo.history.adapter.SwipeControllerActions;
import com.titmouse.anton.todo.history.adapter.TodoAdapter;
import com.titmouse.anton.todo.history.presenter.TodoPresenter;
import com.titmouse.anton.todo.history.presenter.TodoPresenterImpl;
import com.titmouse.anton.todo.history.view.TodoView;
import com.titmouse.anton.todo.oth.RecyclerTouchListener;

import java.util.List;


public class ToDoFragment extends MvpFragment<TodoView, TodoPresenter> implements TodoView, RecyclerTouchListener.ClickListener, FragmentLifecycle {

    private TodoAdapter mAdapter;
    private TextView mEmptyText;
    private RecyclerView mRecyclerView;
    private View mAddTodoButton;


    @NonNull
    @Override
    public TodoPresenter createPresenter() {
        return new TodoPresenterImpl();
    }


    public ToDoFragment() {

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

        mAdapter = new TodoAdapter();
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.addOnItemTouchListener(new RecyclerTouchListener(getContext(), mRecyclerView, this));

        final SwipeController swipeController = new SwipeController(new SwipeControllerActions() {
            @Override
            public void onLeftSwiped(int position) {
                presenter.deleteTodo(position);
            }

            @Override
            public void onRightSwiped(int position) {
                presenter.deleteTodo(position);
            }
        });
        final ItemTouchHelper itemTouchhelper = new ItemTouchHelper(swipeController);
        itemTouchhelper.attachToRecyclerView(mRecyclerView);

        final RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setNestedScrollingEnabled(true);

        return rootView;
    }

    private void showAddTodoDialog(final View view) {
        final AlertDialog.Builder alertDialog = new AlertDialog.Builder(getContext());
        alertDialog.setTitle("Что будем делать, сэр?");
        final EditText input = new EditText(getContext());
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT);
        input.setLayoutParams(lp);
        alertDialog.setView(input);
        alertDialog.setIcon(android.R.drawable.ic_input_add);
        alertDialog.setPositiveButton("Добавить",
                (dialog, which) -> presenter.addTodo(new TodoEntity(mAdapter.getItemCount() + 1, input.getEditableText().toString())));
        alertDialog.setNegativeButton("Отмена",
                (dialog, which) -> dialog.cancel());
        alertDialog.show();
    }


    @Override
    public void showDataList(final List<TodoEntity> todoList) {
        mAdapter.updateList(todoList);
    }

    @Override
    public void hasData(boolean hasData) {
        mEmptyText.setVisibility(hasData ? View.GONE : View.VISIBLE);
    }

    @Override
    public void onClick(final View view, final int position) {
        // TODO: 09.09.2018  make edit
    }

    @Override
    public void onLongClick(final View view, final int position) {
        if (position >= 0) {
            // TODO: 09.09.2018 make notification
        }
    }

    @Override
    public void onOpenFragment() {
        if (presenter != null) {
            presenter.onResume();
        }
    }
}
