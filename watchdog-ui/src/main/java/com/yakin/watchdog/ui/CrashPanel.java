package com.yakin.watchdog.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.widget.ListView;

import com.yakin.watchdog.crash.ICatchHandler;
import com.yakin.watchdog.ui.adapter.CrashStackTraceModel;
import com.yakin.watchdog.ui.adapter.ListViewAdapter;
import com.yakin.watchdog.ui.adapter.ListViewBaseModel;
import com.yakin.watchdog.ui.utils.ContextUtil;
import com.yakin.watchdog.ui.utils.ScreenUtil;

import java.util.ArrayList;
import java.util.List;

public class CrashPanel extends AppCompatActivity implements ICatchHandler {

    private final String CRASH_TITLE = "crash title";
    private final String CRASH_STACK = "crash stack";

    private ListView listView;
    private ListViewAdapter listViewAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ScreenUtil.setStatusBarColor(this, android.R.color.black);
        ScreenUtil.setNavigationBarColor(this, android.R.color.black);

        setContentView(R.layout.watchdog_crash_list);

        String title = getIntent().getStringExtra(CRASH_TITLE);
        String stackTrace = getIntent().getStringExtra(CRASH_STACK);

        setTitle(title);

        listView = (ListView) findViewById(R.id.list);

        listViewAdapter = new ListViewAdapter();
        listView.setAdapter(listViewAdapter);

        List<ListViewBaseModel> listModel = parseStackTraceList(stackTrace.split("\n"));
        listViewAdapter.setListData(listModel);
    }

    private List<ListViewBaseModel> parseStackTraceList(String[] stackTraces) {
        ArrayList<ListViewBaseModel> modelList = new ArrayList<>();
        for (String stackTrace : stackTraces) {
            stackTrace = stackTrace.trim();
            if(!TextUtils.isEmpty(stackTrace)) {
                modelList.add(new CrashStackTraceModel(stackTrace.trim()));
            }
        }
        return modelList;
    }

    @Override
    public void onCatch(long tid, String reason, String stackTrace) {
        Intent intent = new Intent(ContextUtil.getContext(), CrashPanel.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putExtra(CRASH_TITLE, reason);
        intent.putExtra(CRASH_STACK, stackTrace);
        ContextUtil.getContext().startActivity(intent);
    }
}
