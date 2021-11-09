package main.java;

public class Clothing implements Comparable<Clothing> {
    public double price;
    private String description;

    //  you can assume that the price of $4 per clothing item is correct
    public Clothing() {
        this(4.00, "Generic Offtrack Separate");

    }

    public Clothing(double price, String descr) {
        this.price = price;
        this.description = descr;
    }
    
    /**
     * @return the description
     */
    public String getDescription() {
        return description;
    }
    
    @Override
    public boolean equals(Object o) {
        if(!(o instanceof Clothing)) {
            return false;
        }
        Clothing clothes = (Clothing) o;
        if(price != clothes.price) {
            return false;
        }
        return true;
    }

    public int compareTo(Clothing clothes) {
        return new Double(this.price).compareTo(clothes.price);
    }
    
    @Override
    public int hashCode() {
        return (int) (price * 100);
    }
}
