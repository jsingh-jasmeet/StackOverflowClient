package com.example.jasmeet.stackoverflowclient;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

import org.apache.commons.lang3.StringEscapeUtils;
import org.unbescape.csv.CsvEscape;

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

    public Question(long questionID, int score, int answerCount, String title, String authorDisplayName) {
        mQuestionID = questionID;
        mScore = score;
        mAnswerCount = answerCount;
        mTitle = StringEscapeUtils.unescapeHtml4(title);
        mAuthorDisplayName = StringEscapeUtils.unescapeHtml4(authorDisplayName);
        mBody = "";
        mAnswers = new ArrayList<>();
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

    public void setBody(String body) {
        mBody = CsvEscape.unescapeCsv(body);
        Log.v("Question: ", mBody);
    }

    public Answer getAnswerAt(int pos) {
        if (pos < mAnswers.size())
            return mAnswers.get(pos);
        else
            return null;
    }

    public ArrayList<Answer> getAllAnswers() {
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

    }

    @Override
    public int describeContents() {
        return 0;
    }
}
