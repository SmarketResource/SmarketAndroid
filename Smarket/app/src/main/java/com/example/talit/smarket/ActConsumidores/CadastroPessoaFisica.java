package com.example.talit.smarket.ActConsumidores;

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
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.talit.smarket.Activities.AutenticaUsuario;
import com.example.talit.smarket.Async.AsyncSaveConsumer;
import com.example.talit.smarket.R;
import com.example.talit.smarket.Utils.Validacoes;
import com.github.rtoshiro.util.format.SimpleMaskFormatter;
import com.github.rtoshiro.util.format.text.MaskTextWatcher;

public class CadastroPessoaFisica extends AppCompatActivity implements AsyncSaveConsumer.Listener {

    private MaskTextWatcher mtw;
    private SimpleMaskFormatter smf;
    private EditText edtNome;
    private EditText edtSobrenome;
    private EditText edtEmail;
    private static EditText edtTel;
    private EditText edtSenha;
    private EditText edtConfirSenha;
    private Button btnCadastrar;
    private Spinner smp;
    private String nomeStr;
    private String sobrenomeStr;
    private String emailStr;
    private String senhaStr;
    private String confirSenhaStr;
    private int idTelefone;
    private String[] tpTel;
    private String esTel;
    private ArrayAdapter<String> adp;
    public static ProgressBar pb;
    private boolean haCpf;
    private boolean haNome;
    private boolean haSobrenome;
    private boolean haEmail;
    private boolean haTelefone;
    private boolean haSenha;
    private boolean haConfirmarsenha;
    private TextView txtValidaNome;
    private TextView txtValidaSobreNome;
    private TextView txtValidaEmail;
    private TextView txtValidaTelefone;
    private TextView txtValidaSenha;
    private TextView txtValidaConfirSenha;
    private Button btnLevelUm;
    private LinearLayout lnDadosConsumidor;
    private LinearLayout lnDadosSenhaCons;
    private RelativeLayout relativeLevelUm;
    private RelativeLayout relativeLevelDois;
    private Animation direitaParaEsquerda;
    private Animation deBaixoParaCima;
    private ImageView imageUm;
    private ImageView imageDois;
    private TextView txtLevelUm;
    private TextView txtLevelDois;
    public static final String TOKEN = "TOKEN";
    private String tokenUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_cadastro_pessoa_fisica);

        edtNome              = findViewById(R.id.ed_nome);
        edtSobrenome         = findViewById(R.id.ed_sobrenome);
        edtEmail             = findViewById(R.id.ed_email_consumidor);
        edtSenha             = findViewById(R.id.ed_senha_cadastrar);
        edtConfirSenha       = findViewById(R.id.ed_senha_confimar_cadastrar);
        edtTel               = findViewById(R.id.ed_telefones);
        btnCadastrar         = findViewById(R.id.btn_cadastro);
        pb                   = findViewById(R.id.pb_cadastro);
        smp                  = findViewById(R.id.sp_tp_tel);
        txtValidaNome        = findViewById(R.id.txt_nome);
        txtValidaSobreNome   = findViewById(R.id.txt_sobrenome);
        txtValidaEmail       = findViewById(R.id.txt_email);
        txtValidaTelefone    = findViewById(R.id.txt_telefone);
        txtValidaSenha       = findViewById(R.id.txt_senha);
        txtValidaConfirSenha = findViewById(R.id.txt_confirmar_senha);
        btnLevelUm           = findViewById(R.id.bt_level_um);
        lnDadosConsumidor    = findViewById(R.id.ln_dados_consumidor);
        lnDadosSenhaCons     = findViewById(R.id.ln_dados_senha);
        relativeLevelUm      = findViewById(R.id.nivel_um);
        relativeLevelDois    = findViewById(R.id.nivel_dois);
        imageUm              = findViewById(R.id.concluido_um);
        imageDois            = findViewById(R.id.concluido_dois);
        txtLevelUm           = findViewById(R.id.level_um_down);
        txtLevelDois         = findViewById(R.id.level_dois_down);

        SharedPreferences prefs = getSharedPreferences(TOKEN, MODE_PRIVATE);
        tokenUser = prefs.getString("token", null);

        direitaParaEsquerda = AnimationUtils.loadAnimation(this,R.anim.da_direita_para_esquerda);
        deBaixoParaCima     = AnimationUtils.loadAnimation(this,R.anim.para_cima);

        tpTel = new String[]{getString(R.string.smp_desc_telefone), getString(R.string.smp_desc_celular)};
        adp   = new ArrayAdapter<String>(this, R.layout.custom_textview_to_spinner, tpTel);

        adp.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        smp.setAdapter(adp);

        pb.setVisibility(View.INVISIBLE);

        esTel            = "";
        haCpf            = false;
        haNome           = false;
        haSobrenome      = false;
        haEmail          = false;
        haTelefone       = false;
        haSenha          = false;
        haConfirmarsenha = false;

        smf = new SimpleMaskFormatter("(NN)NNNNN-NNNN");
        mtw = new MaskTextWatcher(edtTel, smf);
        edtTel.addTextChangedListener(mtw);


        edtNome.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (TextUtils.isEmpty(charSequence)) {
                    txtValidaNome.setText(R.string.txt_campo_obrigatorio);
                    Validacoes.requestFocus(edtNome);
                    haNome = true;

                } else if (charSequence.length() > 150) {
                    txtValidaNome.setText(R.string.txt_nome_grande);
                    Validacoes.requestFocus(edtNome);
                    haNome = true;

                } else {
                    txtValidaNome.setText(null);
                    haNome = false;
                }
                validaStepUm();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        edtSobrenome.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                if (TextUtils.isEmpty(charSequence)) {
                    txtValidaSobreNome.setText(R.string.txt_campo_obrigatorio);
                    Validacoes.requestFocus(edtSobrenome);
                    haSobrenome = true;

                } else if (charSequence.length() > 150) {
                    txtValidaSobreNome.setText(R.string.txt_nome_grande);
                    Validacoes.requestFocus(edtSobrenome);
                    haSobrenome = true;
                } else {
                    haSobrenome = false;
                    txtValidaSobreNome.setText(null);
                }
                validaStepUm();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        edtEmail.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                if (TextUtils.isEmpty(edtEmail.getText().toString())) {
                    txtValidaEmail.setText(R.string.txt_campo_obrigatorio);
                    Validacoes.requestFocus(edtEmail);
                    haEmail = true;

                } else if (!Validacoes.validaEmail(edtEmail.getText().toString())) {
                    txtValidaEmail.setText(R.string.txt_email_dig_incorretamente);
                    Validacoes.requestFocus(edtEmail);
                    haEmail = true;

                } else {
                    haEmail = false;
                    txtValidaEmail.setText(null);
                }
                validaStepUm();
            }

            @Override
            public void afterTextChanged(Editable editable) {


            }
        });
        edtTel.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                if (edtTel.getText().toString().length() > 1 && edtTel.getText().toString().length() < 13) {
                    txtValidaTelefone.setText(R.string.txt_telefone_invalido);
                    Validacoes.requestFocus(edtTel);
                    haTelefone = true;

                } else {
                    haTelefone = false;
                    txtValidaTelefone.setText(null);
                }
                validaStepUm();
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
        edtConfirSenha.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                if (TextUtils.isEmpty(edtConfirSenha.getText().toString())) {
                    txtValidaConfirSenha.setText(R.string.txt_campo_obrigatorio);
                    Validacoes.requestFocus(edtConfirSenha);
                    haConfirmarsenha = true;

                } else if (edtConfirSenha.length() <= 4) {
                    txtValidaConfirSenha.setText(R.string.txt_senha_pequena);
                    Validacoes.requestFocus(edtConfirSenha);
                    haConfirmarsenha = true;

                } else if (!edtConfirSenha.getText().toString().equals(edtSenha.getText().toString())) {
                    txtValidaConfirSenha.setText(R.string.txt_senha_identica);
                    Validacoes.requestFocus(edtConfirSenha);
                    haConfirmarsenha = true;

                } else {
                    haConfirmarsenha = false;
                    txtValidaConfirSenha.setText(null);

                }
                validaCadastro();
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        smp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View v, int posicao, long id) {
                esTel = parent.getItemAtPosition(posicao).toString();

                if (esTel.equals("Telefone")) {
                    idTelefone = 1;
                    edtTel.setText("");
                    edtTel.removeTextChangedListener(mtw);
                    smf = new SimpleMaskFormatter("(NN)NNNN-NNNN");
                    mtw = new MaskTextWatcher(edtTel, smf);
                    edtTel.addTextChangedListener(mtw);
                }
                if (esTel.equals("Celular")) {
                    idTelefone = 2;
                    edtTel.setText("");
                    edtTel.removeTextChangedListener(mtw);
                    smf = new SimpleMaskFormatter("(NN)NNNNN-NNNN");
                    mtw = new MaskTextWatcher(edtTel, smf);
                    edtTel.addTextChangedListener(mtw);
                }
                if (esTel.equals("Teléfono")) {
                    idTelefone = 1;
                    edtTel.setText("");
                    edtTel.removeTextChangedListener(mtw);
                    smf = new SimpleMaskFormatter("(NN)NNNN-NNNN");
                    mtw = new MaskTextWatcher(edtTel, smf);
                    edtTel.addTextChangedListener(mtw);
                }
                if (esTel.equals("Móviles")) {
                    idTelefone = 2;
                    edtTel.setText("");
                    edtTel.removeTextChangedListener(mtw);
                    smf = new SimpleMaskFormatter("(NN)NNNNN-NNNN");
                    mtw = new MaskTextWatcher(edtTel, smf);
                    edtTel.addTextChangedListener(mtw);
                }
                //Toast.makeText(CadastroConsumidor.this, esTel, Toast.LENGTH_SHORT).show();
            }

            public void onNothingSelected(AdapterView<?> arg0) {
                // TODO Auto-generated method stub
            }
        });
        btnCadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                verificaEntradas();
            }
        });
        btnLevelUm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                lnDadosConsumidor.setVisibility(View.GONE);
                relativeLevelDois.setVisibility(View.GONE);
                relativeLevelUm.setVisibility(View.VISIBLE);
                lnDadosSenhaCons.setVisibility(View.VISIBLE);
                relativeLevelUm.setAnimation(deBaixoParaCima);
                txtLevelUm.setText(null);
                imageUm.setVisibility(View.VISIBLE);
                lnDadosSenhaCons.setAnimation(direitaParaEsquerda);
            }
        });
    }

    public void validaCadastro(){

        if(!TextUtils.isEmpty(edtSenha.getText().toString())&&
                !TextUtils.isEmpty(edtConfirSenha.getText().toString())){
            if(!haSenha && !haConfirmarsenha){
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

    public void validaStepUm(){
        if(!TextUtils.isEmpty(edtNome.getText().toString()) && !TextUtils.isEmpty(edtSobrenome.getText().toString()) &&
                !TextUtils.isEmpty(edtEmail.getText().toString())&& !TextUtils.isEmpty(edtTel.getText().toString())){

            if(!haNome && !haSobrenome && !haTelefone && !haEmail){
                Validacoes.requestFocus(btnLevelUm);
                btnLevelUm.setBackground(getResources().getDrawable(R.drawable.bordas_grid_buttons));
                btnLevelUm.setEnabled(true);
            }else{
                btnLevelUm.setBackgroundResource(R.drawable.inactive_button);
                btnLevelUm.setEnabled(false);
            }
        }else{
            btnLevelUm.setBackgroundResource(R.drawable.inactive_button);
            btnLevelUm.setEnabled(false);
        }
    }

   public void verificaEntradas() {

        if(!haNome && !haSobrenome && !haEmail && !haTelefone && !haSenha && !haConfirmarsenha) {

            if (!TextUtils.isEmpty(edtNome.getText().toString()) &&
                    !TextUtils.isEmpty(edtSobrenome.getText().toString()) && !TextUtils.isEmpty(edtTel.getText().toString()) &&
                    !TextUtils.isEmpty(edtEmail.getText().toString()) && !TextUtils.isEmpty(edtSenha.getText().toString())
                    && !TextUtils.isEmpty(edtConfirSenha.getText().toString())) {

                if (Validacoes.verifyConnection(CadastroPessoaFisica.this)) {

                    LayoutInflater inflater = getLayoutInflater();
                    final View alertLayout = inflater.inflate(R.layout.custom_alerta_dialog_termos, null);
                    final CheckBox aceitar = alertLayout.findViewById(R.id.check_aceito);
                    Button cancelar = alertLayout.findViewById(R.id.cancelar);
                    Button continuar = alertLayout.findViewById(R.id.btn_continuar);
                    AlertDialog.Builder alerta = new AlertDialog.Builder(CadastroPessoaFisica.this);
                    alerta.setView(alertLayout);
                    alerta.setCancelable(false);
                    final AlertDialog dialogo = alerta.create();
                    dialogo.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                    dialogo.show();
                    continuar.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (aceitar.isChecked()) {
                                pb.setVisibility(View.VISIBLE);
                                String telefoneCompleto = edtTel.getText().toString().replace("(", "").replace(")", "").replace("-", "");

                                String dd = telefoneCompleto.substring(0, 2);
                                String telefone = telefoneCompleto.substring(2, telefoneCompleto.length());

                                AsyncSaveConsumer connSaveConsumidor = new AsyncSaveConsumer(CadastroPessoaFisica.this);
                                connSaveConsumidor.execute("Basic " +tokenUser,edtEmail.getText().toString().trim(),edtSenha.getText().toString().trim(),
                                                            edtNome.getText().toString().trim(),edtSobrenome.getText().toString().trim(),
                                                            String.format("%d", idTelefone),dd,telefone);

                                dialogo.dismiss();

                            } else {
                                AlertDialog.Builder builder = new AlertDialog.Builder(CadastroPessoaFisica.this);
                                builder.setTitle(R.string.txt_aviso);
                                builder.setMessage(R.string.txt_aceite_os_termos);
                                builder.setPositiveButton(R.string.txt_fechar, new DialogInterface.OnClickListener() {
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
                    cancelar.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                           dialogo.dismiss();
                        }

                    });

                } else {
                    android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(CadastroPessoaFisica.this);
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

            } else {
                android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(CadastroPessoaFisica.this);
                builder.setTitle(R.string.txt_dados_ent_invalidos);
                builder.setMessage(R.string.txt_dados_dig_corretos);
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                builder.setCancelable(false);
                builder.show();

            }
        }else{
            android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(CadastroPessoaFisica.this);
            builder.setTitle(R.string.txt_dados_ent_invalidos);
            builder.setMessage(R.string.txt_dados_dig_corretos);
            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });
            builder.setCancelable(false);
            builder.show();
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent i = new Intent(CadastroPessoaFisica.this, AutenticaUsuario.class);
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

    @Override
    public void onLoaded(String string, String msg) {
        pb.setVisibility(View.INVISIBLE);
        if (Validacoes.verifyConnection(CadastroPessoaFisica.this)) {

            if (string.equalsIgnoreCase("true")) {

                android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(CadastroPessoaFisica.this);
                builder.setTitle(R.string.txt_sucesso);
                builder.setMessage(R.string.valida_cadastro_sucesso);
                builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        startActivity(new Intent(CadastroPessoaFisica.this, AutenticaUsuario.class));
                        finish();

                    }
                });
                builder.setCancelable(false);
                builder.show();

            }else {
                android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(CadastroPessoaFisica.this);
                builder.setTitle(R.string.txt_aviso);
                builder.setMessage(R.string.valida_cadastro_falha);
                builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        startActivity(new Intent(CadastroPessoaFisica.this, AutenticaUsuario.class));
                        finish();

                    }
                });
                builder.setCancelable(false);
                builder.show();
            }
        } else {
            android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(CadastroPessoaFisica.this);
            builder.setTitle(R.string.txt_verifica_conexao);
            builder.setMessage(R.string.txt_verifica_conexao_tentar);
            builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                    startActivity(new Intent(CadastroPessoaFisica.this, AutenticaUsuario.class));
                    finish();
                }
            });
            builder.setCancelable(false);
            builder.show();
        }
    }
}
