package jx.com.week2;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import jx.com.week2.activity.DetailsActivity;
import jx.com.week2.adapter.MainRecycleAdapter;
import jx.com.week2.bean.DataBean;
import jx.com.week2.bean.NetBookBean;

import jx.com.week2.greendao.DaoMaster;
import jx.com.week2.greendao.DaoSession;
import jx.com.week2.greendao.DataBeanDao;
import jx.com.week2.okhttp.OkHttpUtils;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.main_recycle)
    RecyclerView recyclerView;
    private MainRecycleAdapter adapter;
    private DataBeanDao dataBeanDao;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(this, "user_db",null);
        SQLiteDatabase db = helper.getWritableDatabase();
        DaoMaster daoMaster = new DaoMaster(db);
        DaoSession daoSession = daoMaster.newSession();
        dataBeanDao = daoSession.getDataBeanDao();


        //绑定
        ButterKnife.bind(this);
//设置adapter
        adapter = new MainRecycleAdapter(this);
        if(!OkHttpUtils.getInstance().hasNetwork(MainActivity.this)){
            Toast.makeText(MainActivity.this,"88888",Toast.LENGTH_SHORT).show();
            List<DataBean> list = dataBeanDao.queryBuilder().list();
            adapter.setMlist(list);
        }else {
            //请求数据
            Map<String,String> map = new HashMap<>();
            map.put("keywords","笔记本");
            OkHttpUtils.getInstance().postEnqueue(Apis.MAIN_NETBOOK_URL,map,NetBookBean.class);
        }
        recyclerView.setAdapter(adapter);
        //注册
        EventBus.getDefault().register(this);
        //设置布局管理器
        GridLayoutManager layoutManager = new GridLayoutManager(this, 2);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);


        adapter.setOnLongCallBack(new MainRecycleAdapter.OnLongCallBack() {
            @Override
            public void longClick(int pid) {
                Intent intent = new Intent(MainActivity.this,DetailsActivity.class);
                intent.putExtra("pid",pid);
                startActivity(intent);
            }
        });
    }
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void getEventData(EventBusBean eventBusBean){
            NetBookBean bookBean = (NetBookBean) eventBusBean.getObject();
            adapter.setMlist(bookBean.getData());
        List<DataBean> data = bookBean.getData();
        for (int i = 0; i < data.size(); i++) {
            DataBean bean = new DataBean();
            bean.setTitle(data.get(i).getTitle());
            bean.setPrice(data.get(i).getPrice());
            bean.setImages(data.get(i).getImages());
            bean.setPid(data.get(i).getPid());
            bean.setId((long) i);
            dataBeanDao.insert(bean);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //解绑
        EventBus.getDefault().unregister(this);
    }
}
