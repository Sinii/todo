package com.titmouse.anton.todo.oth;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;


public class RecyclerTouchListener implements RecyclerView.OnItemTouchListener {
	
	private final GestureDetector mGestureDetector;
	private final ClickListener mClickListener;
	
	public RecyclerTouchListener(final Context context, final RecyclerView recyclerView, final ClickListener clickListener) {
		this.mClickListener = clickListener;
		mGestureDetector = new GestureDetector(context, new MySimpleOnGestureListener(recyclerView, clickListener));
	}
	
	@Override
	public boolean onInterceptTouchEvent(final RecyclerView rv, final MotionEvent e) {
		
		final View child = rv.findChildViewUnder(e.getX(), e.getY());
		if (child != null && mClickListener != null && mGestureDetector.onTouchEvent(e)) {
			mClickListener.onClick(child, rv.getChildPosition(child));
		}
		return false;
	}
	
	@Override
	public void onTouchEvent(final RecyclerView rv, final MotionEvent e) {
	}
	
	@Override
	public void onRequestDisallowInterceptTouchEvent(final boolean disallowIntercept) {
	
	}
	
	public interface ClickListener {
		
		void onClick(View view, int position);
		
		void onLongClick(View view, int position);
	}
	
	private static class MySimpleOnGestureListener extends GestureDetector.SimpleOnGestureListener {
		
		private final RecyclerView mRecyclerView;
		private final ClickListener mClickListener;
		
		private MySimpleOnGestureListener(final RecyclerView recyclerView, final ClickListener clickListener) {
			mRecyclerView = recyclerView;
			mClickListener = clickListener;
		}
		
		@Override
		public boolean onSingleTapUp(final MotionEvent e) {
			return true;
		}
		
		@Override
		public void onLongPress(final MotionEvent e) {
			final View child = mRecyclerView.findChildViewUnder(e.getX(), e.getY());
			if (child != null && mClickListener != null) {
				mClickListener.onLongClick(child, mRecyclerView.getChildPosition(child));
			}
		}
	}
}