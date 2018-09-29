package com.neatfaith.dhikrtracker.core.model;

import android.content.res.Resources;

import com.neatfaith.dhikrtracker.App;
import com.neatfaith.dhikrtracker.R;
import com.neatfaith.dhikrtracker.core.utils.Utils;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class Item implements Serializable {

    private long id;
    private ItemTypeSubItem subItem;
    private User user;


    private long tally; //count for adhkar, pages for writing or reading
    private long minutes; //listening
    private long timestamp;
    private String timestampString;



    public Item(long id, ItemTypeSubItem subItem, User user, long tally, long minutes, long timestamp) {
        this.id = id;
        this.subItem = subItem;
        this.user = user;
        this.tally = tally;
        this.minutes = minutes;

        this.setTimestamp(timestamp);
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public ItemTypeSubItem getSubItem() {
        return subItem;
    }

    public void setSubItem(ItemTypeSubItem subItem) {
        this.subItem = subItem;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public long getTally() {
        return tally;
    }

    public void setTally(long tally) {
        this.tally = tally;
    }

    public long getMinutes() {
        return minutes;
    }

    public void setMinutes(long minutes) {
        this.minutes = minutes;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;

        //convert to readable timestamp
        this.timestampString = Utils.timestampToTimeString(this.getTimestamp());
    }

    public String getTimestampString() {
        return timestampString;
    }

    public String getQuantity(){

        long type_id = this.getSubItem().getType().getId();
        Resources res = App.getContext().getResources();

        if ( type_id == 1){ //Adhkar

            return String.format(res.getString(R.string.times_count),this.getTally());
        }
        else if (type_id == 4 || type_id == 5){ //Reading or writing
            return String.format(res.getString(R.string.pages_count),this.getTally());
        }
        else if (type_id == 6){
            return String.format(res.getString(R.string.minutes_count),this.getTally());
        }
        else{
            return "";
        }

    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Item item = (Item) o;
        return id == item.id;
    }

    @Override
    public int hashCode() {

        return (int)getId();
    }

    @Override
    public String toString() {

        Map<String,String> map = new HashMap<>();
        map.put("id",""+this.getId());
        map.put("user",this.getUser().toString());
        map.put("subitem",this.getSubItem().toString());
        map.put("tally",""+this.getTally());
        map.put("minutes",""+this.getMinutes());
        map.put("timestamp",""+this.getTimestamp());

        return map.toString();
    }
}
