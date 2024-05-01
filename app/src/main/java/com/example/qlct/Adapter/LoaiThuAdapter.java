package com.example.qlct.Adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.qlct.Fragment.LoaiThuFragment;
import com.example.qlct.Models.LoaiThu;
import com.example.qlct.R;

import java.util.List;

public class LoaiThuAdapter extends BaseAdapter {
    private List<LoaiThu> loaiThus;
    private LayoutInflater mLayoutInflater;
    private Context mContext;

    public LoaiThuAdapter(Context context, List<LoaiThu> loaiThus) {
        this.loaiThus = loaiThus;
        this.mContext = context;
        mLayoutInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return loaiThus.size();
    }

    @Override
    public Object getItem(int position) {
        return loaiThus.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    static class ViewHolder {
        TextView txtNameLoaiThu;
        ImageView imgEdtLoaiThu, imgDeleteLoaiThu;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = mLayoutInflater.inflate(R.layout.list_loaithu_layout, parent, false);
            holder = new ViewHolder();
            holder.txtNameLoaiThu = convertView.findViewById(R.id.textViewLoaiThu);
            holder.imgEdtLoaiThu = convertView.findViewById(R.id.imageViewEditLoaiThu);
            holder.imgDeleteLoaiThu = convertView.findViewById(R.id.imageViewDeleteLoaiThu);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        final LoaiThu loaiThu = loaiThus.get(position);
        holder.txtNameLoaiThu.setText(loaiThu.getNameLoaiThu());

        holder.imgDeleteLoaiThu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
                builder.setMessage("Bạn có muốn xóa loại thu " + loaiThu.getNameLoaiThu() + " này không?");
                builder.setPositiveButton("Có", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        LoaiThuFragment.loaiThuDelete(loaiThu.getId());
                    }
                });
                builder.setNegativeButton("Không", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                builder.show();
            }
        });

        holder.imgEdtLoaiThu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LoaiThuFragment.loaiThuEdit(mContext, loaiThu.getId(), loaiThu.getNameLoaiThu());
            }
        });

        return convertView;
    }
}
