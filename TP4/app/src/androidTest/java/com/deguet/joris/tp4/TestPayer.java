package com.deguet.joris.tp4;

import com.deguet.joris.tp4.data.Product;
import com.deguet.joris.tp4.monnayeur.binette.BinetteChangeService;
import com.deguet.joris.tp4.monnayeur.deguet.ChangeService;

import junit.framework.Assert;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 1146390 on 2016-05-16.
 */
public class TestPayer implements TestInterface {

    @Override
    public ChangeService service() {
        return new BinetteChangeService();
    }

    @Override
    public String nomEtudiant() {
        return "Binette Charles et Frederic Deschênes";
    }

    @Test
    public void Test2pour1(){
        List<Product> listTest = new ArrayList<Product>();
        Product product2p1 = new Product("TEST",1.00F,"725272730836",true,false);
        listTest.add(product2p1);
        product2p1.amount = 3;

        Assert.assertEquals(2.00F, Utils.calculateTotalPrice(listTest), 0.01F);




    }
    @Test
    public void TestProduitGratuit(){


    }
    @Test
    public void TestTaxes(){


    }
    @Test
    public void TestSansTaxes(){


    }
}
