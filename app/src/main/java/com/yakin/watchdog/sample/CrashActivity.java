package com.yakin.watchdog.sample;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.yakin.watchdog.WatchDog;
import com.yakin.watchdog.ui.CrashPanel;

import androidx.appcompat.app.AppCompatActivity;

public class CrashActivity extends AppCompatActivity {

    TextView stackLog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crash);

        stackLog = findViewById(R.id.stack_log);
        WatchDog.getCrashHandler().registerHandler(new CrashPanel());
    }

    public void onClick(View view) {
        int id = view.getId();
        switch (id) {
            case R.id.java:
                WatchDog.getLogger().d("click java crash button");
                int a = 100 / 0;
                break;
            case R.id.local:
                WatchDog.getLogger().d("click native crash button");
                testNativeCrash();
                break;
        }
    }

    public void testNativeCrash() {
        try {
            Class.forName("dalvik.system.VMDebug").getMethod("crash").invoke(null);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void startActivity(Context context) {
        context.startActivity(new Intent(context, CrashActivity.class));
    }
}
