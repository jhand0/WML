package edu.washington.jhand1.wheresmylimbs;

import android.app.Activity;
import android.content.Intent;
import android.preference.PreferenceManager;
import android.os.Bundle;

public class ActivityMain extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        PreferenceManager.setDefaultValues(this, R.xml.pref_settings, false);

        LimbsApp limbsApp = (LimbsApp) getApplication();

        /* Starting settings activity
        Intent settings =  new Intent(ActivityMain.this, ActivitySettings.class);
        startActivity(settings);
         */

        /* Gray out button
        btnSubmit.setEnabled(true);
         */

    }
}
