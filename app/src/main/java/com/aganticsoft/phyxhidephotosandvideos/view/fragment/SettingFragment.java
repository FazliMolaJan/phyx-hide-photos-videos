package com.aganticsoft.phyxhidephotosandvideos.view.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.aganticsoft.phyxhidephotosandvideos.R;

import butterknife.ButterKnife;

/**
 * Created by ttson
 * Date: 9/22/2017.
 */

public class SettingFragment extends BaseFragment {

    public static SettingFragment newInstance() {
        SettingFragment frg = new SettingFragment();
        Bundle bundle = new Bundle();

        frg.setArguments(bundle);

        return frg;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_setting, container, false);
        ButterKnife.bind(this , v);

        return v;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }
}
