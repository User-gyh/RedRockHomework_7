package com.example.redrockhomework_7;

import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class RvAdapter extends RecyclerView.Adapter<RvAdapter.RvViewHolder> {
    private final List<RvData> list;
    public RvAdapter(List<RvData> list) {
        this.list=list;
    }
    static class RvViewHolder extends RecyclerView.ViewHolder {
        public TextView tvRv1;
        public TextView tvRv2;
        public TextView tvRv3;
        public RvViewHolder(@NonNull View itemView) {
            super(itemView);
            tvRv1=itemView.findViewById(R.id.tv_rv1);
            tvRv2=itemView.findViewById(R.id.tv_rv2);
            tvRv3=itemView.findViewById(R.id.tv_rv3);
        }
    }
    @NonNull
    @Override
    public RvAdapter.RvViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new RvViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.tv_rv, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull RvAdapter.RvViewHolder holder, int position) {
        RvData rvData=list.get(position);
        holder.tvRv1.setText(rvData.getName());
        holder.tvRv2.setText(rvData.getName1());
        holder.tvRv3.setText(rvData.getName2());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}
