package com.example.diogo.clima;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;


public class ClimaHttp {
    //public static String URL="https://api.hgbrasil.com/weather/?format=json&woeid=456570";
    //"https://api.hgbrasil.com/weather/?format=json&city_name="+cidade+"&key=4aaa9ac3";

    public static void setUrl(String urlrecebida){
        URL=urlrecebida;
    }
    public static String getUrl(){
        return url;
    }

    public static String url=getUrl();
    public static String URL=url;



        private static HttpURLConnection connectar(String urlWebservice){
        final int SEGUNDOS= 10000;

        try {
            java.net.URL url = new URL(urlWebservice);
            HttpURLConnection conexao = (HttpURLConnection) url.openConnection();
            conexao.setReadTimeout(10 * SEGUNDOS);
            conexao.setConnectTimeout(15 * SEGUNDOS);
            conexao.setRequestMethod("GET");
            conexao.setDoInput(true);
            conexao.setDoOutput(false);
            conexao.connect();
            return conexao;


        } catch (IOException e) {
            Log.d("ERRO", e.getMessage());
            e.printStackTrace();
        }
        return null;
    }
    public static boolean hasConnected(Context context){
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info = cm.getActiveNetworkInfo();
        return (info != null && info.isConnected());
    }

    public static Clima getClimaFromJson(JSONObject json){
        String date;
        String weekday;
        String max;
        String min;
        String description;
        String condition;
        Clima clima=null;

        try {
            date=json.getString("date");
            weekday=json.getString("weekday");
            max=json.getString("max");
            min=json.getString("min");
            description=json.getString("description");
            condition = json.getString("condition");
            clima = new Clima(date,weekday,max,min,description,condition);

        }catch (JSONException ex){
            Log.d("ERROR",ex.getMessage());
        }
        return clima;
    }

    public static ArrayList<Clima> readJson(JSONObject json) {




        ArrayList<Clima> arrayList = new ArrayList<>();
        try {
            JSONObject results = json.getJSONObject("results");
            JSONArray jsonClima = results.getJSONArray("forecast");

            for(int i=0;i<7;i++){
                JSONObject d0= jsonClima.getJSONObject(i);
                arrayList.add(getClimaFromJson(d0));

            }


        } catch (JSONException e) {

            Log.d("Json", e.getMessage());
            e.printStackTrace();
        }
        return arrayList;
    }

    public static ArrayList<Clima> loadClima() {
        try {
            if (URL == null) {
                setUrl(url);
            }
            HttpURLConnection connection = connectar(URL);
            int response = connection.getResponseCode();
            if (response == HttpURLConnection.HTTP_OK) {
                InputStream inputStream = connection.getInputStream();
                JSONObject json = new JSONObject(bytesParaString(inputStream));
                ArrayList<Clima>  climaList =readJson(json);
                return climaList;
            }

        } catch (Exception e) {
            Log.d("ERRO", e.getMessage());
        }
        return null;
    }
    private static String bytesParaString(InputStream inputStream) {
        byte[] buffer = new byte[1024];
        ByteArrayOutputStream bufferzao = new ByteArrayOutputStream();
        int byteslidos;
        try {
            while ((byteslidos = inputStream.read(buffer)) != -1) {
                bufferzao.write(buffer, 0, byteslidos);

            }
            return new String(bufferzao.toByteArray(), "UTF-8");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }


}
