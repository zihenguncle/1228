package jx.com.quanjiatong.model;




import java.util.Map;

import jx.com.quanjiatong.callback.MyCallBack;

public interface IModel {
    void requestData(String url, Map<String, String> params, Class clazz, MyCallBack callBack);
}
