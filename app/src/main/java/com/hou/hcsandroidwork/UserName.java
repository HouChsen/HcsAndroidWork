package com.hou.hcsandroidwork;

/**
 * Created by 86186 on 2022/6/6.
 */


import android.app.Activity;
import android.app.Application;

import java.util.LinkedList;
import java.util.List;


public class UserName extends Application {
    private String userNAME = "";


    public String getUserNAME() {
        return userNAME;
    }

    public void setUserNAME(String name) {
        userNAME = name;
    }
}
