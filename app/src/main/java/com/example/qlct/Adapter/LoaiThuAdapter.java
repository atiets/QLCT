package com.example.qlct.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.qlct.Fragment.LoaiThuFragment;
import com.example.qlct.Models.LoaiThu;
import com.example.qlct.R;

import java.util.List;

public class LoaiThuAdapter extends BaseAdapter {
    private Context context;
    private int layout;
    private List<LoaiThu> loaiThu;
    private LoaiThuFragment fragment;


    public LoaiThuAdapter(Context context, int layout, List<LoaiThu> loaiThu, LoaiThuFragment fragment) {
        this.context = context;
        this.layout = layout;
        this.loaiThu = loaiThu;
        this.fragment = fragment;
    }

    @Override
    public int getCount() {
        return loaiThu.size();
    }

    @Override
    public Object getItem(int position) {
        return loaiThu.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    private class ViewHolder{
        TextView textViewLoaiThu;
        ImageView imageViewEdit;
        ImageView imageViewDelete;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if(convertView == null) {
            viewHolder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(layout, null);
            viewHolder.textViewLoaiThu = (TextView) convertView.findViewById(R.id.textViewLoaiThu);
            viewHolder.imageViewDelete = (ImageView) convertView.findViewById(R.id.imageViewDeleteLoaiThu);
            viewHolder.imageViewEdit = (ImageView) convertView.findViewById(R.id.imageViewEditLoaiThu);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
         LoaiThu loaithu = loaiThu.get(position);
        System.out.println(loaithu);
        viewHolder.textViewLoaiThu.setText(loaithu.getNameLoaiThu());
        viewHolder.imageViewEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "cap nhat" + loaithu.getNameLoaiThu(), Toast.LENGTH_SHORT).show();
                fragment.DialogCapNhatLoaiThu(loaithu.getNameLoaiThu(), loaithu.getId());
                System.out.println("132" + loaithu.getNameLoaiThu());
            }
        });
        viewHolder.imageViewDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragment.DialogDelete(loaithu.getNameLoaiThu(), loaithu.getId());
            }
        });

        return convertView;
    }
}
