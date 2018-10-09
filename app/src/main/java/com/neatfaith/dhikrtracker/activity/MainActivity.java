package com.neatfaith.dhikrtracker.activity;

import android.content.Intent;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.neatfaith.dhikrtracker.R;
import com.neatfaith.dhikrtracker.adapter.IconTextAdapter;
import com.neatfaith.dhikrtracker.adapter.ItemListAdapter;
import com.neatfaith.dhikrtracker.adapter.UserListAdapter;
import com.neatfaith.dhikrtracker.core.manager.DBManager;
import com.neatfaith.dhikrtracker.core.model.Item;
import com.neatfaith.dhikrtracker.core.model.ItemType;
import com.neatfaith.dhikrtracker.core.model.User;
import com.neatfaith.dhikrtracker.core.utils.Globals;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private ListView mListView;

    private UserListAdapter usersAdapter;
    private ItemListAdapter recentsAdapter;
    private IconTextAdapter moreAdapter;

    private ArrayList<Item> recentsArray = new ArrayList<>();
    private ArrayList<User> usersArray = new ArrayList<>();
    private ArrayList<String> moreOptions = new ArrayList<>();
    private ArrayList<Drawable> moreIcons = new ArrayList<>();


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
                    mListView.setAdapter(moreAdapter);

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

        moreOptions.add(getString(R.string.add_modify_items));
        moreOptions.add(getString(R.string.help_and_support));
        moreOptions.add(getString(R.string.about));

        Resources res = getResources();
        moreIcons.add(res.getDrawable(R.drawable.ic_add_n_modify_black_24dp));
        moreIcons.add(res.getDrawable(R.drawable.ic_help_black_24dp));
        moreIcons.add(res.getDrawable(R.drawable.ic_about_black_24dp));



        int selectedTabId = -1;

        if (savedInstanceState != null){
            selectedTabId = savedInstanceState.getInt("tabId",-1);

        }
        else {
            Bundle bundle = getIntent().getExtras();
            if (bundle != null){
                selectedTabId = bundle.getInt("tabId",-1);
            }
        }

        //database
        DBManager.getInstance().getAllUsers(usersArray);
        DBManager.getInstance().getAllItems(recentsArray);

        //listview
        mListView = (ListView) findViewById(R.id.main_listView);
        mListView.setEmptyView(findViewById(R.id.empty_result));

        moreAdapter =  new IconTextAdapter(this,moreOptions,moreIcons);
        usersAdapter = new UserListAdapter(this,usersArray);
        recentsAdapter = new ItemListAdapter(this,recentsArray);
        mListView.setAdapter(recentsAdapter);


        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        if(selectedTabId == R.id.navigation_recents || selectedTabId == R.id.navigation_users || selectedTabId == R.id.navigation_settings){
            navigation.setSelectedItemId(selectedTabId);
            // NOTE: Adapter for listview is set/updated on the onNavigationItemSelected
        }

        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                int selectedTab = getSelectedTab();

                //recents
                if (selectedTab == 0){

                }
                //users
                else if (selectedTab == 1){

                }
                //more
                else if (selectedTab == 2){


                    if(position == 0){
                        Intent intent = new Intent(MainActivity.this,ItemTypesListActivity.class);
                        startActivityForResult(intent,Globals.SELECT_ITEM_TYPE_REQUEST);
                    }
                    else if(position == 1){
                        Intent intent = new Intent(MainActivity.this,SupportActivity.class);
                        startActivity(intent);
                    }
                    else if(position == 2){
                        Intent intent = new Intent(MainActivity.this,AboutActivity.class);
                        startActivity(intent);
                    }

                }
            }
        });


    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        //keep the selected tab, on device rotation
        int selectedTab = getSelectedTab();
        int tabId = R.id.navigation_recents;
        if (selectedTab == 0){
            tabId = R.id.navigation_recents;
        }
        else if (selectedTab == 1){
            tabId = R.id.navigation_users;
        }
        else if (selectedTab == 2){
            tabId = R.id.navigation_settings;
        }
        outState.putInt("tabId",tabId);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        // Inflate the menu items for the action bar

        int selectedTab = getSelectedTab();

        //show add icon on items and users only
        if (selectedTab == 0 || selectedTab == 1){
            MenuInflater inflater = getMenuInflater();
            inflater.inflate(R.menu.main_activity_actions, menu);
        }


        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {


        int selectedTab = getSelectedTab();

        switch (item.getItemId()) {
            case R.id.action_add:

                //if recents tab is selected
                if (selectedTab == 0){
                    Intent intent = new Intent(this,ItemTypesListActivity.class);
                    startActivityForResult(intent,Globals.SELECT_ITEM_TYPE_REQUEST);
                }

                //if users tab is selected
                else if (selectedTab == 1){
                    Intent intent = new Intent(this,AddUserActivity.class);
                    startActivity(intent);
                }



                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        int selectedTab = getSelectedTab();


        if (resultCode == RESULT_OK && requestCode == Globals.SELECT_ITEM_TYPE_REQUEST) {

            Bundle bundle = data.getExtras();


            //tab recents -  add item
            if (selectedTab == 0){
                Intent intent = new Intent(MainActivity.this,AddItemActivity.class);
                intent.putExtras(bundle);
                startActivity(intent);

            }

            //tab more - add, modify items
            else if (selectedTab == 2){
                Intent intent = new Intent(MainActivity.this,SubItemListActivity.class);
                intent.putExtras(bundle);
                startActivity(intent);
            }



        }
    }

    public int getSelectedTab(){
        ListAdapter adapter = mListView.getAdapter();

        if (adapter == null){
            return -1;
        }
        else if (adapter.equals(recentsAdapter)){
            return 0;
        }
        else if (adapter.equals(usersAdapter)){
            return 1;
        }
        else if (adapter.equals(moreAdapter)){
            return 2;
        }
        else{
            return -1;
        }

    }
}
