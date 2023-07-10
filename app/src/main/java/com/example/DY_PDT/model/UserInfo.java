package com.example.DY_PDT.model;

public class UserInfo {
    String UserName;
    String password;
    String locatsiya;

    public String getLocatsiya() {
        return locatsiya;
    }

    public void setLocatsiya(String locatsiya) {
        this.locatsiya = locatsiya;
    }

    public String getUserName() {
        return UserName;
    }

    public void setUserName(String userName) {
        UserName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
