package main.java;

import java.util.LinkedList;
import main.java.Stuffing.stuffing;

public class Bear implements Comparable<Bear> {
    public Casing casing;
    public Stuffing stuff;
    public Embroidery ink; 
    public LinkedList<NoiseMaker> noisemakers; // accessory
    public LinkedList<Clothing> clothing; // accessory
    public double price;
    public double rawCost;
    public double discountedCost;
    // bear has a shell (requ)
    // bear has stuffing (req)
    // bear has a tattoo/emroider or not (opt)
    // bear has a noisemaker (opt)


    /**
     * Default constructor with basic stuffing.
     */
    public Bear() {
        this.casing = new Casing();
        this.stuff = new Stuffing(stuffing.BASE);
        noisemakers = new LinkedList<>();
        clothing = new LinkedList<>();
        ink = new Embroidery("");
        price = 0;
    }

    /**
     * Constructor which also sets stuffing.
     * @param stuff The stuffing for the bear.
     */
    public Bear(stuffing stuff) {
        this.casing = new Casing();
        this.stuff = new Stuffing(stuff);
        noisemakers = new LinkedList<>();
        clothing = new LinkedList<>();
        ink = new Embroidery("");
        price = 0;
    }

    /**
     * Sets the bear's price.
     * @param incomingPrice The new price.
     */
    public void setPrice(double incomingPrice) {
        this.price = incomingPrice;
    }

    /**
     * Adds a noisemaker.
     * @param noise The noisemaker being added.
     * @return True if successful, otherwise false.
     */
    public boolean addNoise(NoiseMaker noise) {
        if (this.noisemakers.size() >= 5) {
            return false;
        } else {
            for (NoiseMaker noisemaker: noisemakers) {
                if (noise.spot == noisemaker.spot) {
                    return false;
                }
            }
            noisemakers.add(noise);
            return true;
        }
    }
    
    /**
     * Calculates the bear's raw cost.
     * @return The bear's raw cost.
     */
    public double getRawCost() {
        double rawCost = 0;
        for (int i = 0; i < clothing.size(); i++) {
            Clothing clothes = clothing.get(i);
            rawCost += clothes.price;

        }

        for (NoiseMaker noise: noisemakers) {
            rawCost += noise.price;
        }

        if (ink != null) {
            rawCost += ink.price;
        }

        rawCost += stuff.price;
        rawCost += casing.priceModifier;
        return rawCost;
    }

    @Override
    public int compareTo(Bear bear) {
        return new Double(this.price).compareTo(bear.price);
    }
}