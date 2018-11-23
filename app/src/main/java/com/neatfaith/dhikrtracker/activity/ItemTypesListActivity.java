package com.neatfaith.dhikrtracker.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.neatfaith.dhikrtracker.R;
import com.neatfaith.dhikrtracker.adapter.ItemTypeListAdapter;
import com.neatfaith.dhikrtracker.core.manager.DBManager;
import com.neatfaith.dhikrtracker.core.model.ItemType;

import java.util.ArrayList;

public class ItemTypesListActivity extends AppCompatActivity {

    private ListView mListView;


    ArrayList<ItemType> itemTypes = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_types_list);
        setTitle(R.string.select_category);


        mListView = (ListView) findViewById(R.id.itemtypes_listView);

        DBManager.getInstance().getAllItemTypes(itemTypes);

        ItemTypeListAdapter adapter = new ItemTypeListAdapter(this,itemTypes);
        mListView.setAdapter(adapter);

        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Intent intent = new Intent();

                ItemType type = itemTypes.get(position);

                intent.putExtra("itemType", type);


                setResult(RESULT_OK,intent);
                finish();
            }
        });

    }
}
