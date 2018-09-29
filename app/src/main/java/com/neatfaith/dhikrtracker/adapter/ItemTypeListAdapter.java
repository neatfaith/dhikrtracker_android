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

import java.util.ArrayList;

public class ItemTypeListAdapter extends BaseAdapter {

    private Context activity;
    private ArrayList<ItemType> itemTypes;

    public ItemTypeListAdapter(Context activity, ArrayList<ItemType> itemTypes) {
        super();
        this.activity = activity;
        this.itemTypes = itemTypes;
    }

    @Override
    public int getCount() {
        return itemTypes.size();
    }

    @Override
    public Object getItem(int position) {
        return itemTypes.get(position);
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

        ItemTypeListAdapter.ViewHolder holder;
        LayoutInflater inflater =  ((Activity) activity).getLayoutInflater();

        if (convertView == null)
        {
            convertView = inflater.inflate(R.layout.cell_itemtypelist, null);
            holder = new ItemTypeListAdapter.ViewHolder();
            holder.titleTextView = (TextView) convertView.findViewById(R.id.title_textView);


            convertView.setTag(holder);
        }
        else
        {
            holder = (ItemTypeListAdapter.ViewHolder) convertView.getTag();
        }


        ItemType type = itemTypes.get(position);

        holder.titleTextView.setText(type.getTitle());


        return convertView;
    }
}
