package edu.tesis.matias.tesis2015;

import android.os.AsyncTask;
import android.util.Log;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;

public class ArchivoURL extends AsyncTask<Atributo, Void, Void> {
    protected Void doInBackground(Atributo... atributos) {
        Atributo atributo = atributos[0];
        String url = atributo.getUrl();

        Log.i("TEST", url);
        try {
            Log.i("TEST", "url 0");
            URL link = new URL(url);
            Log.i("TEST", "url 1");
            BufferedReader in = new BufferedReader(new InputStreamReader(link.openStream()));

            Log.i("TEST", "url 2");
            String inputLine;

            Log.i("TEST", "url 3");
            while ((inputLine = in.readLine()) != null) {
                if (!inputLine.startsWith("<html>") &&
                        !inputLine.startsWith("<body>") &&
                        !inputLine.startsWith("</body>") &&
                        !inputLine.startsWith("</html>")) {
                    Log.i("TEST", inputLine);
                    if (!inputLine.equals("")) {
                        atributo.setValue(new ArrayList<String>());
                        atributo.addValue(inputLine);
                    }
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
