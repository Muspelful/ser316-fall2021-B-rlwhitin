package main.java;

// You can assume the price of $1 per letter is correct

public class Embroidery {
    private static final double PRICE_PER_LETTER = 1.00;
    double price;
    String embroidText;

    public Embroidery(String embroidery) {
        this.embroidText = embroidery;
        this.price = embroidery.length() * PRICE_PER_LETTER;
    }

    /**
     * @return the embroidText
     */
    public String getEmbroidText() {
        return embroidText;
    }
}
