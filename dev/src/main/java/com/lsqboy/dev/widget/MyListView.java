package com.lsqboy.dev.widget;

import android.content.Context;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AbsListView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dell on 2017/9/10.
 */

public class MyListView extends ListView implements AbsListView.OnScrollListener {
    private View mHeaderView;
    private List<View> viewList = new ArrayList<>();
    int totalItemCount;// 总数量
    int lastVisibieItem;// 最后一个可见的item;
    boolean isLoading;// 判断变量
    IloadListener iLoadListener;// 接口变量
    public MyListView(Context context) {
        super(context);
        this.setOnScrollListener(this);
    }

    public MyListView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.setOnScrollListener(this);
    }

    public MyListView(Context context, AttributeSet attrs, int defStyleAttr) {

        super(context, attrs, defStyleAttr);
        this.setOnScrollListener(this);
    }

//    @Override
//    public boolean onInterceptTouchEvent(MotionEvent ev) {
//        return true;
//    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (mHeaderView != null) {
            if (viewList != null && viewList.size() > 0) {
               for (int i = 0 ; i < viewList.size(); i ++) {
                   if (i== 0){
                       int x = Math.round(ev.getX());
                       int y = Math.round(ev.getY());
                       Rect rect = new Rect();
                       viewList.get(i).getHitRect(rect);
                       if (rect.contains(x, y)) {
                           return false;
                       }
                   }else {
                       int x = Math.round(ev.getX());
                       int y = Math.round(ev.getY());
                       Rect rect = new Rect();
                       viewList.get(i).getHitRect(rect);
                       if (rect.contains(x, y)) {
                           return true;
                       }
                   }

               }
            }
        }
        return super.dispatchTouchEvent(ev);
//        return true;
    }

    @Override
    public void addHeaderView(View v) {
        this.mHeaderView = v;
        viewList.add(v);
        super.addHeaderView(v);
    }

    @Override
    public void onScrollStateChanged(AbsListView absListView, int i) {
        if (totalItemCount == lastVisibieItem && i == SCROLL_STATE_IDLE) {
            if (!isLoading) {
                isLoading = true;
                // 加载更多（获取接口）
                iLoadListener.onLoad();
            }
        }
    }

    @Override
    public void onScroll(AbsListView absListView, int i, int i1, int i2) {
        this.lastVisibieItem = i + i1;
        this.totalItemCount = i2;
    }

    public void setInterface(IloadListener iLoadListener) {

        this.iLoadListener = iLoadListener;
    }


    // 加载更多数据的回调接口
    public interface IloadListener {
        public void onLoad();
    }

    // 加载完成通知隐藏
    public void loadComplete() {
        isLoading = false;
//        footer.findViewById(R.id.footer_layout).setVisibility(View.GONE);

    }
}
