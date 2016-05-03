package com.deguet.joris.tp4;

import android.test.AndroidTestCase;

import com.deguet.joris.tp4.data.Product;
import com.deguet.joris.tp4.data.ProductCRUD;

/**
 * <a href="http://d.android.com/tools/testing/testing_android.html">Testing Fundamentals</a>
 */
public class ApplicationTest extends AndroidTestCase {

    public void testCreateProduct() {
        ProductCRUD crud = new ProductCRUD(getContext());
        crud.deleteAll();
        assertTrue(crud.getAll().size() == 0);
        crud.save(new Product("Test1", 3.45F, "123412341230"));
        assertTrue(crud.getAll().get(0).name.equals("Test1"));
    }

    public void testDeleteAll() {
        ProductCRUD crud = new ProductCRUD(getContext());
        Product product = new Product("Test1", 30.45F, "123412341230");
        Product product2 = new Product("Test2", 30.45F, "112233445562");
        Product product3 = new Product("Test3", 30.45F, "725272730720");
        Product product4 = new Product("Test4", 30.45F, "725272730737");
        crud.saveMany(product, product2, product3, product4);
        crud.deleteAll();
    }

    public void testGetAll() {
        ProductCRUD crud = new ProductCRUD(getContext());
        crud.deleteAll();
        Product product = new Product("Test1", 30.45F, "123412341230");
        Product product2 = new Product("Test2", 30.45F, "112233445562");
        Product product3 = new Product("Test3", 30.45F, "725272730720");
        Product product4 = new Product("Test4", 30.45F, "725272730737");
        crud.saveMany(product, product2, product3, product4);
        assertTrue(crud.getAll().size() == 4);
    }

    public void testSaveOneLong() {
        ProductCRUD crud = new ProductCRUD(getContext());
        crud.deleteAll();
        Product product = new Product("Test1", 30.45F, "123412341230");
        Product product2 = new Product("Test2", 30.45F, "112233445562");
        crud.save(product);
        long ID = crud.save(product2);
        assertTrue(crud.getById(ID).equals(product2));
    }

    public void testSaveOneProduct() {
        ProductCRUD crud = new ProductCRUD(getContext());
        crud.deleteAll();
        Product product = new Product("Test1", 30.45F, "123412341230");
        Product product2 = new Product("Test2", 30.45F, "112233445562");
        crud.save(product);
        crud.save(product2);
        assertTrue(crud.getAll().contains(product2));
    }

    public void testSaveMany() {
        ProductCRUD crud = new ProductCRUD(getContext());
        crud.deleteAll();
        Product product = new Product("Test1", 30.45F, "123412341230");
        Product product2 = new Product("Test2", 30.45F, "112233445562");
        Product product3 = new Product("Test3", 30.45F, "725272730720");
        crud.saveMany(product, product2, product3);
        assertTrue(crud.getAll().size() == 3);
    }

    public void testSaveAndRetrieveProduct() {
        ProductCRUD crud = new ProductCRUD(getContext());
        crud.deleteAll();
        Product product = new Product("Test2", 30.45F, "123412341230");
        crud.save(product);
        crud.save(new Product("Junk", 10.45F, "725272730836"));
        assertTrue(crud.getAll().contains(product));
    }

    public void testDeleteProduct() {
        ProductCRUD crud = new ProductCRUD(getContext());
        crud.deleteAll();
        Product product = new Product("Test3", 4.54F, "725272730836");
        crud.save(product);
        assertTrue(crud.getAll().size() == 1);
        crud.deleteOne(product);
        assertTrue(crud.getAll().size() == 0);
    }

    public void testGetProductByUPC() {
        ProductCRUD crud = new ProductCRUD(getContext());
        crud.deleteAll();
        Product product = new Product("Test4-1", 4.54F, "725272730836");
        Product product2 = new Product("Test4-2", 30.45F, "123412341230");
        crud.saveMany(product, product2);
        assertEquals(product, crud.getByUPC(product.codeBarre));
    }

}