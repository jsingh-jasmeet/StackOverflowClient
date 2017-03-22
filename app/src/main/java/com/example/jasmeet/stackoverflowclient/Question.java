package com.example.jasmeet.stackoverflowclient;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

import org.apache.commons.lang3.StringEscapeUtils;
import org.unbescape.csv.CsvEscape;

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

public class Question implements Parcelable{
    private int mQuestionID;
    private int mScore;
    private int mAnswerCount;
    private String mTitle;
    private String mAuthorDisplayName;
    private String mBody;

    public Question(int questionID, int score, int answerCount, String title, String authorDisplayName) {
        mQuestionID = questionID;
        mScore = score;
        mAnswerCount = answerCount;
        mTitle = StringEscapeUtils.unescapeHtml4(title);
        mAuthorDisplayName = StringEscapeUtils.unescapeHtml4(authorDisplayName);
        mBody = "";
    }

    public void setBody(String body) {
        mBody = CsvEscape.unescapeCsv(body);
        Log.v("Question: ", mBody);
    }

    public int getQuestionID() {
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

    @Override
    public void writeToParcel(Parcel parcel, int flags) {
        parcel.writeInt(mQuestionID);
        parcel.writeInt(mScore);
        parcel.writeInt(mAnswerCount);
        parcel.writeString(mTitle);
        parcel.writeString(mAuthorDisplayName);
        parcel.writeString(mBody);

    }

    private Question(Parcel in) {
        mQuestionID = in.readInt();
        mScore = in.readInt();
        mAnswerCount = in.readInt();
        mTitle = in.readString();
        mAuthorDisplayName = in.readString();
        mBody = in.readString();
    }

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

    @Override
    public int describeContents() {
        // TODO Auto-generated method stub
        return 0;
    }
}
