package com.prumobile.geoquizapp_20200305020;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;


public class StartGame extends AppCompatActivity {

    TextView tvTimer;
    TextView tvResult;
    ImageView ivShowImage;
    HashMap<String,Integer> map = new HashMap<>();
    ArrayList<String> geoList = new ArrayList<>();
    int index;
    Button btn1, btn2, btn3, btn4;
    TextView tvPoints;
    int points;
    CountDownTimer countDownTimer;
    long millisUntilFinished;



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState/*, @Nullable PersistableBundle persistentState*/) {
        super.onCreate(savedInstanceState/*, persistentState*/);
        setContentView(R.layout.start_game);
        tvTimer = findViewById(R.id.tvTimer);
        tvResult = findViewById(R.id.tvResult);
        ivShowImage = findViewById(R.id.ivShowImage);
        btn1 = findViewById(R.id.btn1);
        btn2 = findViewById(R.id.btn2);
        btn3 = findViewById(R.id.btn3);
        btn4 = findViewById(R.id.btn4);
        tvPoints = findViewById(R.id.tvPoints);
        index = 0;
        geoList.add("Arjantin");
        geoList.add("Bangladeş");
        geoList.add("Belarus");
        geoList.add("Belçika");
        geoList.add("Brezilya");
        geoList.add("Bulgaristan");
        geoList.add("Çekya");
        geoList.add("Danimarka");
        geoList.add("Finlandiya");
        geoList.add("Jamaika");
        geoList.add("Japonya");
        geoList.add("Kanada");
        geoList.add("Küba");
        geoList.add("Litvanya");
        geoList.add("Mısır");
        geoList.add("Azerbaycan");
        map.put(geoList.get(0), R.drawable.arjantin);
        map.put(geoList.get(1), R.drawable.banglades);
        map.put(geoList.get(2), R.drawable.belarus);
        map.put(geoList.get(3), R.drawable.belcika);
        map.put(geoList.get(4), R.drawable.brezilya);
        map.put(geoList.get(5), R.drawable.bulgaristan);
        map.put(geoList.get(6), R.drawable.cekya);
        map.put(geoList.get(7), R.drawable.danimarka);
        map.put(geoList.get(8), R.drawable.finlandiya);
        map.put(geoList.get(9), R.drawable.jamaica);
        map.put(geoList.get(10), R.drawable.japonya);
        map.put(geoList.get(11), R.drawable.kanada);
        map.put(geoList.get(12), R.drawable.kuba);
        map.put(geoList.get(13), R.drawable.litvanya);
        map.put(geoList.get(14), R.drawable.misir);
        map.put(geoList.get(15), R.drawable.azerbeycan);
        Collections.shuffle(geoList);
        millisUntilFinished = 10000;
        points = 0;
        startGame();
    }

    private void startGame() {
        millisUntilFinished = 10000;
        tvTimer.setText("" + (millisUntilFinished/1000) + "s");
        tvPoints.setText(points + " / " + geoList.size());
        generateQuestions(index);
        countDownTimer = new CountDownTimer(millisUntilFinished, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                tvTimer.setText("" +(millisUntilFinished / 1000) + "s");
            }

            @Override
            public void onFinish() {
                index++;
                if (index > geoList.size() - 1){
                    ivShowImage.setVisibility(View.GONE);
                    btn1.setVisibility(View.GONE);
                    btn2.setVisibility(View.GONE);
                    btn3.setVisibility(View.GONE);
                    btn4.setVisibility(View.GONE);
                    Intent intent = new Intent(StartGame.this,GameOver.class);
                    intent.putExtra("points", points);
                    startActivity(intent);
                    finish();
                } else {
                    startGame();
                }
            }
        }.start();
    }

    private void generateQuestions(int index) {
        ArrayList<String> geoListTemp = (ArrayList<String>) geoList.clone();
        String correctAnswer = geoList.get(index);
        geoListTemp.remove(correctAnswer);
        Collections.shuffle(geoListTemp);
        ArrayList<String> newList = new ArrayList<>();
        newList.add(geoListTemp.get(0));
        newList.add(geoListTemp.get(1));
        newList.add(geoListTemp.get(2));
        newList.add(correctAnswer);
        Collections.shuffle(newList);
        btn1.setText(newList.get(0));
        btn2.setText(newList.get(1));
        btn3.setText(newList.get(2));
        btn4.setText(newList.get(3));
        ivShowImage.setImageResource(map.get(geoList.get(index)));
    }

    public void nextQuestion(View view) {
        countDownTimer.cancel();
        index++;
        if (index > geoList.size() - 1){
            ivShowImage.setVisibility(View.GONE);
            btn1.setVisibility(View.GONE);
            btn2.setVisibility(View.GONE);
            btn3.setVisibility(View.GONE);
            btn4.setVisibility(View.GONE);
            Intent intent = new Intent(StartGame.this, GameOver.class);
            intent.putExtra("points", points);
            startActivity(intent);
            finish();
        } else {
            startGame();
        }
    }

    public void answerSelected(View view) {
        countDownTimer.cancel();
        String answer = ((Button) view).getText().toString().trim();
        String correctAnswer = geoList.get(index);
        if(answer.equals(correctAnswer)){
            points++;
            tvPoints.setText(points + " / " + geoList.size());
            tvResult.setText("Correct");
        } else {
            tvResult.setText("Wrong");
        }
    }
}

