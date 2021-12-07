package com.company.mathgame;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Locale;
import java.util.Random;

public class Game extends AppCompatActivity {
    //
    TextView score;
    TextView life;
    TextView time;
    //
    TextView question;
    EditText answer;
    //
    Button ok;
    Button next;
    //
    Random random = new Random();
    int numberOne;
    int numberTwo;
    int randomExercise;
    //
    double userAnswer;
    double correctAnswer;
    int userScore = 0;
    int userLife = 3;

    CountDownTimer timer;
    private static final long START_TIMER_IN_MILIS = 30000;
    boolean isTimerRunning;
    long remainingTimeInMilis = START_TIMER_IN_MILIS;

    private final int TRESHHOLD  = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        score = findViewById(R.id.textViewScore);
        life = findViewById(R.id.textViewLife);
        time = findViewById(R.id.textViewTime);

        question = findViewById(R.id.textViewQuestion);
        answer = findViewById(R.id.editTextAnswer);

        ok = findViewById(R.id.buttonOk);
        next = findViewById(R.id.buttonNext);

        gameContinue();
        next.setEnabled(false);


        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    userAnswer = Double.parseDouble(answer.getText().toString());
                }catch (NumberFormatException ex) {
                    next.setEnabled(false);
                    Toast.makeText(getApplicationContext(),"Invalid Argument!",Toast.LENGTH_SHORT).show();
                    return;
                }
                next.setEnabled(true);
                ok.setEnabled(false);
                pauseTimer();
                if (userLife == 0) {
                    Toast.makeText(getApplicationContext(),"GAME OVER!",Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(Game.this,Result.class);
                    intent.putExtra("score",userScore);
                    startActivity(intent);
                    finish();
                }
                if ((int)(userAnswer * TRESHHOLD) == (int)(correctAnswer * TRESHHOLD)) {
                    userScore++;
                    score.setText("" + userScore);
                    question.setText("Your answer is correct!");
                } else {
                    userScore--;
                    userLife--;
                    score.setText("" + userScore);
                    life.setText("" + userLife);
                    question.setText("Sorry, correct answer is: " + ((int)(correctAnswer * TRESHHOLD)) / (double)TRESHHOLD);
                }
            }
        });

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                next.setEnabled(false);
                ok.setEnabled(true);
                answer.setText("");
                resetTimer();
                if (userLife == 0) {
                    Toast.makeText(getApplicationContext(),"GAME OVER!",Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(Game.this,Result.class);
                    intent.putExtra("score",userScore);
                    startActivity(intent);
                    finish();
                }
                else {
                    gameContinue();
                }
            }
        });
    }

    public void gameContinue() {
        numberOne = random.nextInt(11);
        numberTwo = random.nextInt(11);
        randomExercise = random.nextInt(4);
        if (randomExercise == 0) {
            correctAnswer = (numberOne + numberTwo);
            question.setText(numberOne + " + " + numberTwo);
        }
        else if (randomExercise == 1) {
            correctAnswer = (numberOne - numberTwo);
            question.setText(numberOne + " - " + numberTwo);
        }
        else if (randomExercise == 2) {
            correctAnswer = (numberOne * numberTwo);
            question.setText(numberOne + " x " + numberTwo);
        }
        else if (randomExercise == 3) {
            while(numberTwo == 0) {
                numberTwo = random.nextInt(11);
            }
            correctAnswer = ((double)numberOne / numberTwo);
            question.setText(numberOne + " / " + numberTwo);
        }
        startTimer();
    }

    public void startTimer() {
        timer = new CountDownTimer(remainingTimeInMilis, 1000) {
            @Override
            public void onTick(long milisUntilFinished) {
                remainingTimeInMilis = milisUntilFinished;
                updateText();
            }

            @Override
            public void onFinish() {
                isTimerRunning = false;
                pauseTimer();
                resetTimer();
                updateText();
                question.setText("Sorry, Time's up!");
                userLife--;
                life.setText("" + userLife);
                ok.setEnabled(false);
                next.setEnabled(true);
            }
        }.start();
        isTimerRunning = true;
    }

    public void updateText() {
        int second = (int) (remainingTimeInMilis / 1000) % 60;
        String timeLeft = String.format(Locale.getDefault(), "%02d", second);
        time.setText(timeLeft);
    }

    public void pauseTimer() {
        timer.cancel();
        isTimerRunning = false;
    }

    public void resetTimer() {
        remainingTimeInMilis = START_TIMER_IN_MILIS;
        updateText();
    }
}