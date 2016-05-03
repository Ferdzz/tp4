package com.deguet.joris.tp4.monnayeur.binette;

import com.deguet.joris.tp4.monnayeur.deguet.*;
/**
 * Created by 1146390 on 2016-02-23.
 */
public class BinetteTiroir implements TiroirArgent{
    private int[] quantitéDansTirroir = new int[11];

    public int capaciteMaxPour(ArgentPhysique m) {
        if(m.valeurEnCents > 200)
            return 40;
        else
            return 50;
    }

    public void retirerItems(ArgentPhysique m, int nombre) throws ArgentException {
        int index = m.ordinal();

        if(nombre < 0)
            throw new ChangeService.ArgentNegatifException();

        if (quantitéDansTirroir[index] >= nombre &&  index != 10)  {
            quantitéDansTirroir[index] -= nombre;
        }
        else
            throw new ChangeService.UnCentException();

    }

    public int nombreItemsPour(ArgentPhysique m) {

        int index = m.ordinal();
        return quantitéDansTirroir[index];
    }

    public void ajouterItem(ArgentPhysique m, int nombre) throws IllegalArgumentException, ChangeService.ArgentNegatifException {
        int index = m.ordinal();

        if(nombre < 0) {
            throw new ChangeService.ArgentNegatifException();
        }
        if ((quantitéDansTirroir[index] + nombre) <= capaciteMaxPour(m) )  {
            quantitéDansTirroir[index] += nombre;
        }
        else
            throw new IllegalArgumentException("Impossible d'ajouter autant de " + m.nomLisible() );

    }

    public double valeurTotale() {
        int valeurTotEnCents = 0;
        for (ArgentPhysique item: ArgentPhysique.values()) {
            valeurTotEnCents +=  item.valeurEnCents * nombreItemsPour(item);
        }
        double valeurTot = valeurTotEnCents / 100.00;

        return valeurTot;
    }

    public int nombreTotalItems() {
        int count = 0;
        for (int i = 0; i < quantitéDansTirroir.length; i++) {
            count += quantitéDansTirroir[i];
        }
        return count;
    }

}
