package com.neatfaith.dhikrtracker.core.model;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class ItemTypeSubItem implements Serializable {

    private long id;
    private String title;
    private String titleArabic;
    private String meaning;
    private ItemType type;
    private boolean canModify;


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

    public String getMeaning() {
        return meaning;
    }

    public void setMeaning(String meaning) {
        this.meaning = meaning;
    }

    public ItemType getType() {
        return type;
    }

    public void setType(ItemType type) {
        this.type = type;
    }

    public boolean canModify() {
        return canModify;
    }

    public void setCanModify(int canModify) {

        this.canModify = canModify > 0;
    }

    public void setCanModify(boolean canModify) {

        this.canModify = canModify;
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
        map.put("meaning",this.getMeaning());
        map.put("type",""+this.getType().toString());

        return map.toString();
    }
}
