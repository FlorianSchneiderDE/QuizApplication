package com.example.android.quizapplication;

import android.content.res.Resources;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    String[] buttonStatus = new String[4];
    int[] idList = new int[4];
    int currentQuestion = 0;
    Resources res;
    String[] questionType;
    String[] correctAnswers;

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
        for(int i=0;i<4;i++){
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
    }

    public void displaySolution(View v) {
        int[] answerNumber = new int[4];
        if (android.text.TextUtils.isDigitsOnly(correctAnswers[currentQuestion]))
        {
            for(int i=0;i<4;i++)
            {
                answerNumber[i] = Integer.parseInt(correctAnswers[currentQuestion].substring(i,i+1));
            }
        }
        for(int i=0;i<4;i++){
            if (buttonStatus[i].equals("active")) {
                if (answerNumber[i]==1)
                    buttonStatus[i] = "correct";
                else
                    buttonStatus[i] = "wrong";
            }
        }
        updateUI(v);

    }
}
