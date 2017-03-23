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

import com.example.jasmeet.stackoverflowclient.Models.Answer;

import java.util.ArrayList;

import us.feras.mdv.MarkdownView;

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
        MarkdownView bodyTextView = (MarkdownView) listItemView.findViewById(R.id.answer_list_item_body);
        ImageView isAcceptedImageView = (ImageView) listItemView.findViewById(R.id.answer_list_item_accepted);

        if (currentAnswer.getAuthorDisplayName() != null)
            authorDisplayNameTextView.setText(currentAnswer.getAuthorDisplayName());
        else
            authorDisplayNameTextView.setText(R.string.unknown_user);
        scoreTextView.setText(Integer.toString(currentAnswer.getScore()));
        bodyTextView.loadMarkdown(currentAnswer.getBody(), "file:///android_asset/classic.css");
        if (currentAnswer.isAccepted()) {
            isAcceptedImageView.setVisibility(View.VISIBLE);
        } else {
            isAcceptedImageView.setVisibility(View.GONE);
        }

        return listItemView;
    }
}
