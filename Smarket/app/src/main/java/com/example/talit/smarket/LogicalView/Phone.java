package com.example.talit.smarket.LogicalView;

/**
 * Created by talit on 15/02/2018.
 */

public class Phone {

    private String areadCode;
    private String phoneNumer;
    private int typeId;

    public Phone(String areadCode, String phoneNumer, int typeId) {
        this.areadCode = areadCode;
        this.phoneNumer = phoneNumer;
        this.typeId = typeId;
    }

    public String getAreadCode() {
        return areadCode;
    }

    public void setAreadCode(String areadCode) {
        this.areadCode = areadCode;
    }

    public String getPhoneNumer() {
        return phoneNumer;
    }

    public void setPhoneNumer(String phoneNumer) {
        this.phoneNumer = phoneNumer;
    }

    public int getTypeId() {
        return typeId;
    }

    public void setTypeId(int typeId) {
        this.typeId = typeId;
    }
}
