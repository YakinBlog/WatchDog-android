package com.yakin.watchdog.ui.adapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.ArrayList;
import java.util.List;

public class ListViewAdapter extends BaseAdapter {

    private List<ListViewBaseModel> mList;

    public ListViewAdapter() {
        mList = new ArrayList<>();
    }

    public void setListData(List<ListViewBaseModel> list) {
        if(!mList.isEmpty()) {
            mList.clear();
        }
        addListData(list);
    }

    public void addListData(List<ListViewBaseModel> list) {
        mList.addAll(list);
        notifyDataSetChanged();
    }

    public void addData(ListViewBaseModel<?> data) {
        mList.add(data);
        notifyDataSetChanged();
    }

    public boolean isEmpty() {
        return mList.isEmpty();
    }

    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public ListViewBaseModel<?> getItem(int position) {
        return mList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return getItem(position).getAndBindView(convertView, parent);
    }
}
