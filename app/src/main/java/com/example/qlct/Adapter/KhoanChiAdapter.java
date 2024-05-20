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
import com.example.qlct.Models.KhoanChi;
import com.example.qlct.Models.LoaiChi;
import com.example.qlct.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class KhoanChiAdapter extends RecyclerView.Adapter<KhoanChiAdapter.MyViewHolder> {
    ArrayList<LoaiChi> loaiChiArrayList;
    private ArrayList<KhoanChi> chis;
    static DatabaseHandler database;

    private LayoutInflater mLayoutInflater;
    private Context mContext;
    int idLoaiChi;
    View v;
    private Context context;
    private int layout;
    private KhoanChiFragment fragment;
    private String Loai;

    public KhoanChiAdapter(Context context, int layout, ArrayList<KhoanChi> chis, ArrayList<LoaiChi> loaiChiArrayList) {
        this.context = context;
        this.layout = layout;
        this.chis = chis;
        this.loaiChiArrayList = loaiChiArrayList;
        this.mLayoutInflater = LayoutInflater.from(context);
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView txtNameChi, txtSoTien;
        ImageView imgEdtChi, imgDeleteChi;

        public MyViewHolder(View itemView) {
            super(itemView);
            txtNameChi = itemView.findViewById(R.id.textViewChi);
            txtSoTien = itemView.findViewById(R.id.textViewTienChi);
            imgEdtChi = itemView.findViewById(R.id.imageViewEditChi);
            imgDeleteChi = itemView.findViewById(R.id.imageViewDeleteChi);
        }
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View item = mLayoutInflater.inflate(R.layout.list_khoanchi_layout, parent, false);
        v = item;
        database = new DatabaseHandler(context);
        loaiChiArrayList = database.getAllLoaiChi();
        System.out.println("nat4" + loaiChiArrayList);
        return new MyViewHolder(item);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        KhoanChi chi = chis.get(position);
        holder.txtNameChi.setText(chi.getTenChi());
        holder.txtSoTien.setText(String.valueOf(chi.getLoaiChi()));

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int idLoaiChi = chi.getIdLoaiChi();
                KhoanChiFragment.showDialogFullChi(context, chi, idLoaiChi);
            }
        });
        holder.imgDeleteChi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setMessage("Bạn có muốn xóa Khoản Chi " + chi.getTenChi() + " này không?");
                builder.setPositiveButton("Có", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        KhoanChiFragment.deleteKhoanChi(chi.getIdChi());
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

        holder.imgEdtChi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editChi(chi);
            }
        });
    }

    @Override
    public int getItemCount() {
        return chis.size();
    }

    public void editChi(final KhoanChi chi) {
        final Dialog dialog = new Dialog(context);
        dialog.setContentView(R.layout.diag_update_chi);

        Button imgClose = dialog.findViewById(R.id.btnHuyCapNhatChi);
            final EditText edtEditKhoanChi = dialog.findViewById(R.id.edtupdateNameChi);
            final EditText edtEditSoTienChi = dialog.findViewById(R.id.edtupdateTienChi);
            final EditText edtEditNgayChi = dialog.findViewById(R.id.edtupdateNgayChi);
            final Spinner spShowLoaiChiEdit = dialog.findViewById(R.id.spupdateLoaiChi);
            Button btnEditUpKhoanChi = dialog.findViewById(R.id.btnCapNhatKhoanChi);

            if (loaiChiArrayList == null) {
                Toast.makeText(context, "Danh sách loại chi không tồn tại.", Toast.LENGTH_SHORT).show();
                return;
            }

            addItemsToSpinnerLoaiChi(spShowLoaiChiEdit, context, loaiChiArrayList);

            edtEditKhoanChi.setText(chi.getTenChi());
            edtEditSoTienChi.setText(String.valueOf(chi.getSoTien()));
            edtEditNgayChi.setText(chi.getThoiDiemChi());

            String selectedLoaiChi = chi.getLoaiChi();
            if (selectedLoaiChi != null) {
                for (int i = 0; i < loaiChiArrayList.size(); i++) {
                    if (selectedLoaiChi.equals(loaiChiArrayList.get(i).getNameLoaiChi())) {
                        spShowLoaiChiEdit.setSelection(i);
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

            edtEditNgayChi.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    chonNgay(edtEditNgayChi);
                }
            });

            btnEditUpKhoanChi.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String edtKhoanChi = edtEditKhoanChi.getText().toString();
                    String edtSoTien = edtEditSoTienChi.getText().toString();
                    String edtNgayChi = edtEditNgayChi.getText().toString();
                    if (edtKhoanChi.isEmpty() || edtSoTien.isEmpty() || edtNgayChi.isEmpty()) {
                        Toast.makeText(context, "Vui lòng nhập đầy đủ thông tin.", Toast.LENGTH_SHORT).show();
                    } else {
                        edtEditKhoanChi.setError(null);
                        edtEditSoTienChi.setError(null);
                        edtEditNgayChi.setError(null);
                        int soTien = Integer.parseInt(edtSoTien);
                        KhoanChiFragment.editKhoanChi(chi.getIdChi(), edtKhoanChi, Loai, soTien, edtNgayChi, idLoaiChi);
                        dialog.dismiss();
                    }
                }
            });

            dialog.show();
    }


    public void addItemsToSpinnerLoaiChi(Spinner spChonLoaiChi, Context context, ArrayList<LoaiChi> loaichi) {
        if (context == null || loaichi == null || loaichi.isEmpty()) {
            return;
        }

        SpLoaiChiAdapter spinAdapter = new SpLoaiChiAdapter(context, loaichi);
        spChonLoaiChi.setAdapter(spinAdapter);

        spChonLoaiChi.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapter, View v, int position, long id) {
                idLoaiChi = loaichi.get(position).getId();
                Loai = loaichi.get(position).getNameLoaiChi();
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
