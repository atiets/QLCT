package com.example.qlct.Fragment;

import android.app.Dialog;
import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.qlct.Adapter.LoaiThuAdapter;
import com.example.qlct.Data.DatabaseHandler;
import com.example.qlct.Models.LoaiThu;
import com.example.qlct.R;

import java.util.ArrayList;

public class LoaiThuFragment extends Fragment {
    Button btnThemLoaiThu;
    static DatabaseHandler database;
    static LoaiThuAdapter loaiThuAdapter;
    ListView listViewLoaiThu;
    static ArrayList<LoaiThu> loaiThuArrayList;
    static View vi;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_loai_thu, container, false);
        vi = v;
        addControls(v);
        addEvents();
        return v;
    }

    private void addEvents() {
        btnThemLoaiThu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogAddLoaiThu();
            }
        });
    }

    private void dialogAddLoaiThu() {
        final Dialog dialog = new Dialog(getContext());
        dialog.setContentView(R.layout.diag_add_loaithu);

        Button btnThem = dialog.findViewById(R.id.btnThemLoaiThu);
        Button btnHuy = dialog.findViewById(R.id.btnHuyThemLoaiThu);
        final EditText edtThemLoaiThu = dialog.findViewById(R.id.edtNameLoaiThu);

        btnHuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        btnThem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String edtLoaiThu = edtThemLoaiThu.getText().toString();
                if (edtLoaiThu.isEmpty()) {
                    edtThemLoaiThu.setError("Không được bỏ trống");
                } else {
                    edtThemLoaiThu.setError(null);
                    database.addLoaiThu(new LoaiThu(0, edtLoaiThu));
                    loadData();
                    dialog.dismiss();
                }
            }
        });

        dialog.show();
    }

    private static void loadData() {
        Cursor cursor = database.GetDate("SELECT  * FROM loaithu WHERE deleteGlag = 0");
        loaiThuArrayList.clear();
        while (cursor.moveToNext()) {
            int id = cursor.getInt(0);
            String tenLoaiThu = cursor.getString(1);
            int deleteGlag = cursor.getInt(2);
            loaiThuArrayList.add(new LoaiThu(id, tenLoaiThu));
        }
        loaiThuAdapter.notifyDataSetChanged();
    }

    private void addControls(View v) {
        btnThemLoaiThu = v.findViewById(R.id.btnThemLoaiThu); // Thay thế FloatingActionButton bằng Button
        database = new DatabaseHandler(getContext());
        listViewLoaiThu = v.findViewById(R.id.listViewThemLoaiThu);
        loaiThuArrayList = new ArrayList<>();

        loaiThuAdapter = new LoaiThuAdapter(getContext(), loaiThuArrayList);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext().getApplicationContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        listViewLoaiThu.setAdapter(loaiThuAdapter);
        loadData();
    }

    public static void loaiThuDelete(int id) {
        database.QueryData("UPDATE loaithu SET deleteGlag = 1 WHERE id = '" + id + "'");
        loadData();
    }

    public static void loaiThuEdit(final Context c, final int id, String nameLoaiThu) {
        final Dialog dialog = new Dialog(c);
        dialog.setContentView(R.layout.diag_update_loaithu);
        Button btnHuy = dialog.findViewById(R.id.btnHuyCNLoaiThu);
        final EditText edtEditData = dialog.findViewById(R.id.edtNameLoaiThu);
        Button btnUpdate = dialog.findViewById(R.id.btnCNLoaiThu);

        edtEditData.setText(nameLoaiThu);

        btnHuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String edtEdit = edtEditData.getText().toString();
                if (edtEdit.isEmpty()) {
                    edtEditData.setError("Không được bỏ trống");
                } else {
                    edtEditData.setError(null);

                    database.QueryData("UPDATE loaithu SET tenLoaiThu = '" + edtEdit + "' WHERE id = '" + id + "'");
                    loadData();
                    dialog.dismiss();
                }
            }
        });

        dialog.show();
    }
}
