package me.khrystal.viewpagerdemo;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.ViewGroup;

import java.util.ArrayList;

/**
 * @FileName: me.khrystal.viewpagerdemo.MyPagerAdapter.java
 * @Fuction:
 * @author: kHRYSTAL
 * @email: 723526676@qq.com
 * @date: 2016-03-09 20:57
 * @UpdateUser:
 * @UpdateDate:
 */
public class MyPagerAdapter  extends FragmentPagerAdapter {

    private ArrayList<String> titles;


    public MyPagerAdapter(FragmentManager fm, ArrayList<String> list) {
        super(fm);
        this.titles = list;
    }


    @Override
    public CharSequence getPageTitle(int position) {
        return titles.get(position);
    }

    @Override
    public int getCount() {
        return titles.size();
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        return super.instantiateItem(container, position);
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
    }

    @Override
    public Fragment getItem(int position) {
        Bundle b = new Bundle();
        b.putString("title", titles.get(position%titles.size()));
        return FragmentListContent.getInstance(b);
    }
}
