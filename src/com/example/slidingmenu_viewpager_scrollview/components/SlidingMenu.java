package com.example.slidingmenu_viewpager_scrollview.components;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.RelativeLayout;
import android.widget.Scroller;

/**
 * 自定义SlidingMenu; 
 * 实现功能：1.点击打开menu 2.内容页面 随手指移动  3.menu 打开时,内容页面任意位置点击关闭menu.
 * @author zy
 * 2015/11/29
 */
public class SlidingMenu extends RelativeLayout {
	private static final String TAG = "SlidingMenu";
	private Scroller scroller;
	private View menuView;
	private View slidView;
	private int screenWidth;
	private int menuViewWidth;
	private float startX;
	private float downX;
	private float downY;
	//slidingView scroll 的累积距离
	private float scrollXDistance;
	private float lastScrollX;
	private boolean isMenuOpen;
	//判断是否拦截touch 事件的flag
	private boolean isIntercepted;
	//手指移动距离小于该值时定性为点击事件，否则定性滑动事件
	private static final int ONCLICK_CONDITION = 30;
	//menu 代开或关闭动画时长,单位毫秒
	private static final int MENU_TOGGLE_ANI_TIME = 500;
	
	public SlidingMenu(Context context, AttributeSet attrs) {
		super(context, attrs);
		scroller = new Scroller(context);
		DisplayMetrics  dm = new DisplayMetrics();     
		WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
		wm.getDefaultDisplay().getMetrics(dm);     
		screenWidth = dm.widthPixels;
		//初始化menu 宽度为700
		menuViewWidth = 700;
	}
	
	@Override
	protected void onLayout(boolean changed, int l, int t, int r, int b) {
		super.onLayout(changed, l, t, r, b);
		if (changed) {
			Log.i("slidingMenu", "slidingMenu");
			menuView = getChildAt(0);
			MarginLayoutParams menuLayoutParams = (MarginLayoutParams) menuView.getLayoutParams();
			menuLayoutParams.width = menuViewWidth;
			menuView.setLayoutParams(menuLayoutParams);
			slidView = getChildAt(1);
			MarginLayoutParams slidLayoutParams = (MarginLayoutParams) slidView.getLayoutParams();
			slidLayoutParams.width = screenWidth;
			slidView.setLayoutParams(slidLayoutParams);
		}
	}
	
	@Override
    public boolean onInterceptTouchEvent(MotionEvent event) {
    	switch (event.getAction()) {
		case MotionEvent.ACTION_DOWN:
			isIntercepted = false;
			float x = event.getRawX();
			//仅当Menu 打开，并且touch 的坐标X 在slidingView 上时才拦截事件
			if(isMenuOpen && x >= menuViewWidth) {
				isIntercepted = true;
				return true;
			}
			break;
		default:
			break;
		}
        return super.onInterceptTouchEvent(event);
    }
	
	@SuppressLint("ClickableViewAccessibility")
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		super.onTouchEvent(event);
		switch (event.getAction()) {
		case MotionEvent.ACTION_DOWN:
			startX = event.getRawX();
			downX = startX;
			downY = event.getRawY();
			lastScrollX = slidView.getScrollX();
			scrollXDistance = 0;
			break;
		case MotionEvent.ACTION_MOVE: //当手指轻触屏幕时，可能不调用ACTION_MOVE 事件
			Log.i(TAG, "MotionEvent.ACTION_MOVE");
			if(isIntercepted) {
				float x = event.getRawX();
				float deltaX = startX - x;
				float curScrollX = slidView.getScrollX();
				float deltaScrollX = curScrollX - lastScrollX;
				//slidingView 随手指移动
				if(deltaX <= -slidView.getScrollX() && deltaX >= (-slidView.getScrollX() - menuViewWidth)) {
					slidView.scrollBy((int)deltaX, 0);
				}
				scrollXDistance += Math.abs(deltaScrollX);
				lastScrollX = curScrollX;
				startX = x;
			}
			break;
		case MotionEvent.ACTION_UP:
		case MotionEvent.ACTION_CANCEL:
			if(isIntercepted) {
				float upX = event.getRawX();
				float upY = event.getRawY();
				/*
				 * 判断点击事件的两个条件：1.up 与down 间距; 2.scrollX 的累积距离;
				 */
				if(Math.abs(upX - downX) + Math.abs(upY - downY) < ONCLICK_CONDITION 
						&& scrollXDistance < ONCLICK_CONDITION) {
					toggle();
				}else { 
					/*
					 * 当滑动距离小于menu的一半时自动回到原来位置;大于menu 一半时自动继续直至滑动完成
					 */
					if(Math.abs(slidView.getScrollX()) <= menuViewWidth/2) {
						scroller.startScroll(slidView.getScrollX(), 0, -slidView.getScrollX() , 0,300);
						isMenuOpen = false;
					}else{
						scroller.startScroll(slidView.getScrollX(), 0, -menuViewWidth-slidView.getScrollX(), 0,300);
						isMenuOpen = true;
					}
				}
			}
			invalidate();
			break;
		}
		return true;
	}
	
	public void toggle() {
        smoothScrollTo(menuViewWidth);
	}
	
	private void smoothScrollTo(int x) {
		if(isMenuOpen) {
			scroller.startScroll(-x, 0, x, 0,MENU_TOGGLE_ANI_TIME);
			isMenuOpen = false;
		}else {
			scroller.startScroll(0, 0, -x, 0,MENU_TOGGLE_ANI_TIME);
			isMenuOpen = true;
		}
		invalidate();
    }
	
	@Override  
    public void computeScroll() {  
        if (scroller.computeScrollOffset()) {  
            slidView.scrollTo(scroller.getCurrX(), 0);  
            postInvalidate();  
        }  
    }  
}
