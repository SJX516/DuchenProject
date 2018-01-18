package com.duchen.template.ui;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Gallery;

import com.duchen.template.utils.DLog;

public class CustomViewPager extends ViewPager {

	private static final String TAG = "CustomViewPager";

	public CustomViewPager(Context context) {
		this(context, null);
	}
	
    public CustomViewPager(Context context, AttributeSet attr) {
    	super(context, attr);
    }
	
	/**
	 * 默认内嵌ViewPager是能够横向滑动
	 */
	private boolean bCanScrollInnerViewPager = true;
	
	public void setCanScrollInnerViewPager(boolean b) {
		bCanScrollInnerViewPager = b;
	}
	
	/**
	 * 默认内嵌Gallery是能够横向滑动
	 */
	private boolean bCanScrollInnerGallery = true;
	
	public void setCanScrollInnerGallery(boolean b) {
		bCanScrollInnerGallery = b;
	}
	
	/**
	 * 重载canScroll是为了解决ViewPager内嵌横向滑动控件问题
	 * 1 被内嵌的ViewPager能够横向滑动
	 * 2 被内嵌的Gallery能够横向滑动
	 */
	@Override
	protected boolean canScroll(View v, boolean checkV, int dx, int x, int y) {
		if (v != this && v instanceof ViewPager && bCanScrollInnerViewPager) {
			
			int cur = ((ViewPager) v).getCurrentItem();
			if (dx < 0) {
				/**
				 * 向右滑动，判断是否向右滑到最后一个pager,如果内嵌的滑到了最后一个,那么把事件交给父View处理
				 * 如果没有的,返回true表示当前子View需要处理滑动事件
				 */
				int count = ((ViewPager) v).getAdapter().getCount();
				if (cur == (count - 1)) {
					return super.canScroll(v, checkV, dx, x, y);
				}
			} else {
				/**
				 * 向左滑动，判断是否向左滑到最前一个pager
				 */
				if (0 == cur) {
					return super.canScroll(v, checkV, dx, x, y);
				}
			}
			return true;
		} else if (v instanceof Gallery && bCanScrollInnerGallery) {
			return true;
		} else if (v instanceof IGestureConsumer) {
			return true;
		}
		
		return super.canScroll(v, checkV, dx, x, y);
	}
	
	/**
	 * 默认ViewPager是可以滑动
	 */
	private boolean mAllowedScrolling = true;
	
	public void setAllowedScrolling(boolean b) {
		mAllowedScrolling = b;
	}
	
	@Override
	public boolean onInterceptTouchEvent(MotionEvent event) {
		try {
			if(mAllowedScrolling || event.getAction() == MotionEvent.ACTION_DOWN){
				return super.onInterceptTouchEvent(event);
			}else{
				return false;
			}
		} catch (Exception e) {
			DLog.e(TAG, e.getMessage());
		}
		return false;
	}
	
	@Override
	public boolean onTouchEvent(MotionEvent arg0) {
		if(mAllowedScrolling){
			return super.onTouchEvent(arg0);
		}else{
			return false;
		}
	}

	public interface IGestureConsumer {

	}
}
