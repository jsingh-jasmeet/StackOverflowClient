package com.example.jasmeet.stackoverflowclient.Models;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

import org.apache.commons.lang3.StringEscapeUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Jasmeet on 3/21/2017.
 * <p>
 * Basic skeleton of Question class:
 * questionID is an integer referring to a unique ID of the question.
 * Score refers to the no. of downvotes received by the question subtracted from the no. of upvotes received by the question.
 * answerCount refers to number of answers available for the given question.
 * Title is a String variable referring to title of the question, i.e., the question itself.
 * AuthorDisplayName is a String variable referring to display name of the author of the question.
 * AuthorProfileImageURL refers to URL of the profile image of the author.
 */

public class Question implements Parcelable {


    public static final Parcelable.Creator<Question> CREATOR = new Parcelable.Creator<Question>() {
        @Override
        public Question createFromParcel(Parcel source) {
            return new Question(source);
        }

        @Override
        public Question[] newArray(int size) {
            return new Question[size];
        }
    };
    private long mQuestionID;
    private int mScore;
    private int mAnswerCount;
    private String mTitle;
    private String mAuthorDisplayName;
    private String mBody;
    private ArrayList<Answer> mAnswers;
    private String mQuestionLink;

    public Question(JSONObject questionObject) {

        try {

            this.mQuestionID = questionObject.getLong("question_id");
            this.mScore = questionObject.getInt("score");
            this.mAnswerCount = questionObject.getInt("answer_count");
            this.mTitle = questionObject.getString("title");
            this.mAuthorDisplayName = questionObject.optJSONObject("owner").optString("display_name");
            this.mBody = questionObject.optString("body");
            this.mQuestionLink = questionObject.getString("link");

            this.mTitle = StringEscapeUtils.unescapeXml(this.mTitle);
            this.mAuthorDisplayName = StringEscapeUtils.unescapeXml(this.mAuthorDisplayName);

            this.mAnswers = new ArrayList<>();

            JSONArray answersArray = questionObject.getJSONArray("answers");

            for (int j = 0; j < answersArray.length(); j++) {
                JSONObject answerObject = answersArray.getJSONObject(j);

                Answer answer = new Answer(answerObject);

                this.addAnswer(answer);
            }

        } catch (final JSONException e) {
            Log.e("Question.java", "Json parsing error: " + e.getMessage());
        }
    }

    private Question(Parcel in) {
        mQuestionID = in.readLong();
        mScore = in.readInt();
        mAnswerCount = in.readInt();
        mTitle = in.readString();
        mAuthorDisplayName = in.readString();
        mBody = in.readString();
        if (mAnswers == null) {
            mAnswers = new ArrayList<>();
        }
        in.readTypedList(mAnswers, Answer.CREATOR);
        mQuestionLink = in.readString();
    }

    public void addAnswer(Answer a) {
        mAnswers.add(a);
    }

    public long getQuestionID() {
        return mQuestionID;
    }

    public int getScore() {
        return mScore;
    }

    public int getAnswerCount() {
        return mAnswerCount;
    }

    public String getTitle() {
        return mTitle;
    }

    public String getAuthorDisplayName() {
        return mAuthorDisplayName;
    }

    public String getBody() {
        return mBody;
    }

    public String getQuestionLink() {
        return mQuestionLink;
    }

    public ArrayList<Answer> getAllAnswers() {

        this.getSortedAnswers();


        Log.v("Question", Integer.toString(mAnswers.size()));

        return mAnswers;
    }

    @Override
    public void writeToParcel(Parcel parcel, int flags) {
        parcel.writeLong(mQuestionID);
        parcel.writeInt(mScore);
        parcel.writeInt(mAnswerCount);
        parcel.writeString(mTitle);
        parcel.writeString(mAuthorDisplayName);
        parcel.writeString(mBody);
        parcel.writeTypedList(mAnswers);
        parcel.writeString(mQuestionLink);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public void getSortedAnswers() {

        if (mAnswers.size() != 0) {
            int number = mAnswers.size();
            quicksort(0, number - 1);
        }
    }

    private void quicksort(int low, int high) {
        int i = low, j = high;
        // Get the pivot element from the middle of the list
        int pivot = mAnswers.get(low + (high - low) / 2).getScore();

        // Divide into two lists
        while (i <= j) {
            // If the current value from the left list is smaller than the pivot
            // element then get the next element from the left list
            while (mAnswers.get(i).getScore() > pivot) {
                i++;
            }
            // If the current value from the right list is larger than the pivot
            // element then get the next element from the right list
            while (mAnswers.get(j).getScore() < pivot) {
                j--;
            }

            // If we have found a value in the left list which is larger than
            // the pivot element and if we have found a value in the right list
            // which is smaller than the pivot element then we exchange the
            // values.
            // As we are done we can increase i and j
            if (i <= j) {
                exchange(i, j);
                i++;
                j--;
            }
        }
        // Recursion
        if (low < j)
            quicksort(low, j);
        if (i < high)
            quicksort(i, high);
    }

    private void exchange(int i, int j) {
        Answer temp = mAnswers.get(j);
        mAnswers.remove(j);
        mAnswers.add(j, mAnswers.get(i));
        mAnswers.remove(i);
        mAnswers.add(i, temp);
    }
}
