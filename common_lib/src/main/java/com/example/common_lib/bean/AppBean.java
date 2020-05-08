package com.example.common_lib.bean;

public class AppBean {
    private Integer app_id;
    private String public_date;
    private String app_name;
    private String app_icon;
    private String app_url;
    private String app_describe;
    private String version_name;

    @Override
    public String toString() {
        return "AppBean{" +
                "app_id=" + app_id +
                ", public_date='" + public_date + '\'' +
                ", app_name='" + app_name + '\'' +
                ", app_icon='" + app_icon + '\'' +
                ", app_url='" + app_url + '\'' +
                ", app_describe='" + app_describe + '\'' +
                ", version_name='" + version_name + '\'' +
                '}';
    }

    public Integer getApp_id() {
        return app_id;
    }

    public void setApp_id(Integer app_id) {
        this.app_id = app_id;
    }

    public String getPublic_date() {
        return public_date;
    }

    public void setPublic_date(String public_date) {
        this.public_date = public_date;
    }

    public String getApp_name() {
        return app_name;
    }

    public void setApp_name(String app_name) {
        this.app_name = app_name;
    }

    public String getApp_icon() {
        return app_icon;
    }

    public void setApp_icon(String app_icon) {
        this.app_icon = app_icon;
    }

    public String getApp_url() {
        return app_url;
    }

    public void setApp_url(String app_url) {
        this.app_url = app_url;
    }

    public String getApp_describe() {
        return app_describe;
    }

    public void setApp_describe(String app_describe) {
        this.app_describe = app_describe;
    }

    public String getVersion_name() {
        return version_name;
    }

    public void setVersion_name(String version_name) {
        this.version_name = version_name;
    }
}
