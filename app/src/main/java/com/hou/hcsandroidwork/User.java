package com.hou.hcsandroidwork;

/**
 * Created by 86186 on 2022/6/6.
 */


/**
 * Created by 86186 on 2022/4/20.
 */

public class User {
    private String user_name;
    private String user_password;
    private String user_phone;


    public User(String user_name, String user_password,
                String user_phone) {
        this.user_name = user_name;
        this.user_password=user_password;
        this.user_phone = user_phone;
    }
    public User() {}

    public String getUser_name() {
        return user_name;
    }
    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getUser_password() {
        return user_password;
    }
    public void setUser_password(String user_password) {
        this.user_password = user_password;
    }

    public String getUser_phone() {
        return user_phone;
    }
    public void setUser_phone(String user_phone) {this.user_phone = user_phone;}
    @Override
    public String toString() {
        return user_password;
    }
}

