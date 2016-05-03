package com.deguet.joris.tp4;

import com.deguet.joris.tp4.monnayeur.deguet.*;
import com.deguet.joris.tp4.monnayeur.binette.*;
import org.junit.Assert;
import org.junit.Test;

/**
 * Created by 1146390 on 2016-03-23.
 */
public class TestTiroir implements TestInterface {

    public ChangeService service() {
        return new BinetteChangeService();
    }

    public String nomEtudiant() {
        return "Binette Charles";
    }

    @Test
    public void testAjouter() throws ArgentException {
        TiroirArgent tiroirTest = new BinetteTiroir();
        for (ArgentPhysique item: ArgentPhysique.values())
        {
            tiroirTest.ajouterItem(item,1);
            Assert.assertEquals("Ajouter Simple" + item.nomLisible , 1, tiroirTest.nombreItemsPour(item));
        }
         int nombreItems = tiroirTest.nombreTotalItems();
         Assert.assertEquals("Nombre Incorrecte" ,11, nombreItems);

        for (ArgentPhysique item: ArgentPhysique.values())
        {
            tiroirTest.ajouterItem(item,3);
            Assert.assertEquals("Ajouter Multiple" + item.nomLisible , 4, tiroirTest.nombreItemsPour(item));
        }
        nombreItems = tiroirTest.nombreTotalItems();
        Assert.assertEquals("Nombre Incorrecte" ,44, nombreItems);

    }
    @Test
    public void testRetirer(){
        TiroirArgent tiroirTest = service().tiroirPlein();

        for (ArgentPhysique item: ArgentPhysique.values())
        {
            if(item != ArgentPhysique.piece1s) {
                try {
                    tiroirTest.retirerItems(item, 1);
                } catch (ArgentException e) {
                    Assert.fail();
                }
                Assert.assertEquals("Retirer Simple" + item.nomLisible, tiroirTest.capaciteMaxPour(item) - 1, tiroirTest.nombreItemsPour(item));
            }
        }
        int nombreItems = tiroirTest.nombreTotalItems();
        Assert.assertEquals("Nombre Incorrecte" ,440, nombreItems);

        for (ArgentPhysique item: ArgentPhysique.values())
        {
            if(item != ArgentPhysique.piece1s) {
                try {
                    tiroirTest.retirerItems(item, 4);
                } catch (ArgentException e) {
                    Assert.fail();
                }
                Assert.assertEquals("Retirer Multiple" + item.nomLisible, tiroirTest.capaciteMaxPour(item) - 5, tiroirTest.nombreItemsPour(item));
            }
        }
        nombreItems = tiroirTest.nombreTotalItems();
        Assert.assertEquals("Nombre Incorrecte" ,400, nombreItems);

    }
    @Test (expected = ChangeService.UnCentException.class)
    public void testRetirer1s() throws ArgentException {
        TiroirArgent tiroir = new BinetteTiroir();
        tiroir.ajouterItem(ArgentPhysique.piece1s,2);
        tiroir.retirerItems(ArgentPhysique.piece1s, 1);
    }
    @Test(expected = IllegalArgumentException.class)
    public void testAjouterPlein() throws ArgentException {
        TiroirArgent tiroir = service().tiroirPlein();
        tiroir.ajouterItem(ArgentPhysique.billet100,1);

    }

    @Test
    public void testValeurTotal() throws ArgentException {
        TiroirArgent tiroir = new BinetteTiroir();
        tiroir.ajouterItem(ArgentPhysique.billet100,3);
        tiroir.ajouterItem(ArgentPhysique.billet10,3);
        tiroir.ajouterItem(ArgentPhysique.billet5,3);
        tiroir.ajouterItem(ArgentPhysique.piece25s,3);
        Assert.assertEquals(345.75 , tiroir.valeurTotale(), 0.001);
    }

    @Test (expected = ChangeService.ArgentNegatifException.class )
    public void testAjouterNeg() throws ArgentException {
        TiroirArgent tiroir = new BinetteTiroir();
        tiroir.ajouterItem(ArgentPhysique.billet100,-1);
    }

    @Test (expected = ChangeService.ArgentNegatifException.class )
    public void testRetirerNeg() throws ArgentException {
        TiroirArgent tiroir = new BinetteTiroir();
        tiroir.retirerItems(ArgentPhysique.billet100,-1);
    }

}
