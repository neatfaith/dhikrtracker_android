package com.neatfaith.dhikrtracker.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.neatfaith.dhikrtracker.R;
import com.neatfaith.dhikrtracker.core.model.ItemType;
import com.neatfaith.dhikrtracker.core.model.ItemTypeSubItem;

import java.util.ArrayList;

public class ItemTypeSubItemListAdapter extends BaseAdapter {

    private Context activity;
    private ArrayList<ItemTypeSubItem> subItems;

    public ItemTypeSubItemListAdapter(Context activity, ArrayList<ItemTypeSubItem> subItems) {
        super();
        this.activity = activity;
        this.subItems = subItems;
    }

    @Override
    public int getCount() {
        return subItems.size();
    }

    @Override
    public Object getItem(int position) {
        return subItems.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    private class ViewHolder {

        TextView titleTextView;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ItemTypeSubItemListAdapter.ViewHolder holder;
        LayoutInflater inflater =  ((Activity) activity).getLayoutInflater();

        if (convertView == null)
        {
            convertView = inflater.inflate(R.layout.cell_itemtypesubitemlist, null);
            holder = new ItemTypeSubItemListAdapter.ViewHolder();
            holder.titleTextView = (TextView) convertView.findViewById(R.id.subitem_title);


            convertView.setTag(holder);
        }
        else
        {
            holder = (ItemTypeSubItemListAdapter.ViewHolder) convertView.getTag();
        }


        ItemTypeSubItem subItem = subItems.get(position);

        holder.titleTextView.setText(subItem.getTitle());


        return convertView;
    }
}
