package com.dv.tictactoe;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView score1, score2;
    private Button [] buttons = new Button[9];
    private Button reset;


    private int p1, p2, roundCount;
    boolean activePlayer;

    //p1 => 0
    //p2 => 1
    //empty => 2

    int [] gameState = {2,2,2,2,2,2,2,2,2};

    int [][] winningPositions = {
            {0,1,2}, {3,4,5}, {6,7,8}, //rows
            {0,3,6}, {1,4,7}, {2,5,8}, //columns
            {0,4,8}, {2,4,6} //cross

    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        score1 = findViewById(R.id.score1);
        score2 = findViewById(R.id.score2);

        reset = findViewById(R.id.reset);

        for(int i=0;i< buttons.length; i++){
            String buttonID = "btn" + (i+1);
            int resourceId = getResources().getIdentifier(buttonID, "id", getPackageName());
            buttons[i] = findViewById(resourceId);
            buttons[i].setOnClickListener(this);
        }

        p1 = 0;
        p2 = 0;
        activePlayer = true;
    }

    @Override
    public void onClick(View v) {


        if(!((Button)v).getText().toString().equals("")){
            return;
        }
        String buttonID = v.getResources().getResourceEntryName(v.getId()); //btn_2
        int gamePointer = Integer.parseInt(buttonID.substring(buttonID.length()-1, buttonID.length())); //2 , btn_ will be trimmed

        if(activePlayer){
            ((Button) v).setText("X");
            gameState[gamePointer] = 0;
        }else{
            ((Button) v).setText("O");
            gameState[gamePointer] = 1;
        }

        roundCount++;

        if(checkWinner()){
            if(activePlayer){
                p1++;
                updatePlayerScore();
                Toast.makeText(this, "Player One Won!", Toast.LENGTH_SHORT).show();

            }else {
                p2++;
                updatePlayerScore();
                Toast.makeText(this, "Player Two Won!", Toast.LENGTH_SHORT).show();
            }
        }else if(roundCount == 9){
            reset();
            Toast.makeText(this, "No Winner!!!", Toast.LENGTH_SHORT).show();
        }else{
            activePlayer = !activePlayer;
        }

        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reset();

                score1.setText(Integer.toString(0));
                score2.setText(Integer.toString(0));

            }
        });
    }

    public boolean checkWinner(){
        boolean winner = false;

        for(int [] winningPosition : winningPositions){
            if(gameState[winningPosition[0]] == gameState[winningPosition[1]] &&
                    gameState[winningPosition[1]] == gameState[winningPosition[2]] &&
                    gameState[winningPosition[0]] != 2){
                winner = true;
            }
        }
        return winner;
    }

    public void updatePlayerScore(){
        score1.setText(Integer.toString(p1));
        score2.setText(Integer.toString(p2));
        reset();
    }

    public void reset(){
         activePlayer = true;

         for(int i=0;i< buttons.length;i++){
             gameState[i] = 2;
             buttons[i].setText("");
         }

    }
}