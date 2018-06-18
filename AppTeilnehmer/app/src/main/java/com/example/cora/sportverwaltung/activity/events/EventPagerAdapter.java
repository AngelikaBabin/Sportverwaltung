package com.example.cora.sportverwaltung.activity.events;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.cora.sportverwaltung.businesslogic.misc.Filter;

/**
 * @rajic everything
 * @kandut extracted from EventsSwipeActivity and code cleanup
 */

public class EventPagerAdapter extends FragmentPagerAdapter {

    public EventPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        Fragment result = null;

        switch (position) {
            case 0:
                result = EventsFragment.newInstance(Filter.ALL);
                break;

            case 1:
                result = EventsFragment.newInstance(Filter.CURRENT);
                break;

            case 2:
                result = EventsFragment.newInstance(Filter.PAST);
                break;
        }

        return result;
    }

    @Override
    public int getCount() {
        return Filter.values().length;
    }
}

