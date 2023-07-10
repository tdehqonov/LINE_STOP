package com.example.DY_PDT.model;

import android.media.Image;

import java.util.Date;

public class Item {
  private long id;
  private String title;
  private String description;
  private int image;
  private String area;
  private String line;
  private String shop;
  private String device;
  private String type;
  private String department;
  private String supplier;
  private String created_at;
  private String  from;
  private String to;
  private String minutes;
  private String alarm;

  public Item(String description, String shop, String area, String department, String created_at, String from, String to, String minutes) {
    this.shop = shop;
    this.area = area;
    this.department=department;
    this.description = description;
    this.created_at=created_at;
    this.from=from;
    this.to=to;
    this.minutes=minutes;
  }

//  public Item(long id, String title, String description, int image, String area, String line, String shop, String device, String type, String department, String supplier, String created_at, String from, String to, String minutes, String alarm) {
//    this.id = id;
//    this.title = title;
//    this.description = description;
//    this.image = image;
//    this.area = area;
//    this.line = line;
//    this.shop = shop;
//    this.device = device;
//    this.type = type;
//    this.department = department;
//    this.supplier = supplier;
//    this.created_at = created_at;
//    this.from = from;
//    this.to = to;
//    this.minutes = minutes;
//    this.alarm = alarm;
//  }



  public String getAlarm() {
    return alarm;
  }

  public void setAlarm(String alarm) {
    this.alarm = alarm;
  }


  public String getMinutes() {
    return minutes;
  }

  public void setMinutes(String minutes) {
    this.minutes = minutes;
  }

  public String getCreated_at() {
    return created_at;
  }

  public void setCreated_at(String created_at) {
    this.created_at = created_at;
  }

  public String getFrom() {
    return from;
  }

  public void setFrom(String from) {
    this.from = from;
  }

  public String getTo() {
    return to;
  }

  public void setTo(String to) {
    this.to = to;
  }

  public String getArea() {
    return area;
  }

  public void setArea(String area) {
    this.area = area;
  }

  public String getLine() {
    return line;
  }

  public void setLine(String line) {
    this.line = line;
  }

  public String getShop() {
    return shop;
  }

  public void setShop(String shop) {
    this.shop = shop;
  }

  public String getDevice() {
    return device;
  }

  public void setDevice(String device) {
    this.device = device;
  }

  public String getType() {
    return type;
  }

  public void setType(String type) {
    this.type = type;
  }

  public String getDepartment() {
    return department;
  }

  public void setDepartment(String department) {
    this.department = department;
  }

  public String getSupplier() {
    return supplier;
  }

  public void setSupplier(String supplier) {
    this.supplier = supplier;
  }

  private boolean published;


  public Item(long id,int image ,String title, String description, String area, String line, String shop, String device, String type, String department, String supplier, boolean published) {
    this.id = id;
    this.title = title;
    this.image=image;
    this.description = description;
    this.area = area;
    this.line = line;
    this.shop = shop;
    this.device = device;
    this.type = type;
    this.department = department;
    this.supplier = supplier;
    this.published = published;


  }

  public Item(long id,int image , String title, String description, boolean published) {
    this.id = id;
    this.image=image;
    this.title = title;
    this.description = description;
    this.published = published;
  }

  public Item(String title, String description,int image) {
    this.title = title;
    this.description = description;
    this.image=image;
  }

  public Item(String description, String shop, String area, String department, String created_at, String from, String to, String minutes,String alarm) {
    this.shop = shop;
    this.area = area;
    this.department=department;
    this.description = description;
    this.created_at=created_at;
    this.from=from;
    this.to=to;
    this.minutes=minutes;
    this.alarm=alarm;
  }


  public int getImage() {
    return image;
  }

  public void setImage(int image) {
    this.image = image;
  }

  public void setId(long id) {
    this.id = id;
  }
  
  public long getId() {
    return id;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public boolean isPublished() {
    return published;
  }

  public void setPublished(boolean isPublished) {
    this.published = isPublished;
  }

  @Override
  public String toString() {
    return "Tutorial [id=" + id + ", title=" + title + ", desc=" + description + ", published=" + published + "]";
  }

}
