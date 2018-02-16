package com.example.talit.smarket.LogicalView;

/**
 * Created by talit on 12/02/2018.
 */

public class Search {

    private int idString;
    private String strBusca;

    public Search(int idString, String strBusca) {
        this.idString = idString;
        this.strBusca = strBusca;
    }

    public int getIdString() {
        return idString;
    }

    public void setIdString(int idString) {
        this.idString = idString;
    }

    public String getTxtBusca() {
        return strBusca;
    }

    public void setTxtBusca(String strBusca) {
        this.strBusca = strBusca;
    }
}
