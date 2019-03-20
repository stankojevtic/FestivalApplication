package com.example.festivalapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class Activity_Register extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        TextView alreadyRegisteredButon  = (TextView)findViewById(R.id.backtologin);
        alreadyRegisteredButon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Activity_Login.class);
                //intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                //finish();
                startActivity(intent);
            }
        });
    }
}
