package com.deguet.joris.tp4;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.deguet.joris.tp4.data.Product;
import com.deguet.joris.tp4.monnayeur.deguet.ArgentPhysique;

import java.util.ArrayList;

/**
 * Created by 1146390 on 2016-05-03.
 */
public class TransactionDialog extends DialogFragment {

    /*
    TODO btnvoirEtatCaisse -- CECI N'EST PAS MENTIONNÉ DANS LE WORD?
    -- voir l'état du tirroir caisse en tout temps

    DONE OnCreate
    --afficher tous les possibilité d'argentphysique dans la liste


    DONE btnlistener pour ajouter/soustraire
    --ajouter des billet ou des pièces
    --a 0 ne supprime pas de la liste mais empêche d'aller plus bas que 0



    TODO btnlistener pour envoyer change
     --creer un objet change et l'envoyer au monayeur pour completer transaction
     --afficher un autre dialog avec l'argent a rendre total et les objet dans une liste

    */

    private ChangeAdapter adapter;
    private ArrayList<ArgentPhysique> listArgent;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        final View v = inflater.inflate(R.layout.dialog_transaction_start, null);

        listArgent = new ArrayList<>();
        for (ArgentPhysique argent : ArgentPhysique.values()) {
            listArgent.add(argent);
        }

        this.adapter = new ChangeAdapter(getActivity(), listArgent);
        ListView list = (ListView)v.findViewById(R.id.list_transaction);
        list.setAdapter(adapter);

        builder.setView(v)
                // Add action buttons
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {

                    }
                })
                .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        getDialog().cancel();
                    }
                });
        return builder.create();
    }
}
