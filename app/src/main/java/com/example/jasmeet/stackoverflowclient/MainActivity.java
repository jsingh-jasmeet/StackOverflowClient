package com.example.jasmeet.stackoverflowclient;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import com.example.jasmeet.stackoverflowclient.Models.Answer;
import com.example.jasmeet.stackoverflowclient.Models.Question;

import org.apache.commons.lang3.StringEscapeUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();
    private static int pageCount = 0;
    private Context ctx;
    private ListView questionListView;
    private ArrayList<Question> questions;
    private String query;
    private View footer;
    private MyQuestionAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ctx = this;

        final EditText searchEditText = (EditText) findViewById(R.id.search_edit_text);
        ImageButton searchImageButton = (ImageButton) findViewById(R.id.search_button);

        questions = new ArrayList<>();
        questionListView = (ListView) findViewById(R.id.question_list_view);

        footer = LayoutInflater.from(getBaseContext()).inflate(R.layout.get_more_results_textview, null);

        searchImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (searchEditText.getText().toString().compareTo("") == 0) {
                    Toast.makeText(getBaseContext(), R.string.empty_search_box, Toast.LENGTH_LONG).show();
                } else {

                    Log.v(TAG, "In Listener");

                    try {
                        query = URLEncoder.encode(searchEditText.getText().toString(), "UTF-8");
                    } catch (UnsupportedEncodingException e) {
                        query = searchEditText.getText().toString().replaceAll(" ", "%20");
                    }

                    Log.v(TAG, query);
                    pageCount = 1;
                    new getQuestions().execute();
                }
            }
        });

        footer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pageCount++;
                new getQuestions().execute();
                questionListView.smoothScrollToPosition(10 * (pageCount - 1));
            }
        });

        questionListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {

                Question question = questions.get(position);

                Intent viewQuestion = new Intent(ctx, ViewQuestionActivity.class);
                viewQuestion.putExtra("question", question);
                ctx.startActivity(viewQuestion);


            }
        });

        //Check if no view has focus to hide keyboard
        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    private class getQuestions extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            if (questions != null && pageCount == 1) {
                questions.clear();
                footer.setVisibility(View.GONE);

            }
            Toast.makeText(MainActivity.this, "Fetching questions...", Toast.LENGTH_LONG).show();
        }

        @Override
        protected Void doInBackground(Void... arg0) {

            HttpHandler sh = new HttpHandler();
            //Making a request to URL and getting response

            String url = "http://api.stackexchange.com/2.2/search/advanced?pagesize=10&order=desc&sort=votes&site=stackoverflow&title=" + query + "&page=" + pageCount + "&filter=!DDp24Ye4wFIqB1txVg6e77a3TUX.OeHXwIvj0PU08JVueK-NlKT";
            String jsonStr = sh.makeServiceCall(url);

            if (jsonStr != null) {
                //Log.v(TAG, jsonStr);
                try {
                    JSONObject root = new JSONObject(jsonStr);

                    JSONArray questionsArray = root.getJSONArray("items");

                    for (int i = 0; i < questionsArray.length(); i++) {
                        JSONObject questionObject = questionsArray.getJSONObject(i);
                        long questionID = questionObject.getLong("question_id");
                        int score = questionObject.getInt("score");
                        int answerCount = questionObject.getInt("answer_count");
                        String title = questionObject.getString("title");
                        String authorDisplayName = questionObject.optJSONObject("owner").optString("display_name");
                        String body = questionObject.optString("body");
                        String questionLink = questionObject.getString("link");

                        title = StringEscapeUtils.unescapeXml(title);
                        authorDisplayName = StringEscapeUtils.unescapeXml(authorDisplayName);

                        Question question = new Question(questionID, score, answerCount, title, authorDisplayName, questionLink);
                        question.setBody(body);

                        ArrayList<Answer> answers = getAnswers(questionObject.getJSONArray("answers"));

                        for (int j = 0; j < answers.size(); j++) {
                            question.addAnswer(answers.get(j));
                        }

                        questions.add(question);
                    }

                    if (questionsArray.length() == 0) {
                        Toast.makeText(getApplicationContext(), "The search did not return any results", Toast.LENGTH_LONG).show();
                    } else {
                        runOnUiThread(new Runnable() {
                            public void run() {
                                footer.setVisibility(View.VISIBLE);
                                questionListView.addFooterView(footer);
                            }
                        });
                    }

                } catch (final JSONException e) {
                    Log.e(TAG, "Json parsing error: " + e.getMessage());
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getApplicationContext(), "Json parsing error: " + e.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    });
                }
            } else {
                Log.e(TAG, "Couldn't get questions from server.");
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getApplicationContext(),
                                "Couldn't get questions from Server. Try again later.",
                                Toast.LENGTH_LONG).show();
                    }
                });
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            if (adapter == null) {
                adapter = new MyQuestionAdapter(MainActivity.this, questions);
                questionListView.setAdapter(adapter);
            } else if (pageCount == 1) {
                questionListView.setAdapter(adapter);
            } else {
                adapter.notifyDataSetChanged();
            }
        }

        protected ArrayList<Answer> getAnswers(JSONArray answersArray) {

            ArrayList<Answer> answers = new ArrayList<>();

            try {
                for (int i = 0; i < answersArray.length(); i++) {
                    JSONObject answerObject = answersArray.getJSONObject(i);
                    long answerID = answerObject.getLong("answer_id");
                    int score = answerObject.getInt("score");
                    boolean isAccepted = answerObject.getBoolean("is_accepted");
                    String body = answerObject.getString("body");
                    String authorDisplayName = answerObject.getJSONObject("owner").optString("display_name");

                    Answer answer = new Answer(answerID, score, isAccepted, body, authorDisplayName);

                    //Log.v(TAG, Long.toString(answer.getAnswerID()) + " " + answer.getAuthorDisplayName());
                    answers.add(answer);
                }
            } catch (final JSONException e) {
                Log.e(TAG, "Json parsing error: " + e.getMessage());
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getApplicationContext(), "Json parsing error: " + e.getMessage(), Toast.LENGTH_LONG).show();
                    }
                });
            }

            return answers;
        }
    }
}