package com.neatfaith.dhikrtracker.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.neatfaith.dhikrtracker.R;
import com.neatfaith.dhikrtracker.core.model.Item;

import java.util.ArrayList;

public class ItemListAdapter extends BaseAdapter {

    private Context activity;
    private ArrayList<Item> items;

    public ItemListAdapter(Context activity, ArrayList<Item> items) {
        super();
        this.activity = activity;
        this.items = items;
    }

    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public Object getItem(int position) {
        return items.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }


    private class ViewHolder {

        TextView typeTextView;
        TextView timestampTextView;
        TextView titleTextView;
        TextView quantityTextView;
        TextView usernameTextView;

    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {


        ItemListAdapter.ViewHolder holder;
        LayoutInflater inflater =  ((Activity) activity).getLayoutInflater();

        if (convertView == null)
        {
            convertView = inflater.inflate(R.layout.cell_itemlist, null);
            holder = new ItemListAdapter.ViewHolder();
            holder.typeTextView = (TextView) convertView.findViewById(R.id.item_typename);
            holder.timestampTextView = (TextView) convertView.findViewById(R.id.item_timestamp);
            holder.titleTextView = (TextView) convertView.findViewById(R.id.item_title);
            holder.quantityTextView = (TextView) convertView.findViewById(R.id.item_quantity);
            holder.usernameTextView = (TextView) convertView.findViewById(R.id.item_username);



            convertView.setTag(holder);
        }
        else
        {
            holder = (ItemListAdapter.ViewHolder) convertView.getTag();
        }


        Item item = this.items.get(position);


        holder.typeTextView.setText(item.getSubItem().getType().getTitle());
        holder.titleTextView.setText(item.getSubItem().getTitle());
        holder.timestampTextView.setText(item.getTimestampString());
        holder.quantityTextView.setText(item.getQuantity());
        holder.usernameTextView.setText(item.getUser() != null ? item.getUser().getName() : "" );

        return convertView;
    }
}
