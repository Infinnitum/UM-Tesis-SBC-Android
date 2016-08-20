package edu.tesis.matias.tesis2015;

import android.app.Activity;
import android.os.Environment;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FilenameFilter;
import java.io.IOException;
import java.text.SimpleDateFormat;

public final class Archivo extends Activity {

    public static File SDCARD = Environment.getExternalStorageDirectory();
    public static File FOLDER = new File(Archivo.SDCARD + "/e2gkb");
    public static String KB_EXT = ".kb";
    public static String KX_EXT = ".kx";

    public static String[] getFileList() {
        String[] s;
        if (Archivo.FOLDER.exists()) {
            FilenameFilter filter = new FilenameFilter() {
                @Override
                public boolean accept(File dir, String filename) {
                    return filename.contains(Archivo.KB_EXT);
                }
            };
            s = Archivo.FOLDER.list(filter);
        }
        else {
            s = new String[0];
        }
        return s;
    }

    public static String getFileMaxDate() {
        String max = "";
        long last =  0;

        if (Archivo.FOLDER.exists()) {
            FilenameFilter filter = new FilenameFilter() {
                @Override
                public boolean accept(File dir, String filename) {
                    return filename.contains(Archivo.KB_EXT) || filename.contains(Archivo.KX_EXT);
                }
            };
            for (String f : Archivo.FOLDER.list(filter)) {
                File file = new File(Archivo.FOLDER, f);
                if (file.lastModified() > last) last = file.lastModified();
            }

            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
            max = sdf.format(last);
        }

        return max;
    }

    public static StringBuilder getFileText(String name) {
        File file = new File(Archivo.FOLDER, name);
        StringBuilder text = new StringBuilder();
        try {
            BufferedReader br = new BufferedReader(new FileReader(file));
            String line;
            while ((line = br.readLine()) != null) {
                text.append(line);
                text.append('\n');
            }
        }
        catch (IOException e) {
            //text.append(e.toString());
            text.append("");
        }
        return text;
    }

    public static StringBuilder getFileTextKX(String name) {
        String n = name.replace(Archivo.KB_EXT, Archivo.KX_EXT);
        return Archivo.getFileText(n);
/*
        File file = new File(Archivo.FOLDER, name);
        StringBuilder text = new StringBuilder();
        try {
            BufferedReader br = new BufferedReader(new FileReader(file));
            String line;
            while ((line = br.readLine()) != null) {
                text.append(line);
                text.append('\n');
            }
        }
        catch (IOException e) {
            //text.append(e.toString());
            text.append("");
        }
        return text;
*/
    }
/*
    public static String getURL(String url) {
        new ArchivoURL().execute(url);
        return a.getValue();
    }
*/
}
