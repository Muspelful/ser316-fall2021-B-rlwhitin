package main.java;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

// This class provides functionality for a BearWorkshop class.
public class BearWorkshop implements BearWorkshopInterface {
    // Workshop has a collection of bears
    // Workshop has a customer
    Customer customer;
    List<Bear> bearCart;
    //The number of bears to get one free
    private static int oneBearFree = 3;
    //The number of clothes on one bear to get one free
    private static int oneClothesFree = 3;
    //The number of accessories required to get a discount.
    private static int bulkAccThreshold = 10;
    private static double bulkAccDiscount = .1;

    /**
     * Default constructor for BearWorkshop.
     */
    public BearWorkshop() {
        this("AZ");
    }

    /**
     * This is a parameterized ctor for a BearWorkshop.
     * @param state customer is in
     */
    public BearWorkshop(String state) {
        bearCart = new LinkedList<>();
        customer = new Customer(state);
    }

    /**
     * This is a convenience method to calculate the cost of one bears in the
     * shopping cart for a customer in the BearFactory. It should take the 
     * accessory discounts into account correctly. 
     * @param bear to get cost of
     * @return double representation of bear cost
     */
    // Fixed in assignment 3: Did not apply the 10% discount for having ten accessories.
    // Also refactored to not change the bear's price variable.
    @Override
    public double getCost(Bear bear) {
        double cost = 0;
        Collections.sort(bear.clothing);
        int numFree = bear.clothing.size() / oneClothesFree;
        ArrayList<Clothing> freeClothes = new ArrayList<>();

        for (int i = 0; i < bear.clothing.size(); i++) {
            Clothing clothes = bear.clothing.get(i);
            if (i < numFree) {
                freeClothes.add(clothes);
            } else {
                cost += clothes.price;
            }
        }

        for (NoiseMaker noise: bear.noisemakers) {
            cost += noise.price;
        }

        if (bear.ink != null) {
            cost += bear.ink.price;
        }

        cost += bear.stuff.price;
        cost += bear.casing.priceModifier;

        int accessoryCount = bear.clothing.size() + bear.noisemakers.size() - freeClothes.size();

        if (accessoryCount >= bulkAccThreshold) {
            cost *= (1 - bulkAccDiscount);
        }

        return cost;
    }

    // Function to get the raw cost of a bear without any discounts
    // Fixed in assignment 3: No longer resets the bear's price to zero. Now it just returns the value.
    /**
     * Calculates the raw cost of a bear.
     * @param bear The bear being calculated
     * @return The raw cost
     */
    public double getRawCost(Bear bear) {
        double rawCost = 0;
        for (int i = 0; i < bear.clothing.size(); i++) {
            Clothing clothes = bear.clothing.get(i);
            rawCost += clothes.price;

        }

        for (NoiseMaker noise: bear.noisemakers) {
            rawCost += noise.price;
        }

        if (bear.ink != null) {
            rawCost += bear.ink.price;
        }

        rawCost += bear.stuff.price;
        rawCost += bear.casing.priceModifier;

        return rawCost;
    }

    /**
     * Utility method to calculate tax for purchases by customers in different
     * states.
     * You can assume these taxes are what we want, so they are not wrong.
     * @return
     */
    public double calculateTax() {
        double tax;
        switch (customer.state) {
            case "AZ":
                tax = 1.07;
                break;
            case "NY":
                tax = 1.09;
                break;
            case "VA":
                tax = 1.05;
                break;
            case "DC":
                tax = 1.105;
                break;
            case "CA":
                tax = 1.1;
                break;
            default:
                tax = 1.05;
                break;
        }
        return tax;
    }

    /**
     * Utility method to add a bear to the BearFactory - so to the shopping cart.
     * 
     * @param bear to add
     * @return true if successful, false otherwise
     */
    // Fixed in assignment 3: adding an element to a LinkedList will always be successful, so it could never reach the else.
    // Also, add() already returns true or false, so having this function check it was redundant.
    @Override
    public boolean addBear(Bear bear)        {
        return bearCart.add(bear);
    }

    // Simple means to remove a bear from the shopping cart
    // Fixed in assignment 3-- remove() from a linkedlist already returns a boolean, so the old version was redundant.
    @Override
    public boolean removeBear(Bear bear)    {
        return (bearCart.remove(bear));
    }

    /**
     * This is a function to allow customers to checkout their shopping cart
     * It should return the total cost of they purchase. 
     * @return The checkout total
     */
    // Fixed in assignment 3: massively simplified by using other functions and an enhanced for loop.
    @Override
    public double checkout() {
        if (this.customer.getAge() <= 13 && this.customer.parent.getAge() < 18) {
            System.out.println("Guardian is too young");
            return -1;
        }
        double totalRawCost = 0;
        for (Bear thisBear : bearCart) {
            totalRawCost += getRawCost(thisBear);
        }


        return (totalRawCost - calculateSavings()) * calculateTax();
    }


    /**
     * Returns the savings for advertised bundle savings.
     * - Bears are buy 2 bears, get a third one free. It is always the cheapest bear that is free.
     * The price here is meant when all discounts for a single bear are applied
     * - It is 10% off the cost of a bear when a single bear has 10 or more accessories
     * (anything on a bear is an accessory) that the customer pays for
     * (so if clothes are free these do not count).
     *  
     * <p>- Clothes are buy 2, get one free on each bear.
     * Always the cheapest clothes are free. So if a bear has 6 clothes, 
     * then the two cheapest ones will be free and it would count as 4 accessories (see above).
     * 
     * <p>- Inking on a specific bear is free if raw cost > $70.
     *  
     * <p>TIP: the implemented savings methods in the BearWorkshop1-5 classes
     * do not use the getCost method implemented in this base class.
     * They implement their own savings calculation
     * All of them do however use the getRawCost method implemented in this base class. 
     *  
     * <p>e.g.: You buy 3 bears, one bear has 3 clothing items, the other two have 4 clothing items.
     * None of them have embroidery or noise makers and they have the same StuffingTypes. 
     * Now, on each bear one clothing item will be free, since buy 2 get 1 free on a bear.
     * So for costs we have the bear with StuffingTypes. For one we pay only 2 clothing items, 
     * for 2 we still pay for 3 clothing items.
     * 
     * <p>Since all clothing is the same price the bear with only 2 paid clothing items is cheapest.
     * So we will get that bear for free. 
     * We will only have to pay for 2 bears, with 3 clothing items each. 
     * @return savings if the customer checks out with the current bears as double
     */
    public double calculateSavings() {
        double totalDiscountedCost = 0;
        double totalRawCost = 0;
        double totalSavings;
        for(Bear thisBear : bearCart) {
            totalRawCost += getRawCost(thisBear);
            totalDiscountedCost += getCost(thisBear);
        }
        
        totalSavings = totalRawCost - totalDiscountedCost;
        
        Collections.sort(bearCart);
        int freeBears = bearCart.size() / oneBearFree;
        
        for(int count = 0; count < freeBears; count++) {
            totalSavings += getCost(bearCart.get(count));
        }
        
        return totalSavings;
    }

    /**
     * Changes the number of bears required to get one free.
     * Must be at least two.
     * @param oneBearFree the oneBearFree to set
     */
    public static void setOneBearFree(int oneBearFree) {
        if(oneBearFree > 1) {
            BearWorkshop.oneBearFree = oneBearFree;
        }
    }

    /**
     * Changes the number of required clothes to get one free.
     * Must be at least 2.
     * @param oneClothesFree the oneClothesFree to set
     */
    public static void setOneClothesFree(int oneClothesFree) {
        if (oneClothesFree > 1) {
            BearWorkshop.oneClothesFree = oneClothesFree;
        }
    }

    /**
     * Changes the number of accessories required for a discount.
     * Must be at least 0.
     * @param bulkAccThreshold the bulkAccThreshold to set
     */
    public static void setBulkAccThreshold(int bulkAccThreshold) {
        if(bulkAccThreshold >= 0) {
            BearWorkshop.bulkAccThreshold = bulkAccThreshold;
        }
    }

    /**
     * Changes the discount when buying many accessories.
     * Must be less than 100%.
     * @param bulkAccDiscount the bulkAccDiscount to set
     */
    public static void setBulkAccDiscount(double bulkAccDiscount) {
        if(bulkAccDiscount < 1) {
            BearWorkshop.bulkAccDiscount = bulkAccDiscount;
        }
    }


}