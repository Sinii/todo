package com.titmouse.anton.todo.todolist.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;

import static android.support.v7.widget.helper.ItemTouchHelper.LEFT;
import static android.support.v7.widget.helper.ItemTouchHelper.RIGHT;

public class SwipeController extends ItemTouchHelper.Callback {
    final SwipeControllerActions mSwipeControllerActions;

    public SwipeController(final SwipeControllerActions swipeControllerActions) {
        mSwipeControllerActions = swipeControllerActions;
    }

    @Override
    public int getMovementFlags(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder) {
        return makeMovementFlags(0, LEFT | RIGHT);
    }

    @Override
    public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
        return false;
    }

    @Override
    public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {

        switch (direction) {
            case LEFT: {
                mSwipeControllerActions.onLeftSwiped(viewHolder.getAdapterPosition());
                break;
            }
            case RIGHT: {
                mSwipeControllerActions.onRightSwiped(viewHolder.getAdapterPosition());

                break;
            }
        }
    }


}