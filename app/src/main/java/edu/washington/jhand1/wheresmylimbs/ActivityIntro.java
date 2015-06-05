package edu.washington.jhand1.wheresmylimbs;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class ActivityIntro extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro);

        LimbsApp limbsApp = (LimbsApp) getApplication();

        TextView txtIntro = (TextView) findViewById(R.id.txtIntro);
        Button btnContinue = (Button) findViewById(R.id.btnContinue);

        txtIntro.setText(limbsApp.getIntro());

        btnContinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent game = new Intent(ActivityIntro.this, ActivityGame.class);
                startActivity(game);
                finish();
            }
        });
    }
}
