package edu.washington.jhand1.wheresmylimbs;

import android.app.Activity;
import android.content.Intent;
import android.preference.PreferenceManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class ActivityMain extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        PreferenceManager.setDefaultValues(this, R.xml.pref_settings, false);

        LimbsApp limbsApp = (LimbsApp) getApplication();

        Button btnPlay = (Button) findViewById(R.id.btnPlay);
        Button btnSettings = (Button) findViewById(R.id.btnSettings);

        btnPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent game = new Intent(ActivityMain.this, ActivityIntro.class);
                startActivity(game);
            }
        });

        btnSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent game = new Intent(ActivityMain.this, ActivitySettings.class);
                startActivity(game);
            }
        });
    }
}
