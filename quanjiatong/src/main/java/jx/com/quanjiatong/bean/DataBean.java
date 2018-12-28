package jx.com.quanjiatong.bean;

public class DataBean {
        /**
         * bargainPrice : 11800.0
         * createtime : 2017-10-10T17:33:37
         * detailUrl : https://item.m.jd.com/product/4338107.html?utm#_source=androidapp&utm#_medium=appshare&utm#_campaign=t#_335139774&utm#_term=QQfriends
         * images : https://m.360buyimg.com/n0/jfs/t6700/155/2098998076/156185/6cf95035/595dd5a5Nc3a7dab5.jpg!q70.jpg
         * itemtype : 0
         * pid : 57
         * price : 5199.0
         * pscid : 40
         * salenum : 4343
         * sellerid : 1
         * subhead : 【i5 MX150 2G显存】全高清窄边框 8G内存 256固态硬盘 支持指纹识别 预装WIN10系统
         * title : 小米(MI)Air 13.3英寸全金属轻薄笔记本(i5-7200U 8G 256G PCle SSD MX150 2G独显 FHD 指纹识别 Win10）银

         */

        private Long id;
        private String images;
        private int pid;
        private double price;
        private String title;

        private String replace(){
            return images.replace("https","http");
        }

        public String splic(){
            String[] split = replace().split("\\|");
            return split[0];
        }
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getImages() {
        return images;
    }

    public void setImages(String images) {
        this.images = images;
    }

    public int getPid() {
        return pid;
    }

    public void setPid(int pid) {
        this.pid = pid;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
