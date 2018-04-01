package com.example.talit.smarket.Activities;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Checkable;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.talit.smarket.ActConsumers.CadastroPessoaFisica;
import com.example.talit.smarket.ActConsumers.CadastroPessoaJuridica;
import com.example.talit.smarket.ActConsumers.PaginaInicialConsumidor;
import com.example.talit.smarket.LogicalView.Usuario;
import com.example.talit.smarket.R;
import com.example.talit.smarket.Retrofit.SecurityToken;
import com.example.talit.smarket.Sqlite.DbConn;
import com.example.talit.smarket.Utils.Validacoes;
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.HttpMethod;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

import java.util.ArrayList;
import java.util.Arrays;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class AutenticaUsuario extends AppCompatActivity {

    private EditText email;
    private EditText senha;
    private Button btnEntrar;
    private Button cadastrar;
    private Button btnEsqueceuSenha;
    private TextView txtValidacoes;
    private boolean haEmail;
    private boolean haSenha;
    private LoginButton lb;
    private CallbackManager callbackManager;
    public static ProgressBar pbRecuperar;
    public static AlertDialog dialogo;
    public RelativeLayout relativeCasa;
    public RelativeLayout relativeEmpresa;
    public static final String TOKEN = "TOKEN";
    private String tokenUser;
    private Retrofit retrofit;
    HttpLoggingInterceptor logging;
    private DbConn dbconn;
    private ProgressDialog progress;
    private CheckBox checkVisualizarSenha;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_autentica_usuario);

        email = findViewById(R.id.ed_email_cliente);
        senha = findViewById(R.id.ed_senha_cliente);
        btnEntrar = findViewById(R.id.btn_entrar);
        cadastrar = findViewById(R.id.btn_cadastrar);
        btnEsqueceuSenha = findViewById(R.id.btn_exqueceu_senha);
        txtValidacoes = findViewById(R.id.txt_valida_autenticacao);
        lb = findViewById(R.id.login_button);
        lb = findViewById(R.id.login_button);
        checkVisualizarSenha = findViewById(R.id.mostrarSenha);

        dbconn = new DbConn(AutenticaUsuario.this);

        SharedPreferences prefs = getSharedPreferences(TOKEN, MODE_PRIVATE);
        tokenUser = prefs.getString("token", null);

        cadastrar.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                alertaDialogoCadastro();
            }
        });

        if (Validacoes.verifyConnection(AutenticaUsuario.this)) {
            logging = new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY);
            OkHttpClient.Builder httpClient = new OkHttpClient.Builder().addInterceptor(logging);
            retrofit = Validacoes.getRetrofitProprieties("https://smarketapi.azurewebsites.net/", httpClient);

        } else {
            Validacoes.alertDialogWarning(AutenticaUsuario.this, getString(R.string.txt_verifica_conexao_tentar),
                    getString(R.string.txt_verifica_conexao), R.drawable.ic_public_black_24dp);
        }

        haEmail = false;
        haSenha = false;
        senha.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                if (TextUtils.isEmpty(charSequence)) {
                    txtValidacoes.setText(R.string.txt_senha_necessaria);
                    Validacoes.requestFocus(senha);
                    checkVisualizarSenha.setVisibility(View.GONE);
                    txtValidacoes.setVisibility(View.VISIBLE);
                    haSenha = true;

                } else if (!Validacoes.validaSenha(senha.getText().toString())) {
                    txtValidacoes.setText(R.string.txt_senha_pequena);
                    Validacoes.requestFocus(senha);
                    checkVisualizarSenha.setVisibility(View.GONE);
                    txtValidacoes.setVisibility(View.VISIBLE);
                    haSenha = true;

                } else {
                    checkVisualizarSenha.setVisibility(View.VISIBLE);
                    txtValidacoes.setVisibility(View.INVISIBLE);
                    haSenha = false;
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        email.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                if (TextUtils.isEmpty(charSequence)) {
                    txtValidacoes.setText(R.string.txt_email_necessario);
                    Validacoes.requestFocus(email);
                    checkVisualizarSenha.setVisibility(View.GONE);
                    txtValidacoes.setVisibility(View.VISIBLE);
                    haEmail = true;

                } else if (!Validacoes.validaEmail(email.getText().toString())) {
                    txtValidacoes.setText(R.string.txt_email_dig_incorretamente);
                    Validacoes.requestFocus(email);
                    checkVisualizarSenha.setVisibility(View.GONE);
                    txtValidacoes.setVisibility(View.VISIBLE);
                    haEmail = true;
                } else {
                    checkVisualizarSenha.setVisibility(View.VISIBLE);
                    txtValidacoes.setVisibility(View.INVISIBLE);
                    haEmail = false;
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        btnEntrar.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (Validacoes.verifyConnection(AutenticaUsuario.this)) {
                    if (!haEmail && !haSenha) {
                        if (!TextUtils.isEmpty(email.getText().toString()) && !TextUtils.isEmpty(senha.getText().toString())) {

                            progress = ProgressDialog.show(AutenticaUsuario.this, null, getString(R.string.txt_aguarde), false, true);
                            askAuthorizedData(email.getText().toString().trim(), senha.getText().toString().trim(), "password");

                        } else {
                            Validacoes.alertDialogWarning(AutenticaUsuario.this, getString(R.string.txt_insira_dados),
                                    getString(R.string.txt_campos_vazios), R.drawable.ic_error_outline_black_24dp);
                        }
                    } else {

                        Validacoes.alertDialogWarning(AutenticaUsuario.this, getString(R.string.txt_dados_dig_corretos),
                                getString(R.string.txt_dados_ent_invalidos), R.drawable.ic_error_outline_black_24dp);

                    }

                } else {
                    Validacoes.alertDialogWarning(AutenticaUsuario.this, getString(R.string.txt_verifica_conexao_tentar),
                            getString(R.string.txt_verifica_conexao), R.drawable.ic_public_black_24dp);
                }

            }
        });

        checkVisualizarSenha.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(!b){
                    senha.setTransformationMethod(PasswordTransformationMethod.getInstance());
                }else{
                    senha.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                }
            }
        });
        /*if (checkVisualizarSenha.isChecked()) {
            senha.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
            Toast.makeText(AutenticaUsuario.this, "checkbox", Toast.LENGTH_SHORT).show();

        } else {
            senha.setInputType(129);
        }*/
        callbackManager = CallbackManager.Factory.create();
        lb.setReadPermissions(Arrays.asList("email", "public_profile", "user_friends", "user_birthday"));
        lb.setReadPermissions("user_friends,email");
        lb.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                //goMainScreen();
                //Toast.makeText(getApplicationContext(), "ver", Toast.LENGTH_LONG).show();
                graphRequest(loginResult);
                Log.i("error", loginResult.toString());
            }

            @Override
            public void onCancel() {

            }

            @Override
            public void onError(FacebookException error) {
                Log.i("erro ai", error.toString());
                if (!Validacoes.verifyConnection(AutenticaUsuario.this)) {
                    android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(AutenticaUsuario.this);
                    builder.setTitle(R.string.txt_verifica_conexao);
                    builder.setMessage(R.string.txt_verifica_conexao_tentar);
                    builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
                    builder.setCancelable(false);
                    builder.show();
                }
            }
        });
        btnEsqueceuSenha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Validacoes.verifyConnection(AutenticaUsuario.this)) {
                    alertaDialogoEsqueceuSenha();

                } else {
                    Validacoes.alertDialogWarning(AutenticaUsuario.this, getString(R.string.txt_verifica_conexao_tentar),
                            getString(R.string.txt_verifica_conexao), R.drawable.ic_public_black_24dp);
                }
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }

    public void graphRequest(LoginResult token) {

        ArrayList<String> array = new ArrayList<>();
        if (token != null && token.getAccessToken().getPermissions() != null) {
            for (String value : token.getAccessToken().getPermissions()) {
                array.add(value);
            }
        }
        Log.i("array", array.toString());

        new GraphRequest(
                AccessToken.getCurrentAccessToken(),
                "/me?fields=id,name,email",
                null,
                HttpMethod.GET,
                new GraphRequest.Callback() {
                    public void onCompleted(GraphResponse response) {
                        startActivity(new Intent(AutenticaUsuario.this, PaginaInicialConsumidor.class));
                        finish();
                    }
                }
        ).executeAsync();
    }

    public void alertaDialogoEsqueceuSenha() {
        LayoutInflater inflater = getLayoutInflater();
        final View alertLayout = inflater.inflate(R.layout.custom_alerta_dialog_resetar_senha, null);
        Button btnEnviar = alertLayout.findViewById(R.id.btn_enviar);
        Button btnCancelar = alertLayout.findViewById(R.id.btn_cancelar);
        final TextView txtValidaEmail = alertLayout.findViewById(R.id.txt_valida_autenticacao);
        final EditText edtEmail = alertLayout.findViewById(R.id.ed_email);
        pbRecuperar = alertLayout.findViewById(R.id.ps_recuperar);
        pbRecuperar.setVisibility(View.INVISIBLE);
        AlertDialog.Builder alerta = new AlertDialog.Builder(this);
        alerta.setView(alertLayout);
        alerta.setCancelable(false);
        dialogo = alerta.create();
        dialogo.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialogo.show();
        btnEnviar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String strEmail;
                strEmail = edtEmail.getText().toString();

                if (TextUtils.isEmpty(strEmail)) {
                    txtValidaEmail.setText(R.string.txt_email_necessario);
                    Validacoes.requestFocus(edtEmail);
                } else if (!Validacoes.validaEmail(strEmail)) {
                    txtValidaEmail.setText(R.string.txt_email_dig_incorretamente);
                    Validacoes.requestFocus(edtEmail);
                } else {
                    txtValidaEmail.setText(null);
                    // chamar aqui a autenticação de senha
                    //RecuperarSenha connRec = new RecuperarSenha();
                    //connRec.execute(edtEmail.getText().toString());

                    android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(AutenticaUsuario.this);
                    builder.setTitle(R.string.txt_email_enviado);
                    builder.setMessage(R.string.txt_nova_senha);
                    builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                            dialogo.dismiss();
                        }
                    });
                    builder.setCancelable(false);
                    builder.show();
                }
            }
        });
        btnCancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogo.dismiss();
            }
        });

    }

    public void alertaDialogoCadastro() {

        LayoutInflater inflater = getLayoutInflater();
        final View alertLayout = inflater.inflate(R.layout.custom_alert_dialog_perfil_comprador, null);
        Button btnCancelar = alertLayout.findViewById(R.id.btn_cancelar_acao);
        AlertDialog.Builder alerta = new AlertDialog.Builder(this);
        relativeCasa = alertLayout.findViewById(R.id.minhaCasa);
        relativeEmpresa = alertLayout.findViewById(R.id.minhaEmpresa);

        alerta.setView(alertLayout);
        alerta.setCancelable(false);
        dialogo = alerta.create();
        dialogo.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialogo.show();

        btnCancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogo.dismiss();
            }
        });

        relativeCasa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogo.dismiss();
                startActivity(new Intent(AutenticaUsuario.this, CadastroPessoaFisica.class));
                finish();
            }
        });

        relativeEmpresa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogo.dismiss();
                startActivity(new Intent(AutenticaUsuario.this, CadastroPessoaJuridica.class));
                finish();
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(AutenticaUsuario.this, WelcomeScreen.class));
        finish();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                break;
            default:
                break;
        }
        return true;
    }

    public void askAuthorizedData(String email, String senha, String grantType) {

        SecurityToken serviceAuthorized = retrofit.create(SecurityToken.class);
        Call<Usuario> callUser = serviceAuthorized.getAuthentication(email, senha, grantType);
        callUser.enqueue(new Callback<Usuario>() {
            @Override
            public void onResponse(Call<Usuario> call, Response<Usuario> response) {

                if (response.isSuccessful()) {
                    progress.dismiss();
                    dbconn.insertConsumidor("1", response.body().getAccess_token(), response.body().getTypeUser());
                    startActivity(new Intent(AutenticaUsuario.this, PaginaInicialConsumidor.class));
                    finish();

                } else {
                    progress.dismiss();
                    Validacoes.alertDialogWarning(AutenticaUsuario.this, getString(R.string.valida_usuario_existente),
                            getString(R.string.txt_aviso), R.drawable.ic_error_outline_black_24dp);
                }
            }

            @Override
            public void onFailure(Call<Usuario> call, Throwable t) {
                progress.dismiss();
                Validacoes.alertDialogWarning(AutenticaUsuario.this, getString(R.string.txt_erro_inesperado),
                        getString(R.string.txt_verifica_conexao), R.drawable.ic_public_black_24dp);

            }
        });
    }
}
