package com.example.talit.smarket.Async;

import android.os.AsyncTask;
import android.util.Log;

import com.example.talit.smarket.LogicalView.Usuario;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by talit on 28/01/2018.
 */

public class AsyncSaveConsumer extends AsyncTask<String, String, String> {

    private Listener mListener;
    private String token;
    private String email;
    private String senha;
    private String nome;
    private String sobrenome;
    private String tpTelefone;
    private String dd;
    private String telefone;

    public interface Listener {

        public void onLoaded(String string, String msg);

    }

    public AsyncSaveConsumer(Listener mListener) {

        this.mListener = mListener;
    }

    @Override
    protected String doInBackground(String... n) {
        String api_url = "https://smarketapi.azurewebsites.net/API/Consumers/SaveConsumer";

        token = n[0];
        email = n[1];
        senha = n[2];
        nome = n[3];
        sobrenome = n[4];
        tpTelefone = n[5];
        dd = n[6];
        telefone = n[7];

        HttpURLConnection urlConnection;

        try {
            URL url = new URL(api_url);
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("POST");
            urlConnection.setDoOutput(true);
            urlConnection.setRequestProperty("Authorization", token);
            urlConnection.setRequestProperty("Content-Type", "application/json");
            JSONObject jsonObject = new JSONObject();
            jsonObject.accumulate("UserLogin", email);
            jsonObject.accumulate("UserPass", senha);
            jsonObject.accumulate("Name", nome);
            jsonObject.accumulate("LastName", sobrenome);
            jsonObject.accumulate("TypePhoneId", tpTelefone);
            jsonObject.accumulate("AreaCode", dd);
            jsonObject.accumulate("PhoneNumber", telefone);
            String json = jsonObject.toString();
            OutputStream outputStream = new BufferedOutputStream(urlConnection.getOutputStream());
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(outputStream, "utf-8"));
            writer.write(json);
            writer.flush();
            writer.close();
            outputStream.close();
            Log.i("Json", json);
            InputStream inputStream;
            if (urlConnection.getResponseCode() < HttpURLConnection.HTTP_BAD_REQUEST) {
                inputStream = urlConnection.getInputStream();
            } else {
                inputStream = urlConnection.getErrorStream();
            }
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            String temp, response = "";
            while ((temp = bufferedReader.readLine()) != null) {
                response += temp;
                Log.i("teste_api_login", response);
            }
            return response;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }
    @Override
    protected void onPostExecute(String result) {
        super.onPostExecute(result);

        try {

            JSONObject api_result = new JSONObject(result);
            String status = api_result.getString("Error");
            String msg = api_result.getString("Message");

            if (status.equals("false")) {

                if (mListener != null) {
                    mListener.onLoaded("true", msg);
                }

            } else {

                if (mListener != null) {
                    mListener.onLoaded("false",msg);
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
