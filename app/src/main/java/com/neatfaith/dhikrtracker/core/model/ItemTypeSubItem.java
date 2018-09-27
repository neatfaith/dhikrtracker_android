package com.neatfaith.dhikrtracker.core.model;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class ItemTypeSubItem {

    private long id;
    private String title;
    private String titleArabic;
    private ItemType type;

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

    public ItemType getType() {
        return type;
    }

    public void setType(ItemType type) {
        this.type = type;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ItemTypeSubItem that = (ItemTypeSubItem) o;
        return id == that.id;
    }

    @Override
    public int hashCode() {

        return (int) getId();
    }

    @Override
    public String toString() {

        Map<String,String> map = new HashMap<>();
        map.put("id",""+this.getId());
        map.put("title",this.getTitle());
        map.put("titleArabic",this.getTitleArabic());
        map.put("type",""+this.getType().toString());

        return map.toString();
    }
}
