package tdd.product;

import java.math.BigDecimal;

/**
 * Created by serg on 9/1/16.
 */
public class Product {

    private String name;
    private int quantity;  // it doesn't used in current release
    private BigDecimal price;

    public Product(String name, int quantity, String price) {
        this.name = name;
        this.quantity = quantity;
        this.price = new BigDecimal(price);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "Product{" +
            "name='" + name + '\'' +
            ", quantity=" + quantity +
            ", price=" + price +
            '}';
    }
}
