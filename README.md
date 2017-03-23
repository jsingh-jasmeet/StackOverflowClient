# StackOverflowClient
This is a simple Stack Overflow client Android app composed of two activities.

The MainActivity includes a search field, a search button, and a listview below the two. 

Entering a query in the search field and hitting the search button retrieves the top ten questions from Stack Overflow (sorted by the score of each question). Users can scroll to the bottom of the list and press a button to retreive ten more results.

Each result includes the question title, the score of each question, the number of answers on each question, and the author of the question. Tapping a result will take the user to the ViewQuestionActivity.

In the ViewQuestionActivity, users can read the body of the question, as well as all answers to that question. Each Answer displays the answer score, the body of the answer, and the author of the answer.
