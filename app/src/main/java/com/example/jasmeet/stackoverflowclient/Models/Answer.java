package com.example.jasmeet.stackoverflowclient.Models;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Jasmeet on 3/22/2017.
 */

public class Answer implements Parcelable {
    public static final Parcelable.Creator<Answer> CREATOR = new Parcelable.Creator<Answer>() {
        @Override
        public Answer createFromParcel(Parcel source) {
            return new Answer(source);
        }

        @Override
        public Answer[] newArray(int size) {
            return new Answer[size];
        }
    };
    private long mAnswerID;
    private int mScore;
    private boolean mIsAccepted;
    private String mBody;
    private String mAuthorDisplayName;

    public Answer(long answerID, int score, boolean isAccepted, String body, String authorDisplayName) {
        mAnswerID = answerID;
        mScore = score;
        mIsAccepted = isAccepted;
        mBody = body;
        mAuthorDisplayName = authorDisplayName;
    }

    private Answer(Parcel in) {
        mAnswerID = in.readLong();
        mScore = in.readInt();
        mIsAccepted = in.readByte() != 0;
        mBody = in.readString();
        mAuthorDisplayName = in.readString();
    }

    public long getAnswerID() {
        return mAnswerID;
    }

    public int getScore() {
        return mScore;
    }

    public boolean isAccepted() {
        return mIsAccepted;
    }

    public String getBody() {
        return mBody;
    }

    public String getAuthorDisplayName() {
        return mAuthorDisplayName;
    }

    @Override
    public void writeToParcel(Parcel parcel, int flags) {
        parcel.writeLong(mAnswerID);
        parcel.writeInt(mScore);
        parcel.writeByte((byte) (mIsAccepted ? 1 : 0));
        parcel.writeString(mBody);
        parcel.writeString(mAuthorDisplayName);
    }

    @Override
    public int describeContents() {
        return 0;
    }
}
