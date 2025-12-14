package com.example.taskmanager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.ViewHolder> {

    private List<History> historyList;

    public HistoryAdapter(List<History> historyList) {
        this.historyList = historyList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_history, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        History history = historyList.get(position);

        holder.txtAction.setText(history.getAction());
        holder.txtDetails.setText(history.getDetails());
        holder.txtDate.setText(history.getCreatedAt());
    }

    @Override
    public int getItemCount() {
        return historyList.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        TextView txtAction, txtDetails, txtDate;

        ViewHolder(View itemView) {
            super(itemView);
            txtAction = itemView.findViewById(R.id.txtAction);
            txtDetails = itemView.findViewById(R.id.txtDetails);
            txtDate = itemView.findViewById(R.id.txtDate);
        }
    }
}

