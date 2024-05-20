package com.example.qlct.Adapter;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.example.qlct.Data.DatabaseHandler;
import com.example.qlct.Fragment.KhoanChiFragment;
import com.example.qlct.Fragment.KhoanThuFragment;
import com.example.qlct.Models.KhoanChi;
import com.example.qlct.Models.KhoanThu;
import com.example.qlct.Models.LoaiChi;
import com.example.qlct.Models.LoaiThu;
import com.example.qlct.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class KhoanThuAdapter extends RecyclerView.Adapter<KhoanThuAdapter.MyViewHolder> {
    ArrayList<LoaiThu> loaiThuArrayList;
    private ArrayList<KhoanThu> thus;
    static DatabaseHandler database;

    private LayoutInflater mLayoutInflater;
    private Context mContext;
    int idLoaiThu;
    View v;
    private Context context;
    private int layout;
    private KhoanThuFragment fragment;
    private String Loai;

    public KhoanThuAdapter(Context context, int layout, ArrayList<KhoanThu> thus, ArrayList<LoaiThu> loaiThuArrayList) {
        this.context = context;
        this.layout = layout;
        this.thus = thus;
        this.loaiThuArrayList = loaiThuArrayList;
        this.mLayoutInflater = LayoutInflater.from(context);
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView txtNameThu, txtSoTien;
        ImageView imgEdtThu, imgDeleteThu;

        public MyViewHolder(View itemView) {
            super(itemView);
            txtNameThu = itemView.findViewById(R.id.textViewThu);
            txtSoTien = itemView.findViewById(R.id.textViewTienThu);
            imgEdtThu = itemView.findViewById(R.id.imageViewEditThu);
            imgDeleteThu = itemView.findViewById(R.id.imageViewDeleteThu);
        }
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View item = mLayoutInflater.inflate(R.layout.list_loaithu_layout, parent, false);
        v = item;
        database = new DatabaseHandler(context);
        loaiThuArrayList = database.getAllLoaiThu();
        return new MyViewHolder(item);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        KhoanThu thu = thus.get(position);
        holder.txtNameThu.setText(thu.getTenThu());
        holder.txtSoTien.setText(String.valueOf(thu.getLoaiThu()));

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int idLoaiThu = thu.getIdLoaiThu();
                KhoanThuFragment.showDialogFullThu(context, thu, idLoaiThu);
            }
        });
        holder.imgDeleteThu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setMessage("Bạn có muốn xóa Khoản Thu " + thu.getTenThu() + " này không?");
                builder.setPositiveButton("Có", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        KhoanThuFragment.deleteKhoanThu(thu.getIdThu());
                    }
                });
                builder.setNegativeButton("Không", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                builder.show();
            }
        });

        holder.imgEdtThu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editThu(thu);
            }
        });
    }

    @Override
    public int getItemCount() {
        return thus.size();
    }

    public void editThu(final KhoanThu thu) {
        final Dialog dialog = new Dialog(context);
        dialog.setContentView(R.layout.diag_update_thu);

        Button imgClose = dialog.findViewById(R.id.btnHuyCapNhatThu);
        final EditText edtEditKhoanThu = dialog.findViewById(R.id.edtupdateNameThu);
        final EditText edtEditSoTienThu = dialog.findViewById(R.id.edtupdateTienThu);
        final EditText edtEditNgayThu = dialog.findViewById(R.id.edtupdateNgayThu);
        final Spinner spShowLoaiThuEdit = dialog.findViewById(R.id.spupdateLoaiThu);
        Button btnEditUpKhoanThu = dialog.findViewById(R.id.btnCapNhatKhoanThu);

        if (loaiThuArrayList == null) {
            Toast.makeText(context, "Danh sách loại thu không tồn tại.", Toast.LENGTH_SHORT).show();
            return;
        }

        addItemsToSpinnerLoaiThu(spShowLoaiThuEdit, context, loaiThuArrayList);

        edtEditKhoanThu.setText(thu.getTenThu());
        edtEditSoTienThu.setText(String.valueOf(thu.getSoTien()));
        edtEditNgayThu.setText(thu.getThoiDiemThu());

        String selectedLoaiThu = thu.getLoaiThu();
        if (selectedLoaiThu != null) {
            for (int i = 0; i < loaiThuArrayList.size(); i++) {
                if (selectedLoaiThu.equals(loaiThuArrayList.get(i).getNameLoaiThu())) {
                    spShowLoaiThuEdit.setSelection(i);
                    break;
                }
            }
        }

        imgClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        edtEditNgayThu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                chonNgay(edtEditNgayThu);
            }
        });

        btnEditUpKhoanThu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String edtKhoanThu = edtEditKhoanThu.getText().toString();
                String edtSoTien = edtEditSoTienThu.getText().toString();
                String edtNgayThu = edtEditNgayThu.getText().toString();
                if (edtKhoanThu.isEmpty() || edtSoTien.isEmpty() || edtNgayThu.isEmpty()) {
                    Toast.makeText(context, "Vui lòng nhập đầy đủ thông tin.", Toast.LENGTH_SHORT).show();
                } else {
                    edtEditKhoanThu.setError(null);
                    edtEditSoTienThu.setError(null);
                    edtEditNgayThu.setError(null);
                    int soTien = Integer.parseInt(edtSoTien);
                    KhoanThuFragment.editKhoanThu(thu.getIdThu(), edtKhoanThu, Loai, soTien, edtNgayThu, idLoaiThu);
                    dialog.dismiss();
                }
            }
        });

        dialog.show();
    }


    public void addItemsToSpinnerLoaiThu(Spinner spChonLoaiThu, Context context, ArrayList<LoaiThu> loaithu) {
        if (context == null || loaithu == null || loaithu.isEmpty()) {
            return;
        }

        SpLoaiThuAdapter spinAdapter = new SpLoaiThuAdapter(context, loaithu);
        spChonLoaiThu.setAdapter(spinAdapter);

        spChonLoaiThu.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapter, View v, int position, long id) {
                idLoaiThu = loaithu.get(position).getId();
                Loai = loaithu.get(position).getNameLoaiThu();
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });
    }



    private void chonNgay(final EditText chonNgay) {
        final Calendar calendar = Calendar.getInstance();
        int ngay = calendar.get(Calendar.DATE);
        int thang = calendar.get(Calendar.MONTH);
        int nam = calendar.get(Calendar.YEAR);
        DatePickerDialog datePickerDialog = new DatePickerDialog(context, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                calendar.set(year, month, dayOfMonth);
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
                chonNgay.setText(simpleDateFormat.format(calendar.getTime()));
            }
        }, nam, thang, ngay);
        datePickerDialog.show();
    }
}

