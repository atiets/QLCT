package com.example.qlct.Fragment;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.qlct.Adapter.LoaiChiAdapter;
import com.example.qlct.Data.DatabaseHandler;
import com.example.qlct.Models.LoaiChi;
import com.example.qlct.R;

import java.util.ArrayList;

public class LoaiChiFragment extends Fragment {
    private DatabaseHandler databaseHandler;
    private ListView listView;
    private ArrayList<LoaiChi> arrayList;
    private Button btnThem;
    private LoaiChiAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_loai_chi, container, false);
        initDatabaseSQLite();
        initViews(view);
        initListeners();
        return view;
    }

    private void initDatabaseSQLite() {
        databaseHandler = new DatabaseHandler(requireContext());
        databaseHandler.QueryData("CREATE TABLE IF NOT EXISTS LoaiChi(Id INTEGER PRIMARY KEY AUTOINCREMENT, tenLoaiChi VARCHAR(200))");
        arrayList = new ArrayList<>();
        databaseSQLite();
    }

    private void databaseSQLite() {
        arrayList.clear();
        Cursor cursor = databaseHandler.GetData("SELECT * FROM LoaiChi");
        while (cursor.moveToNext()) {
            String tenLoaiChi = cursor.getString(1);
            int id = cursor.getInt(0);
            arrayList.add(new LoaiChi(id, tenLoaiChi));
        }
        if (adapter != null) {
            adapter.notifyDataSetChanged();
        }
    }

    private void initViews(View view) {
        listView = view.findViewById(R.id.listViewLoaiChi);
        btnThem = view.findViewById(R.id.btnThemLoaiChi);
        adapter = new LoaiChiAdapter(requireContext(), R.layout.list_loaichi_layout, arrayList, this); // Khởi tạo adapter
        listView.setAdapter(adapter);
    }

    private void initListeners() {
        btnThem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DiaLogThem();
            }
        });
    }

    private void DiaLogThem() {
        Dialog dialog = new Dialog(requireContext());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.diag_add_loachi);
        EditText edt = dialog.findViewById(R.id.edtNameLoaiChi);
        Button btnAdd = dialog.findViewById(R.id.btnThemLoaiChi);
        Button btnHuy = dialog.findViewById(R.id.btnHuyThemLoaiChi);
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String tenLoaiChi = edt.getText().toString().trim();
                if(tenLoaiChi.equals("")) {
                    Toast.makeText(requireContext(), "Vui lòng nhập tên loại chi", Toast.LENGTH_SHORT).show();
                }
                else {
                    databaseHandler.QueryData("INSERT INTO LoaiChi VALUES(null, '"+ tenLoaiChi +"')");
                    Toast.makeText(requireContext(), "Đã thêm loại chi mới", Toast.LENGTH_SHORT).show();
                    dialog.dismiss();
                    databaseSQLite();
                }
            }
        });
        btnHuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    public void DialogCapNhatLoaiChi(String tenLoaiChi, int id) {
        Dialog dialog = new Dialog(requireContext());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.diag_update_loaichi);
        EditText edt = dialog.findViewById(R.id.edtNameLoaiChi);
        Button btnEdit = dialog.findViewById(R.id.btnCNLoaiChi);
        Button btnHuy = dialog.findViewById(R.id.btnHuyCNLoaiChi);
        edt.setText(tenLoaiChi);
        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String tenLoaiChi = edt.getText().toString().trim();
                databaseHandler.QueryData("UPDATE LoaiChi SET tenLoaiChi ='"+ tenLoaiChi +"' WHERE Id = '"+ id +"'");
                Toast.makeText(requireContext(), "Đã cập nhật thành công", Toast.LENGTH_SHORT).show();
                dialog.dismiss();
                databaseSQLite();
            }
        });
        btnHuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId()==R.id.menuAddLoaiThu) {
            DiaLogThem();
        }
        return super.onOptionsItemSelected(item);
    }

    public void DialogDelete(String tenLoaiChi, int id) {
        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
        builder.setMessage("Bạn có muốn xóa loại chi " + tenLoaiChi + " này không");
        builder.setPositiveButton("Có", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                databaseHandler.QueryData("DELETE FROM LoaiChi WHERE Id='" + id + "'");
                Toast.makeText(requireContext(), "Đã xóa loại chi " + tenLoaiChi + " thành công", Toast.LENGTH_SHORT).show();
                databaseSQLite();
            }
        });
        builder.setNegativeButton("Không", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        });
        builder.show();
    }
}
