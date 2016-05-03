package com.deguet.joris.tp4;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.deguet.joris.tp4.data.Product;
import com.deguet.joris.tp4.data.ProductCRUD;
import com.deguet.joris.tp4.data.Purchase;
import com.deguet.joris.tp4.data.PurchaseCRUD;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    public List<Product> products;
//    public List<Product> availableProducts;
    public ProductsAdapter adapter;
    public ProductCRUD productCRUD;
    public PurchaseCRUD purchaseCRUD;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        this.availableProducts = new ArrayList<>();
        this.products = new ArrayList<>();
        this.productCRUD = new ProductCRUD(getApplicationContext());
        this.purchaseCRUD = new PurchaseCRUD(getApplicationContext());

        this.adapter = new ProductsAdapter(MainActivity.this, products);
        ListView list = (ListView)findViewById(R.id.listProduct);
        list.setAdapter(adapter);

        final MainActivity that = this;
        ((Button) findViewById(R.id.btnScanner)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                IntentIntegrator integrator = new IntentIntegrator(that);
                integrator.initiateScan();
            }
        });

        ((Button) findViewById(R.id.btnAddProduct)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new NewProductDialog().setAct(that).show(getFragmentManager(), "Hey");
            }
        });

        ((Button)findViewById(R.id.btnPay)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pay();
            }
        });

        updatePrice(0);


//        File file = new File(getFilesDir(), "products.txt");
//        if(file.exists()) {
//            try {
//                BufferedReader reader = new BufferedReader(new FileReader(file));
//                String line;
//                while((line = reader.readLine()) != null) {
//                    String[] info = line.split(",");
//                    Product product = new Product(info[0], Float.parseFloat(info[1]), info[2]);
//                    availableProducts.add(product);
//                }
//                adapter.notifyDataSetChanged();
//                reader.close();
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.empty_products) {
//            clearAvailableProducts(this);
            productCRUD.deleteAll();
            products.clear();
            adapter.notifyDataSetChanged();
            return true;
        } else if (id == R.id.create_products) {
//            clearAvailableProducts(this);
            products.clear();

            ArrayList<Product> products = new ArrayList<>();
            products.add(new Product("Lait", 2.65F, "123412341230"));
            products.add(new Product("Bonbons", 10.45F, "112233445562"));
            products.add(new Product("T-shirt", 3.98F, "725272730720"));
            products.add(new Product("Colibri", 10.00F, "725272730737"));
            products.add(new Product("Soleil en boite", 10.00F, "725272730744"));
            products.add(new Product("Application de gestion", 10.25F, "725272730751"));
            products.add(new Product("Programmeur bas de gamme", 126.58F, "725272730805"));
            products.add(new Product("Banane deja mangee", 10.00F, "725272730812"));
            products.add(new Product("Mouette", 0.99F, "725272730836"));
            productCRUD.saveMany(products);

            return true;
        } else if (id == R.id.fill_list) {
            products.clear();
            if(productCRUD.getAll().size() < 3) {
                Toast.makeText(this, R.string.not_enough_products, Toast.LENGTH_SHORT).show();
                return true;
            }

            List<Integer> list = new ArrayList<Integer>();
            for(int i = 0; i < productCRUD.getAll().size(); i++){
                list.add(i);
            }
            Collections.shuffle(list);
            Integer[] randomArray = list.subList(0, 3).toArray(new Integer[3]);

            Random r = new Random();
            for (Integer i : randomArray) {
                Product p1 = productCRUD.getAll().get(i);
                Product p2 = new Product(p1.name, r.nextInt(10) + 1, p1.price);
                p2.codeBarre = p1.codeBarre;
                products.add(p2);
            }
            adapter.notifyDataSetChanged();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        IntentResult scanResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, intent);
        // quand un resultat valide a ete trouve
        if (scanResult != null) {
            scanToList(scanResult.getContents());
        }
    }

    public void plus(View v){
        Product i = (Product) v.getTag();
        i.amount++;
        adapter.notifyDataSetChanged();
    }

    public void minus(View v){
        Product i = (Product) v.getTag();
        i.amount--;
        if(i.amount == 0)
            products.remove(i);
        adapter.notifyDataSetChanged();
    }

    public void updatePrice(float total) {
        ((TextView)findViewById(R.id.lblTotal)).setText(getResources().getString(R.string.total).replace("%d", "" + String.format("%.02f", total)));
    }

    public void scanToList(String code) {
        for (Product p : productCRUD.getAll()) {
            if(p.codeBarre.equals(code)) {
                for (Product p2: products) {
                    if(p2.codeBarre.equals(p.codeBarre)) {
                        Toast.makeText(this, getResources().getText(R.string.duplicateProduct), Toast.LENGTH_LONG).show();
                        return;
                    }
                }
                Product p3 = new Product(p.name, 1, p.price);
                p3.codeBarre = p.codeBarre;

                products.add(p3);
                adapter.notifyDataSetChanged();
                return;
            }
        }
        Toast.makeText(this, getResources().getString(R.string.productNotFound), Toast.LENGTH_LONG).show();
    }

    public void pay() {
        if(products.size() <= 0) {
            Toast.makeText(getApplicationContext(), R.string.no_product_to_pay, Toast.LENGTH_SHORT).show();
            return;
        }

        for (Purchase p : purchaseCRUD.getAll()) {
            Log.i("Caisse", p.toString());
        }

        Purchase purchase = new Purchase(products);
        purchaseCRUD.save(purchase);

        products.clear();
        adapter.notifyDataSetChanged();
    }
}
