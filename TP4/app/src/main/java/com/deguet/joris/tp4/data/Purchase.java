package com.deguet.joris.tp4.data;

import com.deguet.joris.tp4.Utils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by 1452284 on 2016-04-26.
 */
public class Purchase {
    public Long id;
    public List<Product> products;
    public float total;
    public Date date;
    public float taxTotal = 0;

    public Purchase(List<Product> products) {
        this.products = products;
        this.date = new Date();

        this.total = Utils.calculateTotalPrice(products);
        this.taxTotal = Utils.calculateTaxes(products, this.total);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "Total: " + total + " pour les produits " + products.toString() + " au " + date;
    }

    @Override
    public boolean equals(Object o) {
        if(!(o instanceof Purchase))
            return false;
        else
            return products.equals(((Purchase) o).products) && date.equals(((Purchase) o).date) && total == ((Purchase) o).total;
    }
}
