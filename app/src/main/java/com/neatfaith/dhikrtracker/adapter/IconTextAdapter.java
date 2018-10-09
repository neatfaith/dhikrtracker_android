package com.neatfaith.dhikrtracker.adapter;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.neatfaith.dhikrtracker.R;

import java.util.ArrayList;

public class IconTextAdapter extends BaseAdapter {

    private Context activity;
    private ArrayList<String> titleArray;
    private ArrayList<Drawable> iconArray;

    public IconTextAdapter(Context activity, ArrayList<String> titleArray, ArrayList<Drawable> iconArray) {
        this.activity = activity;
        this.titleArray = titleArray;
        this.iconArray = iconArray;
    }

    @Override
    public int getCount() {
        return titleArray.size();
    }

    @Override
    public Object getItem(int position) {
        return titleArray.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }


    private class ViewHolder {

        ImageView iconImageView;
        TextView titleTextView;

    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        IconTextAdapter.ViewHolder holder;
        LayoutInflater inflater =  ((Activity) activity).getLayoutInflater();

        if (convertView == null)
        {
            convertView = inflater.inflate(R.layout.cell_icon_text_list, null);
            holder = new IconTextAdapter.ViewHolder();
            holder.iconImageView = (ImageView) convertView.findViewById(R.id.icontext_icon);
            holder.titleTextView = (TextView) convertView.findViewById(R.id.icontext_title);

            convertView.setTag(holder);
        }
        else
        {
            holder = (IconTextAdapter.ViewHolder) convertView.getTag();
        }


        String title = titleArray.get(position);
        Drawable image = iconArray.get(position);

        holder.iconImageView.setImageDrawable(image);
        holder.titleTextView.setText(title);


        return convertView;


    }
}
