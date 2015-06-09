/*
 *
Copyright (c) 2011 by Samsung Electronics Co., Ltd. Media Solution Center
 * All rights reserved.

This software is the confidential and proprietary information
 * of Samsung Electronics Co., Ltd("Confidential Information"). You
 * shall not disclose such Confidential Information and shall use
 * it only in accordance with the terms of the license agreement
 * you entered into with Samsung Electronics Co., Ltd.
 */

package com.example.parralaxscroll;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.animation.DecelerateInterpolator;
import android.widget.LinearLayout;
import android.widget.ScrollView;

/**
 * 
 * created at 2014. 7. 8.
 * 
 * @author sunsoo.park
 */
public class PScrollView extends ScrollView {
    private static final String TAG = PScrollView.class.getSimpleName();
    onScrollViewScrollChangedListener mScrollChangedListener;
    private Context mContext;
    private ContainerView containerView;
    private boolean windowIsOverlayed = false;
    private View mHeaderView;
    private View mTabView;

    public PScrollView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        mContext = context;
        initValue(context);
    }

    public PScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        initValue(context);
    }

    public PScrollView(Context context) {
        super(context, null);
        mContext = context;
        initValue(context);
    }

    private void initValue(Context context) {
        windowIsOverlayed = ((Activity) context).getWindow().hasFeature(Window.FEATURE_ACTION_BAR_OVERLAY);
    }


    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        if (getChildCount() > 0) {
            ViewGroup view = (ViewGroup) getChildAt(0);
            removeView(view);
            addcontainerView(view);
        }
        Log.v(TAG, "onFinishInflate : " + getChildCount());
    }

    private void addcontainerView(View view) {
        if (containerView == null) {
            containerView = new ContainerView(mContext);
            containerView.addView(view);
            addView(containerView);
        } else {
            containerView.addView(view);
        }

    }

    public View getcontainerView() {
        return containerView;
    }

    public boolean hascontainerView() {
        if (this.getChildCount() > 0 && containerView != null) {
            return true;
        }
        return false;
    }

    public boolean removecontainerView() {
        if (this.getChildCount() > 0) {
            if (containerView != null) {
                removeView(containerView);
                return true;
            }
        }
        return false;
    }

    int startY = -1;
    int startViewTop = 0;
    Drawable saved;

    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        if (mScrollChangedListener != null) {
            mScrollChangedListener.onScrollChanged(l, t, oldl, oldt);
        }
        super.onScrollChanged(l, t, oldl, oldt);
        // TODO if needs implements to make header view doing parallax scrolling
        if (mHeaderView != null) {
            mHeaderView.setTranslationY(t - (t / 2));
        }
        // getcontainerView().setTranslationY(t - (t / 2));
        int actionbarH = 0;
        if (isActionBarOverlaid()) {
            actionbarH = ((Activity) mContext).getActionBar().getHeight();
        }

        ViewGroup list = (ViewGroup) ((ViewGroup) getcontainerView()).getChildAt(1);

        if (mTabView != null) {
            int tabpViewYpos = (int) mTabView.getY();
            int scrolly = getScrollY();
            if (mTabView.getTop() - scrolly <= getTop() + actionbarH) {
                if (startY == -1) {
                    startY = tabpViewYpos - actionbarH;
                    saved = getcontainerView().getBackground();
                    Drawable d = mTabView.getBackground();
                    // getcontainerView().setBackground(d);
                    ((Activity) mContext).getActionBar().setBackgroundDrawable(d);
                    getcontainerView().animate().setInterpolator(new DecelerateInterpolator(2.0F)).setDuration(250L).start();
                }
                mTabView.setTranslationY(t - startY);
            } else {
                Drawable f = getResources().getDrawable(android.R.color.transparent);
                ((Activity) mContext).getActionBar().setBackgroundDrawable(f);
                if (saved != null)
                    getcontainerView().setBackground(saved);
                startY = -1;
                mTabView.setTranslationY(0);
            }

        }

    }

    private boolean isActionBarOverlaid() {
        return ((Activity) mContext).getWindow().hasFeature(Window.FEATURE_ACTION_BAR_OVERLAY);
    }

    public interface onScrollViewScrollChangedListener {
        public void onScrollChanged(int l, int t, int oldl, int oldt);
    }

    public void setOnScrollListener(onScrollViewScrollChangedListener listener) {
        this.mScrollChangedListener = listener;
    };

    private static class ContainerView extends LinearLayout {

        public ContainerView(Context context) {
            super(context);
        }

    }

    public void addTabView(View tab) {
        this.mTabView = tab;

    }

    public void setTabView(View tab) {
        this.mTabView = tab;
    }

    public void addHeaderView(View header) {
        this.mHeaderView = header;
        containerView.addView(header,0);
    }

    public void setHeaderView(View header) {
        this.mHeaderView = header;
    }

}
