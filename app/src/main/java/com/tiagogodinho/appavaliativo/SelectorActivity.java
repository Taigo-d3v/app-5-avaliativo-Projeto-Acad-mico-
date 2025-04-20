package com.tiagogodinho.appavaliativo;

import android.content.Intent;
import android.os.Bundle;

import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.tiagogodinho.appavaliativo.CodDecod.CodDecodActivity;
import com.tiagogodinho.appavaliativo.JokenPow.JokenPowActivity;
import com.tiagogodinho.appavaliativo.RandomWord.RandomWordActivity;
import com.tiagogodinho.appavaliativo.SecretCode.SecretCodeActivity;

public class SelectorActivity extends AppCompatActivity {

    Button bntBack;
    TextView bntCodDecod, bntJokenPow, bntSecretCode, bntRandomWord;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.selector_activity);

        bntBack = findViewById(R.id.bnt_voltar);
        bntJokenPow = findViewById(R.id.BntJokenPow);
        bntSecretCode = findViewById(R.id.BntSecretCode);
        bntRandomWord = findViewById(R.id.BntRandomWord);
        bntCodDecod = findViewById(R.id.BntCodDecod);

        bntRandomWord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(SelectorActivity.this, RandomWordActivity.class);
                startActivity(i);

                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
            }
        });
        bntSecretCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(SelectorActivity.this, SecretCodeActivity.class);
                startActivity(i);

                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
            }
        });
        bntCodDecod.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(SelectorActivity.this, CodDecodActivity.class);
                startActivity(i);

                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
            }
        });
        bntJokenPow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(SelectorActivity.this, JokenPowActivity.class);
                startActivity(i);

                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
            }
        });
        bntBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                overridePendingTransition(
                        R.anim.slide_in_left,
                        R.anim.slide_out_right);

            }
        });

        //Reação visual do click
        bntSecretCode.setOnTouchListener(new View.OnTouchListener() {
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
        bntJokenPow.setOnTouchListener(new View.OnTouchListener() {
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
        bntRandomWord.setOnTouchListener(new View.OnTouchListener() {
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
        bntCodDecod.setOnTouchListener(new View.OnTouchListener() {
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
}