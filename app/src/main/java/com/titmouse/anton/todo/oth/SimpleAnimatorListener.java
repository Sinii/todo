package com.titmouse.anton.todo.oth;

import android.view.animation.Animation;

public class SimpleAnimatorListener implements Animation.AnimationListener {
	private final Runnable mRunnable;
	
	public SimpleAnimatorListener(final Runnable afterAnimation) {
		mRunnable = afterAnimation;
	}
	
	@Override
	public void onAnimationStart(final Animation animation) {
	
	}
	
	@Override
	public void onAnimationEnd(final Animation animation) {
		if (mRunnable != null) {
			mRunnable.run();
		}
	}
	
	@Override
	public void onAnimationRepeat(final Animation animation) {
	
	}
}
