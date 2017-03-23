package com.example.jasmeet.stackoverflowclient;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.example.jasmeet.stackoverflowclient.Models.Question;

import us.feras.mdv.MarkdownView;

public class ViewQuestionActivity extends AppCompatActivity {

    private static final String TAG = ViewQuestionActivity.class.getSimpleName();
    private Context ctx;
    private Question question;
    private LinearLayout questionView;
    private ListView answerListView;
    private MyAnswerAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_question);

        Intent intent = getIntent();
        question = intent.getParcelableExtra("question");

        questionView = (LinearLayout) LayoutInflater.from(getBaseContext()).inflate(R.layout.question_view, null);

        setUpViews();

        answerListView = (ListView) findViewById(R.id.answer_listview);
        adapter = new MyAnswerAdapter(this, question.getAllAnswers());
        answerListView.setAdapter(adapter);

        answerListView.addHeaderView(questionView);

    }

    private void setUpViews() {
        TextView displayNameTextView = (TextView) questionView.findViewById(R.id.view_question_display_name);
        TextView titleTextView = (TextView) questionView.findViewById(R.id.view_question_title);
        TextView scoreTextView = (TextView) questionView.findViewById(R.id.view_question_score);
        TextView answerCountTextView = (TextView) questionView.findViewById(R.id.view_question_answer_count);
        MarkdownView bodyTextView = (MarkdownView) questionView.findViewById(R.id.view_question_body);

        displayNameTextView.setText(question.getAuthorDisplayName());
        titleTextView.setText(question.getTitle());
        scoreTextView.setText(Integer.toString(question.getScore()));
        answerCountTextView.setText(Integer.toString(question.getAnswerCount()));
        bodyTextView.loadMarkdown(question.getBody(), "file:///android_asset/classic.css");
    }
}
