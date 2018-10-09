package com.neatfaith.dhikrtracker.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ListView;

import com.neatfaith.dhikrtracker.R;
import com.neatfaith.dhikrtracker.adapter.ItemTypeSubItemListAdapter;
import com.neatfaith.dhikrtracker.core.manager.DBManager;
import com.neatfaith.dhikrtracker.core.model.ItemType;
import com.neatfaith.dhikrtracker.core.model.ItemTypeSubItem;
import com.neatfaith.dhikrtracker.core.utils.Globals;

import java.util.ArrayList;

public class SubItemListActivity extends AppCompatActivity {

    ListView mListView;

    ItemType itemType;
    ArrayList<ItemTypeSubItem> subItems = new ArrayList<>();
    ItemTypeSubItemListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sub_item_list);

        mListView = (ListView) findViewById(R.id.subitems_listView);


        if (savedInstanceState != null){
            itemType = (ItemType) savedInstanceState.getSerializable("itemType");
        }
        else {
            itemType = (ItemType) getIntent().getExtras().getSerializable("itemType");
        }


        DBManager.getInstance().getSubItemsForType(subItems,itemType.getId()+"");

        adapter = new ItemTypeSubItemListAdapter(this,subItems);
        mListView.setAdapter(adapter);



    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putSerializable("itemType",itemType);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.subitemlist_activity_actions, menu);

        return super.onCreateOptionsMenu(menu);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.action_add_subitem:

                Intent intent = new Intent(SubItemListActivity.this,AddSubItemActivity.class);

                Bundle bundle = new Bundle();
                bundle.putSerializable("itemType",itemType);

                intent.putExtras(bundle);
                startActivity(intent);

                return true;

            default:
                return super.onOptionsItemSelected(item);
        }

    }
}
