package com.example.qlct.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.qlct.Models.KhoanThu;
import com.example.qlct.R;

import java.util.List;

public class IncomeDailyAdapter extends RecyclerView.Adapter<IncomeDailyAdapter.IncomeViewHolder> {

    private List<KhoanThu> khoanThuList;

    public IncomeDailyAdapter(List<KhoanThu> incomeList) {
        this.khoanThuList = incomeList;
    }

    @NonNull
    @Override
    public IncomeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_item_thongketheoloai, parent, false);
        return new IncomeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull IncomeViewHolder holder, int position) {
        KhoanThu khoanThu = khoanThuList.get(position);
        holder.tvIncomeTitle.setText(khoanThu.getTenThu());
        holder.tvIncomeAmount.setText(String.valueOf(khoanThu.getSoTien()));
    }

    @Override
    public int getItemCount() {
        return khoanThuList.size();
    }

    public static class IncomeViewHolder extends RecyclerView.ViewHolder {
        TextView tvIncomeTitle, tvIncomeAmount;

        public IncomeViewHolder(@NonNull View itemView) {
            super(itemView);
            tvIncomeTitle = itemView.findViewById(R.id.tv_dmKhoan);
            tvIncomeAmount = itemView.findViewById(R.id.tv_sotien);
        }
    }
}
