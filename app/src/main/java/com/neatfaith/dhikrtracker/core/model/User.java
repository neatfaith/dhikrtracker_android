package com.neatfaith.dhikrtracker.core.model;


import com.neatfaith.dhikrtracker.core.utils.Utils;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 *  This is the user that we are recording the adhkar, fasting etc. We can keep records for multiple users.
 */

public class User {

    private long id;
    private String name;
    private long created;
    private String createdTimestamp;

    public User() {
        this.id = 0;
        this.name = null;
        this.created = 0;

    }


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCreatedTimestamp() {
        return createdTimestamp;
    }

    public long getCreated() {
        return created;
    }

    public void setCreated(long created) {
        this.created = created;

        //convert to readable timestamp
        this.createdTimestamp = Utils.timestampToTimeString(this.getCreated());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return id == user.id;
    }

    @Override
    public int hashCode() {

        return (int) getId();
    }

    @Override
    public String toString() {

        Map<String,String> map = new HashMap<>();

        map.put("id",""+this.getId());
        map.put("name",this.getName());
        map.put("created",""+this.getCreated());
        map.put("createdTimestamp", this.getCreatedTimestamp());



        return map.toString();
    }
}
