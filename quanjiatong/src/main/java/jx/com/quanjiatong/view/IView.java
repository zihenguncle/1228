package jx.com.quanjiatong.view;

public interface IView<T> {
    void getDataSuccess(T data);
    void getDataFail(String error);
}
