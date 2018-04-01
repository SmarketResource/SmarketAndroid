package com.example.talit.smarket.ActConsumers;

import android.content.Intent;
import android.graphics.drawable.Animatable;
import android.graphics.drawable.AnimatedVectorDrawable;
import android.graphics.drawable.Drawable;
import android.support.design.widget.NavigationView;
import android.support.graphics.drawable.AnimatedVectorDrawableCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import com.example.talit.smarket.R;

import com.example.talit.smarket.Sqlite.DbConn;

public class PaginaInicialConsumidor extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener  {

    private DbConn dbconn;
    private NavigationView navigationView;
    private DrawerLayout dl;
    private ImageView imgBtnMenu;
    private AnimatedVectorDrawableCompat menuRotation;
    private Animatable animatable;
    private EditText edtSearch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_pagina_inicial_consumidor);

        dbconn = new DbConn(PaginaInicialConsumidor.this);

        Button btn = findViewById(R.id.button2);
        imgBtnMenu = findViewById(R.id.img_menu);
        dl = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.navigation_view);
        edtSearch = findViewById(R.id.edt_search);

        menuRotation = AnimatedVectorDrawableCompat.create(this,R.drawable.ic_menu_animatable);
        imgBtnMenu.setImageDrawable(menuRotation);
        animatable= (Animatable)imgBtnMenu.getDrawable();


        navigationView.setNavigationItemSelectedListener(this);
        imgBtnMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dl.openDrawer(Gravity.LEFT);
            }
        });
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               dbconn.deleteConsumidor();
            }
        });

        edtSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                animatable.start();
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

    }


    @SuppressWarnings("StatementWithEmptyBody")
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == 1) {


        } else if (id == 1) {
            /*dbconn = new DbConn(PaginalnicialConsumidor.this);

            if (dbconn.selectConsumidor().getTpAcesso() == 2) {
                LoginManager.getInstance().logOut();
                dbconn.deleteConsumidor();
                dbconn.deleteHistorico();
                startActivity(new Intent(getApplicationContext(), WelcomeScreen.class));
                finish();

            } else {
                dbconn.deleteConsumidor();
                dbconn.deleteHistorico();
                if (dbconn.totalItensCarrinho() > 0) {
                    dbconn.deleteSacola();
                }

                startActivity(new Intent(getApplicationContext(), WelcomeScreen.class));
                finish();
            }*/

        } else if (id == 1) {



        }else if(id == 1){


        }else if(id == 1){


        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);

        return true;
    }

}
