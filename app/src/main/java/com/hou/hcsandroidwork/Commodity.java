package com.hou.hcsandroidwork;

/**
 * Created by 86186 on 2022/6/9.
 */

public class Commodity {
    private String comm_name;
    private String comm_price;
    private String comm_degree;

    public Commodity(String comm_name, String comm_price,
                String comm_degree) {
        this.comm_name = comm_name;
        this.comm_price=comm_price;
        this.comm_degree = comm_degree;
    }
    public Commodity() {}

    public String getComm_name() {
        return comm_name;
    }

    public void setComm_name(String comm_name) {
        this.comm_name = comm_name;
    }

    public String getComm_price() {
        return comm_price;
    }

    public void setComm_price(String comm_price) {
        this.comm_price = comm_price;
    }

    public String getComm_degree() {
        return comm_degree;
    }

    public void setComm_degree(String comm_degree) {
        this.comm_degree = comm_degree;
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
