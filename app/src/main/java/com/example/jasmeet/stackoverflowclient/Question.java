package com.example.jasmeet.stackoverflowclient;

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

public class Question {
    private int mQuestionID;
    private int mScore;
    private int mAnswerCount;
    private String mTitle;
    private String mAuthorDisplayName;

    public Question(int questionID, int score, int answerCount, String title, String authorDisplayName) {
        mQuestionID = questionID;
        mScore = score;
        mAnswerCount = answerCount;
        mTitle = title;
        mAuthorDisplayName = authorDisplayName;
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
}
