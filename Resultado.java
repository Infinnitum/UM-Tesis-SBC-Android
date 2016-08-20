package edu.tesis.matias.tesis2015;

import android.app.Activity;
import android.util.Log;
import java.util.ArrayList;

public class Resultado extends Activity {
    private Atributo atributo;
    private ArrayList<String> valor;


    public Resultado (Atributo atributo, ArrayList<String> valor) {
        this.atributo = atributo;
        this.valor = valor;
    }
    public Resultado (KBase kbase, String linea) {
        this.atributo = kbase.findAtributos(linea.split("]")[0].substring(1).trim().toUpperCase());
        this.valor = new ArrayList<String>();
        for (String s : linea.split("= ")[1].split("\" \"")) {
            this.valor.add(s.replace("\"","").trim().toUpperCase());
        }
    }


    public void resultado(Rule rule, int i) {
        for (String t : this.valor) {
            this.atributo.addValue(t);
            Log.i("TEST2", t);
        }
        this.atributo.addRule(rule);
        this.atributo.setOrigen("RULE");
        this.atributo.setCf(i);
    }


    public Atributo getAtributo() {
        return atributo;
    }
    public void setAtributo(Atributo atributo) {
        this.atributo = atributo;
    }

    public ArrayList<String> getValor() {
        return valor;
    }
    public void setValor(ArrayList<String> valor) {
        this.valor = valor;
    }

    public String toText(){
        String s = "";
        for (String v : this.valor) {
            if (! s.equals(""))
                s = s.concat(", ");
            s = s.concat(v);
        }
        return s.concat("\n");
    }
}
