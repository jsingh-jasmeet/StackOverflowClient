package com.example.jasmeet.stackoverflowclient;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

public class ViewQuestionActivity extends AppCompatActivity {

    private Question question;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_question);

        Intent intent = getIntent();
        question = intent.getParcelableExtra("question");

        setUpViews();

    }

    private void setUpViews(){
        TextView displayNameTextView = (TextView) findViewById(R.id.view_question_display_name);
        TextView titleTextView = (TextView) findViewById(R.id.view_question_title);
        TextView scoreTextView = (TextView) findViewById(R.id.view_question_score);
        TextView answerCountTextView = (TextView) findViewById(R.id.view_question_answer_count);
        TextView bodyTextView = (TextView) findViewById(R.id.view_question_body);

        displayNameTextView.setText(question.getAuthorDisplayName());
        titleTextView.setText(question.getTitle());
        scoreTextView.setText(Integer.toString(question.getScore()));
        answerCountTextView.setText(Integer.toString(question.getAnswerCount()));
        bodyTextView.setText(question.getBody());
    }
}
