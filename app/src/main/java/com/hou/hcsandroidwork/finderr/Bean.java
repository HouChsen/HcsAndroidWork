package com.hou.hcsandroidwork.finderr;

public class Bean {

    private int iconId;
    private String title;
    private String phone;

    public Bean(int iconId, String title, String phone) {
        this.iconId = iconId;
        this.title = title;
        this.phone = phone;
    }

    public int getIconId() {
        return iconId;
    }

    public void setIconId(int iconId) {
        this.iconId = iconId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }


}