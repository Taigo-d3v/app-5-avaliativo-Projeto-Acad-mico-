package com.tiagogodinho.appavaliativo.SecretCode;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.tiagogodinho.appavaliativo.R;

import java.util.List;

public class AdapterSecretCode extends RecyclerView.Adapter<AdapterSecretCode.TentativaViewHolder> {

    private final List<TentativaSecretCode> tentativas;

    public AdapterSecretCode(List<TentativaSecretCode> tentativas) {
        this.tentativas = tentativas;
    }

    @NonNull
    @Override
    public TentativaViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.secret_code_item_tentativa, parent, false);
        return new TentativaViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TentativaViewHolder holder, int position) {
        TentativaSecretCode tentativa = tentativas.get(position);
        TextView[] txts = {holder.txt1, holder.txt2, holder.txt3, holder.txt4};

        for (int i = 0; i < 4; i++) {
            txts[i].setText(String.valueOf(tentativa.digitos[i]));
            switch (tentativa.cores[i]) {
                case 2: txts[i].setBackgroundColor(Color.parseColor("#4CAF50")); break; // verde
                case 1: txts[i].setBackgroundColor(Color.parseColor("#FFC107")); break; // amarelo
                case 0: default: txts[i].setBackgroundColor(Color.parseColor("#F44336")); break; // vermelho
            }
        }
    }

    @Override
    public int getItemCount() {
        return tentativas.size();
    }

    static class TentativaViewHolder extends RecyclerView.ViewHolder {
        TextView txt1, txt2, txt3, txt4;
        TentativaViewHolder(View itemView) {
            super(itemView);
            txt1 = itemView.findViewById(R.id.txt1);
            txt2 = itemView.findViewById(R.id.txt2);
            txt3 = itemView.findViewById(R.id.txt3);
            txt4 = itemView.findViewById(R.id.txt4);
        }
    }
}