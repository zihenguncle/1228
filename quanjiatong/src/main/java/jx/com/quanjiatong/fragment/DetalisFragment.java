package jx.com.quanjiatong.fragment;

import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;
import butterknife.ButterKnife;
import jx.com.quanjiatong.R;
import jx.com.quanjiatong.activity.DetailsActivity;
import jx.com.quanjiatong.base.BaseFragment;
import jx.com.quanjiatong.bean.EventBusBean;

public class DetalisFragment extends BaseFragment {

    @BindView(R.id.text_title)
    TextView title;

    @Override
    protected int getViewById() {
        return R.layout.detalis_fragment_layout;
    }

    @Override
    protected void initData(View view) {
        ButterKnife.bind(this,view);
        if(!EventBus.getDefault().isRegistered(this)){
            EventBus.getDefault().register(this);
        }


    }
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void getEventData(EventBusBean busBean){
        if(busBean.getTag()==1){
            Object object = busBean.getObject();
            title.setText(object.toString());
            Toast.makeText(getActivity(),object.toString(),Toast.LENGTH_SHORT).show();
            ((DetailsActivity)getActivity()).setCurrent(0);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
