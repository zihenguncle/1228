package jx.com.quanjiatong.callback;

/**
 * @param <T>
 */
public interface MyCallBack<T> {
    void onSuccess(T data);
    void onFail(String error);
}
