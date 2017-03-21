package com.example.jasmeet.stackoverflowclient;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private static String TAG = MainActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        ArrayList<Question> questions = new ArrayList<>();

        questions.add(new Question(1, 2, 3, "Question 1 Example", "username1"));
        questions.add(new Question(2, 3, 4, "Question 2 Example", "username2"));
        questions.add(new Question(3, 4, 5, "Question 3 Example", "username3"));
        questions.add(new Question(4, 5, 6, "Question 4 Example", "username4"));
        questions.add(new Question(5, 6, 7, "Question 5 Example", "username5"));

        ListView questionListView = (ListView) findViewById(R.id.question_list_view);
        MyQuestionAdapter adapter = new MyQuestionAdapter(this, questions);
        questionListView.setAdapter(adapter);

    }
}
