package com.example.talit.smarket;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.talit.smarket.Activities.WelcomeScreen;

public class MainAct extends AppCompatActivity {

    public static final String NOME_PREFERENCE = "IDIOMA";
    private static String lingua = "pt";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_main);

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
