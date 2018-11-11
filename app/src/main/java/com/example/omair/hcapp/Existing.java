package com.example.omair.hcapp;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class Existing extends AppCompatActivity{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_existing);

        Button button6 = (Button) findViewById(R.id.button6);
        button6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //add to SQL DB
                Intent intent = new Intent(Existing.this,FinalPage.class);
                startActivity(intent);
            }
        });

    }
}
