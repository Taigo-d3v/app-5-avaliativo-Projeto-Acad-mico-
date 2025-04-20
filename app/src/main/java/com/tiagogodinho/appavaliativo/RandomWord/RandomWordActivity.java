package com.tiagogodinho.appavaliativo.RandomWord;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.tiagogodinho.appavaliativo.R;
import com.tiagogodinho.appavaliativo.RandomWord.Models.Category;

import java.util.ArrayList;
import java.util.Random;

public class RandomWordActivity extends AppCompatActivity {
    private ArrayList<Category> categoryList = new ArrayList<>();
    private MediaPlayer mediaPlayer;
    private CategoryAdapter categoryAdapter;
    private RecyclerView recyclerView;
    private EditText newCategoryInput;
    private Button addCategoryBtn, sortearBtn;
    private TextView resultadoSorteio, bntBack;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.random_word_activity);

        recyclerView = findViewById(R.id.category_recycler);
        newCategoryInput = findViewById(R.id.new_category_input);
        addCategoryBtn = findViewById(R.id.add_category_button);
        sortearBtn = findViewById(R.id.sort_button);
        resultadoSorteio = findViewById(R.id.result_text);
        bntBack = findViewById(R.id.bntBack);

        mediaPlayer = MediaPlayer.create(this,R.raw.som_sorteio);
        categoryAdapter = new CategoryAdapter(categoryList, pos -> {
            categoryList.remove(pos);
            categoryAdapter.notifyDataSetChanged();
        });

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

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(categoryAdapter);

        addCategoryBtn.setOnClickListener(v -> {
            // Obtém o texto inserido pelo usuário no campo "newCategoryInput"
            String cat = newCategoryInput.getText().toString();

            // Verifica se o texto não está vazio
            if (!cat.isEmpty()) {

                // Cria um novo objeto Category com o nome da nova categoria e adiciona à lista de categorias
                categoryList.add(new Category(cat));

                // Limpa o input de texto para o próximo uso
                newCategoryInput.setText("");

                // Notifica o adapter que a lista de categorias foi alterada, para que a interface seja atualizada
                categoryAdapter.notifyDataSetChanged();
            }
        });

        sortearBtn.setOnClickListener(v -> {
            // Verifica se a lista de categorias (categoryList) não está vazia
            if (!categoryList.isEmpty()) {

                Random random = new Random();

                // Escolhe uma categoria aleatória da lista de categorias
                Category randomCategory = categoryList.get(random.nextInt(categoryList.size()));

                // Verifica se a categoria escolhida não está vazia (se tem valores)
                if (!randomCategory.getValues().isEmpty()) {

                    // Sorteia um valor aleatório dentro da categoria escolhida
                    String sorteado = randomCategory.getValues()
                            .get(random.nextInt(randomCategory.getValues().size()))
                            .getValue();

                    // Exibe o resultado do sorteio na interface
                    resultadoSorteio.setText("Sorteado: " + sorteado);

                    // Toca um som ao sortear
                    mediaPlayer.start();
                } else {
                    // Caso a categoria esteja vazia, exibe uma mensagem informando
                    resultadoSorteio.setText("Categoria \"" + randomCategory.getName() + "\" está vazia.");
                }
            } else {
                // Caso não haja categorias criadas, exibe uma mensagem informando
                resultadoSorteio.setText("Nenhuma categoria criada.");
            }
        });

    }

}