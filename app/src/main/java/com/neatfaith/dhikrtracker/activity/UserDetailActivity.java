package com.neatfaith.dhikrtracker.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import com.neatfaith.dhikrtracker.R;
import com.neatfaith.dhikrtracker.adapter.ItemListAdapter;
import com.neatfaith.dhikrtracker.core.manager.DBManager;
import com.neatfaith.dhikrtracker.core.model.Item;
import com.neatfaith.dhikrtracker.core.model.User;

import java.util.ArrayList;

public class UserDetailActivity extends AppCompatActivity {

    private ListView mListView;

    private ItemListAdapter itemsAdapter;
    private ArrayList<Item> itemsArray = new ArrayList<>();

    private User user;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_detail);


        if (savedInstanceState != null){
            user = (User) savedInstanceState.getSerializable("user");
        }
        else {
            user = (User) getIntent().getExtras().getSerializable("user");
        }

        setTitle(getString(R.string.details_for,user.getName()));


        DBManager.getInstance().getItemsForUserId(itemsArray,user.getId());


        mListView = (ListView) findViewById(R.id.userdetail_listView);
        itemsAdapter = new ItemListAdapter(this,itemsArray);
        mListView.setAdapter(itemsAdapter);



    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putSerializable("user",user);

    }
}
