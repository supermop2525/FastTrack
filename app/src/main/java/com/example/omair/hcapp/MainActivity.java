package com.example.omair.hcapp;

import java.io.*;

import android.Manifest;
import android.app.*;
import android.content.*;
import android.content.pm.PackageManager;
import android.net.*;
import android.os.*;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.*;
import android.graphics.*;
import android.widget.*;
import android.provider.*;
import com.microsoft.projectoxford.face.*;
import com.microsoft.projectoxford.face.contract.*;

public class MainActivity extends AppCompatActivity {
    private final int PICK_IMAGE = 1;
    private ProgressDialog detectionProgressDialog;

    private final String apiEndpoint = "https://westcentralus.api.cognitive.microsoft.com/face/v1.0";
    private final String subscriptionKey = "b5a3420af7d5493ebe54642996816f19";
    private final String uriBase = "https://canadacentral.api.cognitive.microsoft.com/face/v1.0";
    private final String subscriptionKey1 = "";

    private final FaceServiceClient faceServiceClient = new FaceServiceRestClient(apiEndpoint, subscriptionKey);
    //private final FaceServiceClient faceServiceClient1 = new FaceServiceRestClient(uriBase, subscriptionKey1);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_real);
        checkPermission();
        Button button1 = (Button)findViewById(R.id.button4);
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //ASYNCcalls.calls x = new ASYNCcalls.calls(); //initialized facelist with faceServiceClient onto Azure once
                //x.makeOnce();
                Intent intent = new Intent(MainActivity.this, StatusCheck.class);
                startActivity(intent);
            }
        });
    }

    public void checkPermission(){
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) !=
                PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.CAMERA},
                    123);
        }
    }
}
