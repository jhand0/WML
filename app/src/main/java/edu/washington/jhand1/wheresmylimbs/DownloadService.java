package edu.washington.jhand1.wheresmylimbs;

import android.app.DownloadManager;
import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.preference.PreferenceManager;
import android.util.Log;

public class DownloadService extends IntentService {

    public static final String tag = "LimbsApp/Download";

    public DownloadService() {
        super("DownloadService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        // Check that user has internet
        ConnectivityManager cm = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        boolean isConnected = activeNetwork != null && activeNetwork.isConnectedOrConnecting();

        if (isConnected) {
            // Specify the url you want to download here
            SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
            String adventure = preferences.getString("adventure", null);

            Log.i(tag, "downloading from: " + adventure);

            // Start the download
            DownloadManager dm = (DownloadManager) getSystemService(DOWNLOAD_SERVICE);
            try {
                DownloadManager.Request request = new DownloadManager.Request(Uri.parse(adventure));
                dm.enqueue(request);
            } catch (IllegalArgumentException e) {
                Log.i(tag, "cannot download map from: " + adventure);
            }
        } else {
            Log.i(tag, "No connectivity");
        }
    }
}
