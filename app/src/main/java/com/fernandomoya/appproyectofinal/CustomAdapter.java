package com.fernandomoya.appproyectofinal;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.ArrayList;

public class CustomAdapter extends BaseAdapter {

    Context c;
    ArrayList<String> dogies;
    LayoutInflater inflater;

    public CustomAdapter() {
    }

    public CustomAdapter(Context c, ArrayList<String> dogies) {
        this.c = c;
        this.dogies = dogies;
    }

    @Override
    public int getCount() {
        return 0;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return null;
    }
}
