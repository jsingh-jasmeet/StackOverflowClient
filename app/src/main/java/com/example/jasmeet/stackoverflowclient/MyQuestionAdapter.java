package com.example.jasmeet.stackoverflowclient;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.jasmeet.stackoverflowclient.Models.Question;

import java.util.ArrayList;

/**
 * Created by Jasmeet on 3/22/2017.
 * <p>
 * The constructor takes ArrayList of type Question.
 */

public class MyQuestionAdapter extends ArrayAdapter<Question> {

    public MyQuestionAdapter(Activity context, ArrayList<Question> questions) {
        super(context, 0, questions);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        View listItemView = convertView;

        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(R.layout.question_list_item_layout, parent, false);
        }

        Question currentQuestion = getItem(position);

        TextView scoreTextView = (TextView) listItemView.findViewById(R.id.question_list_item_score);
        TextView answerCountTextView = (TextView) listItemView.findViewById(R.id.question_list_item_answer_count);
        TextView questionTitleTextView = (TextView) listItemView.findViewById(R.id.question_list_item_title);
        TextView authorDisplayNameTextView = (TextView) listItemView.findViewById(R.id.question_list_item_display_name);

        scoreTextView.setText(Integer.toString(currentQuestion.getScore()));
        answerCountTextView.setText(Integer.toString(currentQuestion.getAnswerCount()));
        questionTitleTextView.setText(currentQuestion.getTitle());
        authorDisplayNameTextView.setText(currentQuestion.getAuthorDisplayName());

        return listItemView;
    }
}
