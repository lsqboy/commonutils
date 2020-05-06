package com.lsqboy.dev.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;

import androidx.viewpager.widget.ViewPager;

/**
 * NoScrollViewPager
 * <p>
 * Created by lsqboy on 2019/1/11.
 * Email：g.lsqboy@gmail.com
 */
public class NoScrollViewPager extends ViewPager {

    private boolean enabled;

    public NoScrollViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.enabled = false;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (this.enabled) {
            return super.onTouchEvent(event);
        }

        return false;
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent event) {
        if (this.enabled) {
            return super.onInterceptTouchEvent(event);
        }

        return false;
    }

    @Override
    public void setCurrentItem(int item, boolean smoothScroll) {
        super.setCurrentItem(item, smoothScroll);
    }

    @Override
    public void setCurrentItem(int item) {
        //去除页面切换时的滑动翻页效果
        super.setCurrentItem(item, false);
    }

    /**
     * Enable or disable the swipe navigation
     * @param enabled
     */
    public void setPagingEnabled(boolean enabled) {
        this.enabled = enabled;
    }
}
