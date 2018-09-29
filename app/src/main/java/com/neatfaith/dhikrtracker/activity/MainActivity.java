package com.neatfaith.dhikrtracker.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.BaseAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.neatfaith.dhikrtracker.R;
import com.neatfaith.dhikrtracker.adapter.ItemListAdapter;
import com.neatfaith.dhikrtracker.adapter.UserListAdapter;
import com.neatfaith.dhikrtracker.core.manager.DBManager;
import com.neatfaith.dhikrtracker.core.model.Item;
import com.neatfaith.dhikrtracker.core.model.User;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private ListView mListView;

    private UserListAdapter usersAdapter;
    private ItemListAdapter recentsAdapter;

    private ArrayList<Item> recentsArray = new ArrayList<>();
    private ArrayList<User> usersArray = new ArrayList<>();



    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {


            switch (item.getItemId()) {
                case R.id.navigation_recents:
                    mListView.setAdapter(recentsAdapter);

                    //redraw actionbar
                    invalidateOptionsMenu();
                    return true;
                case R.id.navigation_users:
                    mListView.setAdapter(usersAdapter);

                    invalidateOptionsMenu();
                    return true;
                case R.id.navigation_settings:
                    mListView.setAdapter(null);

                    invalidateOptionsMenu();
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
        DBManager.getInstance().getAllItems(recentsArray);

        //listview
        mListView = (ListView) findViewById(R.id.main_listView);
        mListView.setEmptyView(findViewById(R.id.empty_result));

        usersAdapter = new UserListAdapter(this,usersArray);
        recentsAdapter = new ItemListAdapter(this,recentsArray);
        mListView.setAdapter(recentsAdapter);


        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        // Inflate the menu items for the action bar

        ListAdapter adapter = mListView.getAdapter();

        //show add icon on items and users only
        if (adapter != null && (adapter.equals(recentsAdapter) || adapter.equals(usersAdapter))){
            MenuInflater inflater = getMenuInflater();
            inflater.inflate(R.menu.main_activity_actions, menu);
        }


        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {


        ListAdapter adapter = mListView.getAdapter();

        switch (item.getItemId()) {
            case R.id.action_add:

                //if users tab is selected
                if (adapter != null && adapter.equals(usersAdapter)){
                    Intent intent = new Intent(this,AddUserActivity.class);
                    startActivity(intent);
                }

                //if recents tab is selected
                if (adapter != null && adapter.equals(recentsAdapter)){
                    Intent intent = new Intent(this,ItemTypesListActivity.class);
                    startActivity(intent);
                }


                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

}
