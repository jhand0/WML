package edu.washington.jhand1.wheresmylimbs;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.List;

public class ActivityGame extends Activity {

    LimbsApp limbsApp;
    List<Item> items;

    TextView txtTurns;
    TextView txtItem1;
    TextView txtItem2;
    TextView txtItem3;
    TextView txtItem4;
    TextView txtIcon1;
    TextView txtIcon2;
    TextView txtIcon3;
    TextView txtIcon4;
    Button btnNorth;
    Button btnEast;
    Button btnSouth;
    Button btnWest;

    // Custom fragment animations
    int animIn = 0;
    int animOut = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        limbsApp = (LimbsApp) getApplication();

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        limbsApp.setDifficulty(Integer.parseInt(preferences.getString("difficulty", null)));
        items = limbsApp.getItems();

        txtTurns = (TextView) findViewById(R.id.txtTurns);
        txtItem1 = (TextView) findViewById(R.id.txtItem1);
        txtItem2 = (TextView) findViewById(R.id.txtItem2);
        txtItem3 = (TextView) findViewById(R.id.txtItem3);
        txtItem4 = (TextView) findViewById(R.id.txtItem4);
        txtIcon1 = (TextView) findViewById(R.id.txtIcon1);
        txtIcon2 = (TextView) findViewById(R.id.txtIcon2);
        txtIcon3 = (TextView) findViewById(R.id.txtIcon3);
        txtIcon4 = (TextView) findViewById(R.id.txtIcon4);
        btnNorth = (Button) findViewById(R.id.btnNorth);
        btnEast = (Button) findViewById(R.id.btnEast);
        btnSouth = (Button) findViewById(R.id.btnSouth);
        btnWest = (Button) findViewById(R.id.btnWest);

        update();

        btnNorth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateAnim(Direction.NORTH);
                limbsApp.move(Direction.NORTH);
                update();
            }
        });

        btnEast.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateAnim(Direction.EAST);
                limbsApp.move(Direction.EAST);
                update();
            }
        });

        btnSouth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateAnim(Direction.SOUTH);
                limbsApp.move(Direction.SOUTH);
                update();
            }
        });

        btnWest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateAnim(Direction.WEST);
                limbsApp.move(Direction.WEST);
                update();
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        limbsApp.createRepo();
    }

    private void update() {
        if (limbsApp.allItemsCollected()) {
            Intent end = new Intent(ActivityGame.this, ActivityEnd.class);
            end.putExtra("win", true);
            startActivity(end);
            finish();
        } else if (limbsApp.movesLeft() <= 0) {
            Intent end = new Intent(ActivityGame.this, ActivityEnd.class);
            end.putExtra("win", false);
            startActivity(end);
            finish();
        } else {
            txtTurns.setText("" + limbsApp.movesLeft());
            updateItems();
            updateButtons();
            Fragment room = FragmentRoom.newInstance(limbsApp.getRoomTitle(),
                    limbsApp.getRoomDescription(), limbsApp.getRoomUpdate());
            getFragmentManager().beginTransaction()
                    .setCustomAnimations(animIn, animOut)
                    .replace(R.id.container, room)
                    .commit();
        }
    }

    // Set animations for fragment transition
    private void updateAnim(Direction direction) {
        switch(direction) {
            case NORTH:
                animIn = R.animator.slide_in_top;
                animOut = R.animator.slide_out_bottom;
                break;
            case EAST:
                animIn = R.animator.slide_in_right;
                animOut = R.animator.slide_out_left;
                break;
            case SOUTH:
                animIn = R.animator.slide_in_bottom;
                animOut = R.animator.slide_out_top;
                break;
            case WEST:
                animIn = R.animator.slide_in_left;
                animOut = R.animator.slide_out_right;
                break;
        }
    }

    private void updateItems() {
        if (items.get(0).isCollected()) {
            txtItem1.setVisibility(View.GONE);
            txtIcon1.setText(items.get(0).getArt());
        } else {
            txtItem1.setText(items.get(0).getArt());
            txtIcon1.setText("");
        }
        if (items.get(1).isCollected()) {
            txtItem2.setVisibility(View.GONE);
            txtIcon2.setText(items.get(1).getArt());
        } else {
            txtItem2.setText(items.get(1).getArt());
            txtIcon2.setText("");
        }
        if (items.get(2).isCollected()) {
            txtItem3.setVisibility(View.GONE);
            txtIcon3.setText(items.get(2).getArt());
        } else {
            txtItem3.setText(items.get(2).getArt());
            txtIcon3.setText("");
        }
        if (items.get(3).isCollected()) {
            txtItem4.setVisibility(View.GONE);
            txtIcon4.setText(items.get(3).getArt());
        } else {
            txtItem4.setText(items.get(3).getArt());
            txtIcon4.setText("");
        }
    }

    private void updateButtons() {
        btnNorth.setEnabled(limbsApp.canMove(Direction.NORTH));
        if (limbsApp.canMove(Direction.NORTH)) {
            btnNorth.setTextColor(getResources().getColor(R.color.btnTextEnable));
            btnNorth.setBackgroundColor(getResources().getColor(R.color.btnBackgroundEnable));
        } else {
            btnNorth.setTextColor(getResources().getColor(R.color.btnTextDisable));
            btnNorth.setBackgroundColor(getResources().getColor(R.color.btnBackgroundDisable));
        }
        btnEast.setEnabled(limbsApp.canMove(Direction.EAST));
        if (limbsApp.canMove(Direction.EAST)) {
            btnEast.setTextColor(getResources().getColor(R.color.btnTextEnable));
            btnEast.setBackgroundColor(getResources().getColor(R.color.btnBackgroundEnable));
        } else {
            btnEast.setTextColor(getResources().getColor(R.color.btnTextDisable));
            btnEast.setBackgroundColor(getResources().getColor(R.color.btnBackgroundDisable));
        }
        btnSouth.setEnabled(limbsApp.canMove(Direction.SOUTH));
        if (limbsApp.canMove(Direction.SOUTH)) {
            btnSouth.setTextColor(getResources().getColor(R.color.btnTextEnable));
            btnSouth.setBackgroundColor(getResources().getColor(R.color.btnBackgroundEnable));
        } else {
            btnSouth.setTextColor(getResources().getColor(R.color.btnTextDisable));
            btnSouth.setBackgroundColor(getResources().getColor(R.color.btnBackgroundDisable));
        }
        btnWest.setEnabled(limbsApp.canMove(Direction.WEST));
        if (limbsApp.canMove(Direction.WEST)) {
            btnWest.setTextColor(getResources().getColor(R.color.btnTextEnable));
            btnWest.setBackgroundColor(getResources().getColor(R.color.btnBackgroundEnable));
        } else {
            btnWest.setTextColor(getResources().getColor(R.color.btnTextDisable));
            btnWest.setBackgroundColor(getResources().getColor(R.color.btnBackgroundDisable));
        }
    }
}
