package ru.sberbank.lesson13.task.aidl.service;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.preference.PreferenceManager;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.Executors;

import ru.sberbank.lesson13.task.aidl.contract.Data;
import ru.sberbank.lesson13.task.aidl.contract.IRemoteDataService;

public class RemoteService extends Service {
    private static final String SP_VALUE = "spValue";
    private SharedPreferences preferences;
    /*private static final int COUNT_OF_WORK = 10;
    private static final int WORK_DURATION = 3000;
    public static final int MSG_REGISTER_CLIENT = 1;
    public static final int MSG_UNREGISTER_CLIENT = 2;
    public static final int MSG_SET_VALUE = 3;
    public static final String MSG_SET_VALUE_FIELD = "data";

    NotificationManager mNM;
    List<Messenger> mClients = new ArrayList<>();
    String mValue;*/

    /*class IncomingHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case MSG_REGISTER_CLIENT:
                    mClients.add(msg.replyTo);
                    break;
                case MSG_UNREGISTER_CLIENT:
                    mClients.remove(msg.replyTo);
                    break;
                case MSG_SET_VALUE:
                    Iterator<Messenger> messengerIterator = mClients.iterator();
                    while (messengerIterator.hasNext()) {
                        Messenger mClient = messengerIterator.next();
                        try {
                            mClient.send(getMessage(msg.getData().getString(MSG_SET_VALUE_FIELD)));
                        } catch (RemoteException e) {
                            messengerIterator.remove();
                        }
                    }
                    break;
                default:
                    super.handleMessage(msg);
            }
        }
    }

    final Messenger mMessenger = new Messenger(new IncomingHandler());*/

    @Override
    public void onCreate() {
        /*mNM = (NotificationManager)getSystemService(NOTIFICATION_SERVICE);
        showNotification();*/
        super.onCreate();
        preferences = PreferenceManager.getDefaultSharedPreferences(this);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Toast.makeText(this, R.string.example_service_started,
                Toast.LENGTH_SHORT).show();
        /*Executors.newSingleThreadExecutor().execute(new Runnable() {
            @Override
            public void run() {
                for (int i = 1; i <= COUNT_OF_WORK; i++) {
                    try {
                        Thread.sleep(WORK_DURATION);
                        mMessenger.send(getMessage(getResources().getString(R.string.data_from_service) + i));
                    } catch (InterruptedException | RemoteException e) {
                        e.printStackTrace();
                    }
                }
            }
        });*/
        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        //mNM.cancel(R.string.example_service_started);
        Toast.makeText(this, R.string.example_service_stopped, Toast.LENGTH_SHORT).show();
        stopSelf();
    }

    @Override
    public IBinder onBind(Intent intent) {
        return new IRemoteDataService.Stub() {
            @Override
            public void write(Data data) throws RemoteException {
                Log.i(RemoteService.class.getSimpleName(), "Some data is written");
                SharedPreferences.Editor editor = preferences.edit();
                editor.putString(SP_VALUE, data.getValue());
                editor.apply();
            }

            @Override
            public Data read() throws RemoteException {
                Log.i(RemoteService.class.getSimpleName(), "Some data is read");
                return new Data(preferences.getString(SP_VALUE, ""));
            }
        };
    }

    /*private void showNotification() {
        CharSequence text = getText(R.string.example_service_started);
        PendingIntent contentIntent = PendingIntent.getActivity(this, 0,
                new Intent(this, MainActivity.class), 0);
        Notification notification = new Notification.Builder(this)
                .setSmallIcon(R.drawable.ic_launcher_background)
                .setTicker(text)
                .setWhen(System.currentTimeMillis())
                .setContentTitle(getText(R.string.example_service_label))
                .setContentText(text)
                .setContentIntent(contentIntent)
                .build();
        mNM.notify(R.string.example_service_started, notification);
    }

    private Message getMessage(String value) {
        Message msg = Message.obtain(null,
                MSG_SET_VALUE, 0, 0);
        Bundle data = new Bundle();
        data.putString(MSG_SET_VALUE_FIELD, value);
        msg.setData(data);
        return msg;
    }*/
}
