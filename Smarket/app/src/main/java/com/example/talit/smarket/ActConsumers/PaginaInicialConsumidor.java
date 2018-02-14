package com.example.talit.smarket.ActConsumers;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.talit.smarket.R;
import com.example.talit.smarket.Sqlite.DbConn;

public class PaginaInicialConsumidor extends AppCompatActivity {

    private DbConn dbconn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_pagina_inicial_consumidor);

        dbconn = new DbConn(PaginaInicialConsumidor.this);

        Button btn = findViewById(R.id.button2);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               dbconn.deleteConsumidor();
            }
        });
    }

}
