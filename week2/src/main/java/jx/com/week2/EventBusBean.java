package jx.com.week2;

import jx.com.week2.bean.NetBookBean;

public class EventBusBean {
    public int tag;
    public Object object;
    public NetBookBean bookBean;

    public NetBookBean getBookBean() {
        return bookBean;
    }

    public void setBookBean(NetBookBean bookBean) {
        this.bookBean = bookBean;
    }

    public int getTag() {
        return tag;
    }

    public void setTag(int tag) {
        this.tag = tag;
    }

    public Object getObject() {
        return object;
    }

    public void setObject(Object object) {
        this.object = object;
    }
}
