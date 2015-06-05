package edu.washington.jhand1.wheresmylimbs;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class ActivityEnd extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_end);



        boolean win = getIntent().getBooleanExtra("win", false);

        LimbsApp limbsApp = (LimbsApp) getApplication();

        TextView txtImage = (TextView) findViewById(R.id.txtImage);
        TextView txtWinLose = (TextView) findViewById(R.id.txtWinLose);
        TextView txtMessage = (TextView) findViewById(R.id.txtMessage);
        Button btnRestart = (Button) findViewById(R.id.btnRestart);
        Button btnMainMenu = (Button) findViewById(R.id.btnMenu);

        if (win) {
            txtImage.setText(":)");
            txtWinLose.setText("YOU WIN");
            txtMessage.setText(limbsApp.getVictoryMessage());
        } else {
            txtImage.setText(":(");
            txtWinLose.setText("YOU LOSE");
            txtMessage.setText(limbsApp.getDeathMessage());
        }

        btnRestart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent game = new Intent(ActivityEnd.this, ActivityIntro.class);
                startActivity(game);
                finish();
            }
        });

        btnMainMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent game = new Intent(ActivityEnd.this, ActivityMain.class);
                startActivity(game);
                finish();
            }
        });
    }
}
