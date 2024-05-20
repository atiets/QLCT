package com.example.qlct.Fragment;
import android.app.DatePickerDialog;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.qlct.Data.DatabaseHandler;
import com.example.qlct.R;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class ChartFragment extends Fragment {
    private TextView txtYear, txtMonth,txtNoData;
    private ImageView btnPrevYear, btnNextYear;
    private int currentYear, currentMonth;

    private SQLiteDatabase ourDatabase;
    private DatabaseHandler dbHelper;
    private PieChart pieChart;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_chart, container, false);

        dbHelper = new DatabaseHandler(getContext());
        txtYear = view.findViewById(R.id.txt_year);
        txtMonth = view.findViewById(R.id.txt_month);
        btnPrevYear = view.findViewById(R.id.btn_prev_year);
        btnNextYear = view.findViewById(R.id.btn_next_year);
        pieChart=view.findViewById(R.id.pie_chart);
        txtNoData = view.findViewById(R.id.txt_no_data);

        // Lấy năm và tháng hiện tại
        Calendar calendar = Calendar.getInstance();
        currentYear = calendar.get(Calendar.YEAR);
        currentMonth = calendar.get(Calendar.MONTH);


        // Hiển thị năm và tháng hiện tại
        updateYearAndMonth();
        // Xử lý sự kiện click vào textview năm
        txtYear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showMonthPickerDialog();
                updateYearAndMonth();
            }
        });
        // Xử lý sự kiện click vào nút lùi một năm
        btnPrevYear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentYear--;
                updateYearAndMonth();
                drawPieChart();
            }
        });

        // Xử lý sự kiện click vào nút tiến một năm
        btnNextYear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentYear++;
                updateYearAndMonth();
                drawPieChart();
            }
        });

        drawPieChart();
        return view;
    }

    private void updateYearAndMonth() {
        txtYear.setText(String.valueOf(currentYear));
        txtMonth.setText(getMonthName(currentMonth));
    }

    private void showMonthPickerDialog() {
        DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                currentYear = year;
                currentMonth = month;

                updateYearAndMonth();
                drawPieChart();
            }
        };

        DatePickerDialog datePickerDialog = new DatePickerDialog(
                getContext(),
                dateSetListener,
                currentYear,
                currentMonth,
                1
        );

        datePickerDialog.show();
    }

    private String getMonthName(int month) {
        String[] monthNames = {"Tháng 1", "Tháng 2", "Tháng 3", "Tháng 4", "Tháng 5", "Tháng 6",
                "Tháng 7", "Tháng 8", "Tháng 9", "Tháng 10", "Tháng 11", "Tháng 12"};
        return monthNames[month];
    }

    private void drawPieChart() {
        Cursor c = null;

        try {
            ourDatabase = dbHelper.getWritableDatabase();

            // Lấy ngày đầu tiên và cuối cùng của tháng hiện tại
            Calendar calendar = Calendar.getInstance();
            calendar.set(currentYear, currentMonth, 1);
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            String startDate = sdf.format(calendar.getTime());
            calendar.add(Calendar.MONTH, 1);
            calendar.set(Calendar.DAY_OF_MONTH, 1);
            calendar.add(Calendar.DATE, -1);
            String endDate = sdf.format(calendar.getTime());

            // Truy vấn tổng chi tiêu theo loại chi tiêu trong tháng hiện tại
            String sql = "SELECT " + DatabaseHandler.getKeyTableLoaiChiKhoanChi() + ", SUM(" + DatabaseHandler.getKeyTableSoTienKhoanChi() + ") " +
                    "FROM " + DatabaseHandler.getKeyNameTableKhoanChi() + " " +
                    "WHERE " + DatabaseHandler.getKeyTableThoiDiemKhoanChi() + " BETWEEN '" + startDate + "' AND '" + endDate + "' " +
                    "GROUP BY " + DatabaseHandler.getKeyTableLoaiChiKhoanChi();

            c = ourDatabase.rawQuery(sql, null);

            if (c.moveToFirst()) {
                // Tạo dữ liệu cho biểu đồ
                List<PieEntry> entries = new ArrayList<>();
                List<Integer> colors = new ArrayList<>();

                int colorIndex = 0;

                do {
                    String loaiChi = c.getString(0);
                    double soTien = c.getDouble(1);

                    entries.add(new PieEntry((float) soTien, loaiChi));
                    colors.add(ColorTemplate.MATERIAL_COLORS[colorIndex % ColorTemplate.MATERIAL_COLORS.length]);

                    colorIndex++;
                } while (c.moveToNext());

                // Tạo biểu đồ
                PieDataSet dataSet = new PieDataSet(entries, "Biểu đồ chi tiêu");
                dataSet.setColors(colors);

                PieData data = new PieData(dataSet);
                pieChart.setData(data);

                // Tạo và gán Description
                Description description = new Description();
                description.setText("Biểu đồ chi tiêu tháng " + getMonthName(currentMonth) + " " + currentYear);
                pieChart.setDescription(description);

                pieChart.setDrawHoleEnabled(false);
                pieChart.setEntryLabelTextSize(14f);
                pieChart.animateY(1400);
                pieChart.invalidate();

                // Hiển thị biểu đồ và ẩn thông báo "Không có chi tiêu"
                pieChart.setVisibility(View.VISIBLE);
                txtNoData.setVisibility(View.GONE);
            } else {
                // Ẩn biểu đồ và hiển thị thông báo "Không có chi tiêu"
                pieChart.setVisibility(View.GONE);
                txtNoData.setVisibility(View.VISIBLE);
            }
        } catch (Exception e) {
            e.printStackTrace();
            Log.e("ChartFragment", "Lỗi khi truy vấn dữ liệu: " + e.getMessage());
        } finally {
            if (c != null) {
                c.close();
            }
            if (ourDatabase != null) {
                ourDatabase.close();
            }
        }
    }
}