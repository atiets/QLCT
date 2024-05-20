package com.example.qlct.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.qlct.Models.KhoanChi;
import com.example.qlct.R;

import java.util.List;

public class ExpenseDailyAdapter extends RecyclerView.Adapter<ExpenseDailyAdapter.ExpenseViewHolder> {

    private List<KhoanChi> khoanChiList;

    public ExpenseDailyAdapter(List<KhoanChi> expenseList) {
        this.khoanChiList = expenseList;
    }

    @NonNull
    @Override
    public ExpenseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_item_thongketheoloai, parent, false);
        return new ExpenseViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ExpenseViewHolder holder, int position) {
        KhoanChi khoanChi = khoanChiList.get(position);
        holder.tvExpenseTitle.setText(khoanChi.getTenChi());
        holder.tvExpenseAmount.setText(String.valueOf(khoanChi.getSoTien()));
    }

    @Override
    public int getItemCount() {
        return khoanChiList.size();
    }

    public static class ExpenseViewHolder extends RecyclerView.ViewHolder {
        TextView tvExpenseTitle, tvExpenseAmount;

        public ExpenseViewHolder(@NonNull View itemView) {
            super(itemView);
            tvExpenseTitle = itemView.findViewById(R.id.tv_dmKhoan);
            tvExpenseAmount = itemView.findViewById(R.id.tv_sotien);
        }
    }
}
