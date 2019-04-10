package com.yakin.watchdog.ui.adapter;

import android.view.View;
import android.widget.TextView;

import com.yakin.watchdog.ui.R;

public class CrashStackTraceHolderView extends ListViewBaseHolderView {

    @Override
    protected int getLayoutResId() {
        return R.layout.watchdog_crash_list_item;
    }

    @Override
    protected ListViewBaseHolder createHolder() {
        return new StackTraceHolder();
    }

    @Override
    protected void setHolderAttrs(ListViewBaseHolder holder, View view) {
        super.setHolderAttrs(holder, view);
        StackTraceHolder h = (StackTraceHolder) holder;
        h.textView = view.findViewById(R.id.text);
    }

    public class StackTraceHolder extends ListViewBaseHolder {
        public TextView textView;
    }
}
