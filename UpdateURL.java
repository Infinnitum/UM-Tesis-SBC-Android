package edu.tesis.matias.tesis2015;

import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;

public class UpdateURL extends AsyncTask<String, Void, Void> {
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
            String dir = "";
            while ((inputLine = in.readLine()) != null) {
                if (!inputLine.startsWith("<html>") &&
                        !inputLine.startsWith("<body>") &&
                        !inputLine.startsWith("</body>") &&
                        !inputLine.startsWith("</html>")) {
                    Log.i("TEST", inputLine);
                    if (inputLine.startsWith("<option>DIR_KB:")) dir=inputLine.replace("<option>DIR_KB:","").replace("</option>","").trim();
                    else if (inputLine.startsWith("<option>MAX_DATE:")) UpdateActivity.max_date=inputLine.replace("<option>MAX_DATE:","").replace("</option>","").trim();
                    else UpdateActivity.urls.add(dir.concat(inputLine.replace("<option>","").replace("</option>","").trim()));

                    Log.i("TEST", dir);
                }
            }
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
