package com.example.talit.smarket.ActConsumidores;

import android.animation.ObjectAnimator;
import android.animation.TimeInterpolator;
import android.content.Context;
import android.content.Intent;
import android.media.Image;
import android.support.v4.view.ViewCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.DecelerateInterpolator;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.talit.smarket.Activities.AutenticaUsuario;
import com.example.talit.smarket.R;
import com.example.talit.smarket.Utils.Validacoes;
import com.github.rtoshiro.util.format.SimpleMaskFormatter;
import com.github.rtoshiro.util.format.text.MaskTextWatcher;

public class CadastroPessoaJuridica extends AppCompatActivity {

    private MaskTextWatcher mtw;
    private SimpleMaskFormatter smf;
    private MaskTextWatcher mtw2;
    private SimpleMaskFormatter smf2;
    private LinearLayout dadosEmpresa;
    private LinearLayout dadosFuncionario;
    private LinearLayout dadosLogin;
    private RelativeLayout relativeLevelUm;
    private Animation direitaParaEsquerda;
    private Animation deBaixoParaCima;
    private EditText edtNomeFantasia;
    private EditText edtRazaoSocial;
    private EditText edtTelefone;
    private EditText edtCnpj;
    private boolean haNomeFantasia;
    private boolean haRazaoSocial;
    private boolean haTelefone;
    private boolean haCnpj;
    private TextView txtValidaNomeFantasia;
    private TextView txtRazaoSocial;
    private TextView txtValidaTelefone;
    private TextView txtValidadCnoj;
    private ImageButton imgLevelDois;

    private static Context c;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        c = this;
        setContentView(R.layout.act_cadastro_pessoa_juridica);
        dadosEmpresa = findViewById(R.id.ln_dados_empresa);
        dadosFuncionario = findViewById(R.id.ln_dados_funcionarios);
        dadosLogin = findViewById(R.id.ln_dados_login);
        relativeLevelUm = findViewById(R.id.nivel_um);
        imgLevelDois = findViewById(R.id.bt_level_dois);
        edtNomeFantasia = findViewById(R.id.edt_nome_fantasia);
        edtRazaoSocial = findViewById(R.id.ed_razao_social);
        edtTelefone = findViewById(R.id.ed_telefones);
        edtCnpj = findViewById(R.id.ed_cnpj);
        txtValidaNomeFantasia = findViewById(R.id.txt_nome_fantasia);
        txtRazaoSocial = findViewById(R.id.txt_razao_social);
        txtValidaTelefone = findViewById(R.id.txt_telefone);
        txtValidadCnoj = findViewById(R.id.txt_cnpj);

        direitaParaEsquerda = AnimationUtils.loadAnimation(this,R.anim.da_direita_para_esquerda);
        deBaixoParaCima = AnimationUtils.loadAnimation(this,R.anim.para_cima);

        haNomeFantasia = false;
        haRazaoSocial= false;
        haTelefone = false;
        haCnpj = false;

        smf = new SimpleMaskFormatter("(NN)NNNN-NNNN");
        mtw = new MaskTextWatcher(edtTelefone, smf);
        edtTelefone.addTextChangedListener(mtw);

        smf2 = new SimpleMaskFormatter("NN.NNN.NNN/NNNN-NN");
        mtw2 = new MaskTextWatcher(edtCnpj, smf2);
        edtCnpj.addTextChangedListener(mtw2);

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
                validaStepDois();
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
                validaStepDois();
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
                validaStepDois();
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
                    txtValidadCnoj.setText(R.string.txt_telefone_invalido);
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
                validaStepDois();
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        imgLevelDois.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dadosEmpresa.setVisibility(View.GONE);
                relativeLevelUm.setVisibility(View.VISIBLE);
                dadosFuncionario.setVisibility(View.VISIBLE);
                relativeLevelUm.setAnimation(deBaixoParaCima);
                dadosFuncionario.setAnimation(direitaParaEsquerda);
            }
        });

    }

    public void validaStepDois(){
        if(!TextUtils.isEmpty(edtNomeFantasia.getText().toString()) && !TextUtils.isEmpty(edtRazaoSocial.getText().toString()) &&
                !TextUtils.isEmpty(edtTelefone.getText().toString())&& !TextUtils.isEmpty(edtCnpj.getText().toString())){

            if(!haNomeFantasia && !haRazaoSocial && !haTelefone && !haCnpj){
               imgLevelDois.setVisibility(View.VISIBLE);
            }else{
                imgLevelDois.setVisibility(View.INVISIBLE);
            }
        }else{
            imgLevelDois.setVisibility(View.INVISIBLE);
        }
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
