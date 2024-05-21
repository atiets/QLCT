package com.example.qlct.Fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CalendarView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.qlct.Adapter.ExpenseDailyAdapter;
import com.example.qlct.Adapter.IncomeDailyAdapter;
import com.example.qlct.Data.DatabaseHandler;
import com.example.qlct.Models.KhoanChi;
import com.example.qlct.Models.KhoanThu;
import com.example.qlct.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class DateFragment extends Fragment {
    private RecyclerView recyclerViewIncome;
    private RecyclerView recyclerViewExpense;

    private IncomeDailyAdapter incomeDailyAdapter;
    private ExpenseDailyAdapter expenseDailyAdapter;

    private DatabaseHandler databaseHandler;
    private TextView totalIncomeTextView;
    private TextView totalExpensesTextView;
    private CalendarView calendarView;
    private TextView totalBalanceTextView;

    private int totalIncome = 0;
    private int totalExpenses = 0;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_date, container, false);

        // Khởi tạo DatabaseHandler và gán vào biến thành viên
        databaseHandler = new DatabaseHandler(getContext());

        calendarView = view.findViewById(R.id.calendarView);
        totalIncomeTextView = view.findViewById(R.id.tv_ThuNhap);
        totalExpensesTextView = view.findViewById(R.id.tv_ChiTieu);
        totalBalanceTextView = view.findViewById(R.id.tv_Tong);
        recyclerViewIncome = view.findViewById(R.id.recyclerViewIncome);
        recyclerViewIncome.setLayoutManager(new LinearLayoutManager(getContext()));

        recyclerViewExpense = view.findViewById(R.id.recyclerViewExpense);
        recyclerViewExpense.setLayoutManager(new LinearLayoutManager(getContext()));

        // Gọi loadTotalIncome() và loadTotalExpenses() với ngày hiện tại
        String currentDate = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(Calendar.getInstance().getTime());
        loadTotalIncome(currentDate);
        loadTotalExpenses(currentDate);

        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                handleSelectedDate(year, month + 1, dayOfMonth);
                hideRC();
            }
        });

        return view;
    }

    private void handleSelectedDate(int year, int month, int dayOfMonth) {
        Calendar selectedDate = Calendar.getInstance();
        selectedDate.set(year, month - 1, dayOfMonth);
        String selectedDateString = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(selectedDate.getTime());
        loadTotalIncome(selectedDateString);
        loadTotalExpenses(selectedDateString);
        loadDMincom(selectedDateString);
        loadDMExpense(selectedDateString);
    }

    public void loadDMExpense(String date)
    {
        List<KhoanChi> khoanChiList = databaseHandler.getKhoanChiList(date);

        if (khoanChiList.isEmpty()) {
            // Hiển thị thông báo hoặc xử lý trường hợp danh sách rỗng
            Toast.makeText(getContext(), "Không có khoản chi nào trong ngày hôm nay.", Toast.LENGTH_SHORT).show();
        } else {
            expenseDailyAdapter = new ExpenseDailyAdapter(khoanChiList);
            recyclerViewExpense.setAdapter(expenseDailyAdapter);
            recyclerViewExpense.setVisibility(View.VISIBLE);
            recyclerViewIncome.setVisibility(View.GONE);

            totalExpensesTextView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (recyclerViewIncome.getVisibility() == View.VISIBLE) {
                        recyclerViewIncome.setVisibility(View.GONE);
                        recyclerViewExpense.setVisibility(View.VISIBLE);
                    } else {
                        recyclerViewIncome.setVisibility(View.VISIBLE);
                        recyclerViewExpense.setVisibility(View.GONE);
                    }
                }
            });
        }
    }
    public void loadDMincom(String date)
    {
        totalIncomeTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<KhoanThu> khoanThuList = databaseHandler.getKhoanThuList(date);

                if (khoanThuList.isEmpty()) {
                    // Hiển thị thông báo hoặc xử lý trường hợp danh sách rỗng
                    Toast.makeText(getContext(), "Không có khoản thu nào trong ngày hôm nay.", Toast.LENGTH_SHORT).show();
                } else {
                    incomeDailyAdapter = new IncomeDailyAdapter(khoanThuList);
                    recyclerViewIncome.setAdapter(incomeDailyAdapter);
                    recyclerViewIncome.setVisibility(View.VISIBLE);
                    recyclerViewExpense.setVisibility(View.GONE);
                }
            }
        });
    }
    private void loadTotalIncome(String date) {
        Log.d("DateFragment", "Loading total income for date: " + date);

        if (databaseHandler != null) {
            totalIncome = databaseHandler.CalculateTotalIncome(date);
            Log.d("DateFragment", "Total income: " + totalIncome);

            if (totalIncomeTextView != null) {
                totalIncomeTextView.setText(String.format("%d", totalIncome));
            } else {
                Log.e("DateFragment", "totalIncomeTextView is null");
                totalIncomeTextView.setText("0");
            }
        } else {
            Log.e("DateFragment", "databaseHandler is null");
            totalIncomeTextView.setText("0");
        }
        updateTotalBalance();
    }

    private void loadTotalExpenses(String date) {
        Log.d("DateFragment", "Loading total Expenses for date: " + date);

        if (databaseHandler != null) {
            totalExpenses = databaseHandler.CalculateTotalExpenses(date);
            Log.d("DateFragment", "Total expenses: " + totalExpenses);

            if (totalExpensesTextView != null) {
                totalExpensesTextView.setText(String.format("%d", totalExpenses));
            } else {
                Log.e("DateFragment", "totalExpensesTextView is null");
                totalExpensesTextView.setText("0");
            }
        } else {
            Log.e("DateFragment", "databaseHandler is null");
            totalExpensesTextView.setText("0");
        }

        updateTotalBalance();
    }

    private void updateTotalBalance() {
        int totalBalance = totalIncome - totalExpenses;
        if (totalBalanceTextView != null) {
            totalBalanceTextView.setText(String.format("%d", totalBalance));
        } else {
            Log.e("DateFragment", "totalBalanceTextView is null");
        }
    }
    private void hideRC()
    {
        recyclerViewIncome.setVisibility(View.GONE);
        recyclerViewExpense.setVisibility(View.GONE);
    }

}