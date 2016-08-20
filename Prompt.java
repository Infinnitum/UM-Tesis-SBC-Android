package edu.tesis.matias.tesis2015;

import android.app.Activity;
import android.util.Log;
import java.util.ArrayList;

public class Prompt extends Activity {
    private Atributo atributo;
    private String type;
    private String question;
    private ArrayList<String> options;
    private String min;
    private String max;
    private boolean utilizado;
    private boolean cf;


    public Prompt () {
        this.atributo = null;
        this.type = null;
        this.question = null;
        this.options = new ArrayList<String>();
        this.min = null;
        this.max = null;
        this.utilizado = false;
        this.cf = false;
    }
    public Prompt (Atributo atributo, String type, String question, ArrayList<String> options, String min, String max, boolean utilizado, boolean cf) {
        this.atributo = atributo;
        this.type = type;
        this.question = question;
        this.options = options;
        this.min = min;
        this.max = max;
        this.utilizado = utilizado;
        this.cf = cf;
    }


    public void resultado(ArrayList<String> r, String cf){
        this.atributo.setValue(r);
        this.atributo.setOrigen("USER");
        this.atributo.setCf(Integer.decode(cf));
        this.utilizado = true;
    }

    public void borrar(String r){
        this.atributo.setValue(new ArrayList<String>());
        this.atributo.setOrigen("N/A");
        this.atributo.setCf(100);
        this.utilizado = false;
    }


    public Atributo getAtributo() {
        return atributo;
    }
    public void setAtributo(Atributo atributo) {
        this.atributo = atributo;
    }

    public String getType() {
        return type;
    }
    public void setType(String type) {
        this.type = type;
    }

    public String getQuestion() {
        return question;
    }
    public void setQuestion(String question) {
        this.question = question;
    }

    public ArrayList<String> getOptions() {
        return this.options;
    }

    public String[] getOptionsChar() {
        String[] s = new String[this.options.size()];
        int i = 0;
        for (String t : this.options) {
            s[i] = t;
            i++;
        }
        return s;
    }
    public void setOptions(ArrayList<String> options) {
        this.options = options;
    }

    public String getMin() {
        return min;
    }
    public void setMin(String min) {
        this.min = min;
    }

    public String getMax() {
        return max;
    }
    public void setMax(String max) {
        this.max = max;
    }

    public boolean getUtilizado() {
        return utilizado;
    }
    public void setUtilizado(boolean utilizado) {
        this.utilizado = utilizado;
    }

    public boolean getCf() {
        return this.cf;
    }
    public void setCf(boolean cf) {
        this.cf = cf;
    }

    public boolean isSingle(){
        return this.type.equals("MULTCHOICE") || this.type.equals("FORCEDCHOICE") || this.type.equals("CHOICE");
    }

    public boolean isMultiple(){
        return this.type.equals("ALLCHOICE");
    }

    public boolean isBoolean(){
        return this.type.equals("YESNO");
    }

    public boolean isNumeric(){
        return this.type.equals("NUMERIC");
    }

    public boolean valueGood(String sv){
        int v;
        boolean r = true;

        try {
            v = Integer.parseInt(sv);

            if (this.isNumeric()) {
                if (this.max != null) {
                    if (v > Integer.parseInt(this.max)) {
                        r=false;
                    }
                }
                if (this.min != null) {
                    if (v < Integer.parseInt(this.min)) {
                        r=false;
                    }
                }
            }
        }
        catch (Exception e) {
            r=false;
        }
        return r;
    }

    public String valueGoodText(){
        String r = "El valor debe ";

        if (this.max != null) {
            if (this.min != null) {
                r = r +  "estar entre " + this.min + " y " + this.max;
            }
            else {
                r = r + "ser mayor o igual a " + this.max;
            }
        }
        else if (this.min != null) {
            r = r + "ser menor o igual a " + this.min;
        }

        return r;
    }

    public void addExtra(String extra) {
        if (this.type.equals("MULTCHOICE") || this.type.equals("FORCEDCHOICE") || this.type.equals("CHOICE") || this.type.equals("ALLCHOICE"))
            this.options.add(extra);
        else if (this.type.equals("NUMERIC")) {
            if (this.min == null) this.min = extra;
            else if (this.max == null) this.max = extra;
        }
    }
}
