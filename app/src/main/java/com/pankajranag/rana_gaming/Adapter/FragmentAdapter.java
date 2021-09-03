package com.pankajranag.rana_gaming.Adapter;

import android.content.Context;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.pankajranag.rana_gaming.Mode.ClassicMode;
import com.pankajranag.rana_gaming.Mode.TdmMode;
import com.pankajranag.rana_gaming.Mode.ArcadeMode;

public class FragmentAdapter extends FragmentPagerAdapter {

    private Context myContext;
    int totalTabs;

    public FragmentAdapter(Context context, FragmentManager fm, int totalTabs) {
        super(fm);
        myContext = context;
        this.totalTabs = totalTabs;
    }

    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                ClassicMode classicMode = new ClassicMode();
                return classicMode;
            case 1:
                ArcadeMode arcadeMode = new ArcadeMode();
                return arcadeMode;
            case 2:
                TdmMode requestFragment = new TdmMode();
                return requestFragment;
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return totalTabs;
    }
}
