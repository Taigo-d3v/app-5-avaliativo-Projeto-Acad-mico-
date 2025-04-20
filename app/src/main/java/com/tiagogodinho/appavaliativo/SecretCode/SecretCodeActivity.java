package com.tiagogodinho.appavaliativo.SecretCode;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
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
    private TextView txtTempo; // Novo elemento para o cronômetro

    // Variáveis para o cronômetro
    private long startTime;
    private boolean timerRunning;
    private Handler timerHandler = new Handler();
    private Runnable timerRunnable;

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
        txtTempo = findViewById(R.id.txtTempo); // Inicializa o TextView do tempo

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

        gerarCodigoSecreto();

        /* SEUS COMENTÁRIOS ORIGINAIS PRESERVADOS */
        // Configuração do cronômetro
        timerRunnable = new Runnable() {
            @Override
            public void run() {
                if (timerRunning) {
                    long millis = System.currentTimeMillis() - startTime;
                    int seconds = (int) (millis / 1000);
                    int minutes = seconds / 60;
                    seconds = seconds % 60;

                    // Formatação MM:SS
                    txtTempo.setText(String.format("Tempo: %02d:%02d", minutes, seconds));
                    timerHandler.postDelayed(this, 500); // Atualiza a cada 0.5s
                }
            }
        };

        // Inicia o cronômetro quando o usuário começa a digitar
        inputCodigo.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!timerRunning && s.length() > 0) {
                    startTimer();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });

        bntBack.setOnClickListener(v -> {
            stopTimer(); // Para o cronômetro ao voltar
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

    /* MÉTODOS DO CRONÔMETRO */
    private void startTimer() {
        startTime = System.currentTimeMillis();
        timerRunning = true;
        timerHandler.postDelayed(timerRunnable, 0);
    }

    private void stopTimer() {
        timerRunning = false;
        timerHandler.removeCallbacks(timerRunnable);
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

        // SEU COMENTÁRIO ORIGINAL: marcar as corretas
        for (int i = 0; i < 4; i++) {
            if (tentativaChars[i] == codigoChars[i]) {
                cores[i] = 2; // verde
                usados[i] = true;
            }
        }

        // SEU COMENTÁRIO ORIGINAL: marcar as amarelas
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
            stopTimer(); // Para o cronômetro ao acertar
            Intent i = new Intent(SecretCodeActivity.this, VictorySecretCode.class);
            esconderTeclado(inputCodigo);

            // SEU COMENTÁRIO ORIGINAL: limpando a variavel
            codigoSecreto = "";
            gerarCodigoSecreto();

            // Passa o tempo gasto para a tela de vitória
            long elapsedTime = System.currentTimeMillis() - startTime;
            i.putExtra("tempo_jogado", elapsedTime);

            startActivity(i);
            // SEU COMENTÁRIO ORIGINAL: animaçao troca de tela
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

    @Override
    protected void onPause() {
        super.onPause();
        if (timerRunning) {
            stopTimer();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        // Opcional: Pode adicionar lógica para retomar o timer se necessário
    }
}