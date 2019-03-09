package ru.sberbank.lesson13.task.aidl.service;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends Activity {
    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        intent = new Intent(MainActivity.this,
                RemoteService.class);
        Button button = findViewById(R.id.startService);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                startService(intent);
            }
        });
        button = findViewById(R.id.stopService);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                stopService(intent);
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        stopService(intent);
    }
}
