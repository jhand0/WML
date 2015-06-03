package edu.washington.jhand1.wheresmylimbs;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import org.apache.http.util.ByteArrayBuffer;
import android.util.Log;

/**
 * Created by artem on 6/3/15.
 */
public class MapDownloadManager {

    private final String PATH = "/data/data/com.helloandroid.imagedownloader/";

    /**
     * Downloads a map
     *
     * @param mapURL The url of the map to download
     * @param fileName The downloaded file will be saved as PATH/fileName
     * @return true if download was successful, false otherwise
     */
    public boolean DownloadFromUrl(String mapURL, String fileName) {
        try {
            URL url = new URL(mapURL);
            File file = new File(fileName);

            long startTime = System.currentTimeMillis();
            Log.d("MapDownloadManager", "download begining");
            Log.d("MapDownloadManager", "download url:" + url);
            Log.d("MapDownloadManager", "downloaded file name:" + fileName);

            // Open a connection to that URL
            URLConnection connection = url.openConnection();

            // Define InputStreams to read from the URLConnection
            InputStream is = connection.getInputStream();
            BufferedInputStream bis = new BufferedInputStream(is);

            // Read bytes to the Buffer until there is nothing more to read (-1)
            ByteArrayBuffer baf = new ByteArrayBuffer(50);
            int current = 0;
            while ((current = bis.read()) != -1) {
                baf.append((byte) current);
            }

            // Convert the Bytes read to a String
            FileOutputStream fos = new FileOutputStream(file);
            fos.write(baf.toByteArray());
            fos.close();
            Log.d("MapDownloadManager", "download ready in"
                    + ((System.currentTimeMillis() - startTime) / 1000)
                    + " sec");
            return true;
        } catch (IOException e) {
            Log.d("MapDownloadManager", "Error: " + e);
            return false;
        }
    }
}
