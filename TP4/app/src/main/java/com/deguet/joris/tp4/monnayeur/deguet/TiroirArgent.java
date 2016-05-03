package com.deguet.joris.tp4.monnayeur.deguet;

public interface TiroirArgent extends Change{

    /**
     * Renvoie la capacité maximale de ce Tiroir pour ce type d'items
     * @param m
     * @return
     */
    int capaciteMaxPour(ArgentPhysique m);

    /**
     * Retire plusieurs items du meme type
     * @param m le type
     * @param nombre le nombre d'items à retirer
     */
    void retirerItems(ArgentPhysique m, int nombre) throws ChangeService.UnCentException, ChangeService.ArgentNegatifException, ArgentException;
	
}
