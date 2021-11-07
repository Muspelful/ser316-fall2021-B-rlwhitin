package main.java;

// You can assume the price of $1 per letter is correct

public class Embroidery {
    final double pricePerLetter = 1.00;
    double price;
    String embroidText;

    public Embroidery(String embroidery) {
        this.embroidText = embroidery;
        this.price = embroidery.length() * pricePerLetter;
    }
}
