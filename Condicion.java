package edu.tesis.matias.tesis2015;

import android.app.Activity;
import android.util.Log;
import java.util.ArrayList;

public class Condicion extends Activity {
    private Atributo atributo;
    private Atributo atributo2;
    private String operador;
    private ArrayList<Valor> valor;

    public Condicion (KBase kbase, String linea) {
        //String l = linea.split("]")[1].trim().toUpperCase();
        Log.i("TEST-Condicion.new", "1");
        this.atributo = kbase.findAtributos(linea.split("]")[0].substring(1).trim().toUpperCase());
        Log.i("TEST-Condicion.new","2");
        this.atributo2 = this.atributo;
        Log.i("TEST-Condicion.new","3");
        this.valor = new ArrayList<Valor>();
        Log.i("TEST-Condicion.new","4");
        //Log.i("TEST","***".concat(l));
/*
        if (l.startsWith("=")) {
            this.operador = "=";
            //Log.i("TEST","*** ".concat(l.split("=")[1].replace("\"","").trim().toUpperCase()));
            this.valor.add(new Valor(kbase, l.split(this.operador)[1].replace("\"", "").trim().toUpperCase()));
            this.atributo = kbase.findAtributos(linea.split(this.operador)[0].split("]")[0].substring(1).trim().toUpperCase());
            this.atributo2 = this.atributo;
            if (l.contains("]"))
                this.atributo2 = kbase.findAtributos(l.split(this.operador)[1].replace("\"", "").trim().toUpperCase().split("]")[0].substring(1).trim().toUpperCase());
        }
        else if (l.startsWith("<=")) {
            this.operador = "<=";
            this.valor.add(new Valor(kbase, l.split("<=")[1].replace("\"","").trim().toUpperCase()));
            if (l.contains("]"))
                this.atributo2 = kbase.findAtributos(l.split("<=")[1].replace("\"", "").trim().toUpperCase().split("]")[0].substring(1).trim().toUpperCase());
        }
        else if (l.startsWith("<")) {
            this.operador = "<";
            this.valor.add(new Valor(kbase, l.split("<")[1].replace("\"","").trim().toUpperCase()));
            Log.i("TEST-QWE", l.split("<")[1].replace("\"","").trim().toUpperCase());
            Log.i("TEST-QWE", l.split("<")[1].replace("\"", "").trim().toUpperCase().split("]")[0].substring(1).trim().toUpperCase());
            if (l.contains("]"))
                this.atributo2 = kbase.findAtributos(l.split("<")[1].replace("\"", "").trim().toUpperCase().split("]")[0].substring(1).trim().toUpperCase());
        }
        else if (l.startsWith(">=")) {
            this.operador = ">=";
            this.valor.add(new Valor(kbase, l.split(">=")[1].replace("\"","").trim().toUpperCase()));
            if (l.contains("]"))
                this.atributo2 = kbase.findAtributos(l.split(">=")[1].replace("\"", "").trim().toUpperCase().split("]")[0].substring(1).trim().toUpperCase());
        }
        else if (l.startsWith(">")) {
            this.operador = ">";
            this.valor.add(new Valor(kbase, l.split(">")[1].replace("\"","").trim().toUpperCase()));
            if (l.contains("]"))
                this.atributo2 = kbase.findAtributos(l.split(">")[1].replace("\"", "").trim().toUpperCase().split("]")[0].substring(1).trim().toUpperCase());
        }
        else if (l.startsWith("<>")) {
            this.operador = "<>";
            this.valor.add(new Valor(kbase, l.split("<>")[1].replace("\"","").trim().toUpperCase()));
            if (l.contains("]"))
                this.atributo2 = kbase.findAtributos(l.split("<>")[1].replace("\"", "").trim().toUpperCase().split("]")[0].substring(1).trim().toUpperCase());
        }
        else if (l.startsWith("!=")) {
            this.operador = "!=";
            this.valor.add(new Valor(kbase, l.split("!=")[1].replace("\"","").trim().toUpperCase()));
            if (l.contains("]"))
                this.atributo2 = kbase.findAtributos(l.split("!=")[1].replace("\"", "").trim().toUpperCase().split("]")[0].substring(1).trim().toUpperCase());
        }
        else if (l.startsWith("!:")) {
            this.operador = "!:";
            for (String s : l.split("!:")[1].trim().toUpperCase().split("\" \"")){
                this.valor.add(new Valor(kbase, s.replace("\"","")));
            }
        }
        else if (l.startsWith("!")) {
            this.operador = "!";
            this.valor.add(new Valor(kbase, l.split("!")[1].replace("\"","").trim().toUpperCase()));
        }
        else if (l.startsWith(":")) {
            this.operador = ":";
            for (String s : l.split(":")[1].trim().toUpperCase().split("\" \"")){
                Log.i("TEST","*** ".concat(s.replace("\"","")));
                this.valor.add(new Valor(kbase, s.replace("\"","")));
            }
        }
*/
        if (linea.contains("<>")) {
            this.operador = "<>";
            this.atributo = kbase.findAtributos(linea.split(this.operador)[0].split("]")[0].substring(1).trim().toUpperCase());
            this.valor.add(new Valor(kbase, linea.split(this.operador)[1].replace("\"", "").trim().toUpperCase()));
            if (linea.split(this.operador)[1].contains("]"))
                this.atributo2 = kbase.findAtributos(linea.split(this.operador)[1].replace("\"", "").trim().toUpperCase().split("]")[0].substring(1).trim().toUpperCase());
            else
                this.atributo2 = this.atributo;
        }
        else if (linea.contains("<=")) {
            this.operador = "<=";
            this.atributo = kbase.findAtributos(linea.split(this.operador)[0].split("]")[0].substring(1).trim().toUpperCase());
            this.valor.add(new Valor(kbase, linea.split(this.operador)[1].replace("\"", "").trim().toUpperCase()));
            if (linea.split(this.operador)[1].contains("]"))
                this.atributo2 = kbase.findAtributos(linea.split(this.operador)[1].replace("\"", "").trim().toUpperCase().split("]")[0].substring(1).trim().toUpperCase());
            else
                this.atributo2 = this.atributo;
        }
        else if (linea.contains("<")) {
            this.operador = "<";
            Log.i("TEST-QWE", "C1");
            this.atributo = kbase.findAtributos(linea.split(this.operador)[0].split("]")[0].substring(1).trim().toUpperCase());
            Log.i("TEST-QWE", "C2");
            this.valor.add(new Valor(kbase, linea.split(this.operador)[1].replace("\"", "").trim().toUpperCase()));
            Log.i("TEST-QWE", "C3");
            if (linea.split(this.operador)[1].contains("]")) {
                this.atributo2 = kbase.findAtributos(linea.split(this.operador)[1].replace("\"", "").trim().toUpperCase().split("]")[0].substring(1).trim().toUpperCase());
                Log.i("TEST-QWE", "C4");
            }
            else
                this.atributo2 = this.atributo;
            Log.i("TEST-QWE", "C5");
        }
        else if (linea.contains(">=")) {
            this.operador = ">=";
            this.atributo = kbase.findAtributos(linea.split(this.operador)[0].split("]")[0].substring(1).trim().toUpperCase());
            this.valor.add(new Valor(kbase, linea.split(this.operador)[1].replace("\"", "").trim().toUpperCase()));
            if (linea.split(this.operador)[1].contains("]"))
                this.atributo2 = kbase.findAtributos(linea.split(this.operador)[1].replace("\"", "").trim().toUpperCase().split("]")[0].substring(1).trim().toUpperCase());
            else
                this.atributo2 = this.atributo;
        }
        else if (linea.contains(">")) {
            this.operador = ">";
            this.atributo = kbase.findAtributos(linea.split(this.operador)[0].split("]")[0].substring(1).trim().toUpperCase());
            this.valor.add(new Valor(kbase, linea.split(this.operador)[1].replace("\"", "").trim().toUpperCase()));
            if (linea.split(this.operador)[1].contains("]"))
                this.atributo2 = kbase.findAtributos(linea.split(this.operador)[1].replace("\"", "").trim().toUpperCase().split("]")[0].substring(1).trim().toUpperCase());
            else
                this.atributo2 = this.atributo;
        }
        else if (linea.contains("!=")) {
            this.operador = "!=";
            this.atributo = kbase.findAtributos(linea.split(this.operador)[0].split("]")[0].substring(1).trim().toUpperCase());
            this.valor.add(new Valor(kbase, linea.split(this.operador)[1].replace("\"", "").trim().toUpperCase()));
            if (linea.split(this.operador)[1].contains("]"))
                this.atributo2 = kbase.findAtributos(linea.split(this.operador)[1].replace("\"", "").trim().toUpperCase().split("]")[0].substring(1).trim().toUpperCase());
            else
                this.atributo2 = this.atributo;
        }
        else if (linea.contains("=")) {
            this.operador = "=";
            this.atributo = kbase.findAtributos(linea.split(this.operador)[0].split("]")[0].substring(1).trim().toUpperCase());
            this.valor.add(new Valor(kbase, linea.split(this.operador)[1].replace("\"", "").trim().toUpperCase()));
            if (linea.split(this.operador)[1].contains("]"))
                this.atributo2 = kbase.findAtributos(linea.split(this.operador)[1].replace("\"", "").trim().toUpperCase().split("]")[0].substring(1).trim().toUpperCase());
            else
                this.atributo2 = this.atributo;
        }
        else if (linea.contains("!:")) {
            this.operador = "!:";
            this.atributo = kbase.findAtributos(linea.split(this.operador)[0].split("]")[0].substring(1).trim().toUpperCase());
            this.atributo2 = this.atributo;
            for (String s : linea.split(this.operador)[1].trim().toUpperCase().split("\" \"")){
                this.valor.add(new Valor(kbase, s.replace("\"","")));
            }
        }
        else if (linea.contains("!")) {
            this.operador = "!";
            this.atributo = kbase.findAtributos(linea.split(this.operador)[0].split("]")[0].substring(1).trim().toUpperCase());
            this.atributo2 = this.atributo;
            this.valor.add(new Valor(kbase, linea.split(this.operador)[1].replace("\"","").trim().toUpperCase()));
        }
        else if (linea.contains(":")) {
            this.operador = ":";
            this.atributo = kbase.findAtributos(linea.split(this.operador)[0].split("]")[0].substring(1).trim().toUpperCase());
            this.atributo2 = this.atributo;
            for (String s : linea.split(this.operador)[1].trim().toUpperCase().split("\" \"")){
                Log.i("TEST","*** ".concat(s.replace("\"","")));
                this.valor.add(new Valor(kbase, s.replace("\"","")));
            }
        } else if (linea.contains("?")) {
            this.operador = "?";
            this.atributo = kbase.findAtributos(linea.split(this.operador)[0].split("]")[0].substring(1).trim().toUpperCase());
            this.atributo2 = this.atributo;
            this.valor.add(new Valor(kbase, "TRUE"));
        }
        else {
            this.operador = null;
            this.atributo = null;
            this.atributo2 = null;
            this.valor = null;
        }
        Log.i("TEST-Condicion.new","5");
    }


    public boolean isTrue () {
        //boolean b = false;
        ////for (String s : this.valor) {
        //for (Valor v : this.valor) {
        //    String s = v.getValor();
        //    if (this.atributo.isTrue(this.operador, this.valor) && v.isValido())
        //        b = true;
        //}
        //return b;
        Log.i("TEST-Condicion.isTrue", "1");
        Log.i("TEST-Condicion.isTrue", this.atributo.getName());
        Log.i("TEST-Condicion.isTrue", this.atributo2.getName());
        if (this.atributo2 != this.atributo) return this.atributo.isTrue(this.operador, this.atributo2);
        else return this.atributo.isTrue(this.operador, this.valor);
    }


    public boolean isValid () {
        //Log.i("TEST-QWE", "isValid");
        //Log.i("TEST-QWE", this.atributo.getName());
        //if (this.atributo.isValid())  Log.i("TEST-QWE", "TRUE");
        //Log.i("TEST-QWE", this.atributo2.getName());
        //if (this.atributo2.isValid())  Log.i("TEST-QWE", "TRUE");
        return this.atributo.isValid() && this.atributo2.isValid();
    }


    public boolean isCf () {
        Log.i("TEST-Condicion.isCf", "1");
        return this.atributo.isCf() && this.atributo2.isCf();
    }


    public Atributo getAtributo() {
        return atributo;
    }
    public Atributo getAtributo2() {
        return atributo2;
    }
    public void setAtributo(Atributo atributo) {
        this.atributo = atributo;
    }

    public String getOperador() {
        return operador;
    }
    public void setOperador(String operador) {
        this.operador = operador;
    }

    //public ArrayList<String> getValor() {
    //    return valor;
    //}
    //public void setValor(ArrayList<String> valor) {
    //    this.valor = valor;
    //}

    public String toText(){
        String s = "";
        for (Valor a : this.valor) {
            String v = a.getValor();
            if (! s.equals(""))
                s = s.concat(", ");
            s = s.concat(v);
        }
        s = this.atributo.getName().concat(" ").concat(this.operador).concat(" ").concat(s).concat("\n");
        return s;
    }
}
