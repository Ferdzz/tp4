package com.deguet.joris.tp4;

import com.deguet.joris.tp4.monnayeur.deguet.*;
import com.deguet.joris.tp4.monnayeur.binette.*;
import org.junit.Assert;
import org.junit.Test;

/**
 * Created by 1146390 on 2016-03-23.
 */
public class TestChange implements TestInterface {
    @Test
    public void TestAjouter() throws ArgentException {
        Change changeTest = new BinetteChange();
        changeTest.ajouterItem(ArgentPhysique.billet100,1);
        Assert.assertEquals(1,changeTest.nombreTotalItems());
    }
    @Test (expected = ChangeService.ArgentNegatifException.class )
    public void testAjouterNeg() throws ArgentException {
        Change change = new BinetteChange();
        change.ajouterItem(ArgentPhysique.billet100,-1);
    }

    @Test
    public void testValeurTot() throws ArgentException {
        Change change = new BinetteChange();
        change.ajouterItem(ArgentPhysique.billet100,25);
        change.ajouterItem(ArgentPhysique.billet10,15);
        change.ajouterItem(ArgentPhysique.piece5s,7);
        change.ajouterItem(ArgentPhysique.piece1s,2);
        Assert.assertEquals(2650.37, change.valeurTotale(),0.001);
    }

    @Test
    public void testNombreItemTot() throws ArgentException {
        Change change = new BinetteChange();
        change.ajouterItem(ArgentPhysique.billet100,25);
        change.ajouterItem(ArgentPhysique.billet10,15);
        change.ajouterItem(ArgentPhysique.piece5s,7);
        Assert.assertEquals(47,change.nombreTotalItems());

    }

    @Test
    public void testNombreItem() throws ArgentException {
        Change change = new BinetteChange();
        change.ajouterItem(ArgentPhysique.billet100,25);
        change.ajouterItem(ArgentPhysique.billet10,15);
        change.ajouterItem(ArgentPhysique.piece5s,7);
        Assert.assertEquals(25,change.nombreItemsPour(ArgentPhysique.billet100));
        Assert.assertEquals(15,change.nombreItemsPour(ArgentPhysique.billet10));
        Assert.assertEquals(7,change.nombreItemsPour(ArgentPhysique.piece5s));
        Assert.assertEquals(0,change.nombreItemsPour(ArgentPhysique.billet50));

    }



    public ChangeService service() {
        return new BinetteChangeService();
    }

    public String nomEtudiant() {
        return "Charles Binette";
    }
}
