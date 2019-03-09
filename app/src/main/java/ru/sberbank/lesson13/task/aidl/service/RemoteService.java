package ru.sberbank.lesson13.task.aidl.service;

import android.app.Service;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.IBinder;
import android.os.RemoteException;
import android.widget.Toast;

import ru.sberbank.lesson13.task.aidl.contract.Data;
import ru.sberbank.lesson13.task.aidl.contract.IRemoteDataService;

public class RemoteService extends Service {
    private static final String SP_VALUE = "spValue";
    private SharedPreferences preferences;

    @Override
    public void onCreate() {
        super.onCreate();
        preferences = getApplicationContext().getSharedPreferences("aidl.service", MODE_PRIVATE);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Toast.makeText(this, R.string.remote_service_started,
                Toast.LENGTH_SHORT).show();
        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        Toast.makeText(this, R.string.remote_service_stopped, Toast.LENGTH_SHORT).show();
        stopSelf();
    }

    @Override
    public IBinder onBind(Intent intent) {
        return new IRemoteDataService.Stub() {
            @Override
            public void write(Data data) throws RemoteException {
                SharedPreferences.Editor editor = preferences.edit();
                editor.putString(SP_VALUE, data.getValue());
                editor.apply();
            }

            @Override
            public Data read() throws RemoteException {
                return new Data(preferences.getString(SP_VALUE, ""));
            }
        };
    }
}
