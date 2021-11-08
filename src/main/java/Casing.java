package main.java;

public class Casing {
    double priceModifier;

    String description;

    /**
     * @return the description
     */
    public String getDescription() {
        return description;
    }

    public Casing() {
        this(1.00, "Default outer shell");
    }

    public Casing(double price, String descr) {
        this.priceModifier = price;
        this.description = descr;
    }
}
