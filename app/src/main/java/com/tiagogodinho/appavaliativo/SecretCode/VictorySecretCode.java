package com.tiagogodinho.appavaliativo.SecretCode;

import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.tiagogodinho.appavaliativo.R;

public class VictorySecretCode extends AppCompatActivity {

    TextView bntBack;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.secret_code_victory_activity);

        bntBack = findViewById(R.id.bntBack);

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
    }
}