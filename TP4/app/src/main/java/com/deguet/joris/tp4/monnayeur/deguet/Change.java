package com.deguet.joris.tp4.monnayeur.deguet;

public interface Change {



    /**
     * Renvoie le nombre d'items de ce type contenu dans cet objet
     * @param m type
     * @return
     */
    int nombreItemsPour(ArgentPhysique m);

    /**
     * Ajouter un objet du type spécifié m fois.
     * @param m
     * @param nombre
     */
    void ajouterItem(ArgentPhysique m, int nombre) throws ChangeService.ArgentNegatifException, ArgentException;

    /**
     * Renvoie la valeur totale de l'objet en dollars
     * @return
     */
    double valeurTotale();

    /**
     * Renvoie le nombre total d'items contenu dans cet objet.
     * @return
     */
    int nombreTotalItems();

}
