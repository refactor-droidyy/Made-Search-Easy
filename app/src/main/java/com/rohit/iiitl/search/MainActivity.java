package com.rohit.iiitl.search;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;

public class MainActivity extends AppCompatActivity {

    public static final int PERRISSION_REQUEST_FOR = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M
        && !Settings.canDrawOverlays(this)){
            Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION,
                    Uri.parse("package:" + getPackageName()));

            startActivityForResult(intent,PERRISSION_REQUEST_FOR);
        }else{
// function for showing a floating window 
            showFolatingWindow();
        }
    }

    private void showFolatingWindow() {

        startService(new Intent(MainActivity.this,Bubblehead.class));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == PERRISSION_REQUEST_FOR){
            if(resultCode == RESULT_OK){
                showFolatingWindow();
            }
        }

    }
}

