package com.ilyakamar.inventory_server;

import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {// START

    Button btnSignIn;
    TextView txtSlogan,tv_startScreen_welcome;

    @Override
    protected void onCreate(Bundle savedInstanceState) {// onCreate
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        btnSignIn = findViewById(R.id.bt_startScreen_logIn);
        txtSlogan = findViewById(R.id.txtSlogan);
        tv_startScreen_welcome = findViewById(R.id.tv_startScreen_welcome);

        Typeface face = Typeface.createFromAsset(getAssets(),"fonts/NABILA.TTF");
        txtSlogan.setTypeface(face);
        tv_startScreen_welcome.setTypeface(face);


        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent signIn = new Intent(MainActivity.this,Signin.class);
                startActivity(signIn);
            }
        });


    }// end onCreate


}// END
