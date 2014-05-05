package com.vortega.exercise_2.app;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * Created by vortega on 26/04/14.
 */
public class ItemDto implements Serializable {
    private String id;
    private String title;
    private BigDecimal price;
    private String thumbnail;
    private String picture;

    public ItemDto( String id, String title, BigDecimal price, String thumbnail ){
        this.id = id;
        this.title = title;
        this.price = price;
        this.thumbnail = thumbnail;
    }

    public ItemDto( String id, String title, BigDecimal price, String thumbnail, String picture ){
        this.id = id;
        this.title = title;
        this.price = price;
        this.thumbnail = thumbnail;
        this.picture = picture;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }
}
