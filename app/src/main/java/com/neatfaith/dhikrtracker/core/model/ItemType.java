package com.neatfaith.dhikrtracker.core.model;

import java.util.HashMap;
import java.util.Map;

/**
 * Holds item types like Adhkar, Fasting, Prayers etc
 */

public class ItemType {

    private long id;
    private String title;

    public ItemType() {
        this.id = 0;
        this.title = null;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public String toString() {

        Map<String,String> map = new HashMap<>();
        map.put("id",""+this.getId());
        map.put("title",this.getTitle());

        return map.toString();
    }
}
