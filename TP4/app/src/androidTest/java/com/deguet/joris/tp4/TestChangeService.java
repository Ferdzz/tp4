package com.deguet.joris.tp4;

import com.deguet.joris.tp4.monnayeur.deguet.*;
import com.deguet.joris.tp4.monnayeur.binette.*;
import org.junit.Assert;
import org.junit.Test;

/**
 * Created by 1146390 on 2016-02-23.
 */
public class TestChangeService implements TestInterface {



    @Test
    public void testArrondiA5c() throws ChangeService.ArgentNegatifException {
        Assert.assertEquals(10.6,service().arrondiA5sous(10.61),0.001);
        Assert.assertEquals(10.6,service().arrondiA5sous(10.62),0.001);
        Assert.assertEquals(10.65,service().arrondiA5sous(10.63),0.001);
        Assert.assertEquals(10.65,service().arrondiA5sous(10.64),0.001);
        Assert.assertEquals(10.65,service().arrondiA5sous(10.66),0.001);
        Assert.assertEquals(10.65,service().arrondiA5sous(10.67),0.001);
        Assert.assertEquals(10.7,service().arrondiA5sous(10.68),0.001);
        Assert.assertEquals(10.7,service().arrondiA5sous(10.69),0.001);
    }
    @Test (expected = ChangeService.ArgentNegatifException.class )
    public void testArrondiA5cNeg() throws ChangeService.ArgentNegatifException {
        service().arrondiA5sous(-2.1);
    }

    @Test (expected = ChangeService.MaxArgentException.class )
    public void testServiceTropGrosMontant() throws ArgentException {
        Change change = service().calculerChange(999993.15,service().tiroirPlein());
    }

    @Test (expected = ArgentException.class )
    public void testPasAsserDeChange() throws ArgentException {
        TiroirArgent tiroirArgent = new BinetteTiroir();
        tiroirArgent.ajouterItem(ArgentPhysique.billet100,2);
        Change change = service().calculerChange(20.50,tiroirArgent);
    }

    @Test
    public void testTiroirplein()
    {
        TiroirArgent tiroirTest = service().tiroirPlein();
        Assert.assertEquals("Erreur Billet 100",40,tiroirTest.nombreItemsPour(ArgentPhysique.billet100));
        Assert.assertEquals("Erreur Billet 50",40,tiroirTest.nombreItemsPour(ArgentPhysique.billet50));
        Assert.assertEquals("Erreur Billet 20",40,tiroirTest.nombreItemsPour(ArgentPhysique.billet20));
        Assert.assertEquals("Erreur Billet 10",40,tiroirTest.nombreItemsPour(ArgentPhysique.billet10));
        Assert.assertEquals("Erreur Billet 5",40,tiroirTest.nombreItemsPour(ArgentPhysique.billet5));
        Assert.assertEquals("Erreur Piece 2",50,tiroirTest.nombreItemsPour(ArgentPhysique.piece2));
        Assert.assertEquals("Erreur Piece 1",50,tiroirTest.nombreItemsPour(ArgentPhysique.piece1));
        Assert.assertEquals("Erreur Piece 25s",50,tiroirTest.nombreItemsPour(ArgentPhysique.piece25s));
        Assert.assertEquals("Erreur Piece 10s",50,tiroirTest.nombreItemsPour(ArgentPhysique.piece10s));
        Assert.assertEquals("Erreur Piece 5s",50,tiroirTest.nombreItemsPour(ArgentPhysique.piece5s));
        Assert.assertEquals("Erreur Piece 1s",0,tiroirTest.nombreItemsPour(ArgentPhysique.piece1s));
    }

    @Test
    public void testExactitude()
    {
        TiroirArgent tiroirTest = service().tiroirPlein();
        Change change = new BinetteChange();
        Change change2 = new BinetteChange();
        Change change3 = new BinetteChange();
        Change change4 = new BinetteChange();

        try {
            change = service().calculerChange(30.23,tiroirTest);
            Assert.assertEquals(change.valeurTotale(), 30.25, 0.001);
            change2 = service().calculerChange(150.79,tiroirTest);
            Assert.assertEquals(change2.valeurTotale(), 150.8, 0.001);
            change3 = service().calculerChange(80.55,tiroirTest);
            Assert.assertEquals(change3.valeurTotale(), 80.55, 0.001);
            change4 = service().calculerChange(0.15,tiroirTest);
            Assert.assertEquals(change4.valeurTotale(), 0.15, 0.001);
        } catch (ArgentException e) {
            Assert.fail();
        }
    }

    @Test
    public void testAsserDeChangeSansAsserdeBillet() {
        TiroirArgent tiroirArgent = service().tiroirPlein();
        try {
            tiroirArgent.retirerItems(ArgentPhysique.billet20, 39);
            Change change = service().calculerChange(40.5, tiroirArgent);
            Assert.assertEquals(change.valeurTotale(), 40.5, 0.001);
            Assert.assertEquals(change.nombreItemsPour(ArgentPhysique.billet20), 1);
        }
        catch (ArgentException e)
        {
            Assert.fail();
        }
    }




    public ChangeService service() {
        return new BinetteChangeService();
    }

    public String nomEtudiant() {
        return "Binette Charles";
    }
}
