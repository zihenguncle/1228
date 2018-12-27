package jx.com.week2.bean;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

@Entity
public class DataBean {
    @Id
    private Long id;
    private int pid;
    private double price;
    private String title;
    private String images;
    @Generated(hash = 329486840)
    public DataBean(Long id, int pid, double price, String title, String images) {
        this.id = id;
        this.pid = pid;
        this.price = price;
        this.title = title;
        this.images = images;
    }
    @Generated(hash = 908697775)
    public DataBean() {
    }
    private String replace(){
        return images.replace("https","http");
    }
    public String split(){
        String[] split = replace().split("\\|");
        return split[0];
    }
    public String getImages() {
        return this.images;
    }
    public void setImages(String images) {
        this.images = images;
    }
    public String getTitle() {
        return this.title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public double getPrice() {
        return this.price;
    }
    public void setPrice(double price) {
        this.price = price;
    }
    public int getPid() {
        return this.pid;
    }
    public void setPid(int pid) {
        this.pid = pid;
    }
    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }


}
