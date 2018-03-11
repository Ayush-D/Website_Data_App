package com.example.application.newsapp;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btn = findViewById(R.id.btn);
        final EditText editText = findViewById(R.id.editText);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               String s= "https://www."+ editText.getText().toString();

               UpdatetextView(s);
            }
        });
    }
    //Method
    private void UpdatetextView(String s){

        NetworkTask networkTask = new NetworkTask();
       // networkTask.execute("https://www.google.com","https://www.github.com","https://www.guidance360.in");
        networkTask.execute(s);
    }

    class NetworkTask extends AsyncTask<String,Void,String>{

        @Override
        protected String doInBackground(String... strings) {
            String Stringurl = strings[0];

            try {
                URL url = new URL(Stringurl);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();

                InputStream inputStream = httpURLConnection.getInputStream();

                Scanner scanner = new Scanner(inputStream);
                //WE take all the page and then convert to string we have trick got that using
                //deleminator "\\A"

                scanner.useDelimiter("\\A");

                if(scanner.hasNext()){
                    String str = scanner.next();
                    return  str;
                }

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

            return  "Falied To Fetch";
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            TextView textView = findViewById(R.id.textView);
            textView.setText(s);
        }
    }

}
