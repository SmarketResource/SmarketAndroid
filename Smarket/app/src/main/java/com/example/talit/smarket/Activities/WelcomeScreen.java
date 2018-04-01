package com.example.talit.smarket.Activities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import me.drakeet.materialdialog.MaterialDialog;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

import com.example.talit.smarket.ActConsumers.PaginaInicialConsumidor;
import com.example.talit.smarket.LogicalView.Token;
import com.example.talit.smarket.R;
import com.example.talit.smarket.Retrofit.GeneratedToken;
import com.example.talit.smarket.Sqlite.DbConn;
import com.example.talit.smarket.Utils.Validacoes;

public class WelcomeScreen extends AppCompatActivity {

    private ViewPager viewPager;
    private ViewPagerAdapter viewPagerAdapter;
    private LinearLayout dotsLayout;
    private TextView[] dots;
    private int[] layouts;
    private ImageButton btnSkip, btnNext;
    private DbConn dbconn;
    public static Activity act;
    private static ImageView m2;
    private LinearLayout imaLayout;
    public static final String TAG = "LOG";
    public static final int REQUEST_PERMISSIONS_CODE = 128;
    private MaterialDialog mMaterialDialog;
    private Retrofit retrofit;
    HttpLoggingInterceptor logging;


    @Override
    protected void onResume() {
        super.onResume();
        dbconn = new DbConn(WelcomeScreen.this);

        Intent intent = new Intent(this, PaginaInicialConsumidor.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
        startActivity(intent);
        finish();
/*
        if (dbconn.selectConsumidor() != null) {

                if(dbconn.selectConsumidor().getTypeUser().equals("2") || dbconn.selectConsumidor().getTypeUser().equals("3")) {
                    Intent intent = new Intent(this, PaginaInicialConsumidor.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                    startActivity(intent);
                    finish();
                }

        }*/

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_welcome_screen);

        viewPager  = findViewById(R.id.view_pager);
        dotsLayout = findViewById(R.id.layoutDots);
        btnSkip    = findViewById(R.id.btn_skip);
        btnNext    = findViewById(R.id.btn_next);
        imaLayout  = findViewById(R.id.layoutImage);

        if(Validacoes.verifyConnection(WelcomeScreen.this)) {
            logging = new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY);
            OkHttpClient.Builder httpClient = new OkHttpClient.Builder().addInterceptor(logging);

            retrofit = Validacoes.getRetrofitProprieties("https://smarketapi.azurewebsites.net/API/Login/",httpClient);
            solicitarToken();

        }else{
            Validacoes.alertDialogWarning(WelcomeScreen.this,getString(R.string.txt_verifica_conexao_tentar),
                    getString(R.string.txt_verifica_conexao),R.drawable.ic_public_black_24dp);

        }

        layouts = new int[]{
                R.layout.fragment_introducao_um,
                R.layout.fragment_introducao_dois,
                R.layout.fragment_introducao_um};

        addBottomDots(0);
        viewPagerAdapter = new ViewPagerAdapter();
        viewPager.setAdapter(viewPagerAdapter);
        viewPager.addOnPageChangeListener(viewPagerPageChangeListener);

    }

    public  void btnSkipClick(View v)
    {
        launchHomeScreen();
    }

    public  void btnNextClick(View v)
    {

        int current = getItem(1);
        if (current < layouts.length) {
            viewPager.setCurrentItem(current);
        } else {
            launchHomeScreen();
        }

    }


    ViewPager.OnPageChangeListener viewPagerPageChangeListener = new ViewPager.OnPageChangeListener() {

        @Override
        public void onPageSelected(int position) {
            addBottomDots(position);

            if (position == layouts.length - 1) {
                btnSkip.setVisibility(View.GONE);
            } else {
                btnSkip.setVisibility(View.VISIBLE);
            }
        }

        @Override
        public void onPageScrolled(int arg0, float arg1, int arg2) {

        }

        @Override
        public void onPageScrollStateChanged(int arg0) {

        }
    };

    private void addBottomDots(int currentPage) {
        dots = new TextView[layouts.length];

        dotsLayout.removeAllViews();
        for (int i = 0; i < dots.length; i++) {
            dots[i] = new TextView(this);
            dots[i].setText(Html.fromHtml("&#8226;"));
            dots[i].setTextSize(35);
            dots[i].setTextColor(getResources().getColor(R.color.dot_inactive));
            dotsLayout.addView(dots[i]);

            btnSkip.setVisibility(View.GONE);
            callAccessLocation();
        }

        if (dots.length > 0) {
            dots[currentPage].setTextColor(getResources().getColor(R.color.dot_active));
        }
    }
    private int getItem(int i) {
        return viewPager.getCurrentItem() + i;
    }

    private void launchHomeScreen() {
        startActivity(new Intent(this, AutenticaUsuario.class));
        finish();
    }

    public class ViewPagerAdapter extends PagerAdapter {
        private LayoutInflater layoutInflater;


        public ViewPagerAdapter() {

        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            layoutInflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            View view = layoutInflater.inflate(layouts[position], container, false);
            container.addView(view);

            return view;
        }

        @Override
        public int getCount() {
            return layouts.length;
        }

        @Override
        public boolean isViewFromObject(View view, Object obj) {
            return view == obj;
        }


        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            View view = (View) object;
            container.removeView(view);
        }
    }
    private void callDialog( String message, final String[] permissions ){
        mMaterialDialog = new MaterialDialog(this)
                .setTitle("Permission")
                .setMessage( message )
                .setPositiveButton("Ok", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        ActivityCompat.requestPermissions(WelcomeScreen.this, permissions, REQUEST_PERMISSIONS_CODE);
                        mMaterialDialog.dismiss();
                    }
                })
                .setNegativeButton("Cancel", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mMaterialDialog.dismiss();
                    }
                });
        mMaterialDialog.show();
    }

    public void callAccessLocation() {
        Log.i(TAG, "callAccessLocation()");

        if( ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED){

            if(ActivityCompat.shouldShowRequestPermissionRationale(this, android.Manifest.permission.ACCESS_FINE_LOCATION)){
                callDialog("É preciso a permissão para apresentação dos eventos locais.", new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION});
            }
            else{
                ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_PERMISSIONS_CODE);
            }
        }

    }

    public void callAccessVoice() {
        Log.i(TAG, "callAccessLocation()");

        if( ContextCompat.checkSelfPermission(this, android.Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED){

            if(ActivityCompat.shouldShowRequestPermissionRationale(this, android.Manifest.permission.RECORD_AUDIO)){
                callDialog("É preciso a permissão para utilizar os atalhos de voz.", new String[]{android.Manifest.permission.RECORD_AUDIO});
            }
            else{
                ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.RECORD_AUDIO}, REQUEST_PERMISSIONS_CODE);
            }
        }

    }

    public void escolherIdiomas(View v) {

        LayoutInflater inflater = getLayoutInflater();
        final View alertLayout = inflater.inflate(R.layout.custom_alerta_dialog_idiomas, null);
        final RelativeLayout portugues = alertLayout.findViewById(R.id.portugues);
        final RelativeLayout espanhol  = alertLayout.findViewById(R.id.espanhol);
        final RelativeLayout frances   = alertLayout.findViewById(R.id.frances);
        final RelativeLayout ingles    = alertLayout.findViewById(R.id.ingles);
        Button cancelar                = alertLayout.findViewById(R.id.cancelar);

        AlertDialog.Builder alerta = new AlertDialog.Builder(WelcomeScreen.this);
        alerta.setView(alertLayout);
        alerta.setCancelable(false);
        final AlertDialog dialogo = alerta.create();
        dialogo.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialogo.show();

      /*  btnPortugues.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Context context = LocaleLanguage.setLocale(PaginalnicialConsumidor.this, "Language");
                Resources resources = context.getResources();
                SharedPreferences.Editor editor = getSharedPreferences("IDIOMA", MODE_PRIVATE).edit();
                editor.putString("lingua", "Language");
                editor.commit();
                backToMain();

                //dialogo.dismiss();
            }
        });
        btnFrances.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dialogo.dismiss();
            }
        });
        btnIngles.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Context context = LocaleLanguage.setLocale(PaginalnicialConsumidor.this, "en");
                Resources resources = context.getResources();
                SharedPreferences.Editor editor = getSharedPreferences("IDIOMA", MODE_PRIVATE).edit();
                editor.putString("lingua", "en");
                editor.commit();
                dialogo.dismiss();
                backToMain();

            }
        });
        btnEspanhol.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Context context = LocaleLanguage.setLocale(PaginalnicialConsumidor.this, "es");
                Resources resources = context.getResources();
                SharedPreferences.Editor editor = getSharedPreferences("IDIOMA", MODE_PRIVATE).edit();
                editor.putString("lingua", "es");
                editor.commit();
                dialogo.dismiss();
                backToMain();


            }
        });*/

        cancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogo.dismiss();
            }

        });
    }

    private void solicitarToken() {

        GeneratedToken service = retrofit.create(GeneratedToken.class);
        Call<Token> call = service.getToken();
        call.enqueue(new Callback<Token>() {
            @Override
            public void onResponse(Call<Token> call, Response<Token> response) {
                if (response.isSuccessful()) {
                    Token token = response.body();
                    SharedPreferences.Editor editor = getSharedPreferences("TOKEN", MODE_PRIVATE).edit();
                    editor.putString("token", token.getToken());
                    editor.commit();
                    Log.i("token",token.getToken());

                }
            }

            @Override
            public void onFailure(Call<Token> call, Throwable t) {
                Validacoes.alertDialogWarning(WelcomeScreen.this,getString(R.string.txt_erro_inesperado),
                        getString(R.string.txt_verifica_conexao),R.drawable.ic_public_black_24dp);

            }
        });

    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
