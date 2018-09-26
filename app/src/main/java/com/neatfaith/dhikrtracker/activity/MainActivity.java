package com.neatfaith.dhikrtracker.activity;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.neatfaith.dhikrtracker.R;
import com.neatfaith.dhikrtracker.adapter.UserListAdapter;
import com.neatfaith.dhikrtracker.core.manager.DBManager;
import com.neatfaith.dhikrtracker.core.model.Item;
import com.neatfaith.dhikrtracker.core.model.User;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private ListView mListView;
    private BaseAdapter adapter;

    private UserListAdapter usersAdapter;

    private ArrayList<Item> recentsArray = new ArrayList<>();
    private ArrayList<User> usersArray = new ArrayList<>();



    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {

            mListView.setAdapter(null);


            switch (item.getItemId()) {
                case R.id.navigation_recents:
                    return true;
                case R.id.navigation_users:
                    mListView.setAdapter(usersAdapter);

                    return true;
                case R.id.navigation_settings:
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //database
        DBManager.getInstance().getAllUsers(usersArray);

        //listview
        mListView = (ListView) findViewById(R.id.main_listView);

        usersAdapter = new UserListAdapter(this,usersArray);
//        mListView.setAdapter(usersAdapter);


        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
    }

}
