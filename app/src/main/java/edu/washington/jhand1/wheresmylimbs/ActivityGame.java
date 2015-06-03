package edu.washington.jhand1.wheresmylimbs;

import android.app.Activity;
import android.os.Bundle;

public class ActivityGame extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        /* Gray out button
        btnSubmit.setEnabled(true);
         */

        // Kill (finish) activity before showing end screen
    }
}
