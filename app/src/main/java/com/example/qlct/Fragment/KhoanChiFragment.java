package com.example.qlct.Fragment;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.qlct.Adapter.KhoanChiAdapter;
import com.example.qlct.Adapter.LoaiChiAdapter;
import com.example.qlct.Adapter.SpLoaiChiAdapter;
import com.example.qlct.Data.DatabaseHandler;
import com.example.qlct.Models.KhoanChi;
import com.example.qlct.Models.LoaiChi;
import com.example.qlct.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class KhoanChiFragment extends Fragment {
    Button btnThemKhoanChi;
    static ArrayList<KhoanChi> chiArrayList;
    static ArrayList<LoaiChi> loaichiArrayList;
    RecyclerView listViewKhoanChi;
    LoaiChi loaichi;
    static KhoanChiAdapter chiAdapter;
    int idLoaiChi;
    int idChi;
    String loaiChi;
    static DatabaseHandler database;
    String Loai;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_khoan_chi, container, false);
        addControls(v);
        addEvents();
        return v;
    }


    private void addEvents() {
        btnThemKhoanChi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addKhoanChiDialog();
            }
        });
    }

    private void addControls(View v) {
        btnThemKhoanChi = v.findViewById(R.id.btnThemKhoanChi);
        listViewKhoanChi = v.findViewById(R.id.listViewKhoanChi);
        database = new DatabaseHandler(getContext());
        chiArrayList = new ArrayList<>();

        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext().getApplicationContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);

        loaichiArrayList = database.getAllLoaiChi();

        chiAdapter = new KhoanChiAdapter(getContext(), R.layout.list_loaichi_layout, chiArrayList, loaichiArrayList);

        listViewKhoanChi.setLayoutManager(layoutManager);
        listViewKhoanChi.setItemAnimator(new DefaultItemAnimator());
        listViewKhoanChi.setAdapter(chiAdapter);

        loadData();
    }


    private void addKhoanChiDialog() {
        final Dialog dialog = new Dialog(getContext());
        dialog.setContentView(R.layout.diag_add_chi);

        Button btnHuy = dialog.findViewById(R.id.btnHuyThemKhoanChi);
        final EditText edtThemKhoanChi = dialog.findViewById(R.id.edtNameChi);
        final EditText edtThemSoTienChi = dialog.findViewById(R.id.edtTienChi);
        final EditText edtThemNgayChi = dialog.findViewById(R.id.edtThemNgayChi);
        final Spinner spLoaiChi = dialog.findViewById(R.id.spLoaiChi);
        Button btnThemKhoanChi = dialog.findViewById(R.id.btnThemKhoanChi);

        addItemsToSpinnerLoaiChi(spLoaiChi);

        Calendar calendar = Calendar.getInstance();
        int mYear = calendar.get(Calendar.YEAR);
        int mMonth = calendar.get(Calendar.MONTH);
        int mDay = calendar.get(Calendar.DAY_OF_MONTH);
        edtThemNgayChi.setText(mDay + "/" + (mMonth + 1) + "/" + mYear);

        btnHuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        edtThemNgayChi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                chonNgay(edtThemNgayChi);
            }
        });

        btnThemKhoanChi.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("ResourceType")
            @Override
            public void onClick(View view) {
                String edtKhoanChi = edtThemKhoanChi.getText().toString();
                String edtLoaiChi = Loai;
                String edtSoTien = edtThemSoTienChi.getText().toString();
                String edtNgayChi = edtThemNgayChi.getText().toString();
                if (edtKhoanChi.isEmpty()) {
                    edtThemKhoanChi.setError("Không được bỏ trống");
                } else if (edtSoTien.isEmpty()) {
                    edtThemSoTienChi.setError("Không được bỏ trống");
                } else if (edtNgayChi.isEmpty()) {
                    edtThemNgayChi.setError("Không được bỏ trống");
                } else {
                    edtThemKhoanChi.setError(null);
                    edtThemSoTienChi.setError(null);
                    edtThemNgayChi.setError(null);
                    if (idLoaiChi == 0) {
                        Toast.makeText(getContext(), "Không được bỏ trống", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(getContext(), "Thêm khoản chi thành công", Toast.LENGTH_SHORT).show();
                        database.addKhoanChi(new KhoanChi(idChi, edtKhoanChi, edtLoaiChi, edtNgayChi, Integer.parseInt(edtSoTien), 0, 0, idLoaiChi));
                        loadData();
                        dialog.dismiss();
                    }
                }
            }
        });


        dialog.show();
    }

    public void addItemsToSpinnerLoaiChi(Spinner spChonLoaiChi) {
        loaichiArrayList = new ArrayList<LoaiChi>();
        loaichiArrayList.add(new LoaiChi(0, "Chọn"));

        dataSpinnerLoaiChi(loaichiArrayList);
        System.out.println("loc" + loaichiArrayList);

        int layoutResource = R.layout.list_khoanchi_layout;
        LoaiChiFragment fragmentInstance = null;

        SpLoaiChiAdapter spinAdapter = new SpLoaiChiAdapter(
                getContext(), loaichiArrayList);
        spChonLoaiChi.setAdapter(spinAdapter);
        spChonLoaiChi.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> adapter, View v,
                                       int position, long id) {
                idLoaiChi = loaichiArrayList.get(position).getId();
                Loai = loaichiArrayList.get(position).getNameLoaiChi();
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });
    }


    public static void dataSpinnerLoaiChi(ArrayList<LoaiChi> loaiChiArrayList) {
        Cursor cursor = database.GetDate("SELECT * FROM LoaiChi");
        while (cursor.moveToNext()) {
            int idLoaiChi = cursor.getInt(0);
            String tenLoaiChi = cursor.getString(1);
            System.out.println("tenloaichi" + tenLoaiChi);
            loaiChiArrayList.add(new LoaiChi(idLoaiChi, tenLoaiChi));
            System.out.println("tenloaichi" + loaiChiArrayList);

        }
    }

    private void chonNgay(final EditText chonNgay) {
        final Calendar calendar = Calendar.getInstance();
        int ngay = calendar.get(Calendar.DATE);
        int thang = calendar.get(Calendar.MONTH);
        int nam = calendar.get(Calendar.YEAR);
        DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(), new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                calendar.set(year, month, dayOfMonth);
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
                chonNgay.setText(simpleDateFormat.format(calendar.getTime()));
            }
        }, nam, thang, ngay);
        datePickerDialog.show();
    }

    public static void loadData() {
        Cursor cursor = database.GetDate("SELECT * FROM KhoanChi where deleteFlag = 0");
        chiArrayList.clear();
        while (cursor.moveToNext()) {
            int id = cursor.getInt(0);
            String tenChi = cursor.getString(1);
            String loaiChi = cursor.getString(4);
            String thoiGian = cursor.getString(3);
            int soTien = cursor.getInt(2);
            int danhGia = cursor.getInt(5);
            int deleteFlag = cursor.getInt(6);
            int idLoaiChi = cursor.getInt(7);

            chiArrayList.add(new KhoanChi(id, tenChi, loaiChi, thoiGian, soTien, danhGia, deleteFlag, idLoaiChi));
        }

        chiAdapter.notifyDataSetChanged();
    }

    public static void showDialogFullChi(Context context, KhoanChi chi, int idLoaiChi) {
        if (context == null) {
            throw new IllegalArgumentException("Context must not be null");
        }
        final Dialog dialog = new Dialog(context);
        dialog.setContentView(R.layout.diag_read_chi);

        Button imgCloseViewFullChi = dialog.findViewById(R.id.btnHuyXemKhoanChi);
        TextView txtShowLoaiChiView = dialog.findViewById(R.id.txtLoaiChi);
        TextView txtShowNameKhoanChiView = dialog.findViewById(R.id.txtKhoanChi);
        TextView txtShowSoTienView = dialog.findViewById(R.id.txtTienChi);
        TextView txtShowNgayChiView = dialog.findViewById(R.id.txtNgayChi);

        Cursor cursor = database.GetDate("SELECT * FROM LoaiChi WHERE id = '" + idLoaiChi + "'");
        while (cursor.moveToNext()) {
            String tenLoaiChi = cursor.getString(1);
            txtShowLoaiChiView.setText(tenLoaiChi);
        }

        txtShowNameKhoanChiView.setText(chi.getTenChi());
        txtShowSoTienView.setText(FormatCost(Long.parseLong(chi.getLoaiChi())));
        txtShowNgayChiView.setText(chi.getThoiDiemChi());

        imgCloseViewFullChi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        dialog.show();
    }

    public static void deleteKhoanChi(int id) {
        database.QueryData("UPDATE KhoanChi SET deleteFlag = 1 WHERE idChi = '" + id + "'");
        loadData();
    }

    public static void editKhoanChi(int id, String edtKhoanchi, String edtLoaiChi, int edtSoTien, String edtNgayChi, int idLoaiChi) {
        database.QueryData("UPDATE KhoanChi SET tenChi = '" + edtKhoanchi + "' , loaiChi = '" + edtLoaiChi + "' , soTien = '" + edtSoTien + "' , thoiDiemChi = '" + edtNgayChi + "' , idLoaiChi = " + idLoaiChi + " WHERE idChi = '" + id + "'");
        loadData();
    }
    public static String loaichi(int id) {
        Cursor cursor = database.GetDate("SELECT * FROM LoaiChi WHERE id = '" + id + "'");
        while (cursor.moveToNext()) {
            String tenLoaiChi = cursor.getString(1);
            return tenLoaiChi;
        }
        return null;
    }

    public static String FormatCost(long cost) {
        try {
            DecimalFormatSymbols symbols = new DecimalFormatSymbols();
            symbols.setDecimalSeparator(',');
            DecimalFormat decimalFormat = new DecimalFormat("###,###,###,###", symbols);
            return decimalFormat.format(cost);
        } catch (Exception e) {
            return cost + "";
        }
    }
}
