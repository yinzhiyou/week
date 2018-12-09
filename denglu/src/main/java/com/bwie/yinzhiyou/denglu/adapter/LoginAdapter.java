package com.bwie.yinzhiyou.denglu.adapter;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.bwie.yinzhiyou.denglu.fragment.FragmentHome;
import com.bwie.yinzhiyou.denglu.fragment.FragmentMine;

public class LoginAdapter extends FragmentPagerAdapter {
    private String[] menus=new String[]{"首页","我的"};
    public LoginAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int i) {
        switch (i){
            case 0:
                return new FragmentHome();
            case 1:
                return new FragmentMine();
        }
        return null;
    }

    @Override
    public int getCount() {
        return menus.length;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return menus[position];
    }
}
