package com.tiagogodinho.appavaliativo.SecretCode;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.tiagogodinho.appavaliativo.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class SecretCodeActivity extends AppCompatActivity {
    private EditText inputCodigo;
    private RecyclerView recyclerView;
    private AdapterSecretCode adapter;
    private List<TentativaSecretCode> listaTentativas;
    private String codigoSecreto;
    private TextView bntBack;
    private Button bntEnviar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.secret_code_activity);

        inputCodigo = findViewById(R.id.inputCodigo);
        recyclerView = findViewById(R.id.recyclerTentativas);
        listaTentativas = new ArrayList<>();
        adapter = new AdapterSecretCode(listaTentativas);
        bntBack = findViewById(R.id.bntBack);
        bntEnviar = findViewById(R.id.bntEnviar);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

        gerarCodigoSecreto();

        bntBack.setOnClickListener(v -> {
            finish();
            overridePendingTransition(
                    R.anim.slide_in_left,
                    R.anim.slide_out_right);
        });
        bntBack.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        v.animate().scaleX(0.95f).scaleY(0.95f).alpha(0.7f).setDuration(100).start();
                        break;
                    case MotionEvent.ACTION_UP:
                    case MotionEvent.ACTION_CANCEL:
                        v.animate().scaleX(1f).scaleY(1f).alpha(1f).setDuration(100).start();
                        break;
                }
                return false;
            }
        });

        bntEnviar.setOnClickListener(v -> verificarTentativa());
    }

    private void gerarCodigoSecreto() {
        Random rand = new Random();
        StringBuilder sb = new StringBuilder();
        while (sb.length() < 4) {
            sb.append(rand.nextInt(10));
        }
        codigoSecreto = sb.toString();
    }

    public void verificarTentativa() {
        String tentativa = inputCodigo.getText().toString();
        esconderTeclado(inputCodigo);
        if (tentativa.length() != 4) {
            Toast.makeText(this, "Digite exatamente 4 números!", Toast.LENGTH_SHORT).show();
            return;
        }

        char[] tentativaChars = tentativa.toCharArray();
        char[] codigoChars = codigoSecreto.toCharArray();
        int[] cores = new int[4]; // 0 = vermelho, 1 = amarelo, 2 = verde
        boolean[] usados = new boolean[4]; // para marcar dígitos do código já utilizados

        //marcar as corretas
        for (int i = 0; i < 4; i++) {
            if (tentativaChars[i] == codigoChars[i]) {
                cores[i] = 2; // verde
                usados[i] = true;
            }
        }

        //marcar as amarelas
        for (int i = 0; i < 4; i++) {
            if (cores[i] == 0) { // ainda não marcado
                for (int j = 0; j < 4; j++) {
                    if (!usados[j] && tentativaChars[i] == codigoChars[j]) {
                        cores[i] = 1; // amarelo
                        usados[j] = true;
                        break;
                    }
                }
            }
        }

        TentativaSecretCode novaTentativa = new TentativaSecretCode(tentativaChars, cores);
        listaTentativas.add(novaTentativa);
        adapter.notifyItemInserted(listaTentativas.size() - 1);
        recyclerView.scrollToPosition(listaTentativas.size() - 1);

        if (tentativa.equals(codigoSecreto)) {
            Intent i = new Intent(SecretCodeActivity.this, VictorySecretCode.class);
            esconderTeclado(inputCodigo);
            //limpando a variavel
            codigoSecreto = "";
            gerarCodigoSecreto();

            startActivity(i);
            //animaçao troca de tela
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        }

        inputCodigo.setText("");
    }
    private void esconderTeclado(View view) {
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm != null) {
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }
}
