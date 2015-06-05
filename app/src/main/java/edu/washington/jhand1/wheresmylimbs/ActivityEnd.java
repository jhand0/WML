package edu.washington.jhand1.wheresmylimbs;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.List;

public class ActivityEnd extends Activity {

    TextView txtIcon1;
    TextView txtIcon2;
    TextView txtIcon3;
    TextView txtIcon4;
    List<Item> items;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_end);

        LimbsApp limbsApp = (LimbsApp) getApplication();
        boolean win = getIntent().getBooleanExtra("win", false);
        items = limbsApp.getItems();

        txtIcon1 = (TextView) findViewById(R.id.txtIcon1);
        txtIcon2 = (TextView) findViewById(R.id.txtIcon2);
        txtIcon3 = (TextView) findViewById(R.id.txtIcon3);
        txtIcon4 = (TextView) findViewById(R.id.txtIcon4);
        TextView txtWinLose = (TextView) findViewById(R.id.txtWinLose);
        TextView txtMessage = (TextView) findViewById(R.id.txtMessage);
        Button btnRestart = (Button) findViewById(R.id.btnRestart);
        Button btnMainMenu = (Button) findViewById(R.id.btnMenu);

        if (win) {
            txtWinLose.setText("YOU WIN");
            txtMessage.setText(limbsApp.getVictoryMessage());
        } else {
            txtWinLose.setText("YOU LOSE");
            txtMessage.setText(limbsApp.getDeathMessage());
        }

        updateItems();
        limbsApp.createRepo();

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

    private void updateItems() {
        txtIcon1.setText(items.get(0).getArt());
        txtIcon2.setText(items.get(1).getArt());
        txtIcon3.setText(items.get(2).getArt());
        txtIcon4.setText(items.get(3).getArt());

        if (!items.get(0).isCollected()) {
            txtIcon1.setTextColor(Color.RED);
        }
        if (!items.get(1).isCollected()) {
            txtIcon2.setTextColor(Color.RED);
        }
        if (!items.get(2).isCollected()) {
            txtIcon3.setTextColor(Color.RED);
        }
        if (!items.get(3).isCollected()) {
            txtIcon4.setTextColor(Color.RED);
        }
    }
}
