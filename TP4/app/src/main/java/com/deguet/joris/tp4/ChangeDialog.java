package com.deguet.joris.tp4;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.deguet.joris.tp4.monnayeur.deguet.ArgentException;
import com.deguet.joris.tp4.monnayeur.deguet.ArgentPhysique;
import com.deguet.joris.tp4.monnayeur.deguet.Change;

import java.util.ArrayList;

/**
 * Created by Frederic on 2016-05-14.
 */
public class ChangeDialog extends DialogFragment {

    private Change change;

    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        final View v = inflater.inflate(R.layout.dialog_transaction_end, null);

        String s = "";
        for (ArgentPhysique a : ArgentPhysique.values()) {
            int amount = change.nombreItemsPour(a);
            if(amount > 0) {
                s += amount + "x " + a.nomLisible() + "\n";
            }
        }
        ((TextView)v.findViewById(R.id.transaction_end_listchange)).setText(s);
        ((TextView)v.findViewById(R.id.transaction_end_total)).setText(getResources().getString(R.string.total_change) +  String.format("%.02f", change.valeurTotale()) + " $");

        builder.setView(v)
                // Add action buttons
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        getDialog().dismiss();
                    }
                })
                .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        getDialog().cancel();
                    }
                });
        return builder.create();
    }

    public ChangeDialog setChange(Change change) {
        this.change = change;
        return this;
    }
}
