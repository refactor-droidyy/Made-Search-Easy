package com.rohit.iiitl.search;

import android.app.Service;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.PixelFormat;
import android.media.Image;
import android.os.IBinder;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import androidx.annotation.Nullable;

import java.security.Provider;

public class Bubblehead extends Service {

    private WindowManager windowManager;
    private View bubbleheadview;
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        bubbleheadview = LayoutInflater.from(this).inflate(R.layout.bubbble,null);
        final WindowManager.LayoutParams params = new WindowManager.LayoutParams(
                WindowManager.LayoutParams.WRAP_CONTENT,
                WindowManager.LayoutParams.WRAP_CONTENT,
                WindowManager.LayoutParams.TYPE_PHONE,
                WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
                PixelFormat.TRANSLUCENT);
        params.gravity = Gravity.TOP | Gravity.LEFT;
        params.x = 0;
        params.y = 100;

        windowManager = (WindowManager) getSystemService(WINDOW_SERVICE);
        windowManager.addView(bubbleheadview,params);

        ImageView imgg = bubbleheadview.findViewById(R.id.bubbble_image);
        imgg.setOnTouchListener(new View.OnTouchListener() {

            private int initialX;
            private int inititalY;
            private float touchX;
            private float touchY;
            private int lastActon;

            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {

                if(motionEvent.getAction() == MotionEvent.ACTION_DOWN){

                    initialX = params.x;
                    inititalY = params.y;
                    touchX = motionEvent.getRawX();
                    touchY =  motionEvent.getRawY();
                    lastActon = motionEvent.getAction();

                    return true;
                }
                if(motionEvent.getAction() == MotionEvent.ACTION_UP){
                    Button button = new Button(Bubblehead.this);
                    button.setText("CLOSE");

                    RelativeLayout rlt = bubbleheadview.findViewById(R.id.bubbule);
                    rlt.addView(button);

                    button.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                           stopSelf();
                        }
                    });

                    lastActon = motionEvent.getAction();

                    return true;
                }
                if(motionEvent.getAction() == MotionEvent.ACTION_MOVE){
                    params.x = initialX - (int)(motionEvent.getRawX()  - touchY);
                    params.y = inititalY - (int)(motionEvent.getRawY() - touchY);

                    windowManager.updateViewLayout(bubbleheadview,params);
                    lastActon = motionEvent.getAction();
                    return true;
                }
                return false;
            }
        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if(bubbleheadview != null){
            windowManager.removeView(bubbleheadview);
        }
    }
}
