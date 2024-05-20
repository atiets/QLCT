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
import com.example.qlct.Adapter.KhoanThuAdapter;
import com.example.qlct.Adapter.LoaiChiAdapter;
import com.example.qlct.Adapter.SpLoaiChiAdapter;
import com.example.qlct.Adapter.SpLoaiThuAdapter;
import com.example.qlct.Data.DatabaseHandler;
import com.example.qlct.Models.KhoanChi;
import com.example.qlct.Models.KhoanThu;
import com.example.qlct.Models.LoaiChi;
import com.example.qlct.Models.LoaiThu;
import com.example.qlct.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class KhoanThuFragment extends Fragment {
    Button btnThemKhoanThu;
    static ArrayList<KhoanThu> thuArrayList;
    static ArrayList<LoaiThu> loaithuArrayList;
    RecyclerView listViewKhoanThu;
    LoaiChi loaichi;
    static KhoanThuAdapter thuAdapter;
    int idLoaiThu;
    int idThu;
    String loaiThu;
    static DatabaseHandler database;
    String Loai;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_khoan_thu, container, false);
        addControls(v);
        addEvents();
        return v;
    }


    private void addEvents() {
        btnThemKhoanThu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addKhoanThuDialog();
            }
        });
    }

    private void addControls(View v) {
        btnThemKhoanThu = v.findViewById(R.id.btnThemKhoanThu);
        listViewKhoanThu = v.findViewById(R.id.listViewKhoanThu);
        database = new DatabaseHandler(getContext());
        thuArrayList = new ArrayList<>();

        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext().getApplicationContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);

        loaithuArrayList = database.getAllLoaiThu();

        thuAdapter = new KhoanThuAdapter(getContext(), R.layout.list_loaithu_layout, thuArrayList, loaithuArrayList);

        listViewKhoanThu.setLayoutManager(layoutManager);
        listViewKhoanThu.setItemAnimator(new DefaultItemAnimator());
        listViewKhoanThu.setAdapter(thuAdapter);

        loadData();
    }


    private void addKhoanThuDialog() {
        final Dialog dialog = new Dialog(getContext());
        dialog.setContentView(R.layout.diag_add_thu);

        Button btnHuy = dialog.findViewById(R.id.btnHuyThemKhoanThu);
        final EditText edtThemKhoanThu = dialog.findViewById(R.id.edtNameThu);
        final EditText edtThemSoTienThu = dialog.findViewById(R.id.edtTienThu);
        final EditText edtThemNgayThu = dialog.findViewById(R.id.edtThemNgayThu);
        final Spinner spLoaiThu = dialog.findViewById(R.id.spLoaiThu);
        Button btnThemKhoanThu = dialog.findViewById(R.id.btnThemKhoanThu);

        addItemsToSpinnerLoaiThu(spLoaiThu);

        Calendar calendar = Calendar.getInstance();
        int mYear = calendar.get(Calendar.YEAR);
        int mMonth = calendar.get(Calendar.MONTH);
        int mDay = calendar.get(Calendar.DAY_OF_MONTH);
        edtThemNgayThu.setText(mDay + "/" + (mMonth + 1) + "/" + mYear);

        btnHuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        edtThemNgayThu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                chonNgay(edtThemNgayThu);
            }
        });

        btnThemKhoanThu.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("ResourceType")
            @Override
            public void onClick(View view) {
                String edtKhoanThu = edtThemKhoanThu.getText().toString();
                String edtLoaiThu = Loai;
                String edtSoTien = edtThemSoTienThu.getText().toString();
                String edtNgayThu = edtThemNgayThu.getText().toString();
                if (edtKhoanThu.isEmpty()) {
                    edtThemKhoanThu.setError("Không được bỏ trống");
                } else if (edtSoTien.isEmpty()) {
                    edtThemSoTienThu.setError("Không được bỏ trống");
                } else if (edtNgayThu.isEmpty()) {
                    edtThemNgayThu.setError("Không được bỏ trống");
                } else {
                    edtThemKhoanThu.setError(null);
                    edtThemSoTienThu.setError(null);
                    edtThemNgayThu.setError(null);
                    if (idLoaiThu == 0) {
                        Toast.makeText(getContext(), "Không được bỏ trống", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(getContext(), "Thêm khoản thu thành công", Toast.LENGTH_SHORT).show();
                        database.addKhoanThu(new KhoanThu(idThu, edtKhoanThu, edtLoaiThu, edtNgayThu, Integer.parseInt(edtSoTien), 0, 0, idLoaiThu));
                        loadData();
                        dialog.dismiss();
                    }
                }
            }
        });


        dialog.show();
    }

    public void addItemsToSpinnerLoaiThu(Spinner spChonLoaiThu) {
        loaithuArrayList = new ArrayList<LoaiThu>();
        loaithuArrayList.add(new LoaiThu(0, "Chọn"));

        dataSpinnerLoaiThu(loaithuArrayList);

        int layoutResource = R.layout.list_khoanthu_layout;
        LoaiThuFragment fragmentInstance = null;

        SpLoaiThuAdapter spinAdapter = new SpLoaiThuAdapter(
                getContext(), loaithuArrayList);
        spChonLoaiThu.setAdapter(spinAdapter);
        spChonLoaiThu.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> adapter, View v,
                                       int position, long id) {
                idLoaiThu = loaithuArrayList.get(position).getId();
                Loai = loaithuArrayList.get(position).getNameLoaiThu();
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });
    }


    public static void dataSpinnerLoaiThu(ArrayList<LoaiThu> loaiThuArrayList) {
        Cursor cursor = database.GetDate("SELECT * FROM LoaiThu");
        while (cursor.moveToNext()) {
            int idLoaiThu = cursor.getInt(0);
            String tenLoaiThu = cursor.getString(1);
            loaiThuArrayList.add(new LoaiThu(idLoaiThu, tenLoaiThu));

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
        Cursor cursor = database.GetDate("SELECT * FROM KhoanThu where deleteFlag = 0");
        thuArrayList.clear();
        while (cursor.moveToNext()) {
            int id = cursor.getInt(0);
            String tenThu = cursor.getString(1);
            String loaiThu = cursor.getString(4);
            String thoiGian = cursor.getString(3);
            int soTien = cursor.getInt(2);
            int danhGia = cursor.getInt(5);
            int deleteFlag = cursor.getInt(6);
            int idLoaiThu = cursor.getInt(7);

            thuArrayList.add(new KhoanThu(id, tenThu, loaiThu, thoiGian, soTien, danhGia, deleteFlag, idLoaiThu));
        }

        thuAdapter.notifyDataSetChanged();
    }

    public static void showDialogFullThu(Context context, KhoanThu thu, int idLoaiThu) {
        if (context == null) {
            throw new IllegalArgumentException("Context must not be null");
        }
        final Dialog dialog = new Dialog(context);
        dialog.setContentView(R.layout.diag_read_thu);

        Button imgCloseViewFullThu = dialog.findViewById(R.id.btnHuyXemKhoanThu);
        TextView txtShowLoaiThuView = dialog.findViewById(R.id.txtLoaiThu);
        TextView txtShowNameKhoanThuView = dialog.findViewById(R.id.txtKhoanThu);
        TextView txtShowSoTienView = dialog.findViewById(R.id.txtTienThu);
        TextView txtShowNgayThuView = dialog.findViewById(R.id.txtNgayThu);

        Cursor cursor = database.GetDate("SELECT * FROM LoaiThu WHERE id = '" + idLoaiThu + "'");
        while (cursor.moveToNext()) {
            String tenLoaiThu = cursor.getString(1);
            txtShowLoaiThuView.setText(tenLoaiThu);
        }

        txtShowNameKhoanThuView.setText(thu.getTenThu());
        txtShowSoTienView.setText(FormatCost(Long.parseLong(thu.getLoaiThu())));
        txtShowNgayThuView.setText(thu.getThoiDiemThu());

        imgCloseViewFullThu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        dialog.show();
    }

    public static void deleteKhoanThu(int id) {
        database.QueryData("UPDATE KhoanThu SET deleteFlag = 1 WHERE idThu = '" + id + "'");
        loadData();
    }

    public static void editKhoanThu(int id, String edtKhoanThu, String edtLoaiThu, int edtSoTien, String edtNgayThu, int idLoaiThu) {
        database.QueryData("UPDATE KhoanThu SET tenThu = '" + edtKhoanThu + "' , loaiThu = '" + edtLoaiThu + "' , soTien = '" + edtSoTien + "' , thoiDiemThu = '" + edtNgayThu + "' , idLoaiThu = " + idLoaiThu + " WHERE idThu = '" + id + "'");
        loadData();
    }
    public static String loaithu(int id) {
        Cursor cursor = database.GetDate("SELECT * FROM LoaiThu WHERE id = '" + id + "'");
        while (cursor.moveToNext()) {
            String tenLoaiThu = cursor.getString(1);
            return tenLoaiThu;
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
