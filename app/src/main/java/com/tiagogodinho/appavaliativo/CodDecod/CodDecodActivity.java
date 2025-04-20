package com.tiagogodinho.appavaliativo.CodDecod;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.util.TypedValue;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.tiagogodinho.appavaliativo.R;

public class CodDecodActivity extends AppCompatActivity {
    private MediaPlayer mediaPlayer;
    EditText input;
    ImageView box;
    SeekBar seekBar;
    String decodificado;
    Button bntCodificar, bntDecodificar, bntBack;
    TextView indice, resultado;
    boolean unlock;
    int valorIndice;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.cod_decod_activity);

        //Instanciando objetos do xml
        input = findViewById(R.id.Input);
        bntCodificar = findViewById(R.id.bntCodificar);
        bntDecodificar = findViewById(R.id.bntDecodificar);
        seekBar = findViewById(R.id.BarraDoIndice);
        indice = findViewById(R.id.Indice);
        resultado = findViewById(R.id.Resultado);
        box = findViewById(R.id.box);
        bntBack = findViewById(R.id.bntBack);
        mediaPlayer = MediaPlayer.create(this,R.raw.shake);

        //carregando animaçao do botao
        Animation anim = AnimationUtils.loadAnimation(this,R.anim.pulsion);
        bntCodificar.startAnimation(anim);

        bntBack.setOnClickListener(v -> {
            finish();
            overridePendingTransition(
                    R.anim.slide_in_left,
                    R.anim.slide_out_right);
        });

        //botao de codificar
        bntCodificar.setOnClickListener(v -> {
                esconderTeclado(bntCodificar);
                getInput();
                if( !(decodificado.isEmpty()) && (valorIndice > 0)){
                    bntDecodificar.startAnimation(anim);
                    resultado.setText(codificar(decodificado, valorIndice));
                    animationBox();
                    unlock = true;
                    mediaPlayer.start();
                }else{
                    Toast.makeText(this, "Insira algum valor na caixa e no indice!", Toast.LENGTH_SHORT).show();                }

        });
        //botao de decodificar
        bntDecodificar.setOnClickListener(v -> {
                if(unlock){
                    bntDecodificar.startAnimation(anim);
                    resultado.setText(decodificar(decodificado, 0));
                    animationBox();
                    mediaPlayer.start();
                }
        });

        //barra para selecionar o indice
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                indice.setText("Indice " + progress + "/" + seekBar.getMax());
                valorIndice = progress;
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });
    }
    private void getInput() {
        String texto = input.getText().toString();

        decodificado = texto;
        input.getText().clear();

    }
    public String codificar(String texto, int indice) {
        StringBuilder resultado = new StringBuilder();

        // Percorre cada caractere do texto fornecido
        for (char caractere : texto.toCharArray()) {

            // Verifica se o caractere é uma letra (ignora números, espaços)
            if (Character.isLetter(caractere)) {

                // Define a base do alfabeto dependendo se é maiúscula ou minúscula
                // Se for maiúscula, base será "A" = 65, senão será "a" = 95
                char base = Character.isUpperCase(caractere) ? 'A' : 'a';

                char codificado = (char) ((caractere - base + indice) % 26 + base);
                // Adiciona o caractere codificado ao resultado
                resultado.append(codificado);
            } else {
                // Se não for letra, apenas adiciona o caractere original ao resultado
                resultado.append(caractere);
            }
        }
        // Converte o StringBuilder de volta para String e retorna
        return resultado.toString();
    }
    public static String decodificar(String texto, int indice) {
        StringBuilder resultado = new StringBuilder();

        for (char caractere : texto.toCharArray()) {
            if (Character.isLetter(caractere)) {
                char base = Character.isUpperCase(caractere) ? 'A' : 'a';
                char decodificado = (char) ((caractere - base - indice + 26) % 26 + base);
                resultado.append(decodificado);
            } else {
                // Mantém espaços, pontuação, etc.
                resultado.append(caractere);
            }
        }

        return resultado.toString();
    }
    public void animationBox(){
        box.setImageResource(R.drawable.close_box);
        resultado.setVisibility(View.GONE);

        // ESCONDE O INPUT DE IMEDIATO
        input.setVisibility(View.INVISIBLE);

        float deslocamentoY = TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP, 50, getResources().getDisplayMetrics()
        );

        // Animação: descer + sacudir
        ObjectAnimator descer = ObjectAnimator.ofFloat(box, "translationY", 0f, deslocamentoY);
        ObjectAnimator sacudir = ObjectAnimator.ofFloat(box, "translationX",
                0f, -20f, 20f, -15f, 15f, -10f, 10f, 0f); // efeito de shake

        descer.setDuration(750); // metade do tempo
        sacudir.setDuration(750); // metade do tempo

        AnimatorSet animacaoIda = new AnimatorSet();
        animacaoIda.playTogether(descer, sacudir);

        // Voltar ao normal
        ObjectAnimator voltarY = ObjectAnimator.ofFloat(box, "translationY", deslocamentoY, 0f);
        voltarY.setDuration(750);

        AnimatorSet animacaoVolta = new AnimatorSet();
        animacaoVolta.play(voltarY);

        AnimatorSet sequenciaFinal = new AnimatorSet();
        sequenciaFinal.playSequentially(animacaoIda, animacaoVolta);

        // Quando terminar toda a animação
        sequenciaFinal.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                box.setImageResource(R.drawable.open_box);

                // Animação de input "saindo da caixa"
                input.setTranslationY(100f);
                input.setAlpha(0f);
                input.setVisibility(View.VISIBLE);

                resultado.setTranslationY(100f);
                resultado.setAlpha(0f);
                resultado.setVisibility(View.VISIBLE);

                input.animate()
                        .translationY(0f)
                        .alpha(1f)
                        .setDuration(600)
                        .start();

                resultado.animate()
                        .translationY(0f)
                        .alpha(1f)
                        .setDuration(600)
                        .start();
            }
        });

        sequenciaFinal.start();
    }
    private void esconderTeclado(View view) {
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm != null) {
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }
}

