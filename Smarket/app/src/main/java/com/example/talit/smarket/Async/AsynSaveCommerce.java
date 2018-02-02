package com.example.talit.smarket.Async;

import android.os.AsyncTask;
import android.util.Log;

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
 * Created by talit on 29/01/2018.
 */

public class AsynSaveCommerce  extends AsyncTask<String, String, String> {

    private Listener mListener;
    private String token;
    private String SocialName;
    private String FantasyName;
    private String CNPJ;
    private String AreaCode;
    private String Number;
    private String TypePhoneId;
    private String EmployeeName;
    private String EmployeeLastName;
    private String CPF;
    private String EmployeeRoleId;
    private String UserLogin;
    private String UserPass;

    public interface Listener {

        public void onLoaded(String string, String msg);

    }

    public AsynSaveCommerce(Listener mListener) {

        this.mListener = mListener;
    }

    @Override
    protected String doInBackground(String... n) {

        String api_url = "https://smarketapi.azurewebsites.net/API/Commerce/SaveCommerce";

        token = n[0];
        SocialName = n[1];
        FantasyName = n[2];
        CNPJ = n[3];
        AreaCode = n[4];
        Number = n[5];
        TypePhoneId = n[6];
        EmployeeName = n[7];
        EmployeeLastName = n[8];
        CPF = n[9];
        EmployeeRoleId = n[10];
        UserLogin = n[11];
        UserPass = n[12];

        HttpURLConnection urlConnection;

        try {
            URL url = new URL(api_url);
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("POST");
            urlConnection.setDoOutput(true);
            urlConnection.setRequestProperty("Authorization", token);
            urlConnection.setRequestProperty("Content-Type", "application/json");
            JSONObject jsonObject = new JSONObject();
            jsonObject.accumulate("SocialName", SocialName);
            jsonObject.accumulate("FantasyName", FantasyName);
            jsonObject.accumulate("CNPJ", CNPJ);
            jsonObject.accumulate("AreaCode", AreaCode);
            jsonObject.accumulate("Number", Number);
            jsonObject.accumulate("TypePhoneId", TypePhoneId);
            jsonObject.accumulate("EmployeeName", EmployeeName);
            jsonObject.accumulate("EmployeeLastName", EmployeeLastName);
            jsonObject.accumulate("CPF", CPF);
            jsonObject.accumulate("EmployeeRoleId", EmployeeRoleId);
            jsonObject.accumulate("UserLogin", UserLogin);
            jsonObject.accumulate("UserPass", UserPass);
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
