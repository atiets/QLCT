package com.example.qlct.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.qlct.Fragment.LoaiChiFragment;
import com.example.qlct.Models.LoaiChi;
import com.example.qlct.R;

import java.util.ArrayList;
import java.util.List;

public class LoaiChiAdapter extends BaseAdapter{
    private Context context;
    private int layout;
    private ArrayList<LoaiChi> loaiChi;
    private LoaiChiFragment fragment;


    public LoaiChiAdapter(Context context, int layout, ArrayList<LoaiChi> loaiChi, LoaiChiFragment fragment) {
        this.context = context;
        this.layout = layout;
        this.loaiChi = loaiChi;
        this.fragment = fragment;
    }

    @Override
    public int getCount() {
        return loaiChi.size();
    }

    @Override
    public Object getItem(int position) {
        return loaiChi.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    private class ViewHolder{
        TextView textViewLoaiChi;
        ImageView imageViewEditLoaiChi;
        ImageView imageViewDeleteLoaiChi;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if(convertView == null) {
            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(context);
            convertView = inflater.inflate(layout, parent, false);
            viewHolder.textViewLoaiChi = convertView.findViewById(R.id.textViewLoaiChi);
            viewHolder.imageViewDeleteLoaiChi = convertView.findViewById(R.id.imageViewDeleteLoaiChi);
            viewHolder.imageViewEditLoaiChi = convertView.findViewById(R.id.imageViewEditLoaiChi);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        System.out.println("atit" + loaiChi.get(position).getNameLoaiChi());
        if (viewHolder != null && loaiChi != null && loaiChi.size() > position) {
            LoaiChi LoaiChi = loaiChi.get(position);
            viewHolder.textViewLoaiChi.setText(LoaiChi.getNameLoaiChi());

            viewHolder.imageViewEditLoaiChi.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(context, "cap nhat" + LoaiChi.getNameLoaiChi(), Toast.LENGTH_SHORT).show();
                    fragment.DialogCapNhatLoaiChi(LoaiChi.getNameLoaiChi(), LoaiChi.getId());
                    System.out.println("132" + LoaiChi.getNameLoaiChi());
                }
            });
            viewHolder.imageViewDeleteLoaiChi.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    fragment.DialogDelete(LoaiChi.getNameLoaiChi(), LoaiChi.getId());
                }
            });
        }

        return convertView;
    }

}
