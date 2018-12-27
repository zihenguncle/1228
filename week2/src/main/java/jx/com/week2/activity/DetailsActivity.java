package jx.com.week2.activity;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.umeng.socialize.UMAuthListener;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.UMShareConfig;
import com.umeng.socialize.bean.SHARE_MEDIA;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import jx.com.week2.Apis;
import jx.com.week2.EventBusBean;
import jx.com.week2.R;
import jx.com.week2.Tag;
import jx.com.week2.adapter.ImageAdapter;
import jx.com.week2.bean.DetailsBean;
import jx.com.week2.bean.NetBookBean;
import jx.com.week2.okhttp.OkHttpUtils;

public class DetailsActivity extends AppCompatActivity {

    @BindView(R.id.details_viewpager)
    ViewPager viewPager;
    @BindView(R.id.details_title)
    TextView title;
    @BindView(R.id.details_price)
    TextView price;
    @BindView(R.id.details_addcar)
    Button addcarbut;
    @BindView(R.id.details_head_image)
    SimpleDraweeView imageView;
    private ImageAdapter adapter;

    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            int currentItem = viewPager.getCurrentItem();
            currentItem++;
            viewPager.setCurrentItem(currentItem);
            sendEmptyMessageDelayed(0,2000);
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        ButterKnife.bind(this);
        UMShareConfig config = new UMShareConfig();
        config.isNeedAuthOnGetUserInfo(true);
        UMShareAPI.get(DetailsActivity.this).setShareConfig(config);


        EventBus.getDefault().register(this);
        Intent intent = getIntent();
        int pid = intent.getIntExtra("pid", 65);
        Map<String,String> map = new HashMap<>();
        map.put("pid",pid+"");
        OkHttpUtils.getInstance().postEnqueue(Apis.DETAILS_URL,map,DetailsBean.class);

    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        UMShareAPI.get(this).onActivityResult(requestCode, resultCode, data);
    }

    @OnClick({R.id.details_addcar})
    public void setonClick(View view){
        switch (view.getId()){
            case R.id.details_addcar:
                //获得UMShareAPI实例
                UMShareAPI umShareAPI =  UMShareAPI.get(DetailsActivity.this);
                umShareAPI.getPlatformInfo(this, SHARE_MEDIA.QQ, new UMAuthListener() {
                    @Override
                    public void onStart(SHARE_MEDIA share_media) {

                    }

                    @Override
                    public void onComplete(SHARE_MEDIA share_media, int i, Map<String, String> map) {
                        imageView.setImageURI(map.get("profile_image_url"));
                    }

                    @Override
                    public void onError(SHARE_MEDIA share_media, int i, Throwable throwable) {

                    }

                    @Override
                    public void onCancel(SHARE_MEDIA share_media, int i) {

                    }
                });
                break;
                default:
                    break;

        }
    }
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void getEnevtData(EventBusBean eventBusBean){
            DetailsBean bean = (DetailsBean) eventBusBean.getObject();
            DetailsBean.DataBean data = bean.getData();
            title.setText(data.getTitle());
            price.setText(data.getPrice()+"");
            adapter = new ImageAdapter(this);
            adapter.setStr(data.split());
            viewPager.setCurrentItem(adapter.getCount()/2);
            viewPager.setAdapter(adapter);
            handler.sendEmptyMessageDelayed(0,2000);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
        handler.removeCallbacksAndMessages(null);
    }

}
