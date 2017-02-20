package com.duchen.template.ui;

import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.LinearInterpolator;
import android.view.animation.Transformation;

/**
 * 这是一个用于在 viewGroup 的 dispatchTouchEvent 方法中,拦截触摸事件,并自己实现事件分发
 *
 * todo 提炼核心,使其更为通用!
 */
public class HeaderViewDragger implements AnimationListener {
	
	/**外部实现接口*/
	public interface Callback {
		boolean canDragDown();
		boolean canDragUp();
		boolean onOffsetChanged(int offset, boolean direction, boolean touchUp); // direction TRUE-down  FALSE-up
		View getHeaderView();
		void cancelEvent(MotionEvent ev);
	}
	
	private final static String TAG = HeaderViewDragger.class.getSimpleName();
	
	private Callback mCallback = null;
	private int mDragHeight = 0;
	HeaderViewAnim mAnimation;
	
	// flags
	private boolean mIsAnimOn = true;
	private boolean mIsFirstTouch = true;
	private boolean mIsTouchCatched = false;
	private boolean mIsNeedFix = false;
	private boolean mIsScroller = true;
	private int mStartY, mMoveY;// 按下是的y坐标,move时的y坐标
	private int mStartX, mMoveX;
	private final static int RATIO = 1;// 手势下拉距离比.
	
	public HeaderViewDragger(Callback callback, final int dragHeight) {
		mCallback = callback;		
		mDragHeight = dragHeight;
		mAnimation = new HeaderViewAnim();
		mAnimation.setAnimationListener(this);
	}
	
	public HeaderViewDragger(Callback callback, final int dragHeight, boolean hasAnimation) {
		mCallback = callback;		
		mDragHeight = dragHeight;
		mIsAnimOn = hasAnimation;
		if (mIsAnimOn) {
			mAnimation = new HeaderViewAnim();
			mAnimation.setAnimationListener(this);
		}
	}

	public boolean handleTouchEvent(MotionEvent ev) {
		if (mCallback == null) return false;
		switch (ev.getAction()) {
		// 按下
		case MotionEvent.ACTION_DOWN:
			doActionDown(ev);
			break;
		// 移动
		case MotionEvent.ACTION_MOVE:
			doActionMove(ev);
			break;
		// 抬起
		case MotionEvent.ACTION_UP:
			doActionUp(ev);
			break;
		case MotionEvent.ACTION_CANCEL:
			doActionCancel(ev);
			break;
		default:
			break;
		}
		if (!mIsScroller) {
			mCallback.cancelEvent(ev);
		}
		return !mIsScroller;
	}

	private void doActionDown(MotionEvent event) {
		// 检测是否是一次touch事件.
		if (mIsFirstTouch) {
			mStartY = (int) event.getY();
			mStartX = (int) event.getX();
			mIsFirstTouch = false;
		}
		mIsTouchCatched = false;
	}

	/***
	 * 拖拽移动操作
	 * 
	 * @param event
	 */
	private void doActionMove(MotionEvent event) {
		mMoveY = (int) event.getY();// 获取实时滑动y坐标
		mMoveX = (int) event.getX();
		// 检测是否是一次touch事件.
		if (mIsFirstTouch) {
			mStartY = (int) event.getY();
			mStartX = (int) event.getX();
			mIsFirstTouch = false;
		}
		if (mIsFirstTouch) return;

		if (!mIsTouchCatched && isVerticalMove()) {
			mIsTouchCatched = true;
		}
		if (!mIsTouchCatched) return;

		// >0 向下，<0向上
		int offset = (mMoveY - mStartY) / RATIO;

		// 向下，且不可见
		if (offset > 0 && mCallback.canDragDown()) {
			if (offset > mDragHeight) offset = mDragHeight;
			mIsNeedFix = mCallback.onOffsetChanged(offset-mDragHeight, true, false);
			mIsScroller = false;
		}

		// 向上，且可见
		else if (offset < 0 && mCallback.canDragUp()) {
			if (offset < -mDragHeight) offset = -mDragHeight;
			mIsNeedFix = mCallback.onOffsetChanged(offset, false, false);
			mIsScroller = false;
		}
	}

	/***
	 * 手势抬起操作
	 * 
	 * @param event
	 */

	private void doActionUp(MotionEvent event) {
		mIsFirstTouch = true;
		mIsScroller = true;
		if (!mIsTouchCatched) return;
		if (!mIsNeedFix) return;
		mIsNeedFix = false;
		
		int offset = mMoveY - mStartY;
		if (ignore(offset)) {
			if (offset < 0) {
				mCallback.onOffsetChanged(0, false, true);
			}
			else if (offset > 0) {
				mCallback.onOffsetChanged(-mDragHeight, true, true);
			}
			return;
		} 
		
		if (offset > mDragHeight) offset = mDragHeight;
		if (offset < -mDragHeight) offset = -mDragHeight;
		// 有动画
		if (mIsAnimOn) {
			if (offset < 0 && offset > -mDragHeight) { // 向上
				mAnimation.setParams(offset, -mDragHeight);
				mCallback.getHeaderView().startAnimation(mAnimation);
			}
			else if (offset > 0) { // 向下
				mAnimation.setParams(offset-mDragHeight, 0);
				mCallback.getHeaderView().startAnimation(mAnimation);
			}
		}
		// 无动画
		else {
			if (offset < 0) // 向上
				mCallback.onOffsetChanged(-mDragHeight, false, true);
			else if (offset > 0) // 向下
				mCallback.onOffsetChanged(0, true, true);
		}
	}

	private void doActionCancel(MotionEvent event) {
		mIsFirstTouch = true;
		mIsScroller = true;
	}

	private boolean ignore(int offset) {
		if (Math.abs(offset) < mDragHeight/5) return true;
		return false;
	}
	
	private boolean isVerticalMove() {
		return Math.abs(mStartY - mMoveY) > 2 * Math.abs(mStartX - mMoveX);
	}
	
	//----------------------------Animation--------------------------
	public class HeaderViewAnim extends Animation {

		private int mStart = 0;
		private int mEnd = 0;
		
		public HeaderViewAnim() {
			setInterpolator(new LinearInterpolator());
			setDuration(200);
		}
		
		public void setParams(int start, int end) {
			this.cancel();
			this.reset();
			mStart = start;
			mEnd = end;
		}

		@Override
		protected void applyTransformation(float interpolatedTime, Transformation t) {
			if (mEnd == 0) { // 向下
				int value = (int) ((mEnd - mStart) * interpolatedTime + mStart);
				mCallback.onOffsetChanged(value, mStart>0, false);
			} else {
				int value = (int) ((mEnd - mStart) * interpolatedTime + mStart);
				mCallback.onOffsetChanged(value, mStart>0, false);
			}
		}
	}

	@Override
	public void onAnimationStart(Animation animation) {
	}

	@Override
	public void onAnimationEnd(Animation animation) {
		
		int offset = mMoveY - mStartY;
		if (offset < 0) {
			mCallback.onOffsetChanged(-mDragHeight, false, true);
		}
		else if (offset > 0) {
			mCallback.onOffsetChanged(0, true, true);
		}
	}

	@Override
	public void onAnimationRepeat(Animation animation) {
	}
}
