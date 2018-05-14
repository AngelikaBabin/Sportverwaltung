package com.example.cora.sportverwaltung.activity.events;

import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.v4.view.ViewPager;

import com.example.cora.sportverwaltung.R;
import com.example.cora.sportverwaltung.activity.base.BaseActivity;

public class EventsSwipeActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // display layout
        setContent(R.layout.activity_swipe_events);

        // setup swipe behaviour
        initSwipeBehavior(R.id.container);
    }

    private void initSwipeBehavior(@IdRes int container) {
        EventPagerAdapter eventPagerAdapter = new EventPagerAdapter(getSupportFragmentManager());
        ViewPager viewPager = findViewById(container);
        viewPager.setAdapter(eventPagerAdapter);
    }
}
