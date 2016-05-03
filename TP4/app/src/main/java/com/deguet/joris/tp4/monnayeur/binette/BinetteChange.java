package com.deguet.joris.tp4.monnayeur.binette;



import com.deguet.joris.tp4.monnayeur.deguet.ArgentException;
import com.deguet.joris.tp4.monnayeur.deguet.ArgentPhysique;
import com.deguet.joris.tp4.monnayeur.deguet.Change;
import com.deguet.joris.tp4.monnayeur.deguet.ChangeService;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by 1146390 on 2016-02-23.
 */
public class BinetteChange implements Change {

    private List<ArgentPhysique> items = new LinkedList<ArgentPhysique>();

    public int nombreItemsPour(ArgentPhysique m) {
        int result = 0;
        for (ArgentPhysique i : items)
            if (i.equals(m))
                result++;
        return result;
    }

    public void ajouterItem(ArgentPhysique m, int nombre) throws ArgentException {
        if(nombre >= 0) {
            for (int i = 0; i < nombre; i++) {
                items.add(m);
            }
        }
        else throw new ChangeService.ArgentNegatifException();
    }

    public double valeurTotale() {
        int valeurTot = 0;
        for (int i = 0; i < items.size() ; i++) {
            valeurTot += items.get(i).valeurEnCents;
        }
        double valeurTotDouble = valeurTot / 100.00;
        return  valeurTotDouble;
    }

    public int nombreTotalItems() {
        return items.size();
    }
}
