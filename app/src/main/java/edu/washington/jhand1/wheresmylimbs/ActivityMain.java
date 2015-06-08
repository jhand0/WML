package edu.washington.jhand1.wheresmylimbs;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DownloadManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.ParcelFileDescriptor;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.io.FileInputStream;
import java.io.IOException;

public class ActivityMain extends Activity {

    public static final String tag = "LimbsApp/ActivityMain";
    private DownloadManager dm;
    LimbsApp limbsApp;
    Button btnPlay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        limbsApp = (LimbsApp) getApplication();

        PreferenceManager.setDefaultValues(this, R.xml.pref_settings, false);

        btnPlay = (Button) findViewById(R.id.btnPlay);
        Button btnSettings = (Button) findViewById(R.id.btnSettings);

        btnPlay.setText(limbsApp.getAdventureTitle());
        btnPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intro = new Intent(ActivityMain.this, ActivityIntro.class);
                startActivity(intro);
            }
        });

        btnSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent game = new Intent(ActivityMain.this, ActivitySettings.class);
                startActivity(game);
            }
        });

        dm = (DownloadManager) getSystemService(DOWNLOAD_SERVICE);
    }

    @Override
    protected void onResume() {
        super.onResume();
        limbsApp.createRepo();
        btnPlay.setText(limbsApp.getAdventureTitle());
        broadcastReceiver();
    }

    @Override
    protected void onPause() {
        super.onPause();
        try {
            unregisterReceiver(receiver);
        } catch (IllegalArgumentException e) {
            // Do nothing
        }
    }

    private void broadcastReceiver() {
        // Make sure url is set
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        String adventure = preferences.getString("adventure", null);

        // Download new map
        if (adventure != null && !adventure.equals("tutorial")) {
            // Register receiver to listen for completed downloads
            Log.i(tag, "Download listener fired up.");
            IntentFilter filter = new IntentFilter();
            filter.addAction(DownloadManager.ACTION_DOWNLOAD_COMPLETE);
            registerReceiver(receiver, filter);

            // Start download for new map
            Log.i(tag, "Starting download service...");
            Intent downloadMap = new Intent(getBaseContext(), DownloadService.class);
            startService(downloadMap);
        }
    }

    // This is your receiver that you registered in the onCreate that will receive any messages
    // that match a download-complete like broadcast
    public final BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            Log.i(tag, "onReceive of registered download receiver");

            if (DownloadManager.ACTION_DOWNLOAD_COMPLETE.equals(action)) {
                Log.i(tag, "download complete!");
                long downloadID = intent.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, 0);

                // if the downloadID exists
                if (downloadID != 0) {

                    // Check status
                    DownloadManager.Query query = new DownloadManager.Query();
                    query.setFilterById(downloadID);
                    Cursor c = dm.query(query);
                    if(c.moveToFirst()) {
                        int status = c.getInt(c.getColumnIndex(DownloadManager.COLUMN_STATUS));
                        Log.d(tag, "Status Check: " + status);
                        switch(status) {
                            case DownloadManager.STATUS_PAUSED:
                            case DownloadManager.STATUS_PENDING:
                            case DownloadManager.STATUS_RUNNING:
                                break;
                            case DownloadManager.STATUS_SUCCESSFUL:
                                // The download-complete message said the download was "successful"
                                // then run this code
                                ParcelFileDescriptor file;

                                try {
                                    // Get file from Download Manager
                                    file = dm.openDownloadedFile(downloadID);
                                    FileInputStream fis = new FileInputStream(file.getFileDescriptor());

                                    // Get json string from fis
                                    LimbsApp limbsApp = (LimbsApp) getApplication();
                                    String json = limbsApp.loadJSON(fis);

                                    // Test valid json
                                    if (limbsApp.createRepo(json)) {
                                        // Write json string to file
                                        limbsApp.writeToFile(json);
                                    }
                                    btnPlay.setText(limbsApp.getAdventureTitle());
                                    Log.i(tag, "Download finished.");
                                } catch (IOException e) {
                                    e.printStackTrace();
                                } catch (Exception e) {
                                    Log.i(tag, "Map parsing failed.");
                                    Toast.makeText(ActivityMain.this, "We forgot where we " +
                                                    "put your limbs. Please choose a different" +
                                                    "map.",
                                            Toast.LENGTH_SHORT).show();
                                }
                                break;
                            case DownloadManager.STATUS_FAILED:
                                Log.i(tag, "Download failed.");
                                Toast.makeText(ActivityMain.this, "Couldn't download the map. " +
                                                "Please refresh to try again.",
                                        Toast.LENGTH_SHORT).show();
                                break;
                        }
                    }
                }
            }
        }
    };
}
