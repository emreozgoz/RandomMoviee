package com.example.randommovie;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private ListView lv;
    public ArrayList liste = new ArrayList();
    private ArrayAdapter<String> adapter;
    private static String URL = "https://www.bestrandoms.com/random-movie-generator";
    private ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        lv=(ListView)findViewById(R.id.list);
        adapter=new ArrayAdapter<>(this,android.R.layout.simple_list_item_1,liste);
        Button btn = (Button)findViewById(R.id.button);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new VeriGetir().execute();

            }
        });


    }
    private class VeriGetir extends AsyncTask<Void,Void,Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog=new ProgressDialog(MainActivity.this);
            progressDialog.setTitle("Movies");
            progressDialog.setMessage("Please Wait...");
            progressDialog.setIndeterminate(false);
            progressDialog.show();

        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

            lv.setAdapter(adapter);
            progressDialog.dismiss();

        }

        @Override
        protected Void doInBackground(Void... voids) {
            liste.clear();
            try {
                Document doc = Jsoup.connect(URL).timeout(30*1000).get();
                Elements filmadi = doc.select("p[class].text-center.font-18");
                for (int i=0;i<filmadi.size();i++){

                    liste.add(filmadi.get(i).text());


                }
            } catch (IOException e) {
                e.printStackTrace();
            }


            return null;
        }
    }
}