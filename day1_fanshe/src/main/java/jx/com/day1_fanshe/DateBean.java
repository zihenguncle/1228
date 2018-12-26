package jx.com.day1_fanshe;

public class DateBean {
    private String name;
    private String sex;

    public DateBean() {

    }

    public DateBean(String name, String sex) {
        this.name = name;
        this.sex = sex;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }
}
