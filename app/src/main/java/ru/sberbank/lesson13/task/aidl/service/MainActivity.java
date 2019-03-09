package ru.sberbank.lesson13.task.aidl.service;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

public class MainActivity extends Activity {
    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        intent = new Intent(MainActivity.this,
                RemoteService.class);
        findViewById(R.id.startService).setOnClickListener(v -> startService(intent));
        findViewById(R.id.stopService).setOnClickListener(v -> stopService(intent));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        stopService(intent);
    }
}
