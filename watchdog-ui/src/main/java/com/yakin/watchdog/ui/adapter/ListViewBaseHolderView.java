package com.yakin.watchdog.ui.adapter;

import android.view.View;

public abstract class ListViewBaseHolderView<T extends ListViewBaseHolderView.ListViewBaseHolder> {

    protected abstract int getLayoutResId();

    protected abstract T createHolder();

    protected void setHolderAttrs(T holder, View view) {
        holder.rootView = view;
    }

    T createAndSetHolder(View view) {
        T holder = createHolder();
        setHolderAttrs(holder, view);
        return holder;
    }

    public abstract class ListViewBaseHolder {
        protected View rootView;
    }
}
