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
import java.util.List;

/**
 * Created by talit on 22/01/2018.
 */

public class AsyncAuthUser extends AsyncTask<String, String, String> {

    private String login;
    private String senha;
    private String token;
    private Listener mListener;

    public interface Listener {

        public void onLoaded(String string, Usuario usuario, String msg);

    }

    public AsyncAuthUser(Listener mListener) {

        this.mListener = mListener;
    }

    @Override
    protected String doInBackground(String... n) {
        String api_url = "https://smarketapi.azurewebsites.net/API/Login/AuthUser";

        token = n[0];
        login = n[1];
        senha = n[2];


        HttpURLConnection urlConnection;

        try {
            URL url = new URL(api_url);
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("POST");
            urlConnection.setDoOutput(true);
            urlConnection.setRequestProperty("Authorization", token);
            urlConnection.setRequestProperty("Content-Type", "application/json");
            //urlConnection.setRequestProperty("Accept-Encoding", "application/json");
            JSONObject jsonObject = new JSONObject();
            jsonObject.accumulate("UserLogin", login);
            jsonObject.accumulate("UserPass", senha);
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

       /* try {

            JSONObject api_result = new JSONObject(result);
            String status = api_result.getString("Error");
            String msg = api_result.getString("Message");

            Usuario user = null;
            if (status.equals("false")) {
                JSONArray dados = api_result.getJSONArray("Users");

                for (int i = 0; i < dados.length(); ++i) {
                    JSONObject dados_result = dados.getJSONObject(i);
                    user = new Usuario(dados_result.getString("UserId"),dados_result.getInt("TypeUserId"),
                                        dados_result.getString("UserLogin"),dados_result.getString("UserPass"));
                }
                if (mListener != null) {
                    mListener.onLoaded("true", user,msg);
                }

            } else {

                if (mListener != null) {
                    mListener.onLoaded("false", null,msg);
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
            if (mListener != null) {
                mListener.onLoaded("false", null, null);
            }
        }*/
    }
}
