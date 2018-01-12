package com.example.talit.smarket.Fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.talit.smarket.R;

/**
 * Created by talit on 29/12/2017.
 */

public class IntroducaoUm extends Fragment {

    public IntroducaoUm(){

    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_introducao_um, container, false);
        return v;
    }
}
