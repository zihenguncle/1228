package jx.com.quanjiatong;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import jx.com.quanjiatong.adapter.LinearBookAdapter;
import jx.com.quanjiatong.adapter.NetBookAdapter;
import jx.com.quanjiatong.bean.DataBean;
import jx.com.quanjiatong.bean.NetBookBean;
import jx.com.quanjiatong.presenter.IPresenterImpl;
import jx.com.quanjiatong.url.Apis;
import jx.com.quanjiatong.view.IView;

public class MainActivity extends AppCompatActivity implements IView {

    private int spanCount = 2;
    private IPresenterImpl iPresenter;
    private NetBookAdapter adapter;
    private LinearBookAdapter linearAdapter;
    @BindView(R.id.main_grid_recycle)
    RecyclerView recyclerView;
    @BindView(R.id.main_linear_recycle)
    RecyclerView linear_recycle;
    @BindView(R.id.main_image_kind)
    ImageView image_kind;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //绑定Butterknife
        ButterKnife.bind(this);
        //设置布局管理器
        setLaout();
        iPresenter = new IPresenterImpl(this);

        //请求数据,并设置
        getData();

    }

    private void getData() {
        Map<String,String> map = new HashMap<>();
        map.put("keywords","手机");
        map.put("page",1+"");
        iPresenter.startRequest(Apis.SEARCH_URL,map,NetBookBean.class);
        adapter = new NetBookAdapter(this);
        recyclerView.setAdapter(adapter);
        linearAdapter = new LinearBookAdapter(this);
        linear_recycle.setAdapter(linearAdapter);
    }

    //点击切换视图
    @OnClick({R.id.main_image_kind})
    public void setonClick(View view){
        switch (view.getId()){
            case R.id.main_image_kind:
                if(recyclerView.getVisibility()==View.VISIBLE){
                    recyclerView.setVisibility(View.INVISIBLE);
                    linear_recycle.setVisibility(View.VISIBLE);
                }else {
                    recyclerView.setVisibility(View.VISIBLE);
                    linear_recycle.setVisibility(View.INVISIBLE);
                }
                break;
                default:
                    break;
        }
    }
    //设置布局管理器
    private void setLaout() {
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, spanCount);
        gridLayoutManager.setOrientation(OrientationHelper.VERTICAL);
        recyclerView.setLayoutManager(gridLayoutManager);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        linear_recycle.setLayoutManager(layoutManager);
    }

    @Override
    public void getDataSuccess(Object data) {
        Toast.makeText(MainActivity.this,data.toString(),Toast.LENGTH_SHORT).show();
        if(data instanceof NetBookBean){
            NetBookBean bookBean = (NetBookBean) data;
            List<DataBean> data1 = bookBean.getData();
            adapter.setList(data1);
            linearAdapter.setList(data1);
        }
    }

    @Override
    public void getDataFail(String error) {
        Toast.makeText(MainActivity.this,error,Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        iPresenter.onDetach();
    }

}
