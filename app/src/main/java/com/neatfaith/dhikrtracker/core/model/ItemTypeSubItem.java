package com.neatfaith.dhikrtracker.core.model;

import java.util.HashMap;
import java.util.Map;

public class ItemTypeSubItem {

    private long id;
    private String title;
    private String titleArabic;
    private long typeId;

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

    public String getTitleArabic() {
        return titleArabic;
    }

    public void setTitleArabic(String titleArabic) {
        this.titleArabic = titleArabic;
    }

    public long getTypeId() {
        return typeId;
    }

    public void setTypeId(long typeId) {
        this.typeId = typeId;
    }

    @Override
    public String toString() {

        Map<String,String> map = new HashMap<>();
        map.put("id",""+this.getId());
        map.put("title",this.getTitle());
        map.put("titleArabic",this.getTitleArabic());
        map.put("typeId",""+this.getTypeId());

        return map.toString();
    }
}
