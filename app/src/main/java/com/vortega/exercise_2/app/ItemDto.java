package com.vortega.exercise_2.app;

import java.math.BigDecimal;

/**
 * Created by vortega on 26/04/14.
 */
public class ItemDto {
    String id;
    String title;
    BigDecimal price;
    String thumbnail;

    public ItemDto( String id, String title, BigDecimal price, String thumbnail ){
        this.id = id;
        this.title = title;
        this.price = price;
        this.thumbnail = thumbnail;
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
}
