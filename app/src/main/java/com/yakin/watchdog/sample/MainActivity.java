package com.yakin.watchdog.sample;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.yakin.watchdog.WatchDog;
import com.yakin.watchdog.log.ILogger;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        WatchDog.getLogger().setPrintLog(ILogger.DEBUG, "WatchDog");
    }

    public void onClick(View view) {
        int id = view.getId();
        switch (id) {
            case R.id.crash:
                WatchDog.getLogger().d("click crash button");
                CrashActivity.startActivity(this);
                break;
        }
    }
}
