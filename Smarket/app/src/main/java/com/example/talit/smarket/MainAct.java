package com.example.talit.smarket;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.MainThread;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;

import com.example.talit.smarket.Activities.AutenticaUsuario;
import com.example.talit.smarket.Activities.WelcomeScreen;
import com.example.talit.smarket.Async.AsyncGeneratedToken;
import com.example.talit.smarket.Utils.Validacoes;

public class MainAct extends AppCompatActivity implements AsyncGeneratedToken.Listener {

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

        if(Validacoes.verifyConnection(MainAct.this)) {

            AsyncGeneratedToken conn = new AsyncGeneratedToken(MainAct.this);
            conn.execute();

        }else{
            android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(MainAct.this);
            builder.setTitle(R.string.txt_verifica_conexao);
            builder.setMessage(R.string.txt_verifica_conexao_tentar);
            builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                    finish();
                }
            });
            builder.setCancelable(false);
            builder.show();
        }
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

    @Override
    public void onLoaded(String string, String token) {

        if (Validacoes.verifyConnection(MainAct.this)) {

            if (string.equalsIgnoreCase("true")) {
                SharedPreferences.Editor editor = getSharedPreferences("TOKEN", MODE_PRIVATE).edit();
                editor.putString("token", token);
                editor.commit();

                Thread timerThread = new Thread() {
                    public void run() {
                        try {
                            sleep(2000);
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

        }else{
            android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(MainAct.this);
            builder.setTitle(R.string.txt_verifica_conexao);
            builder.setMessage(R.string.txt_verifica_conexao_tentar);
            builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                    finish();
                }
            });
            builder.setCancelable(false);
            builder.show();
        }
    }
}
