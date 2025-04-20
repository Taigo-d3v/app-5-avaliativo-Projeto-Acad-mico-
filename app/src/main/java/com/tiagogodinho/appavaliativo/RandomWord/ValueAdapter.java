package com.tiagogodinho.appavaliativo.RandomWord;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.tiagogodinho.appavaliativo.R;

import com.tiagogodinho.appavaliativo.RandomWord.Models.ValueItem;

import java.util.ArrayList;

public class ValueAdapter extends RecyclerView.Adapter<ValueAdapter.ViewHolder> {
    private ArrayList<ValueItem> values;
    private OnDeleteClickListener listener;

    public interface OnDeleteClickListener {
        void onDelete(int position);
    }

    public ValueAdapter(ArrayList<ValueItem> values, OnDeleteClickListener listener) {
        this.values = values;
        this.listener = listener;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView valueText;
        ImageButton deleteButton;

        public ViewHolder(View itemView) {
            super(itemView);
            valueText = itemView.findViewById(R.id.value_text);
            deleteButton = itemView.findViewById(R.id.delete_value_button);
        }
    }

    @Override
    public ValueAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.random_word_item_value, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ValueAdapter.ViewHolder holder, int position) {
        holder.valueText.setText(values.get(position).getValue());
        holder.deleteButton.setOnClickListener(v -> listener.onDelete(position));
    }

    @Override
    public int getItemCount() {
        return values.size();
    }
}
