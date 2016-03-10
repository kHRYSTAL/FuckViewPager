package me.khrystal.fuckviewpager;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Point;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;

/**
 * @FileName: me.khrystal.fuckviewpager.FuckViewPager.java
 * @Fuction:
 * @author: kHRYSTAL
 * @email: 723526676@qq.com
 * @date: 2016-03-09 20:39
 * @UpdateUser:
 * @UpdateDate:
 */
public class FuckViewPager extends FrameLayout implements ViewPager.OnPageChangeListener{

    private int mPagerMargin = 0;
    private int mPaddingLeft = 130;
    private int mPaddingRight = 130;
    private int mPageLimit = 3;
    private int mDefaultTopMargin = 10;
    private int mDefaultBottomMargin = 10;

    private Context mContext;

    private ViewPager mPager;
    boolean mNeedRedraw = false;


    public FuckViewPager(Context context) {
        this(context, null, 0);
    }

    public FuckViewPager(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public FuckViewPager(Context context,AttributeSet attrs,int defStyle){
        super(context, attrs,defStyle);
        TypedArray a = context.obtainStyledAttributes(attrs,
                R.styleable.FuckViewPager);
        mPageLimit = a.getInteger(
                R.styleable.FuckViewPager_offscreenPageLimit, mPageLimit);
        mPagerMargin = a.getInteger(R.styleable.FuckViewPager_pagerMargin,
                mPagerMargin);
        mPaddingLeft = a.getInteger(R.styleable.FuckViewPager_paddingLeft,
                mPaddingLeft);
        mPaddingRight = a.getInteger(R.styleable.FuckViewPager_paddingRight,
                mPaddingRight);
        a.recycle();
        View.inflate(context,R.layout.fuck_viewpager,this);
        mPager = (ViewPager)findViewById(R.id.fuck_this);
        mPager.setOffscreenPageLimit(mPageLimit);
        mPager.setPageMargin(mPagerMargin);
        mPager.setOnPageChangeListener(this);
        init(context);
    }

    private void init(Context context){
        setClipChildren(false);
        setLayerType(View.LAYER_TYPE_SOFTWARE,null);
    }


    /**
     * set the number of view cache,better > 3
     */
    public void setFuckOffscreenPageLimit(int pagerDis) {
        if (pagerDis < 3) {
            throw new IllegalArgumentException(
                    "the offscreen page limit must >3");
        }
        if (mPager!=null)
            mPager.setOffscreenPageLimit(pagerDis);
    }

    public void setFuckPagerMargin(int pagerDis) {
        mPager.setPageMargin(pagerDis);
    }

    public ViewPager getViewPager(){
        return mPager;
    }

    private Point mCenter = new Point();
    private Point mInitialTouch = new Point();

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mCenter.x = w/2;
        mCenter.y = h/2;

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                mInitialTouch.x = (int)event.getX();
                mInitialTouch.y = (int)event.getY();
                break;
            default:
                event.offsetLocation(mCenter.x - mInitialTouch.x,mCenter.y - mInitialTouch.y);
                break;
        }
        return mPager.dispatchTouchEvent(event);
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        final int scrollX = mPager.getScrollX();
        final int childCount = mPager.getChildCount();
        for (int i = 0;i<childCount;i++){
            final View child = mPager.getChildAt(i);
            final ViewPager.LayoutParams lp = (ViewPager.LayoutParams)child.getLayoutParams();
            if (lp.isDecor)
                continue;
            final float transformPos = (float)(child.getLeft() -scrollX)/child.getWidth();
            transformPage(child,transformPos);
        }
        if (mNeedRedraw)
            invalidate();
    }

    @Override
    public void onPageSelected(int position) {

    }

    @Override
    public void onPageScrollStateChanged(int state) {
        mNeedRedraw = (state != ViewPager.SCROLL_STATE_IDLE);
    }

    public static final float MAX_SCALE = 1.0f;
    public static final float MIN_SCALE = 0.95f;
    public static final float MIN_ALPHA = 0.5f;
    public void transformPage(View page,float position){

        int pageWidth = page.getWidth();
        int pageHeight = page.getHeight();

        if (position < -1) {
            page.setScaleX(MIN_SCALE);
            page.setScaleX(MIN_SCALE);
        } else if (position <= 1) {
            float scaleFactor = Math.max(MIN_SCALE, 1 - Math.abs(position));
            float vertMargin = pageHeight * (1 - scaleFactor) / 2;
            float horzMargin = pageWidth * (1 - scaleFactor) / 2;
            if (position < 0) {
                page.setTranslationX(horzMargin - vertMargin / 2);
            } else {
                page.setTranslationX(-horzMargin + vertMargin / 2);
            }


            page.setScaleX(scaleFactor);
            page.setScaleY(scaleFactor);
            page.clearAnimation();
        }else {
            page.setScaleX(MIN_SCALE);
            page.setScaleY(MIN_SCALE);
        }
//        if (position < -1) {
//            position = -1;
//        } else if (position > 1) {
//            position = 1;
//        }
//
//        float tempScale = position < 0 ? 1 + position : 1 - position;
//
//        float slope = (MAX_SCALE - MIN_SCALE) / 1;
//        float scaleValue = MIN_SCALE + tempScale * slope;
//        page.setScaleX(scaleValue);
//        page.setScaleY(scaleValue);
//        ViewHelper.setScaleX(page,scaleValue);
//        ViewHelper.setScaleY(page,scaleValue);
    }

}
