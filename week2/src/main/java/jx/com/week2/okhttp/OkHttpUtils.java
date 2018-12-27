package jx.com.week2.okhttp;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Handler;
import android.os.Looper;

import com.google.gson.Gson;

import org.greenrobot.eventbus.EventBus;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import jx.com.week2.EventBusBean;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;

public class OkHttpUtils {



    public OkHttpClient mClient;

    public static OkHttpUtils instance;
    public static OkHttpUtils getInstance(){
        if(instance == null){
            synchronized (OkHttpUtils.class){
                instance = new OkHttpUtils();
            }
        }
        return instance;
    }

    public OkHttpUtils() {
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        //设置读取超时，连接超时，写超时，添加日志拦截
        mClient = new OkHttpClient.Builder()
                .readTimeout(10,TimeUnit.SECONDS)
                .connectTimeout(10,TimeUnit.SECONDS)
                .writeTimeout(10,TimeUnit.SECONDS)
                .addInterceptor(interceptor)
                .build();


    }

    /**
     *
     * @param url
     * @param map
     * @param clazz
     */
    public void postEnqueue(String url, Map<String,String> map, final Class clazz){
        FormBody.Builder builder = new FormBody.Builder();
        for (Map.Entry<String,String> entry : map.entrySet()){
            builder.add(entry.getKey(),entry.getValue());
        }
        RequestBody body = builder.build();
        Request request = new Request.Builder()
                .post(body)
                .url(url)
                .build();
        Call call = mClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, final IOException e) {
                EventBusBean eventBusBean = new EventBusBean();
                eventBusBean.setObject(e.getMessage());
                EventBus.getDefault().post(eventBusBean);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                try {
                    String string = response.body().string();
                    final Object o = new Gson().fromJson(string, clazz);
                    EventBusBean eventBusBean = new EventBusBean();
                    eventBusBean.setObject(o);
                    EventBus.getDefault().post(eventBusBean);
                }catch (Exception e){
                    EventBusBean eventBusBean = new EventBusBean();
                    eventBusBean.setObject(e.getMessage());
                    EventBus.getDefault().post(eventBusBean);
                }
            }
        });
    }
    //是否有可用网络
    public boolean hasNetwork(Context context){
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = cm.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isAvailable();
    }

}
