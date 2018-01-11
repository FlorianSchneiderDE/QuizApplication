package com.example.android.quizapplication;

import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    String[] buttonStatus = new String[4];
    int[] idList = new int[4];
    int currentQuestion = 0;
    int numberOfQuestions;
    Resources res;
    String[] questionType;
    String[] correctAnswers;
    String[] questions;
    String[] answersA,answersB,answersC,answersD;
    String solutionState;
    TypedArray images;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        for(int i=0;i<4;i++){
            buttonStatus[i] = "none";
        }
        idList[0] = R.id.buttonAnswer1;
        idList[1] = R.id.buttonAnswer2;
        idList[2] = R.id.buttonAnswer3;
        idList[3] = R.id.buttonAnswer4;

        res = getResources();
        questionType = res.getStringArray(R.array.questionType);
        correctAnswers = res.getStringArray(R.array.correctAnswers);
        questions = res.getStringArray(R.array.questions);
        answersA = res.getStringArray(R.array.answersA);
        answersB = res.getStringArray(R.array.answersB);
        answersC = res.getStringArray(R.array.answersC);
        answersD = res.getStringArray(R.array.answersD);
        solutionState = "solution";
        numberOfQuestions = correctAnswers.length;
        images = res.obtainTypedArray(R.array.pictures);
    }

    public void changeStatusOfButton(View v) {
        // switch status of Button between active and none
        for(int i=0;i<4;i++){
            if(v.getId()==idList[i]) {
                buttonStatus[i] = buttonStatus[i].equals("active") ? "none" : "active";
            }
            else{
                if (questionType[currentQuestion].equals("singleChoice")){
                    // deactivate button since only one button (v.getID()) can be active for a singleChoice question
                    buttonStatus[i] = "none";
                }
            }

        }
        updateUI(v);
    }

    public void updateUI(View v) {
        //check if currentQuestion > numberQuestions
        if (currentQuestion<numberOfQuestions) {
            ((ImageView) findViewById(R.id.topImage)).setImageDrawable(images.getDrawable(currentQuestion));
            // Update question and button resources
            String[] currentAnswers = new String[4];
            currentAnswers[0] = answersA[currentQuestion];
            currentAnswers[1] = answersB[currentQuestion];
            currentAnswers[2] = answersC[currentQuestion];
            currentAnswers[3] = answersD[currentQuestion];

            for (int i = 0; i < 4; i++) {
                Button answerButton = findViewById(idList[i]);
                answerButton.setText(currentAnswers[i]);
            }
            TextView questionTextView = findViewById(R.id.questionText);
            questionTextView.setText(questions[currentQuestion]);

            // Update button shape
            for (int i = 0; i < 4; i++) {
                switch (buttonStatus[i]) {
                    case "none":
                        (findViewById(idList[i])).setBackgroundResource(R.drawable.buttonshape);
                        break;
                    case "active":
                        (findViewById(idList[i])).setBackgroundResource(R.drawable.buttonshapeactive);
                        break;
                    case "correct":
                        (findViewById(idList[i])).setBackgroundResource(R.drawable.buttonshapecorrect);
                        break;
                    case "wrong":
                        (findViewById(idList[i])).setBackgroundResource(R.drawable.buttonshapewrong);
                        break;
                }
            }

            //Define solution button string
            Button solutionButton = findViewById(R.id.buttonSolution); //cast View to Button
            if (solutionState.equals("next")) {
                solutionButton.setText(R.string.next);
            } else {
                solutionButton.setText(R.string.solution);
            }
        }
    }

    public void displaySolution(View v) {
        //check if currentQuestion < numberQuestions => show questions
        if (currentQuestion<numberOfQuestions) {
            if (solutionState.equals("solution")) {
                int[] answerNumber = new int[4];
                //Single choice or multiple choice question
                if (android.text.TextUtils.isDigitsOnly(correctAnswers[currentQuestion])) {
                    for (int i = 0; i < 4; i++) {
                        answerNumber[i] = Integer.parseInt(correctAnswers[currentQuestion].substring(i, i + 1));
                    }
                    for (int i = 0; i < 4; i++) {
                        if (buttonStatus[i].equals("active")) {
                            if (answerNumber[i] == 1)
                                buttonStatus[i] = "correct";
                            else
                                buttonStatus[i] = "wrong";
                        }
                        (findViewById(idList[i])).setClickable(false);
                    }
                }
                //Free answer mode
                else {

                }
                solutionState = "next";
            } else {
                //Move to next question
                currentQuestion +=1;
                for(int i=0;i<4;i++) {
                    buttonStatus[i] = "none"; //reset all buttons
                    (findViewById(idList[i])).setClickable(true);
                }
                solutionState = "solution";
            }
        }
        //=> show evaluation
        else
        {

        }
        updateUI(v);

    }
}
