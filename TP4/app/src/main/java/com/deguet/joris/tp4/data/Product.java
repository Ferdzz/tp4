package com.deguet.joris.tp4.data;

/**
 * Created by 1452284 on 2016-04-12.
 */
public class Product {
    public Long id;
    public String name;
    public int amount;
    public float price;
    public String codeBarre;
    public boolean is2for1, isTaxable;

    public Product(String name, int amount, float price) {
        this.name = name;
        this.amount = amount;
        this.price = price;
        this.is2for1 = false;
        this.isTaxable = true;
    }

    public Product(String name, float price, String codeBarre) {
        this(name, 0, price);
        this.codeBarre = codeBarre;
    }

    public Product(String name, float price, String codeBarre, boolean is2for1, boolean isTaxable) {
        this(name, price, codeBarre);
        this.is2for1 = is2for1;
        this.isTaxable = isTaxable;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public String toString() {
        if(amount == 0)
            return "Product [id=" + id + ", name=" + name
                    + ", codeBarre=" + codeBarre
                    + ", price=" + price + "]";
        else
            return "Product [amount=" + amount + ", name=" + name
                    + ", codeBarre=" + codeBarre
                    + ", price=" + price + "]";
    }

    @Override
    public Product clone()  {
        Product product = new Product(name, price, codeBarre, is2for1, isTaxable);
        return product;
    }

    @Override
    public boolean equals(Object o) {
        if(!(o instanceof Product))
            return false;
        else
            return codeBarre.equals(((Product)o).codeBarre) && price == price && name.equals(name);
    }
}
