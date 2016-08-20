package edu.tesis.matias.tesis2015;

import android.app.Activity;
import android.util.Log;
import java.util.ArrayList;

public class Atributo extends Activity {
    private String name;
    private ArrayList<String> value;
    private ArrayList<Rule> rules;
    private String origen;
    private String defvalue;
    private int cf;
    private String url;


    public Atributo (String name) {
        this.name = name;
        Log.i("TEST-Atributo", name);
        this.origen = "N/A";
        this.value = new ArrayList<String>();
        this.rules = new ArrayList<Rule>();
        this.cf = 100;
    }

    public boolean isTrue (String operador, Atributo atributo2) {
        Log.i("TEST-Atributo.isTrue", "1");
        //Log.i("TEST-Atributo.isTrue", atributo2.getValue());
        //Log.i("TEST-Atributo.isTrue", "2");
        if (atributo2.getValues().isEmpty()) return false;
        Log.i("TEST-Atributo.isTrue", "3");
        ArrayList<Valor> v = new ArrayList<Valor>();
        Log.i("TEST-Atributo.isTrue", "4");
        v.add(new Valor(null, atributo2.getValue()));
        Log.i("TEST-Atributo.isTrue", "5");
        return this.isTrue(operador, v);
    }

    public boolean isTrue (String operador, ArrayList<Valor> valor) {
        Log.i("TEST-Atributo.isTrue", "6");
        //Log.i("TEST-Atributo.isTrue", this.value.get(0));
        Log.i("TEST-Atributo.isTrue", operador);
        Log.i("TEST-Atributo.isTrue", this.getName());
        Log.i("TEST-Atributo.isTrue", valor.get(0).getValor());
        if (this.value.isEmpty()) return false;
        else {
            Log.i("TEST-Atributo.isTrue", "7");
            int ivalue=0;
            int ivalor=0;
            boolean iusar=true;

            try {
                ivalue=Integer.decode(this.value.get(0));
            }
            catch (Exception e) {
                iusar=false;
            }

            try {
                ivalor=Integer.decode(valor.get(0).getValor());
            }
            catch (Exception e) {
                iusar=false;
            }

            if (operador.equals("=")) {
                if (iusar) return ivalue == ivalor;
                else return this.value.get(0).compareToIgnoreCase(valor.get(0).getValor()) == 0;
            }
            else if (operador.equals("<=")) {
                if (iusar) return ivalue <= ivalor;
                else return this.value.get(0).compareToIgnoreCase(valor.get(0).getValor()) <= 0;
            }
            else if (operador.equals("<")) {
                if (iusar) return ivalue < ivalor;
                else return this.value.get(0).compareToIgnoreCase(valor.get(0).getValor()) < 0;
            }
            else if (operador.equals(">=")) {
                Log.i("TEST-Atributo.isTrue", this.value.get(0));
                Log.i("TEST-Atributo.isTrue", operador);
                Log.i("TEST-Atributo.isTrue", valor.get(0).getValor());
                if (iusar) return ivalue >= ivalor;
                else return this.value.get(0).compareToIgnoreCase(valor.get(0).getValor()) >= 0;
            }
            else if (operador.equals(">")) {
                if (iusar) return ivalue > ivalor;
                else return this.value.get(0).compareToIgnoreCase(valor.get(0).getValor()) > 0;
            }
            else if (operador.equals("<>")) {
                if (iusar) return ivalue != ivalor;
                else return this.value.get(0).compareToIgnoreCase(valor.get(0).getValor()) != 0;
            }
            else if (operador.equals("!=")) {
                if (iusar) return ivalue != ivalor;
                else return this.value.get(0).compareToIgnoreCase(valor.get(0).getValor()) != 0;
            }
            else if (operador.equals("!:")) {
                boolean b = true;
                Log.i("TEST2", this.getName());
                for (String s : this.value) {
                    Log.i("TEST2", s.replace("\"", "").replace("'", ""));
                    for (Valor v : valor) {
                        Log.i("TEST2", v.getValor());
                        Log.i("TEST2", ((Integer) s.replace("\"", "").replace("'", "").compareToIgnoreCase(v.getValor())).toString());
                        if (s.replace("\"", "").compareToIgnoreCase(v.getValor()) == 0)
                            b = false;
                        if (b) Log.i("TEST2", "true");
                        else Log.i("TEST2", "false");
                    }
                }
                if (b) Log.i("TEST2", "-> true");
                else Log.i("TEST2", "-> false");
                return b;
            }
            else if (operador.equals("!")) {
                if (iusar) return ivalue != ivalor;
                else return this.value.get(0).compareToIgnoreCase(valor.get(0).getValor()) != 0;
            }
            else if (operador.equals(":")) {
                Log.i("TEST","equals(\":\")");
                boolean b = false;
                for (String s : this.value) {
                    Log.i("TEST2", s.replace("\"", "").replace("'", ""));
                    for (Valor v : valor) {
                        Log.i("TEST2", v.getValor());
                        Log.i("TEST", ((Integer) s.replace("\"", "").replace("'", "").compareToIgnoreCase(v.getValor())).toString());
                        if (s.replace("\"", "").replace("'", "").compareToIgnoreCase(v.getValor()) == 0)
                            b = true;
                    }
                }
                return b;
            }
            else if (operador.equals("?")) {
                if (iusar) return ivalue == ivalor;
                else return this.value.get(0).compareToIgnoreCase(valor.get(0).getValor()) == 0;
            }
            else {
                return false;
            }
        }
    }


    public boolean isValid() {
        //return this.value != null && (this.origen == "USER" || this.origen == "RULE" || this.origen == "DEFAULT");
        Log.i("TEST-QWE", this.getName());
        //Log.i("TEST-QWE", this.getValue());
        return !this.value.isEmpty();
    }


    public void borrar() {
        this.setValue(new ArrayList<String>());
        this.setOrigen("N/A");
        this.setCf(100);
    }


    public String getName() {
        return this.name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return this.value.get(0);
    }
    public ArrayList<String> getValues() {
        return this.value;
    }
    public void setValue(ArrayList<String> value) {
        this.value = value;
    }
    public void addValue(String value) {
        this.value.add(value);
    }

    public Rule getRule() {
        return this.rules.get(0);
    }
    public ArrayList<Rule> getRules() {
        return this.rules;
    }
    public void setRules(ArrayList<Rule> rules) {
        this.rules = rules;
    }
    public void addRule(Rule rule) {
        this.rules.add(rule);
    }

    public String getOrigen() {
        return this.origen;
    }
    public void setOrigen(String origen) {
        this.origen = origen;
    }

    public String getDefvalue() {
        return this.defvalue;
    }
    public void setDefvalue(String defvalue) {
        this.defvalue = defvalue;
    }

    public int getCf() {
        return this.cf;
    }
    public void setCf(int cf) {
        this.cf = cf;
    }
    public boolean isCf() {
        return this.cf >= KBase.MINCF;
    }

    public String getUrl() {
        return this.url;
    }
    public void setUrl(String url) {
        this.url = url;
    }

    public String toText(){
        String s = "";
        String a = "";

        for (String v : this.value) {
            if (! a.equals(""))
                a = a.concat(", ");
            a = a.concat(v);
        }
        s = "Atributo ".concat(this.name).concat(" CF ").concat(((Integer)this.cf).toString()).concat(" valor/es ").concat(a).concat("\n");

        s = s.concat("Origen ").concat(this.origen).concat("\n\n");
        if (! this.rules.isEmpty()) {
            for (Rule r : this.rules) {
                s = s.concat(r.toText()).concat("\n");
            }
        }

        return s;
    }
}
