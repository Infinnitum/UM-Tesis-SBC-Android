package edu.tesis.matias.tesis2015;

import android.os.AsyncTask;
import android.util.Log;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.net.URL;

public class UpdateGetFile extends AsyncTask<String, Void, Void> {
    protected Void doInBackground(String... urls) {
        String url = urls[0];

        Log.i("TEST", url);
        try {
            Log.i("TEST", "url 0");
            URL link = new URL(url);
            Log.i("TEST", "url 1");
            BufferedReader in = new BufferedReader(new InputStreamReader(link.openStream()));

            Log.i("TEST", "url 2");
            String inputLine;

            Log.i("TEST", "url 3");
            String f = url.split("/")[url.split("/").length-1];
            File file = new File(Archivo.FOLDER, f);
            boolean d = file.delete();

            FileOutputStream outputStream = new FileOutputStream(file.toString());

            while ((inputLine = in.readLine()) != null) {
                outputStream.write(inputLine.concat("\n").getBytes());
            }
            outputStream.close();
            Log.i("TEST", "url 4");
            in.close();
            Log.i("TEST", "url 5");
        }/*
        catch (MalformedURLException e){
            Log.i("TEST", e.toString());
        }
        catch (IOException e) {
            Log.i("TEST", e.toString());
        }*/
        catch (Exception e) {
            e.printStackTrace();
            Log.i("TEST", e.toString());
        }
        return null;
    }
}
