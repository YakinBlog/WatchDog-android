package com.yakin.watchdog.ui.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public abstract class ListViewBaseModel<T extends ListViewBaseHolderView.ListViewBaseHolder> {

    @SuppressWarnings("unchecked")
    View getAndBindView(View convertView, ViewGroup parent) {
        T holder = null;
        if (convertView == null) {
            try {
                ListViewBaseHolderView itemView = createItemView();
                convertView = inflaterView(parent, itemView);
                holder = (T) itemView.createAndSetHolder(convertView);
                convertView.setTag(holder);
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        } else {
            holder = (T) convertView.getTag();
        }
        handleView(holder);
        return convertView;
    }

    private View inflaterView(ViewGroup parent, ListViewBaseHolderView itemView) {
        return LayoutInflater.from(parent.getContext()).inflate(itemView.getLayoutResId(), parent, false);
    }

    protected abstract ListViewBaseHolderView createItemView() throws InstantiationException, IllegalAccessException;

    protected abstract void handleView(T holder);
}

