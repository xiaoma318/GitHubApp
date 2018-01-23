package com.cm.myapp;

import android.app.IntentService;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.util.Log;

/**
 * Created by cm on 1/21/18.
 */

public class MyInstanceService extends IntentService {
    public MyInstanceService() {
        super("MyInstanceService");
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        Log.v("CM", "onHandleIntent");
        for(int i=0;i<10;i++){
            Intent intent1 = new Intent("110");
            intent1.putExtra("name", "i= "+i);
            sendBroadcast(intent1);
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
