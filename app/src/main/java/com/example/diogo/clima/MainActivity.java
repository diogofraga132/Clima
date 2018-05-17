package com.example.diogo.clima;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;


import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ClimaTask mTask;
    ArrayList<Clima> mClima;
    ListView mListClima;
    ArrayAdapter<Clima> mAdapter;
    public static String URL;
        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_main);
            final EditText cidade=(EditText) findViewById(R.id.etCidade);
            Button buscar = (Button) findViewById(R.id.btnBusca);

            buscar.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    cidade.setHint(cidade.getText().toString());
                    cidade.setText("");
                    ClimaHttp.setUrl("https://api.hgbrasil.com/weather/?format=json&city_name="+cidade.getText().toString()+"&key=4aaa9ac3");
                    mListClima = (ListView) findViewById(R.id.listClima);
                    search();
                }
            });
        }
        private void search() {
            try{
                    mClima = new ArrayList<Clima>();


                mAdapter = new ClimaAdapter(getApplicationContext(), mClima);
                mListClima.setAdapter(mAdapter);
                startDownload();
                if (mTask == null) {
                    if (ClimaHttp.hasConnected(this)) {

                    } else {
                        Toast.makeText(getApplicationContext(), "Sem conexão...", Toast.LENGTH_LONG).show();
                    }
                } else if (mTask.getStatus() == AsyncTask.Status.RUNNING) {
                    //Toast.makeText(getApplicationContext(), "......", Toast.LENGTH_LONG).show();
                }
            }catch (Exception E){
                Toast.makeText(getApplicationContext(),E.toString(), Toast.LENGTH_LONG).show();
            }
        }



        public void startDownload() {
                mTask = new ClimaTask();
                mTask.execute();
        }

        class ClimaTask extends AsyncTask<Void, Void, ArrayList<Clima>> {
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                Toast.makeText(getApplicationContext(), "Buscando...", Toast.LENGTH_SHORT).show();
            }

            @Override
            protected ArrayList<Clima> doInBackground(Void... strings) {
                ArrayList<Clima> climaList = ClimaHttp.loadClima();
                return climaList;
            }
            @Override
            protected void onPostExecute(ArrayList<Clima> climas) {
                super.onPostExecute(climas);
                if (climas != null) {
                    mClima.clear();
                    mClima.addAll(climas);
                    mAdapter.notifyDataSetChanged();
                } else {

                    Toast.makeText(getApplicationContext(), "Buscando...", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

