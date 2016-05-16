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
import android.widget.TextView;
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
    private ProductsAdapter productAdapter;
    private MainActivity activity;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        final View v = inflater.inflate(R.layout.dialog_transaction_start, null);

        ((TextView)v.findViewById(R.id.lblTransaction)).setText(String.format("%.02f", (getTotal() + getTaxes(getTotal()))) + "$");

        listArgent = new ArrayList<>();
        for (ArgentPhysique argent : ArgentPhysique.values()) {
            listArgent.add(argent);
        }

        this.adapter = new ChangeAdapter(getActivity(), listArgent);
        final ListView list = (ListView)v.findViewById(R.id.list_transaction);
        list.setAdapter(adapter);

        builder.setView(v)
                // Add action buttons
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        try {
                            double totalDonne = 0;
                            double prix = getTotal() + getTaxes(getTotal());
                            for (ArgentPhysique a : listArgent) {
                                totalDonne += a.getAmounts() * a.valeur();
                            }

                            if (totalDonne - prix < 0) {
                                Toast.makeText(getActivity().getApplicationContext(), R.string.not_enough_money, Toast.LENGTH_LONG).show();
                                return;
                            }

                            Change change = changeService.calculerChange(totalDonne - prix, tiroir);
                            activity.products.clear();
                            activity.adapter.notifyDataSetChanged();
                            logFacture();

                            // put the new change in the tiroir
                            try {
                                for (ArgentPhysique a : listArgent) {
                                    tiroir.ajouterItem(a, a.getAmounts());
                                }
                            } catch (ArgentException e) {
                                Toast.makeText(getActivity().getApplicationContext(), R.string.too_much_money_in_bank, Toast.LENGTH_LONG).show();
                            }

                            new ChangeDialog().setChange(change).show(getFragmentManager(), "Transaction end");
                        } catch (ArgentException e) {
                            Toast.makeText(getActivity().getApplicationContext(), R.string.not_enough_money_in_bank, Toast.LENGTH_LONG).show();
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

    private void logFacture() {
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

        float total = getTotal();
        float taxes = getTaxes(total);
        Log.i("Facture", "Sous total : \t" + total + "$");
        Log.i("Facture", "   Taxes \t" + taxes + "$");
        Log.i("Facture", "Total : \t" + String.format("%.02f", (total + taxes)) + "$");
        Log.i("Facture", "  ");
    }

    private float getTotal() {
        return Utils.calculateTotalPrice(productsList);
    }

    private float getTaxes(float total) {
        return Utils.calculateTaxes(productsList, total);
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

    public TransactionDialog setProductAdapter(ProductsAdapter adapter) {
        this.productAdapter = adapter;
        return this;
    }

    public TransactionDialog setActivity(MainActivity activity) {
        this.activity = activity;
        return this;
    }
}
