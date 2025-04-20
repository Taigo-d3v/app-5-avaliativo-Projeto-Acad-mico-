package com.tiagogodinho.appavaliativo.RandomWord;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.tiagogodinho.appavaliativo.R;

import com.tiagogodinho.appavaliativo.RandomWord.Models.Category;

import java.util.ArrayList;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.ViewHolder> {
    private ArrayList<Category> categories;
    private OnDeleteCategoryListener categoryListener;

    public interface OnDeleteCategoryListener {
        void onDelete(int position);
    }

    public CategoryAdapter(ArrayList<Category> categories, OnDeleteCategoryListener listener) {
        this.categories = categories;
        this.categoryListener = listener;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView categoryName;
        EditText newValueInput;
        Button addValueBtn;
        RecyclerView valueRecycler;
        ImageButton deleteCategoryBtn;

        public ViewHolder(View itemView) {
            super(itemView);
            categoryName = itemView.findViewById(R.id.category_name);
            newValueInput = itemView.findViewById(R.id.new_value_input);
            addValueBtn = itemView.findViewById(R.id.add_value_button);
            valueRecycler = itemView.findViewById(R.id.value_recycler);
            deleteCategoryBtn = itemView.findViewById(R.id.delete_category_button);
        }
    }

    @Override
    public CategoryAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.random_word_item_category, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(CategoryAdapter.ViewHolder holder, int position) {
        Category category = categories.get(position);
        holder.categoryName.setText(category.getName());

        ValueAdapter valueAdapter = new ValueAdapter(category.getValues(), valuePos -> {
            category.getValues().remove(valuePos);
            notifyItemChanged(position);
        });

        holder.valueRecycler.setLayoutManager(new LinearLayoutManager(holder.itemView.getContext()));
        holder.valueRecycler.setAdapter(valueAdapter);

        holder.addValueBtn.setOnClickListener(v -> {
            String val = holder.newValueInput.getText().toString();
            if (!val.isEmpty()) {
                category.addValue(val);
                holder.newValueInput.setText("");
                notifyItemChanged(position);
            }
        });

        holder.deleteCategoryBtn.setOnClickListener(v -> categoryListener.onDelete(position));
    }

    @Override
    public int getItemCount() {
        return categories.size();
    }
}
