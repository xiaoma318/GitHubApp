package com.cm.myapp;

import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.support.annotation.Nullable;
import android.util.Log;

/**
 * Created by cm on 1/22/18.
 */

public class MyService extends Service{
    ServiceHandler handler;
    private final class ServiceHandler extends Handler{
        public ServiceHandler(Looper looper) {
            super(looper);
        }

        @Override
        public void handleMessage(Message msg) {
            Log.d("CM", "handleMessage: "+msg.what);
            stopSelf(msg.what);
        }
    }

    @Override
    public void onCreate() {
        HandlerThread thread = new HandlerThread("service");
        thread.start();
        handler = new ServiceHandler(thread.getLooper());
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Message msg = handler.obtainMessage(startId);
        handler.sendMessage(msg);
        return START_STICKY;
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onDestroy() {
        Log.d("CM", "service destroy");
    }
}
