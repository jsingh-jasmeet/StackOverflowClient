package com.example.jasmeet.stackoverflowclient;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Jasmeet on 3/23/2017.
 */

public class MyAnswerAdapter extends ArrayAdapter<Answer> {

    public MyAnswerAdapter(Activity context, ArrayList<Answer> answers) {
        super(context, 0, answers);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        View listItemView = convertView;

        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(R.layout.answer_list_item_layout, parent, false);
        }

        Answer currentAnswer = getItem(position);

        TextView authorDisplayNameTextView = (TextView) listItemView.findViewById(R.id.answer_list_item_display_name);
        TextView scoreTextView = (TextView) listItemView.findViewById(R.id.answer_list_item_score);
        TextView bodyTextView = (TextView) listItemView.findViewById(R.id.answer_list_item_body);
        ImageView isAcceptedImageView = (ImageView) listItemView.findViewById(R.id.answer_list_item_accepted);

        if (currentAnswer.getAuthorDisplayName() != null)
            authorDisplayNameTextView.setText(currentAnswer.getAuthorDisplayName());
        else
            authorDisplayNameTextView.setText("user_not_found");
        scoreTextView.setText(Integer.toString(currentAnswer.getScore()));
        bodyTextView.setText(currentAnswer.getBody());
        if (currentAnswer.isAccepted()) {
            isAcceptedImageView.setVisibility(View.VISIBLE);
        }

        return listItemView;
    }
}
