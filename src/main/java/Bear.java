package main.java;

import java.util.LinkedList;
import main.java.Stuffing.StuffingTypes;

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
    // bear has StuffingTypes (req)
    // bear has a tattoo/emroider or not (opt)
    // bear has a noisemaker (opt)


    /**
     * Default constructor with basic StuffingTypes.
     */
    public Bear() {
        this.casing = new Casing();
        this.stuff = new Stuffing(StuffingTypes.BASE);
        noisemakers = new LinkedList<>();
        clothing = new LinkedList<>();
        ink = new Embroidery("");
        price = 0;
    }

    /**
     * Constructor which also sets StuffingTypes.
     * @param stuff The StuffingTypes for the bear.
     */
    public Bear(StuffingTypes stuff) {
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
     * Returns true if the price is equal, otherwise false
     */
    @Override
    public boolean equals(Object o) {
        if(!(o instanceof Bear)) {
            return false;
        }
        
        Bear bear = (Bear) o;

        BearWorkshop shop = new BearWorkshop();
        if (!(shop.getCost(this) == shop.getCost(bear))) {
            return false;
        }
        
        
        return true;
    }

    /**
     * Aids in sorting by price.
     */
    @Override
    public int compareTo(Bear bear) {
        if(this.equals(bear)) {
            return 0;
        }
        else {
            return new Double(this.price).compareTo(bear.price);
        }
    }

    /**
     * Hashcode is the cost of the bear in cents, as an integer.
     * (So a hashcode of 250 means the bear costs $2.50.)
     */
    @Override
    public int hashCode() {
        BearWorkshop shop = new BearWorkshop();
        return (int) (shop.getCost(this) * 100);
    }
}