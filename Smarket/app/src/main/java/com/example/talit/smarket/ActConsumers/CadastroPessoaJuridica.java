package com.example.talit.smarket.ActConsumers;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.talit.smarket.Activities.AutenticaUsuario;
import com.example.talit.smarket.LogicalView.CommerceBusiness;
import com.example.talit.smarket.R;
import com.example.talit.smarket.Retrofit.Commerce;
import com.example.talit.smarket.Utils.Validacoes;
import com.github.rtoshiro.util.format.SimpleMaskFormatter;
import com.github.rtoshiro.util.format.text.MaskTextWatcher;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class CadastroPessoaJuridica extends AppCompatActivity {

    private MaskTextWatcher mtw;
    private SimpleMaskFormatter smf;
    private MaskTextWatcher mtw2;
    private SimpleMaskFormatter smf2;
    private MaskTextWatcher mtw3;
    private SimpleMaskFormatter smf3;
    private LinearLayout dadosEmpresa;
    private LinearLayout dadosFuncionario;
    private LinearLayout dadosLogin;
    private RelativeLayout relativeLevelUm;
    private RelativeLayout relativeLevelDois;
    private RelativeLayout relativeLeveltres;
    private Animation direitaParaEsquerda;
    private Animation deBaixoParaCima;
    private Animation direitaParaEsquerdaLogin;
    private EditText edtNomeFantasia;
    private EditText edtRazaoSocial;
    private EditText edtTelefone;
    private EditText edtCnpj;
    private EditText edtNomeFuncionario;
    private EditText edtSobrenome;
    private EditText edtCpf;
    private EditText edtEmailFunc;
    private EditText edtSenha;
    private EditText edtConfirmSenha;
    private boolean haNomeFantasia;
    private boolean haRazaoSocial;
    private boolean haTelefone;
    private boolean haCnpj;
    private boolean haNomeFunc;
    private boolean haSobrenomeFunc;
    private boolean haCpfFunc;
    private boolean haEmail;
    private boolean haSenha;
    private boolean haConfirmSenha;
    private TextView txtLevelUm;
    private TextView txtLevelDois;
    private TextView txtValidaNomeFantasia;
    private TextView txtRazaoSocial;
    private TextView txtValidaTelefone;
    private TextView txtValidadCnoj;
    private TextView txtValidadeNomeFun;
    private TextView txtSobrenomeFunc;
    private TextView txtValidaCpf;
    private TextView txtValidaEmail;
    private TextView txtValidaSenha;
    private TextView txtValidaConfirmSenha;
    private Button imgLevelDois;
    private Button btnLevelTres;
    private Button btnCadastrar;
    private ImageView imageUm;
    private ImageView imageDois;
    private Retrofit retrofit;
    HttpLoggingInterceptor logging;
    public static final String TOKEN = "TOKEN";
    private String tokenUser;
    private static Context c;
    private ProgressDialog progress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        c = this;
        setContentView(R.layout.act_cadastro_pessoa_juridica);
        dadosEmpresa          = findViewById(R.id.ln_dados_empresa);
        dadosFuncionario      = findViewById(R.id.ln_dados_funcionarios);
        dadosLogin            = findViewById(R.id.ln_dados_login);
        relativeLevelUm       = findViewById(R.id.nivel_um);
        relativeLevelDois     = findViewById(R.id.nivel_dois);
        relativeLeveltres     = findViewById(R.id.nivel_tres);
        imgLevelDois          = findViewById(R.id.bt_level_dois);
        edtNomeFantasia       = findViewById(R.id.edt_nome_fantasia);
        edtRazaoSocial        = findViewById(R.id.ed_razao_social);
        edtTelefone           = findViewById(R.id.ed_telefones);
        edtCnpj               = findViewById(R.id.ed_cnpj);
        edtNomeFuncionario    = findViewById(R.id.edt_nome_funcionario);
        edtSobrenome          = findViewById(R.id.edt_sobrenome_funcionario);
        edtCpf                = findViewById(R.id.ed_cpf_funcionario);
        edtEmailFunc          = findViewById(R.id.edt_user_funcionario);
        edtSenha              = findViewById(R.id.edt_senha_funcionario);
        edtConfirmSenha       = findViewById(R.id.edt_senha_confir);
        txtLevelUm            = findViewById(R.id.level_um_down);
        txtLevelDois          = findViewById(R.id.level_dois_down);
        txtValidaNomeFantasia = findViewById(R.id.txt_nome_fantasia);
        txtRazaoSocial        = findViewById(R.id.txt_razao_social);
        txtValidaTelefone     = findViewById(R.id.txt_telefone);
        txtValidadCnoj        = findViewById(R.id.txt_cnpj);
        txtValidadeNomeFun    = findViewById(R.id.txt_nome_func);
        txtSobrenomeFunc      = findViewById(R.id.txt_sobrenome_func);
        txtValidaCpf          = findViewById(R.id.txt_cpf_func);
        txtValidaEmail        = findViewById(R.id.txt_email);
        txtValidaSenha        = findViewById(R.id.txt_senha);
        txtValidaConfirmSenha = findViewById(R.id.txt_senha_confirm);
        imageUm               = findViewById(R.id.concluido_um);
        imageDois             = findViewById(R.id.concluido_dois);
        btnLevelTres          = findViewById(R.id.bt_level_tres);
        btnCadastrar          = findViewById(R.id.bt_cadastrar);

        SharedPreferences prefs = getSharedPreferences(TOKEN, MODE_PRIVATE);
        tokenUser = prefs.getString("token", null);

        direitaParaEsquerda      = AnimationUtils.loadAnimation(this,R.anim.da_direita_para_esquerda);
        deBaixoParaCima          = AnimationUtils.loadAnimation(this,R.anim.para_cima);
        direitaParaEsquerdaLogin = AnimationUtils.loadAnimation(this,R.anim.da_direita_para_esquerda);



        haNomeFantasia  = false;
        haRazaoSocial   = false;
        haTelefone      = false;
        haCnpj          = false;
        haNomeFunc      = false;
        haSobrenomeFunc = false;
        haCpfFunc       = false;
        haEmail         = false;
        haSenha         = false;
        haConfirmSenha  = false;

        smf = new SimpleMaskFormatter("(NN)NNNNN-NNNN");
        mtw = new MaskTextWatcher(edtTelefone, smf);
        edtTelefone.addTextChangedListener(mtw);

        smf2 = new SimpleMaskFormatter("NN.NNN.NNN/NNNN-NN");
        mtw2 = new MaskTextWatcher(edtCnpj, smf2);
        edtCnpj.addTextChangedListener(mtw2);

        smf3 = new SimpleMaskFormatter("NNN.NNN.NNN-NN");
        mtw3 = new MaskTextWatcher(edtCpf, smf3);
        edtCpf.addTextChangedListener(mtw3);

        if (Validacoes.verifyConnection(CadastroPessoaJuridica.this)) {
            logging = new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY);
            OkHttpClient.Builder httpClient = new OkHttpClient.Builder().addInterceptor(logging);
            retrofit = Validacoes.getRetrofitProprieties("https://smarketapi.azurewebsites.net/api/",httpClient);

        } else {
            Validacoes.alertDialogWarning(CadastroPessoaJuridica.this,getString(R.string.txt_verifica_conexao_tentar),
                    getString(R.string.txt_verifica_conexao),R.drawable.ic_public_black_24dp);
        }

        edtNomeFantasia.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                if (TextUtils.isEmpty(charSequence)) {
                    txtValidaNomeFantasia.setText(R.string.txt_campo_obrigatorio);
                    Validacoes.requestFocus(edtNomeFantasia);
                    haNomeFantasia = true;

                } else if (charSequence.length() > 150) {
                    txtValidaNomeFantasia.setText(R.string.txt_nome_grande);
                    Validacoes.requestFocus(edtNomeFantasia);
                    haNomeFantasia = true;

                } else {
                    txtValidaNomeFantasia.setText(null);
                    haNomeFantasia = false;
                }
                validaStepUm();
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        edtRazaoSocial.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (TextUtils.isEmpty(charSequence)) {
                    txtRazaoSocial.setText(R.string.txt_campo_obrigatorio);
                    Validacoes.requestFocus(edtRazaoSocial);
                    haRazaoSocial = true;

                } else if (charSequence.length() > 150) {
                    txtRazaoSocial.setText(R.string.txt_nome_grande);
                    Validacoes.requestFocus(edtRazaoSocial);
                    haRazaoSocial = true;

                } else {
                    txtRazaoSocial.setText(null);
                    haRazaoSocial = false;
                }
                validaStepUm();
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        edtTelefone.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (edtTelefone.getText().toString().length() > 1 && edtTelefone.getText().toString().length() < 13) {
                    txtValidaTelefone.setText(R.string.txt_telefone_invalido);
                    Validacoes.requestFocus(edtTelefone);
                    haTelefone = true;

                }else if (TextUtils.isEmpty(charSequence)) {
                    txtValidaTelefone.setText(R.string.txt_campo_obrigatorio);
                    Validacoes.requestFocus(edtTelefone);
                    haTelefone = true;

                }  else {
                    haTelefone = false;
                    txtValidaTelefone.setText(null);
                }
                validaStepUm();
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        edtCnpj.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                if (TextUtils.isEmpty(charSequence)) {
                    txtValidadCnoj.setText(R.string.txt_campo_obrigatorio);
                    Validacoes.requestFocus(edtCnpj);
                    haCnpj = true;

                } else if (edtCnpj.getText().toString().length() != 18) {
                    txtValidadCnoj.setText(R.string.txt_valida_cnpj);
                    Validacoes.requestFocus(edtCnpj);
                    haCnpj = true;

                } else {
                    haCnpj = false;
                    txtValidadCnoj.setText(null);
                }
                validaStepUm();
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        edtNomeFuncionario.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (TextUtils.isEmpty(charSequence)) {
                    txtValidadeNomeFun.setText(R.string.txt_campo_obrigatorio);
                    Validacoes.requestFocus(edtNomeFuncionario);
                    haNomeFunc = true;

                } else if (charSequence.length() > 150) {
                    txtValidadeNomeFun.setText(R.string.txt_nome_grande);
                    Validacoes.requestFocus(edtNomeFuncionario);
                    haNomeFunc = true;

                } else {
                    txtValidadeNomeFun.setText(null);
                    haNomeFunc = false;
                }
                validaStepDois();
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        edtSobrenome.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (TextUtils.isEmpty(charSequence)) {
                    txtSobrenomeFunc.setText(R.string.txt_campo_obrigatorio);
                    Validacoes.requestFocus(edtSobrenome);
                    haSobrenomeFunc = true;

                } else if (charSequence.length() > 150) {
                    txtSobrenomeFunc.setText(R.string.txt_nome_grande);
                    Validacoes.requestFocus(edtSobrenome);
                    haSobrenomeFunc = true;

                } else {
                    txtSobrenomeFunc.setText(null);
                    haSobrenomeFunc = false;
                }
                validaStepDois();
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        edtCpf.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                if (TextUtils.isEmpty(charSequence)) {
                    txtValidaCpf.setText(R.string.txt_campo_obrigatorio);
                    Validacoes.requestFocus(edtCpf);
                    haCpfFunc = true;

                } else if (edtCpf.getText().toString().length() != 14) {
                    txtValidaCpf.setText(R.string.txt_valida_cpf);
                    Validacoes.requestFocus(edtCpf);
                    haCpfFunc = true;

                } else {
                    haCpfFunc = false;
                    txtValidaCpf.setText(null);
                }
                validaStepDois();
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        edtEmailFunc.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                if (TextUtils.isEmpty(edtEmailFunc.getText().toString())) {
                    txtValidaEmail.setText(R.string.txt_campo_obrigatorio);
                    Validacoes.requestFocus(edtEmailFunc);
                    haEmail = true;

                } else if (!Validacoes.validaEmail(edtEmailFunc.getText().toString())) {
                    txtValidaEmail.setText(R.string.txt_email_dig_incorretamente);
                    Validacoes.requestFocus(edtEmailFunc);
                    haEmail = true;

                } else {
                    haEmail = false;
                    txtValidaEmail.setText(null);
                }
                validaCadastro();
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        edtSenha.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                if (TextUtils.isEmpty(edtSenha.getText().toString())) {
                    txtValidaSenha.setText(R.string.txt_campo_obrigatorio);
                    Validacoes.requestFocus(edtSenha);
                    haSenha = true;

                } else if (!Validacoes.validaSenha(edtSenha.getText().toString())) {
                    txtValidaSenha.setText(R.string.txt_senha_pequena);
                    Validacoes.requestFocus(edtSenha);
                    haSenha = true;

                } else {
                    haSenha = false;
                    txtValidaSenha.setText(null);
                }
                validaCadastro();
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        edtConfirmSenha.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                if (TextUtils.isEmpty(edtConfirmSenha.getText().toString())) {
                    txtValidaConfirmSenha.setText(R.string.txt_campo_obrigatorio);
                    Validacoes.requestFocus(edtConfirmSenha);
                    haConfirmSenha = true;

                } else if (edtConfirmSenha.length() <= 4) {
                    txtValidaConfirmSenha.setText(R.string.txt_senha_pequena);
                    Validacoes.requestFocus(edtConfirmSenha);
                    haConfirmSenha = true;

                } else if (!edtConfirmSenha.getText().toString().equals(edtSenha.getText().toString())) {
                    txtValidaConfirmSenha.setText(R.string.txt_senha_identica);
                    Validacoes.requestFocus(edtConfirmSenha);
                    haConfirmSenha = true;

                } else {
                    haConfirmSenha = false;
                    txtValidaConfirmSenha.setText(null);

                }
                validaCadastro();
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        imgLevelDois.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dadosEmpresa.setVisibility(View.GONE);
                relativeLevelDois.setVisibility(View.GONE);
                relativeLevelUm.setVisibility(View.VISIBLE);
                dadosFuncionario.setVisibility(View.VISIBLE);
                relativeLevelUm.setAnimation(deBaixoParaCima);
                txtLevelUm.setText(null);
                imageUm.setVisibility(View.VISIBLE);
                dadosFuncionario.setAnimation(direitaParaEsquerda);
            }
        });
        btnLevelTres.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dadosFuncionario.setVisibility(View.GONE);
                relativeLeveltres.setVisibility(View.GONE);
                relativeLevelDois.setVisibility(View.VISIBLE);
                dadosLogin.setVisibility(View.VISIBLE);
                relativeLevelDois.setAnimation(deBaixoParaCima);
                txtLevelDois.setText(null);
                imageDois.setVisibility(View.VISIBLE);
                dadosLogin.setAnimation(direitaParaEsquerdaLogin);
            }
        });
        btnCadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                verificaEntradas();
            }
        });
    }
    public void validaCadastro(){
        if(!TextUtils.isEmpty(edtEmailFunc.getText().toString())&& !TextUtils.isEmpty(edtSenha.getText().toString())&&
                !TextUtils.isEmpty(edtConfirmSenha.getText().toString())){
            if(!haEmail && !haSenha && !haConfirmSenha){
                Validacoes.requestFocus(btnCadastrar);
                btnCadastrar.setBackground(getResources().getDrawable(R.drawable.bordas_grid_buttons));
                btnCadastrar.setEnabled(true);
            }else{
                btnCadastrar.setBackground(getResources().getDrawable(R.drawable.inactive_button));
                btnCadastrar.setEnabled(false);
            }

        }else{
            btnCadastrar.setBackground(getResources().getDrawable(R.drawable.inactive_button));
            btnCadastrar.setEnabled(false);
        }
    }
    public void validaStepDois(){
        if(!TextUtils.isEmpty(edtNomeFuncionario.getText().toString()) && !TextUtils.isEmpty(edtSobrenome.getText().toString())&&
                !TextUtils.isEmpty(edtCpf.getText().toString())){
            if(!haNomeFunc && !haSobrenomeFunc && !haCpfFunc){
                Validacoes.requestFocus(btnLevelTres);
                btnLevelTres.setBackground(getResources().getDrawable(R.drawable.bordas_grid_buttons));
                btnLevelTres.setEnabled(true);
            }else{
                btnLevelTres.setBackground(getResources().getDrawable(R.drawable.inactive_button));
                btnLevelTres.setEnabled(false);
            }

        }else{
            btnLevelTres.setBackground(getResources().getDrawable(R.drawable.inactive_button));
            btnLevelTres.setEnabled(false);
        }
    }
    public void validaStepUm(){
        if(!TextUtils.isEmpty(edtNomeFantasia.getText().toString()) && !TextUtils.isEmpty(edtRazaoSocial.getText().toString()) &&
                !TextUtils.isEmpty(edtTelefone.getText().toString())&& !TextUtils.isEmpty(edtCnpj.getText().toString())){

            if(!haNomeFantasia && !haRazaoSocial && !haTelefone && !haCnpj){
                Validacoes.requestFocus(imgLevelDois);
                imgLevelDois.setBackground(getResources().getDrawable(R.drawable.bordas_grid_buttons));
                imgLevelDois.setEnabled(true);
            }else{
                imgLevelDois.setBackground(getResources().getDrawable(R.drawable.inactive_button));
                imgLevelDois.setEnabled(false);
            }
        }else{
            imgLevelDois.setBackground(getResources().getDrawable(R.drawable.inactive_button));
            imgLevelDois.setEnabled(false);
        }
    }
    public void verificaEntradas() {

        if(!haNomeFantasia && !haRazaoSocial && !haTelefone && !haCnpj && !haNomeFunc && !haSobrenomeFunc
                && !haCpfFunc && !haEmail && !haSenha && !haConfirmSenha) {

            if (!TextUtils.isEmpty(edtNomeFantasia.getText().toString()) &&
                    !TextUtils.isEmpty(edtRazaoSocial.getText().toString()) &&
                    !TextUtils.isEmpty(edtTelefone.getText().toString()) &&
                    !TextUtils.isEmpty(edtCnpj.getText().toString()) &&
                    !TextUtils.isEmpty(edtNomeFuncionario.getText().toString())
                    && !TextUtils.isEmpty(edtSobrenome.getText().toString()) &&
                    !TextUtils.isEmpty(edtCpf.getText().toString())&&
                    !TextUtils.isEmpty(edtEmailFunc.getText().toString()) &&
                    !TextUtils.isEmpty(edtSenha.getText().toString())) {

                if (Validacoes.verifyConnection(CadastroPessoaJuridica.this)) {

                    LayoutInflater inflater = getLayoutInflater();
                    final View alertLayout = inflater.inflate(R.layout.custom_alerta_dialog_termos, null);
                    final CheckBox aceitar = alertLayout.findViewById(R.id.check_aceito);
                    Button cancelar = alertLayout.findViewById(R.id.cancelar);
                    Button continuar = alertLayout.findViewById(R.id.btn_continuar);
                    AlertDialog.Builder alerta = new AlertDialog.Builder(CadastroPessoaJuridica.this);
                    alerta.setView(alertLayout);
                    alerta.setCancelable(false);
                    final AlertDialog dialogo = alerta.create();
                    dialogo.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                    dialogo.show();
                    continuar.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (aceitar.isChecked()) {
                                progress = ProgressDialog.show(CadastroPessoaJuridica.this,null,getString(R.string.txt_aguarde), false, true);
                                String telefoneCompleto = edtTelefone.getText().toString().replace("(", "").replace(")", "").replace("-", "");

                                String dd = telefoneCompleto.substring(0, 2);
                                String telefone = telefoneCompleto.substring(2, telefoneCompleto.length());

                                saveCommerce("Basic " +tokenUser, edtRazaoSocial.getText().toString(), edtNomeFantasia.getText().toString(),
                                        edtCnpj.getText().toString(), dd,telefone,1, edtNomeFuncionario.getText().toString(),
                                        edtSobrenome.getText().toString(),edtCpf.getText().toString(),1, edtEmailFunc.getText().toString(),edtSenha.getText().toString());

                                dialogo.dismiss();

                            } else {
                                Validacoes.alertDialogWarning(CadastroPessoaJuridica.this,getString(R.string.txt_aceite_os_termos),
                                        getString(R.string.txt_aviso),R.drawable.ic_error_outline_black_24dp);
                            }

                        }

                    });
                    cancelar.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            dialogo.dismiss();
                            Intent i = new Intent(CadastroPessoaJuridica.this, AutenticaUsuario.class);
                            i.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                            startActivity(i);
                        }

                    });

                } else {
                    Validacoes.alertDialogWarning(CadastroPessoaJuridica.this,getString(R.string.txt_verifica_conexao_tentar),
                            getString(R.string.txt_verifica_conexao),R.drawable.ic_public_black_24dp);
                }

            } else {
                Validacoes.alertDialogWarning(CadastroPessoaJuridica.this,getString(R.string.txt_dados_dig_corretos),
                        getString(R.string.txt_dados_ent_invalidos),R.drawable.ic_error_outline_black_24dp);

            }
        }else{
            Validacoes.alertDialogWarning(CadastroPessoaJuridica.this,getString(R.string.txt_dados_dig_corretos),
                    getString(R.string.txt_dados_ent_invalidos),R.drawable.ic_error_outline_black_24dp);
        }
    }
    public void saveCommerce(String tokenUser, String socialName, String fantasyName, String cnpj, String areaCode,String phoneNumber, int typePhone, String employeeName, String employeeLastName,
                             String cpf,int employeeRoleId,String userLogin, String userPasss){

        Commerce commerce = retrofit.create(Commerce.class);
        Call<CommerceBusiness> callUser = commerce.saveCommerce("application/x-www-form-urlencoded","application/json", tokenUser,  socialName, fantasyName, cnpj, areaCode, phoneNumber, typePhone,
                employeeName, employeeLastName, cpf, employeeRoleId, userLogin, userPasss);

        callUser.enqueue(new Callback<CommerceBusiness>() {
            @Override
            public void onResponse(Call<CommerceBusiness> call, Response<CommerceBusiness> response) {

                if (response.isSuccessful()) {
                    progress.dismiss();
                    Validacoes.alertDialogWarning(CadastroPessoaJuridica.this,getString(R.string.valida_cadastro_sucesso),
                            getString(R.string.txt_sucesso),R.drawable.ic_mail_outline_black_24dp);

                }else{
                    progress.dismiss();
                    Validacoes.alertDialogWarning(CadastroPessoaJuridica.this,getString(R.string.valida_cadastro_falha),
                            getString(R.string.txt_aviso),R.drawable.ic_error_outline_black_24dp);

                }
            }

            @Override
            public void onFailure(Call<CommerceBusiness> call, Throwable t) {

                progress.dismiss();
                Validacoes.alertDialogWarning(CadastroPessoaJuridica.this,getString(R.string.txt_erro_inesperado),
                        getString(R.string.txt_verifica_conexao),R.drawable.ic_public_black_24dp);

            }
        });
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent i = new Intent(CadastroPessoaJuridica.this, AutenticaUsuario.class);
        i.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
        startActivity(i);
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
}
