package edu.tesis.matias.tesis2015;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;

public class AlertBoxOne {
    private AlertDialog.Builder dlgAlert;

    public AlertBoxOne (Context c, String titulo, String msg) {
        this.dlgAlert = new AlertDialog.Builder(c);

        this.dlgAlert.setMessage(msg);
        this.dlgAlert.setTitle(titulo);
        this.dlgAlert.setPositiveButton("OK", null);
        this.dlgAlert.setCancelable(true);

        this.dlgAlert.setPositiveButton("Ok",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                    }
                });
    }

    public void createShow() {
        this.dlgAlert.create().show();
    }

}
