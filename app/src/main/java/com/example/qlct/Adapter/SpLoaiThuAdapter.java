package com.example.qlct.Adapter;

import android.content.Context;
import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.qlct.Models.LoaiThu;
import com.example.qlct.R;

import java.util.ArrayList;


public class SpLoaiThuAdapter extends ArrayAdapter<LoaiThu> {
    private Context context;
    private ArrayList<LoaiThu> data;
    public Resources res;
    private LayoutInflater inflater;

    public SpLoaiThuAdapter(Context context, ArrayList<LoaiThu> objects) {
        super(context, R.layout.spinner_item_layout, objects);

        this.context = context;
        data = objects;
        inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        return getCustomView(position, convertView, parent);
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return getCustomView(position, convertView, parent);
    }

    public View getCustomView(int position, View convertView, ViewGroup parent) {

        View row = inflater.inflate(R.layout.spinner_item_layout, parent, false);
        TextView txtSpinner = (TextView) row.findViewById(R.id.txtSpinner);

        txtSpinner.setText(data.get(position).getNameLoaiThu());

        return row;
    }

}
