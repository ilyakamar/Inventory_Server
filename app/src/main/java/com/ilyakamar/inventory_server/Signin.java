package com.ilyakamar.inventory_server;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.UserHandle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.ilyakamar.inventory_server.Common.Common;
import com.ilyakamar.inventory_server.Model.User;

public class Signin extends AppCompatActivity {


    EditText edtPhone,edtPassword;
    Button btnSignIn;

    // Firebase
    FirebaseDatabase db;
    DatabaseReference users;

    @Override
    protected void onCreate(Bundle savedInstanceState) {// onCreate
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin);


        edtPhone = findViewById(R.id.edtPhone);
        edtPassword = findViewById(R.id.edtPassword);

        btnSignIn = findViewById(R.id.btnSignUp);


        // Init Firebase
        db = FirebaseDatabase.getInstance();
        users = db.getReference("User");

        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signInUser(edtPhone.getText().toString(),edtPassword.getText().toString());

            }
        });


    }// end onCreate

    private void signInUser(final String phone , String password) {

        final ProgressDialog mDialog = new  ProgressDialog(Signin.this);
        mDialog.setMessage("Please waiting...");
        mDialog.show();

        final String  localPhone = phone;
        final String  localPassword = password;

        users.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.child(localPhone).exists())
                {
                    mDialog.dismiss();
                    User user = dataSnapshot.child(localPhone).getValue(User.class);
                    user.setPhone(localPhone);
                    if (Boolean.parseBoolean(user.getIsStaff())) // if isStaff == true
                    {
                        if (user.getPassword().equals(localPassword))
                        {
                            Intent login = new Intent(Signin.this,Home.class);
                            Common.currentUser = user;
                            startActivity(login);
                            finish();

                        }else {
                            Toast.makeText(Signin.this, "Wrong password !",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }else {
                        Toast.makeText(Signin.this, "Please login with Staff account",
                                Toast.LENGTH_SHORT).show();
                    }

                }else {
                    mDialog.dismiss();
                    Toast.makeText(Signin.this, "User not exist in Database",
                            Toast.LENGTH_SHORT).show();
                }
            }// end onDataChange

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }// end onCancelled
        });



    }// end signInUser
}// END
