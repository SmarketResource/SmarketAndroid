package com.example.talit.smarket.LogicalView;

/**
 * Created by talit on 28/12/2017.
 */

public class Usuario {

    private String idUsuario;
    private int tipoId;
    private String login;
    private String senha;

    public Usuario(String idUsuario, int tipoId, String login, String senha) {
        this.idUsuario = idUsuario;
        this.tipoId = tipoId;
        this.login = login;
        this.senha = senha;
    }

    public String getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(String idUsuario) {
        this.idUsuario = idUsuario;
    }

    public int getTipoId() {
        return tipoId;
    }

    public void setTipoId(int tipoId) {
        this.tipoId = tipoId;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }
}
