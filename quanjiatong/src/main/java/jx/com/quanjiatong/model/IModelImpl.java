package jx.com.quanjiatong.model;


import com.google.gson.Gson;

import java.util.Map;

import jx.com.quanjiatong.callback.MyCallBack;
import jx.com.quanjiatong.netutils.RetrofitManager;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;

public class IModelImpl<T> implements IModel {
    @Override
    public void requestData(String url, Map<String, String> params, final Class clazz, final MyCallBack callBack) {
        Map<String, ResponseBody> map = RetrofitManager.getInstance().generateRequestBody(params);

        RetrofitManager.getInstance().postFormBody(url, map).setdataListener(new RetrofitManager.DataListener() {
            @Override
            public void onSuccess(String data) {
                try {
                    Object o = new Gson().fromJson(data, clazz);
                    if (callBack != null) {
                        callBack.onSuccess(o);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    if (callBack != null) {
                        callBack.onFail(e.getMessage());
                    }
                }

            }

            @Override
            public void onFailed(String erroe) {
                if (callBack != null) {
                    callBack.onFail(erroe);
                }
            }

        });

    }

}
