package com.example.DY_PDT.model;

public class shop {
    String shop_title;
    String shop_filter;

    public shop(String  shop_title, String shop_filter) {
        this.shop_title = shop_title;
        this.shop_filter = shop_filter;
    }

    public String getShop_filter() {
        return shop_filter;
    }

    public void setShop_filter(String shop_filter) {
        this.shop_filter = shop_filter;
    }

    public String getShop_title() {
        return shop_title;
    }

    public void setShop_title(String shop_title) {
        this.shop_title = shop_title;
    }
}
