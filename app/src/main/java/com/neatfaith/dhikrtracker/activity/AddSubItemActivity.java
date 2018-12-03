package com.neatfaith.dhikrtracker.activity;

import android.content.Intent;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.neatfaith.dhikrtracker.R;
import com.neatfaith.dhikrtracker.core.manager.DBManager;
import com.neatfaith.dhikrtracker.core.model.ItemType;
import com.neatfaith.dhikrtracker.core.model.ItemTypeSubItem;
import com.neatfaith.dhikrtracker.core.utils.ValidationUtils;

public class AddSubItemActivity extends AppCompatActivity {

    TextInputEditText titleEditText;

    Button cancelButton;
    Button okButton;

    ItemType itemType;

    ItemTypeSubItem subItemToEdit = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_sub_item);

        if (savedInstanceState != null){
            itemType = (ItemType) savedInstanceState.getSerializable("itemType");
            subItemToEdit = (ItemTypeSubItem) savedInstanceState.getSerializable("subItemToEdit");
        }
        else {
            Bundle bundle = getIntent().getExtras();
            if (bundle != null){
                itemType = (ItemType) bundle.getSerializable("itemType");
                subItemToEdit = (ItemTypeSubItem) bundle.getSerializable("subItemToEdit");

            }

        }



        titleEditText = (TextInputEditText) findViewById(R.id.add_subitem_title);

        cancelButton = (Button) findViewById(R.id.add_subitem_cancelButton);
        okButton = (Button) findViewById(R.id.add_subitem_okButton);


        //editing
        if (subItemToEdit != null){
            titleEditText.append(subItemToEdit.getTitle());
        }


        okButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String title = titleEditText.getText().toString();

                //Validation
                boolean formValid = true;

                //name
                if (!ValidationUtils.isValidText(title)){
                    titleEditText.setError(getString(R.string.error_enter_valid_value));
                    formValid = false;
                }
                else {
                    titleEditText.setError(null);
                }


                if (formValid){

                    //continue adding to db

                    if (subItemToEdit != null){ //editing
                        DBManager.getInstance().updateItemTypeSubItemWithId(subItemToEdit.getId(),title,itemType.getId());
                    }
                    else { //add new
                        DBManager.getInstance().insertItemTypeSubItem(title,itemType.getId());

                    }

                    //then close
                    finish();

                    Intent intent = new Intent(AddSubItemActivity.this,SubItemListActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

                    intent.putExtra("itemType",itemType);
                    startActivity(intent);
                }


            }
        });

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putSerializable("itemType",itemType);
        outState.putSerializable("subItemToEdit",subItemToEdit);
    }
}
