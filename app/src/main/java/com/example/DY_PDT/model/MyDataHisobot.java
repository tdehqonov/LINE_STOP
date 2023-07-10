package com.example.DY_PDT.model;

public class MyDataHisobot {
    private String shop;
    private int soni;
    private int minut;
    private String filterHisobot;

//    public MyDataHisobot(String shop, int soni, int minut) {
//        this.shop = shop;
//        this.soni = soni;
//        this.minut = minut;
//    }

    public MyDataHisobot(String shop, int soni, int minut, String filterHisobot) {
        this.shop = shop;
        this.soni = soni;
        this.minut = minut;
        this.filterHisobot = filterHisobot;
    }

    public String getFilterHisobot() {
        return filterHisobot;
    }

    public void setFilterHisobot(String filterHisobot) {
        this.filterHisobot = filterHisobot;
    }

    public String getShop() {
        return shop;
    }

    public void setShop(String shop) {
        this.shop = shop;
    }

    public int getSoni() {
        return soni;
    }

    public void setSoni(int soni) {
        this.soni = soni;
    }

    public int getMinut() {
        return minut;
    }

    public void setMinut(int minut) {
        this.minut = minut;
    }
}
