package com.neatfaith.dhikrtracker.core.model;

import android.content.res.Resources;

import com.neatfaith.dhikrtracker.App;
import com.neatfaith.dhikrtracker.R;
import com.neatfaith.dhikrtracker.core.utils.Utils;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class Item implements Serializable {

    private long id;
    private ItemTypeSubItem subItem;
    private User user;


    private long tally = 0; //count for adhkar, pages for writing or reading
    private long minutes = 0; //listening

    private String formattedTally;

    private long timestamp;
    private String timestampString;



    public Item(long id, ItemTypeSubItem subItem, User user, long tally, long minutes, long timestamp) {
        this.id = id;
        this.subItem = subItem;
        this.user = user;
        this.minutes = minutes;

        this.setTally(tally);
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
        this.formattedTally = Utils.formatNumber(tally);

    }

    public String getFormattedTally() {
        return formattedTally;
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

        if ( Item.isAdhkarId(type_id)){ //Adhkar

            return res.getString(R.string.times_count,this.getFormattedTally());
        }
        else if (Item.isReadingId(type_id) || Item.isWritingId(type_id)){ //Reading or writing
            return res.getString(R.string.pages_count,this.getFormattedTally());
        }
        else if (Item.isPrayerId(type_id) || Item.isListeningId(type_id)){
            return res.getString(R.string.minutes_count,this.getFormattedTally());
        }
        else{

            if (getTally() > 0){
                return ""+getFormattedTally();
            }
            else if (getMinutes() > 0){
                return ""+getMinutes();
            }

            return "";
        }

    }

    public static boolean isAdhkarId(long id){
        return id == 1;
    }
    public static boolean isFastingId(long id){
        return id == 2;
    }
    public static boolean isPrayerId(long id){
        return id == 3;
    }
    public static boolean isReadingId(long id){
        return id == 4;
    }
    public static boolean isWritingId(long id){
        return id == 5;
    }
    public static boolean isListeningId(long id){
        return id == 6;
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
