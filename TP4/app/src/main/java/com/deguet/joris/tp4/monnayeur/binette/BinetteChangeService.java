package com.deguet.joris.tp4.monnayeur.binette;


import com.deguet.joris.tp4.monnayeur.deguet.*;


/**
 * Created by 1146390 on 2016-02-23.
 */
public class BinetteChangeService implements ChangeService {
    public Change calculerChange(double montant, TiroirArgent tiroir) throws ArgentException {
        Change result = new BinetteChange();
        if (montant <= tiroir.valeurTotale()){
            double remaining = this.arrondiA5sous(montant);
            for (ArgentPhysique items : ArgentPhysique.values()) {
                if ((items.valeur() <= remaining) && (tiroir.nombreItemsPour(items) >= 1)) {
                    int remainingEnCent = (int) (remaining * 100);
                    int quantiteRequise = remainingEnCent / items.valeurEnCents;
                    if (quantiteRequise > 0)
                    {
                        if (tiroir.nombreItemsPour(items) >= quantiteRequise) {
                            result.ajouterItem(items, quantiteRequise);
                            tiroir.retirerItems(items, quantiteRequise);
                            remaining = ((double) (remainingEnCent - (items.valeurEnCents * quantiteRequise)))/100;
                        } else {
                            int nombreitems = tiroir.nombreItemsPour(items);
                            result.ajouterItem(items, nombreitems);
                            tiroir.retirerItems(items, nombreitems);
                            remaining = ((double) (remainingEnCent - (items.valeurEnCents * nombreitems))) / 100;
                        }
                      }

                }
            }
            if(remaining > 0)
            {
                for (ArgentPhysique items : ArgentPhysique.values())
                {
                    tiroir.ajouterItem(items,result.nombreItemsPour(items));
                }
                throw new ArgentException();
            }
            return result;

        }
        throw new MaxArgentException();
    }


    public double arrondiA5sous(double montant) throws ArgentNegatifException {
        if(montant < 0){
            throw new ArgentNegatifException();
        }
        int montantenCents = (int)(montant*100);
        switch (montantenCents%5)
        {

            case 1:
                return ((double)(montantenCents - 1)/100);
            case 2:
                return ((double)(montantenCents - 2)/100);
            case 3:
                return ((double)(montantenCents + 2)/100);
            case 4:
                return ((double)(montantenCents + 1)/100);
            case 0:
            default:
                return montant;
        }

    }

    public TiroirArgent tiroirPlein() {
        TiroirArgent NouveauTiroir = new BinetteTiroir();
        for (ArgentPhysique items: ArgentPhysique.values())
        {
                if(items.valeurEnCents != 1)
                    try {
                        NouveauTiroir.ajouterItem(items,NouveauTiroir.capaciteMaxPour(items));
                    } catch (Exception e) {}
        }

            return NouveauTiroir;

    }

    public TiroirArgent tiroirMoitierPlein() {
        TiroirArgent NouveauTiroir = new BinetteTiroir();
        for (ArgentPhysique items: ArgentPhysique.values())
        {
            if(items.valeurEnCents != 1)
                try {
                    NouveauTiroir.ajouterItem(items,(NouveauTiroir.capaciteMaxPour(items)/2));
                } catch (Exception e) {}
        }
        return NouveauTiroir;
    }
}
