package com.example.talit.smarket.Utils;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.talit.smarket.R;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by talit on 04/01/2018.
 */

public class Validacoes {


    public static  String[] verifica;

    public static final boolean validaEmail(String email){

        if(email.contains("@")){

            verifica = email.split("@");
            return (verifica.length > 1);
        }
        return false;
    }
    public static final boolean validaSenha(String senha){

        return senha.length() > 4;
    }
    public static final void requestFocus(View focusView) { focusView.requestFocus(); }

    public static final boolean validaCPF(String cpf){
        return cpf.matches("[0-9]{3}\\.?[0-9]{3}\\.?[0-9]{3}\\-?[0-9]{2}");
    }
    public static final boolean validaTelefone(String tel){
        return tel.matches("[^0-9]*");
    }

    public static final boolean isNumeric(String str)
    {
        return str.matches("^\\d{2}.\\d{3}.\\d{3}/\\d{4}-\\d{2}$");
    }
    public static boolean verifyConnection(Context context)
    {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        if (netInfo != null && netInfo.isConnected()) {
            return true;

        } else {
            return false;
        }
    }
    public static Retrofit getRetrofitProprieties(String url, OkHttpClient.Builder httpClient){
        
        return new Retrofit.Builder()
                .baseUrl(url)
                .client(httpClient.build())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }
    public static void alertDialogWarning(Activity activity, String message, String title, int imageWarn){

        LayoutInflater inflater = activity.getLayoutInflater();
        final View alertLayout = inflater.inflate(R.layout.custom_alert_dialog_warnings, null);

        TextView txtTitle = alertLayout.findViewById(R.id.txt_title);
        TextView txtBody = alertLayout.findViewById(R.id.txt_body);
        ImageView imageWarning = alertLayout.findViewById(R.id.image_warning);
        Button cancelar = alertLayout.findViewById(R.id.cancelar);

        txtTitle.setText(title);
        txtBody.setText(message);
        imageWarning.setImageResource(imageWarn);

        AlertDialog.Builder alerta = new AlertDialog.Builder(activity);
        alerta.setView(alertLayout);
        alerta.setCancelable(false);
        final AlertDialog dialogo = alerta.create();
        dialogo.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialogo.show();
        cancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogo.dismiss();
            }

        });
    }


}
