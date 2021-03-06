package com.example.common_lib.java_bean;

import java.io.Serializable;

public class FlashContentBean implements Serializable {

    private int flash_content_id;
    private int flash_id;
    private String content;
    private int image_id;

    public FlashContentBean(String content, int image_id) {
        this.content = content;
        this.image_id = image_id;
    }

    public int getFlash_content_id() {
        return flash_content_id;
    }

    public void setFlash_content_id(int flash_content_id) {
        this.flash_content_id = flash_content_id;
    }

    public int getFlash_id() {
        return flash_id;
    }

    public void setFlash_id(int flash_id) {
        this.flash_id = flash_id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }


    public int getImage_id() {
        return image_id;
    }

    public void setImage_id(int image_id) {
        this.image_id = image_id;
    }

}
