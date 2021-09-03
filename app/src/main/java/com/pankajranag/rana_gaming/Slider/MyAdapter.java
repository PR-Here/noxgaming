package com.pankajranag.rana_gaming.Slider;

import android.content.Context;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.pankajranag.rana_gaming.Slider.fragment.first_f;
import com.pankajranag.rana_gaming.Slider.fragment.four_f;
import com.pankajranag.rana_gaming.Slider.fragment.second_f;
import com.pankajranag.rana_gaming.Slider.fragment.third_f;

public class MyAdapter extends FragmentPagerAdapter {

    private Context myContext;
    int totalTabs;

    public MyAdapter(Context context, FragmentManager fm, int totalTabs) {
        super(fm);
        myContext = context;
        this.totalTabs = totalTabs;
    }

    // this is for fragment tabs
    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                first_f homeFragment = new first_f();
                return homeFragment;
            case 1:
                second_f sportFragment = new second_f();
                return sportFragment;
            case 2:
                third_f movieFragment = new third_f();
                return movieFragment;
            case 3:
                four_f four_f = new four_f();
                return four_f;
            default:
                return null;
        }
    }

    // this counts total number of tabs
    @Override
    public int getCount() {
        return totalTabs;
    }
}
