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
        product2p1.amount = 4;
        Assert.assertEquals(2.00F, Utils.calculateTotalPrice(listTest), 0.01F);
        product2p1.amount = 5;
        Assert.assertEquals(3.00F, Utils.calculateTotalPrice(listTest), 0.01F);
    }
    @Test
    public void TestProduitGratuit(){


    }
    @Test
    public void TestTaxes(){
        List<Product> listTest = new ArrayList<Product>();
        Product product1 = new Product("TEST1",1.00F,"725272730836",true,true);
        Product product2 = new Product("TEST2",2.00F,"725272730836",true,false);
        Product product3 = new Product("TEST3",3.00F,"725272730836",true,true);
        Product product4 = new Product("TEST4",4.00F,"725272730836",true,true);


    }
    @Test
    public void TestSansTaxes(){


    }
}
