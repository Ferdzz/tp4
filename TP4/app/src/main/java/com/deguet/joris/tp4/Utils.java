package com.deguet.joris.tp4;

import com.deguet.joris.tp4.data.Product;

import java.util.List;

/**
 * Created by 1452284 on 2016-05-03.
 */
public class Utils {
    public static float calculateTotalPrice(List<Product> products) {
        float total = 0;

        for (Product p: products) {
            if(p.is2for1) { // 2 for 1
                total += (int)(p.amount % 2 + Math.floor(p.amount / 2)) * p.price;
            } else {
                total += p.amount * p.price;
            }
        }
        return total;
    }

    public static float calculateTaxes(List<Product> products, float totalPrice) {
        float totalTaxes = 0;
        for (Product p : products) {
            if(p.isTaxable) {
                totalTaxes += .15 * (priceForProductWithDiscount(products, p));
            }
        }
        return totalTaxes;
    }

    public static float priceForProductWithDiscount(List<Product> products, Product product) {
        float total = 0;
        for (Product p : products) {
            if(p.equals(product)) {
                if(p.is2for1) { // 2 for 1
                    total += (int)(p.amount % 2 + Math.floor(p.amount / 2)) * p.price;
                } else {
                    total += p.amount * p.price;
                }
            }
        }
        return total;
    }
}
