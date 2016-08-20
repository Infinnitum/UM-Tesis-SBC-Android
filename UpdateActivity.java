package edu.tesis.matias.tesis2015;

import android.app.Activity;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

public class UpdateActivity extends Activity {
    static public ArrayList<String> urls;
    static public String max_date;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);

        TextView td = (TextView) findViewById(R.id.updateDump);
        td.setMovementMethod(ScrollingMovementMethod.getInstance());
    }

    public void updateback(View view) {
        this.finish();
    }

    public void updateload(View view) {
        EditText valor = (EditText) findViewById(R.id.updateUrl);
        String url = valor.getText().toString();

        UpdateActivity.urls = new ArrayList<String>();

        if (!url.equals("")) {
            TextView dump = (TextView) findViewById(R.id.updateDump);
            Log.i("TEST", url);
            try {
                new UpdateURL().execute(url).get(60, TimeUnit.SECONDS);
            }
            catch (Exception e) {
                dump.setText("ERROR: No se pudo acceder al recurso");
                Log.i("TEST", e.toString());
            }
            Log.i("TEST", "END");
            String s="Iniciando actualizacion\n";
            Log.i("TEST", "END");
            for (String t : UpdateActivity.urls) {
                s=s.concat("\n").concat(t);
                try {
                    new UpdateGetFile().execute(t).get(60, TimeUnit.SECONDS);
                }
                catch (Exception e) {
                    dump.setText("ERROR: No se pudo acceder al recurso");
                    Log.i("TEST", e.toString());
                }
            }
            s=s.concat("\n\nFinalizando actualizacion");
            dump.setText(s);
            Log.i("TEST", "END");
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_update, menu);
        return true;
    }

}
