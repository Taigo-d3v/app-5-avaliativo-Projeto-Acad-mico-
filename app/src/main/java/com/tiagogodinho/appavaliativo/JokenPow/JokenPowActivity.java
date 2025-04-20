package com.tiagogodinho.appavaliativo.JokenPow;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.animation.AnimatorSet;
import android.os.Bundle;
import android.os.Handler;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.tiagogodinho.appavaliativo.R;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class JokenPowActivity extends AppCompatActivity {

    TextView bntBack;
    private ImageView user, cpu;
    private ImageButton btnPedra, btnPapel, btnTesoura;
    private TextView scoreText;
    private int playerScore = 0;
    private int cpuScore = 0;
    private final String[] options = {"pedra", "papel", "tesoura"};
    private final Map<String, Integer> spriteMap = new HashMap<>();
    private String playerChoice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.joken_pow_activity);

        scoreText = findViewById(R.id.score);
        user = findViewById(R.id.user);
        cpu = findViewById(R.id.cpu);
        btnPedra = findViewById(R.id.btnPedra);
        btnPapel = findViewById(R.id.btnPapel);
        btnTesoura = findViewById(R.id.btnTesoura);
        bntBack = findViewById(R.id.bntBack);

        spriteMap.put("pedra", R.drawable.pedra);
        spriteMap.put("papel", R.drawable.papel);
        spriteMap.put("tesoura", R.drawable.tesoura);

        //botao para as 3 opcoes
        btnPedra.setOnClickListener(v -> startRound("pedra"));
        btnPapel.setOnClickListener(v -> startRound("papel"));
        btnTesoura.setOnClickListener(v -> startRound("tesoura"));

        //botao para voltar
        bntBack.setOnClickListener(v -> {
            finish();
            overridePendingTransition(
                    R.anim.slide_in_left,
                    R.anim.slide_out_right);
        });

        //animacao visual ao ser tocado
        btnPedra.setOnTouchListener(new View.OnTouchListener() {
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
        btnPapel.setOnTouchListener(new View.OnTouchListener() {
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
        btnTesoura.setOnTouchListener(new View.OnTouchListener() {
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
    }
    public void startRound(String choice) {
        // Armazena a escolha do jogador
        this.playerChoice = choice;

        // Define as imagens iniciais das mãos como "pedra" para ambos
        user.setImageResource(spriteMap.get("pedra"));
        cpu.setImageResource(spriteMap.get("pedra"));

        // Inicia a animação das mãos antes de mostrar o resultado
        animateHands(() -> {

            // Escolha aleatória da CPU entre "pedra", "papel" e "tesoura"
            String cpuChoice = options[new Random().nextInt(3)];

            // Atualiza a imagem da escolha do jogador e da CPU após a animação
            user.setImageResource(spriteMap.get(playerChoice));
            cpu.setImageResource(spriteMap.get(cpuChoice));

            // Verifica quem venceu a rodada
            String result = getWinner(playerChoice, cpuChoice);

            if (result.equals("Jogador")) {
                // Jogador venceu: incrementa a pontuação e mostra resultado animado
                playerScore++;
                updateScore();
                showAnimatedResult("Você venceu a rodada!");
            } else if (result.equals("CPU")) {
                // CPU venceu: incrementa a pontuação e mostra resultado animado
                cpuScore++;
                updateScore();
                showAnimatedResult("Você perdeu a rodada!");
            } else {
                // Empate: apenas mostra o resultado
                showAnimatedResult("Empate!");
            }

            // Verifica se alguém já venceu 2 rodadas para declarar o vencedor final
            if (playerScore == 2 || cpuScore == 2) {
                String finalWinner = playerScore == 2 ?
                        "Parabéns, você venceu!" :
                        "Mais sorte na próxima vez!";

                // Aguarda 1 segundo antes de mostrar o resultado final da partida
                new Handler().postDelayed(() -> showAnimatedResult(finalWinner), 1000);

                // Reinicia a pontuação para nova partida
                playerScore = 0;
                cpuScore = 0;
            }
        });
    }
    public void showAnimatedResult(String message) {
        TextView resultMessage = findViewById(R.id.resultado);

        // Define o texto com a mensagem recebida (ex: "Você venceu a rodada!")
        resultMessage.setText(message);

        // Inicialmente, deixa o TextView invisível (transparente)
        resultMessage.setAlpha(0f);

        // Garante que o TextView está visível no layout
        resultMessage.setVisibility(View.VISIBLE);

        // Centraliza o texto horizontalmente
        resultMessage.setGravity(Gravity.CENTER);

        // Inicia uma animação(ficar visível)
        resultMessage.animate()
                .alpha(1f) // muda a opacidade para totalmente visível
                .setDuration(250) // duração do fade-in (250ms)
                .withEndAction(() -> resultMessage.animate()
                        .alpha(0f) // depois do tempo de exibição
                        .setDuration(250) // duração
                        .setStartDelay(500) // espera 0.5s antes de sumir
                        .withEndAction(() -> resultMessage.setVisibility(View.GONE)) // esconde o TextView depois da animação
                        .start())
                .start(); //
    }
    public void animateHands(Runnable onEnd) {
        // Cria uma animação para o jogador, movendo verticalmente -50 pixels e voltando
        ObjectAnimator playerAnim = ObjectAnimator.ofFloat(user, "translationY", 0f, -50f, 0f);

        // Cria uma animação igual para a CPU
        ObjectAnimator cpuAnim = ObjectAnimator.ofFloat(cpu, "translationY", 0f, -50f, 0f);

        // Define que ambas animações vão se repetir 2 vezes (3 no total: original + 2 repetições)
        playerAnim.setRepeatCount(2);
        cpuAnim.setRepeatCount(2);

        // Define a duração de cada ciclo da animação (375ms)
        playerAnim.setDuration(375);
        cpuAnim.setDuration(375);

        // Agrupa as duas animações para rodarem ao mesmo tempo
        AnimatorSet set = new AnimatorSet();
        set.playTogether(playerAnim, cpuAnim);
        set.start(); // Inicia as animações

        // Quando as animações terminarem, executa o código que foi passado por parâmetro (onEnd)
        set.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                onEnd.run(); // Executa o Runnable após o término da animação
            }
        });
    }
    // Metodo que determina o vencedor com base nas escolhas do jogador e da CPU
    public String getWinner(String player, String cpu) {
        // Se as escolhas forem iguais, é empate
        if (player.equals(cpu)) return "Empate";

        // Verifica se o jogador ganhou com base nas regras do jogo
        if ((player.equals("pedra") && cpu.equals("tesoura")) ||     // Pedra ganha de Tesoura
                (player.equals("papel") && cpu.equals("pedra")) ||       // Papel ganha de Pedra
                (player.equals("tesoura") && cpu.equals("papel"))) {     // Tesoura ganha de Papel
            return "Jogador";
        } else {
            return "CPU";
        }
    }
    private void updateScore() {
        scoreText.setText("Você: " + playerScore + "  |  CPU: " + cpuScore);
    }
}