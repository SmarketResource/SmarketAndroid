package com.example.talit.smarket.Async;

import android.app.AlertDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;

import com.example.talit.smarket.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by talit on 22/01/2018.
 */

public class AsyncGeneratedToken extends AsyncTask<String, String, String> {

    private Listener mListener;

    public interface Listener {

        public void onLoaded(String string, String token);
    }

    public AsyncGeneratedToken(Listener mListener){

        this.mListener = mListener;
    }

    @Override
    protected String doInBackground(String... strings) {

        String api_url = "https://smarketapi.azurewebsites.net/API/Login/GetGeneratedToken";
        String response = "";
        HttpURLConnection urlConnection;

        try {

            URL url = new URL(api_url);
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            InputStream inputStream;
            if (urlConnection.getResponseCode() < HttpURLConnection.HTTP_BAD_REQUEST) {
                inputStream = urlConnection.getInputStream();
            } else {
                inputStream = urlConnection.getErrorStream();
            }
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            String temp;
            while ((temp = bufferedReader.readLine()) != null) {
                response += temp;
            }
            Log.i("response", response);
            return response;

        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(String result) {
        super.onPostExecute(result);

        try {
            JSONObject get_status = new JSONObject(result);
            String status = get_status.getString("Error");

            if(status.equals("false")) {
                JSONObject getToken = new JSONObject(result);
                String token = getToken.getString("Token");

                if (mListener != null) {
                    mListener.onLoaded("true",token);
                }

            }else{

                if (mListener != null) {
                    mListener.onLoaded("false",null);
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
            if (mListener != null) {
                mListener.onLoaded("false",null);
            }
        }
    }

}
