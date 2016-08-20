package edu.tesis.matias.tesis2015;

import android.app.Activity;
import android.util.Log;
import java.util.ArrayList;

public class Rule extends Activity {
    private String name;
    private ArrayList<Condicion> condicion;
    private String operador;
    private ArrayList<Resultado> resultadoThen;
    private ArrayList<Resultado> resultadoElse;


    public Rule () {
        this.name = null;
        this.condicion = new ArrayList<Condicion>();
        this.operador = null;
        this.resultadoThen = new ArrayList<Resultado>();
        this.resultadoElse = new ArrayList<Resultado>();
    }
    public Rule (String name, ArrayList<Condicion> condicion, String operador, ArrayList<Resultado> resultadoThen, ArrayList<Resultado> resultadoElse) {
        this.name = name;
        this.condicion = condicion;
        this.operador = operador;
        this.resultadoThen = resultadoThen;
        this.resultadoElse = resultadoElse;
    }


    public boolean isTrue() {
        boolean valor;
        boolean usar = false;
        boolean b;
        boolean tmp;

        if (this.operador.equals("OR")) b=false;
        else b=true;

        for (Condicion c : this.condicion) {
            if (c.isValid()) {
                usar=true;
                if (c.isCf()) valor=c.isTrue();
                else valor=false;

                tmp=b;
                if (this.operador.equals("OR")) b=tmp||valor;
                else b=tmp&&valor;
            }
        }
        if (usar) return b;
        else return true;
    }


    public void execute() {
        boolean usar = true;
        boolean resultado;
        boolean valor;
        boolean tmp;

        if (this.operador.equals("OR")) resultado=false;
        else resultado=true;

        Log.i("TEST2", "**** " + this.getName());
        for (Condicion c : this.condicion) {
            Log.i("TEST-Rule.execute", "1");
            valor=c.isValid();
            Log.i("TEST-Rule.execute", "2");
            if (!valor) usar=false;
            Log.i("TEST-Rule.execute", "3");

            if (c.isCf()) valor=c.isTrue();
            else valor=false;
            Log.i("TEST-Rule.execute", "4");
            tmp=resultado;
            if (this.operador.equals("OR")) resultado=tmp||valor;
            else resultado=tmp&&valor;

            if (!valor) resultado=false;
        }
        if (resultado) Log.i("TEST2", "---> true"); else Log.i("TEST2", "---> false");
        if (usar) Log.i("TEST2", "---> true"); else Log.i("TEST2", "---> false");
        if (usar) {
            int i = this.getCF();
            if (resultado) this.resultado(this, this.resultadoThen, i);
            else this.resultado(this, this.resultadoElse, i);
        }
    }


    public void resultado(Rule rule, ArrayList<Resultado> resultados, int i) {
        for (Resultado r : resultados) {
            Log.i("TEST2", r.getAtributo().getName());
            r.resultado(rule, i);
        }
    }


    public boolean hasResultado(Atributo a){
        boolean b = false;
        for (Resultado r : this.resultadoThen) {
            if (r.getAtributo().equals(a)) b = true;
        }
        for (Resultado r : this.resultadoElse) {
            if (r.getAtributo().equals(a)) b = true;
        }
        return b;
    }


    public int getCF() {
        int i=999;
        int t;

        for (Condicion c : this.condicion) {
            if (c.isValid()) {
                t = c.getAtributo().getCf();
                if (i == 999) i = t;
                else if (this.operador.equals("OR") && t > i) i = t;
                else if (this.operador.equals("AND") && t < i) i = t;
            }
        }

        return i;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<Condicion> getCondicion() {
        return condicion;
    }
    public void setCondicion(ArrayList<Condicion> condicion) {
        this.condicion = condicion;
    }
    public void addCondicion(KBase kbase, String condicion) {
        this.condicion.add(new Condicion(kbase, condicion));
    }

    public String getOperador() {
        return operador;
    }
    public void setOperador(String operador) {
        this.operador = operador;
    }

    public ArrayList<Resultado> getResultadoThen() {
        return resultadoThen;
    }
    public void setResultadoThen(ArrayList<Resultado> resultadoThen) {
        this.resultadoThen = resultadoThen;
    }
    public void addResultadoThen(KBase kbase, String resultado) {
        this.resultadoThen.add(new Resultado(kbase, resultado));
    }

    public ArrayList<Resultado> getResultadoElse() {
        return resultadoElse;
    }
    public void setResultadoElse(ArrayList<Resultado> resultadoElse) {
        this.resultadoElse = resultadoElse;
    }
    public void addResultadoElse(KBase kbase, String resultado) {
        this.resultadoElse.add(new Resultado(kbase, resultado));
    }

    public String toText(){
        String s = "";

        for (Condicion c : this.condicion) {
            if (! s.equals(""))
                s = s.concat("\n");
            s = s.concat("    * ").concat(c.toText());
        }
        s = "Regla ".concat(this.name).concat("\n").concat(s);

        s = s.concat("Entonces").concat("\n");
        for (Resultado r : this.resultadoThen) {
            s = s.concat("    * ").concat(r.toText());
        }

        if (!this.resultadoElse.isEmpty()) {
            s = s.concat("De otro modo").concat("\n");
            for (Resultado r : this.resultadoElse) {
                s = s.concat("    * ").concat(r.toText());
            }
        }

        for (Condicion c : this.condicion) {
            s = s.concat(c.getAtributo().toText()).concat("\n");
        }

        return s;
    }
}
