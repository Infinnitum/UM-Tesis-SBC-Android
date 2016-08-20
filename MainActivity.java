package edu.tesis.matias.tesis2015;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.net.Uri;
import android.os.Bundle;
import android.app.Activity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.ToggleButton;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

public class MainActivity extends Activity {

    private KBase kbase = new KBase();
    private Prompt prompt;
    private ArrayList<CheckBox> checkBoxes;
    private ArrayList<String> strings;
    boolean has_ask_show = false;
    boolean has_action_show = false;
    boolean has_delete_show = false;
    boolean has_ask_delete_show = false;
    boolean has_explain_show = false;

    boolean has_ask_show_ant = false;
    boolean has_action_show_ant = false;
    boolean has_delete_show_ant = false;
    boolean has_ask_delete_show_ant = false;
    boolean has_explain_show_ant = false;

    private String search_url = "https://www.google.com.ar/search?q={SEARCH}";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /*
        TextView td = (TextView) findViewById(R.id.kbtextdump);
        td.setMovementMethod(ScrollingMovementMethod.getInstance());
        TextView av = (TextView) findViewById(R.id.askView);
        av.setMovementMethod(ScrollingMovementMethod.getInstance());
        TextView ex = (TextView) findViewById(R.id.askExplain);
        ex.setMovementMethod(ScrollingMovementMethod.getInstance());
        */

        kb_hide();
        kb_checknew();
    }

    public void hideSoftKeyboard() {
        if(getCurrentFocus()!=null) {
            InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
            //inputMethodManager.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0);
        }
    }

    public void menu_show() {
        LinearLayout l = (LinearLayout) findViewById(R.id.menuLayout);
        l.setVisibility(View.VISIBLE);
    }
    public void menu_hide() {
        LinearLayout l = (LinearLayout) findViewById(R.id.menuLayout);
        l.setVisibility(View.GONE);
    }
    public void kb_show() {
        LinearLayout l = (LinearLayout) findViewById(R.id.kbLayout);
        l.setVisibility(View.VISIBLE);
    }
    public void kb_hide() {
        LinearLayout l = (LinearLayout) findViewById(R.id.kbLayout);
        l.setVisibility(View.GONE);
    }
    public void ask_show(boolean focus, String y) {
        LinearLayout l = (LinearLayout) findViewById(R.id.askLayout);
        if (!has_ask_show) {
            View v = LayoutInflater.from(this).inflate(getResources().getIdentifier(y, "layout", getPackageName()), null);
            l.addView(v);
            has_ask_show = true;
        }
        if (focus) {
            if (y.equalsIgnoreCase("ask_text"))
                findViewById(R.id.askText).requestFocus();
            else if (y.equalsIgnoreCase("ask_numeric"))
                findViewById(R.id.askNumber).requestFocus();
            else
                l.requestFocus();
        }
    }
    public void ask_hide() {
        LinearLayout l = (LinearLayout) findViewById(R.id.askLayout);
        l.removeAllViews();
        has_ask_show = false;
    }
    public void ask_change(int i) {
        LinearLayout l = (LinearLayout) findViewById(R.id.askLayout);
        l.setVisibility(i);
    }
    public void action_show() {
        LinearLayout l = (LinearLayout) findViewById(R.id.actionLayout);
        if (!has_action_show) {
            View v = LayoutInflater.from(this).inflate(
                    R.layout.tool_action, null);
            l.addView(v);
            has_action_show = true;
        }
    }
    public void action_hide() {
        LinearLayout l = (LinearLayout) findViewById(R.id.actionLayout);
        l.removeAllViews();
        has_action_show = false;
    }
    public void action_change(int i) {
        LinearLayout l = (LinearLayout) findViewById(R.id.actionLayout);
        l.setVisibility(i);
    }
    public void delete_show() {
        LinearLayout l = (LinearLayout) findViewById(R.id.deleteLayout);
        if (!has_delete_show) {
            View v = LayoutInflater.from(this).inflate(
                    R.layout.tool_delete, null);
            l.addView(v);
            has_delete_show = true;
        }
    }
    public void delete_hide() {
        LinearLayout l = (LinearLayout) findViewById(R.id.deleteLayout);
        l.removeAllViews();
        has_delete_show = false;
    }
    public void delete_change(int i) {
        LinearLayout l = (LinearLayout) findViewById(R.id.deleteLayout);
        l.setVisibility(i);
    }
    public void askDelete_hide() {
        LinearLayout l = (LinearLayout) findViewById(R.id.askDeleteLayout);
        l.removeAllViews();
        has_ask_delete_show = false;
    }
    public void askDelete_show(boolean fill) {
        LinearLayout l = (LinearLayout) findViewById(R.id.askDeleteLayout);
        if (!has_ask_delete_show) {
            View v = LayoutInflater.from(this).inflate(
                    R.layout.ask_delete, null);
            l.addView(v);
            has_ask_delete_show = true;

            if (fill && !this.kbase.listarRespuesta().isEmpty()) {
                LinearLayout t = (LinearLayout) findViewById(R.id.askDelete);
                int i=0;

                this.strings = new ArrayList<String>();
                this.checkBoxes = new ArrayList<CheckBox>();

                for (String s : this.kbase.listarRespuesta()) {
                    CheckBox cb = new CheckBox(this);
                    cb.setText(s);
                    cb.setId(i);
                    this.strings.add(i,s);
                    this.checkBoxes.add(i,cb);
                    t.addView(cb);
                    i++;
                }
            }
        }
    }
    public void explain_show() {
        LinearLayout l = (LinearLayout) findViewById(R.id.explainLayout);
        if (!has_explain_show) {
            View v = LayoutInflater.from(this).inflate(
                    R.layout.kb_explain, null);
            l.addView(v);
            has_explain_show = true;
        }
    }
    public void explain_hide() {
        LinearLayout l = (LinearLayout) findViewById(R.id.explainLayout);
        l.removeAllViews();
        has_explain_show = false;
    }
    public void explain_change(int i) {
        LinearLayout l = (LinearLayout) findViewById(R.id.explainLayout);
        l.setVisibility(i);
    }
    public void ask_question(String value) {
        TextView question = (TextView) findViewById(R.id.askView);
        question.setText(value);
    }
    public void ask_cf(boolean show) {
        EditText cf = (EditText) findViewById(R.id.askCf);
        LinearLayout l = (LinearLayout) findViewById(R.id.askCfLayout);
        cf.setText("100");
        if (show) {
            l.setVisibility(View.VISIBLE);
        } else {
            l.setVisibility(View.GONE);
        }
    }
    public void ask_goal(String value) {
        TextView goal = (TextView) findViewById(R.id.askGoal);
        goal.setText(value);
    }
    public void ask_explain(String value) {
        //TextView explain = (TextView) findViewById(R.id.askExplain);
        //explain.setText(value);
    }

    public void asktrazabilidad(View view) {
        new AlertBoxOne(this, getResources().getString(R.string.trazabilidad), kbase.explain()).createShow();
    }

    public void kbupdate(View view) {
        Intent intent = new Intent(this, UpdateActivity.class);
        startActivity(intent);
    }

    public void kbstart(View view) {
        Spinner spinner = (Spinner) findViewById(R.id.kbspinner);
        //TextView td = (TextView) findViewById(R.id.kbtextdump);

        //td.setText(Archivo.getFileText((String) spinner.getSelectedItem()).append("\n\n--------------\n\n").append(Archivo.getFileTextDAX((String) spinner.getSelectedItem())));
        this.kbase = new KBase();
        this.kbase.addFile(Archivo.getFileText((String) spinner.getSelectedItem()));
        this.kbase.addFileKX(Archivo.getFileTextKX((String) spinner.getSelectedItem()));

        kb_hide();
        action_show();
        delete_show();

        this.asknext(view);
    }
    public void asknext(View view) {
        Log.i("TEST", "asknext 1");
        this.kbase.updateAtributos();
        Log.i("TEST", "asknext 2");

        if (kbase.completeGoals()) {
            Log.i("TEST", "asknext 3.1");
            ask_hide();
            action_hide();
            delete_show();
            explain_show();

            ask_goal(kbase.getGoalsTxt());
            //ask_explain(kbase.explain().toUpperCase());

            hideSoftKeyboard();
            Log.i("TEST", "asknext 3.1.5");

            this.search_url = kbase.getSearch_url().replace("{SEARCH}",kbase.getGoalsSearch());

            new AlertBoxOne(this, kbase.getRem(), kbase.getGoalsTxt()).createShow();
        }
        /*
        else if (kbase.completePrompt()) {
            Log.i("TEST", "3.2");
            ask_all(View.GONE, false);
            question.setText("* NO SE PUEDE OBTENER NINGUN RESULTADO VALIDO");
        }
        */
        else {
            Log.i("TEST", "asknext 3.3");
            this.prompt = this.kbase.nextPrompt();
            //hideSoftKeyboard();
            Log.i("TEST", "asknext 3.3.1");
            if (this.prompt == null) {
                Log.i("TEST", "asknext 9");
                ask_hide();
                action_hide();
                delete_show();
                explain_show();
                ask_goal("* NO SE PUEDE OBTENER NINGUN RESULTADO VALIDO");
                //ask_explain(kbase.explain().toUpperCase());
                //Log.i("TEST", "chan");
            }
            else /*if (this.prompt != null)*/ {
                Log.i("TEST", "asknext 4");
                ask_hide();
                action_show();
                delete_show();
                explain_hide();

                Log.i("TEST", "asknext 5");
                if (this.prompt.isNumeric()) {
                    Log.i("TEST", "asknext 6.1");
                    ask_show(true, "ask_numeric");
                    if (this.prompt.getAtributo().getDefvalue() != null) {
                        EditText b = (EditText) findViewById(R.id.askNumber);
                        b.setText(this.prompt.getAtributo().getDefvalue());
                    }
                    //show_softinput = true;
                    Log.i("TEST", "asknext 7.1");
                }
                else if (this.prompt.isBoolean()) {
                    Log.i("TEST", "asknext 6.2");
                    ask_show(false, "ask_boolean");
                    if (this.prompt.getAtributo().getDefvalue() != null) {
                        ToggleButton b = (ToggleButton) findViewById(R.id.askYesno);
                        b.setChecked(false);
                        if (this.prompt.getAtributo().getDefvalue().equals("TRUE")) {
                            b.setChecked(true);
                        }
                    }
                    hideSoftKeyboard();
                    Log.i("TEST", "asknext 7.2");
                }
                else if (this.prompt.isMultiple()) {
                    Log.i("TEST", "asknext 6.3");
                    ask_show(false, "ask_multiple");
                    int i=0;
                    LinearLayout l = (LinearLayout) findViewById(R.id.askMultiple);
                    this.strings = new ArrayList<String>();
                    this.checkBoxes = new ArrayList<CheckBox>();
                    for (String s : this.prompt.getOptions()) {
                        CheckBox cb = new CheckBox(this);
                        cb.setText(s);
                        cb.setId(i);
                        if (s.equalsIgnoreCase(this.prompt.getAtributo().getDefvalue())) {
                            cb.setChecked(true);
                        }
                        this.strings.add(i, s);
                        this.checkBoxes.add(i, cb);
                        l.addView(cb);
                        i++;
                    }
                    hideSoftKeyboard();
                    Log.i("TEST", "asknext 7.3");
                }
                else if (this.prompt.isSingle()) {
                    Log.i("TEST", "asknext 6.4");
                    ask_show(false, "ask_single");
                    Spinner sp = (Spinner) findViewById(R.id.askSingle);
                    ArrayAdapter<CharSequence> ad = new ArrayAdapter<CharSequence>(this, android.R.layout.simple_spinner_item, this.prompt.getOptionsChar());
                    sp.setAdapter(ad);
                    if (this.prompt.getAtributo().getDefvalue() != null) {
                        Log.i("TESTDEFAULT",this.prompt.getAtributo().getDefvalue());
                        Log.i("TESTDEFAULT", String.valueOf(ad.getPosition(this.prompt.getAtributo().getDefvalue())));
                        sp.setSelection(ad.getPosition(this.prompt.getAtributo().getDefvalue()));
                    }
                    hideSoftKeyboard();
                    Log.i("TEST", "asknext 7.4");
                } else {
                    Log.i("TEST", "asknext 6.5");
                    ask_show(true, "ask_text");
                    if (this.prompt.getAtributo().getDefvalue() != null) {
                        EditText b = (EditText) findViewById(R.id.askText);
                        b.setText(this.prompt.getAtributo().getDefvalue());
                    }
                    //show_softinput = true;
                    Log.i("TEST", "asknext 7.5");
                }

                Log.i("TEST", "asknext 8.1");
                ask_question(this.prompt.getQuestion());
                Log.i("TEST", "asknext 8.2");
                ask_cf(this.prompt.getCf());
                Log.i("TEST", "asknext 8.3");
            }
        }
    }
    public void kbase(View view) {
        Spinner spinner = (Spinner) findViewById(R.id.kbspinner);
        ArrayAdapter<CharSequence> adapter = new ArrayAdapter<CharSequence>(this, android.R.layout.simple_spinner_item, Archivo.getFileList());
        spinner.setAdapter(adapter);

        menu_hide();
        kb_show();
        explain_hide();
    }
    public void kbback(View view) {
        menu_show();
        kb_hide();
        explain_hide();
    }
    public void askback(View view) {
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(),0);

        kb_show();
        ask_hide();
        action_hide();
        delete_hide();
        explain_hide();

        this.kbase = null;
        this.prompt = null;
    }
    public void asksend(View view) {
        boolean good = true;

        if (this.prompt != null) {
            ArrayList<String> v = new ArrayList<String>();

            if (this.prompt.isNumeric()) {
                EditText valor = (EditText) findViewById(R.id.askNumber);
                if (this.prompt.valueGood(valor.getText().toString())) {
                    v.add(valor.getText().toString());
                    valor.setText("");
                }
                else {
                    good = false;

                    new AlertBoxOne(this, "Valor incorrecto", this.prompt.valueGoodText()).createShow();
                }
            }
            else if (this.prompt.isBoolean()) {
                ToggleButton valor = (ToggleButton) findViewById(R.id.askYesno);
                v.add(((Boolean)valor.isChecked()).toString().toUpperCase());
                valor.setChecked(false);
            }
            else if (this.prompt.isMultiple()) {
                LinearLayout l = (LinearLayout) findViewById(R.id.askMultiple);
                for (CheckBox c : this.checkBoxes) {
                    if (c.isChecked()) {
                        v.add(this.strings.get(c.getId()));
                    }
                }
                l.removeAllViewsInLayout();
            }
            else if (this.prompt.isSingle()) {
                Spinner valor = (Spinner) findViewById(R.id.askSingle);
                v.add((String) valor.getSelectedItem());
            }
            else {
                EditText valor = (EditText) findViewById(R.id.askText);
                v.add(valor.getText().toString());
                valor.setText("");
            }

            EditText cf = (EditText) findViewById(R.id.askCf);
            this.prompt.resultado(v, cf.getText().toString());
        }
        if (good) {
            this.asknext(view);
        }
    }

    public void asksearch(View view) {
        Intent i = new Intent(Intent.ACTION_VIEW);
        i.setData(Uri.parse(this.search_url));
        startActivity(i);
    }

    public void deleteAnswer(View view) {
        if (!this.kbase.listarRespuesta().isEmpty()) {
            has_ask_show_ant = has_ask_show;
            if (has_ask_show) {
                ask_change(View.GONE);
            }
            has_action_show_ant = has_action_show;
            if (has_action_show) {
                action_change(View.GONE);
            }
            has_delete_show_ant = has_delete_show;
            if (has_delete_show) {
                delete_change(View.GONE);
            }
            askDelete_show(true);

            has_explain_show_ant = has_explain_show;
            if (has_explain_show) {
                explain_change(View.GONE);
            }
        }
    }
    public void deleteBack(View view) {
        has_ask_show = has_ask_show_ant;
        if (has_ask_show) {
            ask_change(View.VISIBLE);
        }
        has_action_show = has_action_show_ant;
        if (has_action_show) {
            action_change(View.VISIBLE);
        }
        has_delete_show = has_delete_show_ant;
        if (has_delete_show) {
            delete_change(View.VISIBLE);
        }
        askDelete_hide();

        has_explain_show = has_explain_show_ant;
        if (has_explain_show) {
            explain_change(View.VISIBLE);
        }
    }
    public void deleteConfirm(View view) {
        LinearLayout l = (LinearLayout) findViewById(R.id.askDelete);
        for (CheckBox c : this.checkBoxes) {
            if (c.isChecked()) {
                this.kbase.anularPregunta(this.strings.get(c.getId()));
            }
        }
        l.removeAllViewsInLayout();

        has_ask_show = has_ask_show_ant;
        if (has_ask_show) {
            ask_change(View.VISIBLE);
        }
        has_action_show = has_action_show_ant;
        if (has_action_show) {
            action_change(View.VISIBLE);
        }
        has_delete_show = has_delete_show_ant;
        if (has_delete_show) {
            delete_change(View.VISIBLE);
        }
        askDelete_hide();

        has_explain_show = has_explain_show_ant;
        if (has_explain_show) {
            explain_change(View.VISIBLE);
        }

        asknext(view);
    }

    public void kb_checknew() {
        Resources res = getResources();
        String url = res.getString(R.string.tesis_checknew_PROD_php);

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

            String server_max = UpdateActivity.max_date;
            String file_max = Archivo.getFileMaxDate();
            if (!server_max.isEmpty()) {
                if (!file_max.isEmpty()) {
                    if (server_max.compareToIgnoreCase(file_max) > 0) {
                        new AlertBoxOne(this, "Aviso", "Hay actualizaciones listas para descargar").createShow();
                    }
                }
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public void onBackPressed() {
        //LinearLayout m = (LinearLayout) findViewById(R.id.menuLayout);
        LinearLayout l = (LinearLayout) findViewById(R.id.kbLayout);

        if (has_ask_delete_show) {
            deleteBack(null);
        }
        else if (has_ask_show) {
            deleteAnswer(null);
        }
        else if (has_explain_show) {
            deleteAnswer(null);
        }
        else if (l.getVisibility() == View.VISIBLE) {
            kbback(null);
        }
        else {
            super.onBackPressed();
        }
    }
}
