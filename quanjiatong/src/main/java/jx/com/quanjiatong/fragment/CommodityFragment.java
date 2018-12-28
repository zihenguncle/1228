package jx.com.quanjiatong.fragment;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import org.greenrobot.eventbus.EventBus;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import jx.com.quanjiatong.MainActivity;
import jx.com.quanjiatong.R;
import jx.com.quanjiatong.activity.DetailsActivity;
import jx.com.quanjiatong.adapter.ImageAdapter;
import jx.com.quanjiatong.base.BaseFragment;
import jx.com.quanjiatong.bean.DetailsBean;
import jx.com.quanjiatong.bean.EventBusBean;
import jx.com.quanjiatong.presenter.IPresenterImpl;
import jx.com.quanjiatong.url.Apis;
import jx.com.quanjiatong.view.IView;

public class CommodityFragment extends BaseFragment implements IView {

    private IPresenterImpl iPresenter;
    private ImageAdapter adapter;

    @BindView(R.id.com_viewpaget)
    ViewPager viewPager;
    @BindView(R.id.com_title)
    TextView title;
    @BindView(R.id.com_price)
    TextView price;
    private DetailsBean.DataBean data1;

    @SuppressLint("HandlerLeak")
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
    protected int getViewById() {
        return R.layout.commodity_fragment_layout;
    }

    @Override
    protected void initData(View view) {
        ButterKnife.bind(this,view);

        //实例化
        iPresenter = new IPresenterImpl(this);
        //请求数据
        getData();

    }

    @OnClick({R.id.com_title,R.id.com_price})
    public void setonClick(View view){
        EventBusBean busBean = new EventBusBean();
        switch (view.getId()){
            case R.id.com_title:
                busBean.setObject(data1.getTitle());
                busBean.setTag(1);
                EventBus.getDefault().post(busBean);
                break;
            case R.id.com_price:
                busBean.setObject(data1.getPrice()+"");
                busBean.setTag(2);
                EventBus.getDefault().post(busBean);
                break;
                default:
                    break;
        }
    }
    private void getData() {
        Map<String,String> map = new HashMap<>();
        String pid = ((DetailsActivity) getActivity()).getPid();
        map.put("pid",pid);
        iPresenter.startRequest(Apis.DETAILS,map,DetailsBean.class);
    }

    @Override
    public void getDataSuccess(Object data) {
        if(data instanceof DetailsBean){
            DetailsBean bean = (DetailsBean) data;
            data1 = bean.getData();
            adapter = new ImageAdapter(getActivity());
            adapter.setStr(data1.split());
            viewPager.setAdapter(adapter);
            viewPager.setCurrentItem(adapter.getCount()/2);
            handler.sendEmptyMessageDelayed(0,2000);
            title.setText(data1.getTitle());
            price.setText(data1.getPrice()+"");
        }
    }

    @Override
    public void getDataFail(String error) {
        Toast.makeText(getActivity(),error,Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        handler.removeCallbacksAndMessages(null);
    }
}
