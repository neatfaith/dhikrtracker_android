package com.neatfaith.dhikrtracker.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
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

    private AlertDialog actionsDialog;
    private AlertDialog cantModifyDialog;
    private int itemLongClickPosition = -1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sub_item_list);

        mListView = (ListView) findViewById(R.id.subitems_listView);


        if (savedInstanceState != null){
            itemType = (ItemType) savedInstanceState.getSerializable("itemType");
            itemLongClickPosition = savedInstanceState.getInt("itemLongClickPosition");
        }
        else {

            Bundle bundle = getIntent().getExtras();
            if (bundle != null){
                itemType = (ItemType) bundle.getSerializable("itemType");
                itemLongClickPosition = bundle.getInt("itemLongClickPosition");
            }
        }


        DBManager.getInstance().getSubItemsForType(subItems,itemType.getId()+"");

        adapter = new ItemTypeSubItemListAdapter(this,subItems);
        mListView.setAdapter(adapter);

        //actions dialog
        CharSequence[] actions = {getString(R.string.action_edit),getString(R.string.action_delete)};
        AlertDialog.Builder builder = new AlertDialog.Builder(SubItemListActivity.this);
        builder.setTitle(R.string.select_action)
                .setItems(actions, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int index) {

                        if (itemLongClickPosition < 0){
                            return;
                        }

                        ItemTypeSubItem subItem = subItems.get(itemLongClickPosition);

                        //handle chosen action

                        if (index == 0){ //edit

                            Intent intent = new Intent(SubItemListActivity.this,AddSubItemActivity.class);
                            intent.putExtra("subItemToEdit",subItem);
                            intent.putExtra("itemType",itemType);

                            startActivity(intent);

                        }
                        else if (index == 1){ //delete

                            AlertDialog.Builder builder = new AlertDialog.Builder(SubItemListActivity.this);
                            builder.setMessage("Are you sure, you want to delete "+subItem.getTitle().toUpperCase())
                                    .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int id) {
                                            ItemTypeSubItem subItem = subItems.get(itemLongClickPosition);
                                            DBManager.getInstance().deleteItemTypeSubItemWithId(subItem.getId());
                                            notifyDataChanged();
                                        }
                                    })
                                    .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int id) {
                                            // User cancelled the dialog
                                        }
                                    });
                            builder.create().show();


                        }

                    }
                });
        actionsDialog = builder.create();


        //cantModify dialog
        AlertDialog.Builder builder2 = new AlertDialog.Builder(SubItemListActivity.this);
        builder2.setMessage(R.string.action_cant_edit_delete)
                .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                    }
                })
                .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // User cancelled the dialog
                    }
                });
        cantModifyDialog = builder2.create();

        mListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {

                itemLongClickPosition = position;

                ItemTypeSubItem subItem = subItems.get(itemLongClickPosition);

                if (subItem.canModify()){
                    actionsDialog.show();
                }
                else {
                    cantModifyDialog.show();
                }


                return true;

            }
        });



    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putSerializable("itemType",itemType);
        outState.putInt("itemLongClickPosition",itemLongClickPosition);

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

                intent.putExtra("itemType",itemType);
                startActivity(intent);

                return true;

            default:
                return super.onOptionsItemSelected(item);
        }

    }

    private void notifyDataChanged(){
        subItems.clear();
        DBManager.getInstance().getSubItemsForType(subItems,itemType.getId()+"");
        adapter.notifyDataSetChanged();
    }
}
