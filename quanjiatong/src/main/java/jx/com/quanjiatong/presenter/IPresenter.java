package jx.com.quanjiatong.presenter;

import java.util.Map;

public interface IPresenter {
    void startRequest(String url, Map<String, String> params, Class clazz);
}
