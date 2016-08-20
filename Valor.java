package edu.tesis.matias.tesis2015;

import android.util.Log;

public class Valor {
    private String valor;
    private Atributo atributo;
    private boolean valido;


    public Valor (KBase kbase, String valor) {
        if (valor.contains("]")) {
            this.atributo = kbase.findAtributos(valor.split("]")[0].substring(1).trim().toUpperCase());
            Log.i("TEST-QWE", valor);
            Log.i("TEST-QWE", valor.split("]")[0].substring(1).trim().toUpperCase());
            Log.i("TEST-QWE", this.atributo.getName());
            //Log.i("TEST-QWE", kbase.findAtributos(valor.split("]")[0].substring(1).trim().toUpperCase()).getValue());
        } else
            this.valor = valor;
        this.valido = true;
    }

    public boolean isValido() {
        return valido;
    }

    public void setValido(boolean valido) {
        this.valido = valido;
    }

    public String getValor() {
        if (valor.isEmpty())
            return this.atributo.getValue();
        else
            return valor;
    }

}
