package jx.com.quanjiatong.adapter;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import jx.com.quanjiatong.fragment.CommodityFragment;
import jx.com.quanjiatong.fragment.DetalisFragment;
import jx.com.quanjiatong.fragment.DiscussFragment;

public class TabLayoutAdapter extends FragmentPagerAdapter {

    String[] str = new String[]{"商品","详情","评论"};

    public TabLayoutAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int i) {
        switch (i){
            case 1:
                return new CommodityFragment();
            case 0:
                return new DetalisFragment();
            case 2:
                return new DiscussFragment();
                default:
                    return new Fragment();
        }
    }

    @Override
    public int getCount() {
        return str.length;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return str[position];
    }
}
