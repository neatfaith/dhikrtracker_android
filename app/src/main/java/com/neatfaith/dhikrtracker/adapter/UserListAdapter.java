package com.neatfaith.dhikrtracker.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.neatfaith.dhikrtracker.R;
import com.neatfaith.dhikrtracker.core.model.User;

import java.util.ArrayList;

public class UserListAdapter extends BaseAdapter{

    private Context activity;
    private ArrayList<User> users;

    public UserListAdapter(Context activity, ArrayList<User> users) {
        super();
        this.activity = activity;
        this.users = users;
    }

    @Override
    public int getCount() {
        return users.size();
    }

    @Override
    public Object getItem(int i) {
        return users.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }


    private class ViewHolder {

        TextView nameTextView;
        TextView dateTextView;

    }


    @Override
    public View getView(int position, View convertView, ViewGroup viewGroup) {

        ViewHolder holder;
        LayoutInflater inflater =  ((Activity) activity).getLayoutInflater();

        if (convertView == null)
        {
            convertView = inflater.inflate(R.layout.cell_userlist, null);
            holder = new ViewHolder();
            holder.nameTextView = (TextView) convertView.findViewById(R.id.username_textView);
            holder.dateTextView = (TextView) convertView.findViewById(R.id.userdate_textView);


            convertView.setTag(holder);
        }
        else
        {
            holder = (ViewHolder) convertView.getTag();
        }



        User user = this.users.get(position);


        holder.nameTextView.setText(user.getName());
        holder.dateTextView.setText(user.getCreatedTimestamp());


        return convertView;
    }
}
