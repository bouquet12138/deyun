package com.example.common_lib.java_bean;

import java.io.Serializable;
import java.util.List;

public class StoreBean implements Serializable {

    private int head_img_id;
    private ImageBean head_img;

    private String product_img_ids;
    private List<ImageBean> product_imgs;

    private int store_id;
    private String store_name;
    private String store_type;
    private String province;
    private String city;
    private String district;
    private String detailed_address;
    private String longitude;
    private String latitude;
    private String business_hours;
    private String store_describe;
    private String contact_phone;

    public StoreBean(String store_name, String store_type, String province, String city,
                     String district, String detailed_address, String business_hours, String store_describe, String contact_phone) {
        this.store_name = store_name;
        this.store_type = store_type;
        this.province = province;
        this.city = city;
        this.district = district;
        this.detailed_address = detailed_address;
        this.business_hours = business_hours;
        this.store_describe = store_describe;
        this.contact_phone = contact_phone;
    }

    public void setStore_id(int store_id) {
        this.store_id = store_id;
    }

    public int getStore_id() {
        return store_id;
    }

    public void setStore_name(String store_name) {
        this.store_name = store_name;
    }

    public String getStore_name() {
        return store_name;
    }

    public void setStore_type(String store_type) {
        this.store_type = store_type;
    }

    public String getStore_type() {
        return store_type;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getProvince() {
        return province;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCity() {
        return city;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getDistrict() {
        return district;
    }

    public void setDetailed_address(String detailed_address) {
        this.detailed_address = detailed_address;
    }

    public String getDetailed_address() {
        return detailed_address;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setBusiness_hours(String business_hours) {
        this.business_hours = business_hours;
    }

    public String getBusiness_hours() {
        return business_hours;
    }

    public void setStore_describe(String store_describe) {
        this.store_describe = store_describe;
    }

    public String getStore_describe() {
        return store_describe;
    }

    public void setContact_phone(String contact_phone) {
        this.contact_phone = contact_phone;
    }

    public String getContact_phone() {
        return contact_phone;
    }

    public int getHead_img_id() {
        return head_img_id;
    }

    public void setHead_img_id(int head_img_id) {
        this.head_img_id = head_img_id;
    }

    public ImageBean getHead_img() {
        return head_img;
    }

    public void setHead_img(ImageBean head_img) {
        this.head_img = head_img;
    }

    public String getProduct_img_ids() {
        return product_img_ids;
    }

    public void setProduct_img_ids(String product_img_ids) {
        this.product_img_ids = product_img_ids;
    }

    public List<ImageBean> getProduct_imgs() {
        return product_imgs;
    }

    public void setProduct_imgs(List<ImageBean> product_imgs) {
        this.product_imgs = product_imgs;
    }
}
