package com.company.mathgame;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class Game extends AppCompatActivity {

    TextView score;
    TextView life;
    TextView time;
    //
    TextView question;
    EditText answer;
    //
    Button ok;
    Button next;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        score = findViewById(R.id.textViewScore);
        life = findViewById(R.id.textViewLife);
        time = findViewById(R.id.textViewTime);

        question = findViewById(R.id.textViewQuestion);
        answer = findViewById(R.id.editTextAnswer);
    }
}