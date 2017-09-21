package com.aganticsoft.phyxhidephotosandvideos.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.aganticsoft.phyxhidephotosandvideos.view.fragment.BaseFragment;

import java.util.List;

/**
 * Created by ttson
 * Date: 9/22/2017.
 */

public class HomePagerAdapter extends FragmentPagerAdapter {
    private List<BaseFragment> frgs;

    public HomePagerAdapter(FragmentManager fm, List<BaseFragment> frgs) {
        super(fm);

        this.frgs = frgs;
    }

    @Override
    public Fragment getItem(int position) {
        return frgs.get(position);
    }

    @Override
    public int getCount() {
        return frgs.size();
    }
}
