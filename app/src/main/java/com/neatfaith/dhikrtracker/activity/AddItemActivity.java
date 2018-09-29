package com.neatfaith.dhikrtracker.activity;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.neatfaith.dhikrtracker.R;
import com.neatfaith.dhikrtracker.core.manager.DBManager;
import com.neatfaith.dhikrtracker.core.model.ItemType;
import com.neatfaith.dhikrtracker.core.model.ItemTypeSubItem;
import com.neatfaith.dhikrtracker.core.model.User;
import com.neatfaith.dhikrtracker.core.utils.ValidationUtils;

import java.util.ArrayList;
import java.util.List;

public class AddItemActivity extends AppCompatActivity {

    EditText quantityEditText;
    EditText subitemEditText;
    EditText userEditText;




    Button cancelButton;
    Button continueButton;

    ArrayList<ItemTypeSubItem> subItems = new ArrayList<>();
    ItemType itemType;

    AlertDialog selectSubitemDialog;
    int selectedItem = -1;


    ArrayList<User> users = new ArrayList<>();
    int selectedUser = -1;

    AlertDialog selectUserDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_item);


        quantityEditText = (EditText) findViewById(R.id.quantity_editText);

        cancelButton = (Button) findViewById(R.id.add_item_cancelButton);
        continueButton = (Button) findViewById(R.id.add_item_continueButton);

        itemType = (ItemType) getIntent().getExtras().getSerializable("itemType");

        //db
        DBManager.getInstance().getSubItemsForType(subItems,this.itemType.getId()+"");
        DBManager.getInstance().getAllUsers(users);



        continueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //check if items for alert dialog exist
                if(subItems == null || subItems.size() == 0){

                    Toast.makeText(AddItemActivity.this, "No Types to choose from",
                            Toast.LENGTH_LONG).show();
                    return;
                }

                String quantity = quantityEditText.getText().toString();

                //Validation
                boolean formValid = true;

                //quantity
                if (!ValidationUtils.isValidLong(quantity)){
                    quantityEditText.setError(getString(R.string.error_enter_valid_value));
                    formValid = false;
                }
                else {
                    quantityEditText.setError(null);
                }


                //subitem
                if (selectedItem < 0){
                    subitemEditText.setError(getString(R.string.error_enter_valid_value));
                    formValid = false;
                }

                //user
                if (selectedUser < 0){
                    userEditText.setError(getString(R.string.error_enter_valid_value));
                    formValid = false;
                }


                if (formValid){

                    //continue adding to db
                    ItemTypeSubItem subItem = subItems.get(selectedItem);
                    User user = users.get(selectedUser);

                    DBManager.getInstance().insertItem(subItem.getId(),user.getId(),Long.parseLong(quantity),0);



                    //then close
                    finish();
                }


            }
        });
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });





        // ######## SUBITEM
        // setup the alert builder
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Choose an item");

        // add a radio button list
        CharSequence[] subitemsArr = this.getSubitemsForCurrentType();
        builder.setSingleChoiceItems(subitemsArr, selectedItem, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // user checked an item
                selectedItem = which;
            }
        });

        // add OK and Cancel buttons
        builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // user clicked OK
                if(subItems!= null && subItems.size() > 0){
                    ItemTypeSubItem subItem = subItems.get(selectedItem);

                    subitemEditText.setText(subItem.getTitle());
                }

            }
        });
        builder.setNegativeButton(R.string.cancel, null);

        // create the alert dialog
        selectSubitemDialog = builder.create();

        subitemEditText = (EditText) findViewById(R.id.add_item_subitem);


        subitemEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectSubitemDialog.show();

            }
        });



        //########## USER
        // setup the alert builder
        AlertDialog.Builder builder2 = new AlertDialog.Builder(this);
        builder2.setTitle("Select User");

        // add a radio button list
        final CharSequence[] usersArr = this.getAllUsers();
        builder2.setSingleChoiceItems(usersArr, selectedUser, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // user checked an item
                selectedUser = which;
            }
        });
        // add OK and Cancel buttons
        builder2.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if(users != null && users.size() > 0){
                    User user = users.get(selectedUser);

                    userEditText.setText(user.getName());
                }
            }
        });
        builder2.setNegativeButton(R.string.cancel, null);

        // create the alert dialog
        selectUserDialog = builder2.create();

        userEditText = (EditText) findViewById(R.id.add_item_user);

        userEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectUserDialog.show();

            }
        });
    }

    private CharSequence[] getSubitemsForCurrentType() {

        CharSequence subitemsArray[] = new CharSequence[subItems.size()];

        for (int i = 0; i < subItems.size(); i++) {
            ItemTypeSubItem sub = subItems.get(i);
            subitemsArray[i] = sub.getTitle();
        }

        return subitemsArray;
    }

    private CharSequence[] getAllUsers() {

        CharSequence usersArray[] = new CharSequence[users.size()];

        for (int i = 0; i < users.size(); i++) {
            User user = users.get(i);
            usersArray[i] = user.getName();
        }

        return usersArray;
    }
}
