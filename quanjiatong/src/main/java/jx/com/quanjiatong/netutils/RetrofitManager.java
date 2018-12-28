package jx.com.quanjiatong.netutils;

import java.util.HashMap;
import java.util.Map;

import java.util.concurrent.TimeUnit;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import rx.Observer;
import rx.Scheduler;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class RetrofitManager<T> {
    private final String BASE_URL = "http://www.zhaoapi.cn/product/";

    private OkHttpClient mClient;
    private static RetrofitManager mRetrofitManager;
    private final BaseApis mBaseApis;

    public static synchronized RetrofitManager getInstance() {
        if (mRetrofitManager == null) {
            mRetrofitManager = new RetrofitManager();
        }
        return mRetrofitManager;
    }

    public RetrofitManager() {
        //日志拦截器
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        mClient = new OkHttpClient.Builder()
                .connectTimeout(10,TimeUnit.SECONDS)
                .readTimeout(10,TimeUnit.SECONDS)
                .writeTimeout(10,TimeUnit.SECONDS)
                .addInterceptor(interceptor)
                //失败重新加载
                .retryOnConnectionFailure(true)
                .build();
        Retrofit retrofit = new Retrofit.Builder()
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .baseUrl(BASE_URL)
                .client(mClient)
                .build();
        mBaseApis = retrofit.create(BaseApis.class);
    }
    public Map<String, RequestBody> generateRequestBody(Map<String,String> requestDataMap){
        Map<String,RequestBody> requestBodyMap = new HashMap<>();
        for (String key : requestDataMap.keySet()){
            RequestBody requestBody = RequestBody.create(MediaType.parse("multipart/form-data"),
                    requestDataMap.get(key) == null ? "" : requestDataMap.get(key));
            requestBodyMap.put(key,requestBody);
        }
        return requestBodyMap;
    }

    /**
     * get请求
     * @param url
     * @return
     */
    public RetrofitManager get(String url){
        mBaseApis.get(url)
                //后台执行在那么线程中
                .subscribeOn(Schedulers.io())
                //最终完成之后执行在那个线程
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
        return mRetrofitManager;
    }

    /**
     * 表单post请求
     * @param url
     * @param map
     * @return
     */
    public RetrofitManager postFormBody(String url,Map<String,RequestBody> map){
        if(map == null){
            map = new HashMap<>();
        }
        mBaseApis.postFormBody(url,map)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
        return mRetrofitManager;
    }

    public RetrofitManager post(String url,Map<String,String> map){
        if(map == null){
            map = new HashMap<>();
        }
        mBaseApis.psot(url,map)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
        return mRetrofitManager;
    }

    private Observer observer = new Observer<ResponseBody>() {
        @Override
        public void onCompleted() {

        }

        @Override
        public void onError(Throwable e) {
            if(listener != null){
                listener.onFailed(e.getMessage());
            }
        }

        @Override
        public void onNext(ResponseBody responseBody) {
            try {
                String data = responseBody.string();
                if(listener!=null){
                    listener.onSuccess(data);
                }
            }catch (Exception e){
                e.printStackTrace();
                if(listener != null){
                    listener.onFailed(e.getMessage());
                }
            }
        }
    };

    public DataListener listener;
    public void setdataListener(DataListener listener){
        this.listener = listener;
    }
    public interface DataListener{
        void onSuccess(String data);
        void onFailed(String erroe);
    }

}
