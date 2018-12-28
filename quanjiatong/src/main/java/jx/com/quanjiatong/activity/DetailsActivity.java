package jx.com.quanjiatong.activity;

import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import jx.com.quanjiatong.R;
import jx.com.quanjiatong.adapter.TabLayoutAdapter;
import jx.com.quanjiatong.presenter.IPresenterImpl;
import jx.com.quanjiatong.view.IView;

public class DetailsActivity extends AppCompatActivity{

    @BindView(R.id.details_tablayout)
    TabLayout tabLayout;
    @BindView(R.id.details_viewpager)
    ViewPager viewPager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        //绑定Butterknife
        ButterKnife.bind(this);
        //设置Adapter
        viewPager.setAdapter(new TabLayoutAdapter(getSupportFragmentManager()));
        tabLayout.setupWithViewPager(viewPager);

    }

    public String getPid(){
        //获得传来的pid
        Intent intent = getIntent();
        int pid = intent.getIntExtra("pid", 65);
        return pid+"";
    }

    public void setCurrent(int i){
        viewPager.setCurrentItem(i);
    }



}
