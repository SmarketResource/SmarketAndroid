package com.example.talit.smarket;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;

import com.example.talit.smarket.Activities.WelcomeScreen;

public class MainAct extends AppCompatActivity {

    public static final String NOME_PREFERENCE = "IDIOMA";
    private static String lingua = "pt";
    private LinearLayout linearImage;
    private LinearLayout linearTxt;
    private Animation cimaParaBaixo;
    private Animation baixoParaCima;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_main);
        linearImage = findViewById(R.id.linearLayout);
        linearTxt = findViewById(R.id.linearBemVinde);

        cimaParaBaixo = AnimationUtils.loadAnimation(this,R.anim.de_cima_para_baixo);
        linearImage.setAnimation(cimaParaBaixo);

        baixoParaCima = AnimationUtils.loadAnimation(this,R.anim.de_baixo_para_cima);
        linearTxt.setAnimation(baixoParaCima);

        Thread timerThread = new Thread() {
            public void run() {
                try {
                    sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {

                    Intent intent = new Intent(MainAct.this, WelcomeScreen.class);
                    startActivity(intent);

                }
            }
        };
        timerThread.start();
    }
    @Override
    protected void onResume() {
        super.onResume();
        SharedPreferences prefs = getSharedPreferences(NOME_PREFERENCE, MODE_PRIVATE);
        lingua = prefs.getString("lingua", null);

    }
    // descomentar quando implementar o LocaleLengage
    /*protected void attachBaseContext(Context base){

        super.attachBaseContext(LocaleLanguage.onAttach(base,lingua));
    }*/

    @Override
    protected void onPause(){
        super.onPause();
        finish();
    }
}
