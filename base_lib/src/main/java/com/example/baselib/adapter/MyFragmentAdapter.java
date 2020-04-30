package com.example.baselib.adapter;


import com.example.baselib.util.CollectionsUtil;

import java.util.List;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

public class MyFragmentAdapter extends FragmentPagerAdapter {

    private List<Fragment> mFragments;
    private List<String> mPageTitles;

    public MyFragmentAdapter(FragmentManager fm, List<Fragment> fragments, List<String> pageTitles) {
        super(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        mFragments = fragments;
        mPageTitles = pageTitles;
    }

    public MyFragmentAdapter(FragmentManager fm, List<Fragment> fragments) {
        super(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        mFragments = fragments;
    }

  /*  public MyFragmentAdapter(FragmentManager fm) {
        super(fm);
    }*/

    @Override
    public Fragment getItem(int i) {
        return mFragments.get(i);
    }

    @Override
    public int getCount() {
        return mFragments.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        if (!CollectionsUtil.isEmpty(mPageTitles))
            return mPageTitles.get(position);
        return null;
    }

}
