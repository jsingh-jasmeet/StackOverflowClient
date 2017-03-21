package com.example.jasmeet.stackoverflowclient;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();
    private ListView questionListView;
    ArrayList<Question> questions;
    private String query;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final EditText searchEditText = (EditText) findViewById(R.id.search_edit_text);
        ImageButton searchImageButton = (ImageButton) findViewById(R.id.search_button);

        searchImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (searchEditText.getText().toString().compareTo("") == 0) {
                    Toast.makeText(getBaseContext(), R.string.empty_search_box, Toast.LENGTH_LONG).show();
                } else {
                    query = searchEditText.getText().toString().replaceAll(" ","%20");
                    new getQuestions().execute();
                }
            }
        });

        questions = new ArrayList<>();

        questionListView = (ListView) findViewById(R.id.question_list_view);
        MyQuestionAdapter adapter = new MyQuestionAdapter(this, questions);
        questionListView.setAdapter(adapter);
    }

    private void displayQuestions() {
        questions = new ArrayList<>();
        questionListView = (ListView) findViewById(R.id.question_list_view);
        MyQuestionAdapter adapter = new MyQuestionAdapter(this, questions);
        questionListView.setAdapter(adapter);
    }

    private class getQuestions extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            Toast.makeText(MainActivity.this, "Fetching questions...", Toast.LENGTH_LONG).show();
        }

        @Override
        protected Void doInBackground(Void... arg0) {

            HttpHandler sh = new HttpHandler();
            //Making a request to URL and getting response

            String url = "http://api.stackexchange.com/2.2/search/advanced?order=desc&max=20&sort=votes&site=stackoverflow&title=" + query;
            String jsonStr = sh.makeServiceCall(url);

            if (jsonStr != null) {
                Log.v(TAG, jsonStr);
            }
            return null;
        }
    }
}
