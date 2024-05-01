package com.example.qlct.Fragment;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import java.util.ArrayList;

import com.example.qlct.Adapter.LoaiThuAdapter;
import com.example.qlct.Data.DatabaseHandler;
import com.example.qlct.Models.LoaiThu;
import com.example.qlct.R;

public class LoaiThuFragment extends Fragment {

    private DatabaseHandler databaseHandler;
    private ListView listView;
    private ArrayList<LoaiThu> arrayList;
    private Button btnThem;
    private LoaiThuAdapter adapter; // Khai báo adapter

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_loai_thu, container, false);
        initDatabaseSQLite();
        initViews(view);
        initListeners();
        return view;
    }

    private void initDatabaseSQLite() {
        databaseHandler = new DatabaseHandler(requireContext());
        databaseHandler.QueryData("CREATE TABLE IF NOT EXISTS LoaiThu(Id INTEGER PRIMARY KEY AUTOINCREMENT, tenLoaiThu VARCHAR(200))");
        arrayList = new ArrayList<>();
        databaseSQLite();
    }

    private void databaseSQLite() {
        arrayList.clear();
        Cursor cursor = databaseHandler.GetData("SELECT * FROM LoaiThu");
        while (cursor.moveToNext()) {
            String tenLoaiThu = cursor.getString(1);
            int id = cursor.getInt(0);
            arrayList.add(new LoaiThu(id, tenLoaiThu));
        }
        if (adapter != null) { // Kiểm tra adapter trước khi cập nhật
            adapter.notifyDataSetChanged();
        }
    }

    private void initViews(View view) {
        listView = view.findViewById(R.id.listViewLoaiThu);
        btnThem = view.findViewById(R.id.btnThemLoaiThu);
        adapter = new LoaiThuAdapter(requireContext(), R.layout.list_loaithu_layout, arrayList, this); // Khởi tạo adapter
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
        dialog.setContentView(R.layout.diag_add_loaithu);
        EditText edt = dialog.findViewById(R.id.edtNameLoaiThu);
        Button btnAdd = dialog.findViewById(R.id.btnThemLoaiThu);
        Button btnHuy = dialog.findViewById(R.id.btnHuyThemLoaiThu);
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String tenLoaiThu = edt.getText().toString().trim();
                if(tenLoaiThu.equals("")) {
                    Toast.makeText(requireContext(), "Vui lòng nhập tên loại thu", Toast.LENGTH_SHORT).show();
                }
                else {
                    databaseHandler.QueryData("INSERT INTO LoaiThu VALUES(null, '"+ tenLoaiThu +"')");
                    Toast.makeText(requireContext(), "Đã thêm loại thu mới", Toast.LENGTH_SHORT).show();
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

    public void DialogCapNhatLoaiThu(String tenLoaiThu, int id) {
        Dialog dialog = new Dialog(requireContext());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.diag_update_loaithu);
        EditText edt = dialog.findViewById(R.id.edtNameLoaiThu);
        Button btnEdit = dialog.findViewById(R.id.btnCNLoaiThu);
        Button btnHuy = dialog.findViewById(R.id.btnHuyCNLoaiThu);
        edt.setText(tenLoaiThu);
        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String tenLoaiThu = edt.getText().toString().trim();
                databaseHandler.QueryData("UPDATE LoaiThu SET tenLoaiThu ='"+ tenLoaiThu +"' WHERE Id = '"+id+"'");
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

    public void DialogDelete(String tenLoaiThu, int id) {
        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
        builder.setMessage("Bạn có muốn xóa loại thu " + tenLoaiThu + " này không");
        builder.setPositiveButton("Có", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                databaseHandler.QueryData("DELETE FROM LoaiThu WHERE Id='" + id + "'");
                Toast.makeText(requireContext(), "Đã xóa loại thu " + tenLoaiThu + " thành công", Toast.LENGTH_SHORT).show();
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
