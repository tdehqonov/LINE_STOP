package com.example.DY_PDT.model;

public class area {

    String area_title;
    String area_filter;

    public area( String area_title, String area_filter) {
        this.area_title = area_title;
        this.area_filter = area_filter;
    }


    public String getArea_title() {
        return area_title;
    }

    public void setArea_title(String area_title) {
        this.area_title = area_title;
    }

    public String getArea_filter() {
        return area_filter;
    }

    public void setArea_filter(String area_filter) {
        this.area_filter = area_filter;
    }
}
