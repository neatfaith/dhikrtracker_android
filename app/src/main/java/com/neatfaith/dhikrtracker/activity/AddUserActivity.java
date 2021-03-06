package com.neatfaith.dhikrtracker.activity;

import android.content.Intent;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.neatfaith.dhikrtracker.R;
import com.neatfaith.dhikrtracker.core.manager.DBManager;
import com.neatfaith.dhikrtracker.core.model.User;
import com.neatfaith.dhikrtracker.core.utils.ValidationUtils;

public class AddUserActivity extends AppCompatActivity {

    TextInputEditText nameEditText;

    Button cancelButton;
    Button okButton;

    User userToEdit = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_user);

        nameEditText = (TextInputEditText) findViewById(R.id.add_user_name);

        cancelButton = (Button) findViewById(R.id.add_user_cancelButton);
        okButton = (Button) findViewById(R.id.add_user_okButton);

        //state
        if (savedInstanceState != null){
            userToEdit = (User) savedInstanceState.getSerializable("userToEdit");
        }
        else {
            Bundle bundle = getIntent().getExtras();

            if (bundle != null){
                userToEdit = (User) bundle.getSerializable("userToEdit");
            }
        }


        if (userToEdit != null){ //editing
            setTitle(R.string.edit_user);
            nameEditText.append(userToEdit.getName());
        }
        else { //adding new user
            setTitle(R.string.add_new_user);
        }





        okButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String name = nameEditText.getText().toString();

                //Validation
                boolean formValid = true;

                //name
                if (!ValidationUtils.isValidText(name)){
                    nameEditText.setError(getString(R.string.error_enter_valid_value));
                    formValid = false;
                }
                else {
                    nameEditText.setError(null);
                }


                if (formValid){

                    //continue adding to db
                    if (userToEdit != null){ //update
                        DBManager.getInstance().updateUserWithId(userToEdit.getId(),name);
                    }
                    else { //add new
                        DBManager.getInstance().insertUser(name);
                    }



                    //then close
                    finish();

                    Intent intent = new Intent(AddUserActivity.this,MainActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    intent.putExtra("tabId",R.id.navigation_users);
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

        outState.putSerializable("userToEdit",userToEdit);

    }
}
