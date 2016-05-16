package com.deguet.joris.tp4;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import com.deguet.joris.tp4.data.Product;
import com.deguet.joris.tp4.monnayeur.deguet.ArgentException;
import com.deguet.joris.tp4.monnayeur.deguet.ArgentPhysique;
import com.deguet.joris.tp4.monnayeur.deguet.Change;
import com.deguet.joris.tp4.monnayeur.deguet.ChangeService;
import com.deguet.joris.tp4.monnayeur.deguet.TiroirArgent;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 1146390 on 2016-05-03.
 */
public class TransactionDialog extends DialogFragment {

    /*
    TODO btnvoirEtatCaisse -- CECI N'EST PAS MENTIONNÉ DANS LE WORD?
    -- voir l'état du tirroir caisse en tout temps avec un btn dans le menu

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
    private List<Product> productsList;
    private TiroirArgent tiroir;
    private ChangeService changeService;

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
                        logFacture();

                        try {
                            double total = 0;
                            for (ArgentPhysique a : listArgent) {
                                total += a.getAmounts() * a.valeur();
                            }
                            Change change = changeService.calculerChange(total, tiroir);
                            new ChangeDialog().setChange(change).show(getFragmentManager(), "Transaction end");
//                            getDialog().dismiss();
                        } catch (ArgentException e) {
                            Toast.makeText(getActivity().getApplicationContext(), R.string.not_enough_money, Toast.LENGTH_SHORT).show();
                        }
                    }
                })
                .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        getDialog().cancel();
                    }
                });
        return builder.create();
    }

    public TransactionDialog setProductsList(List<Product> productsList) {
        this.productsList = new ArrayList<>(productsList);
        return this;
    }

    public TransactionDialog setChangeService(ChangeService changeService) {
        this.changeService = changeService;
        return this;
    }

    public TransactionDialog setTiroir(TiroirArgent tiroir) {
        this.tiroir = tiroir;
        return this;
    }
    
    private float logFacture() {
        Log.i("Facture", "  ");
        Log.i("Facture", "Liste des produits : ");
        for (Product p : productsList) {
            String string = "  " + p.name;
            if(p.is2for1)
                string += " (2 pour 1)";
            if(!p.isTaxable)
                string += " (non taxable)";
            string += "\t x" + p.amount;
            Log.i("Facture", "\t" + string);
        }

        float total = Utils.calculateTotalPrice(productsList);
        float taxes = Utils.calculateTaxes(productsList, total);
        Log.i("Facture", "Sous total : \t" + total + "$");
        Log.i("Facture", "   Taxes \t" + taxes + "$");
        Log.i("Facture", "Total : \t" + String.format("%.02f", (total + taxes)) + "$");
        Log.i("Facture", "  ");

        return total + taxes;
    }
}
