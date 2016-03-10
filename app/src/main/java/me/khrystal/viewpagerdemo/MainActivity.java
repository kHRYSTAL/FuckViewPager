package me.khrystal.viewpagerdemo;

import android.graphics.Color;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.util.ArrayList;

import me.khrystal.fuckviewpager.FuckViewPager;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        FuckViewPager pager = (FuckViewPager)findViewById(R.id.viewpager);
        ArrayList<String> list = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            list.add(""+i);
        }

        MyPagerAdapter adapter = new MyPagerAdapter(getSupportFragmentManager(),list);
        pager.getViewPager().setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }
}
