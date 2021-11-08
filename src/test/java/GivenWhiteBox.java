package test.java;

import main.java.*;

import static org.junit.Assert.assertEquals;

import org.junit.Before;

import org.junit.Test;

import main.java.BearWorkshop;


public class GivenWhiteBox {
    BearWorkshop oneBear;
    BearWorkshop shop;

    @Before
    public void setUp() throws Exception {
        shop = new BearWorkshop();
    }


    /* The test calculated the price wrong.
     * The total cost of the bear should be 31 dollars, plus 7% for tax, which comes out to $33.17.
     */
    @Test
    public void checkoutOneBear() {
        // One Student
        oneBear = new BearWorkshop("AZ");
        oneBear.addBear(new Bear());
        Double ans = oneBear.checkout();
        assertEquals(33.17, ans, 0.005);
    }

    /*
     * Node and edge coverage
     * Tests 54-55-56
     */
    @Test
    public void hasNoiseMakerCost() {
        Bear bear = new Bear();
        bear.noisemakers.add(new NoiseMaker());
        assertEquals(shop.getCost(bear), 41, .005);
    }
    
    /*
     * Edge coverage
     * Tests 58-60
     */
    @Test
    public void nullInkCost() {
        Bear bear = new Bear();
        bear.ink = null;
        assertEquals(shop.getCost(bear), 31, .005);
    }
    
    /*
     * Node coverage
     * Tests 41-42-43-44-45-46-47-48-49-52-46-47-49-50-52-46-47-49-50-54-56-58-59-60-62-63-65
     */
    @Test
    public void threeClothesCost() {
        Bear bear = new Bear();
        for (int count = 0; count < 3; count++) {
            bear.clothing.add(new Clothing());
        }
        assertEquals(shop.getCost(bear), 39, .005);
    }
    
    @Test
    public void tenAccessoriesCost() {
        Bear bear = new Bear();
        for (int count = 0; count < 14; count++) {
            bear.clothing.add(new Clothing());
        }
        assertEquals(shop.getCost(bear), 63.9,.005);
    }
    
    @Test
    public void threeBears() {
        for (int count = 0; count < 3; count++) {
            shop.addBear(new Bear());
        }
        assertEquals(shop.calculateSavings(), 31, .005);
    }
    
    @Test
    public void testRemoveBear() {
        Bear bear = new Bear();
        shop.addBear(bear);
        assertEquals(shop.removeBear(bear), true);
    }
    
    @Test
    public void rawClothes() {
        Bear bear = new Bear();
        bear.clothing.add(new Clothing());
        assertEquals(shop.getRawCost(bear), 35, .005);
    }
    
    @Test
    public void rawNoisemaker() {
        Bear bear = new Bear();
        bear.noisemakers.add(new NoiseMaker());
        assertEquals(shop.getRawCost(bear), 41, .005);
    }
    
    @Test
    public void nullInkRawCost() {
        Bear bear = new Bear();
        bear.ink = null;
        assertEquals(shop.getRawCost(bear), 31, .005);
    }
    
    @Test
    public void newYorkTax() {
        BearWorkshop shopNY = new BearWorkshop("NY");
        assertEquals(shopNY.calculateTax(), 1.09, .005);
    }
    
    @Test
    public void virginiaTax() {
        BearWorkshop shopNY = new BearWorkshop("VA");
        assertEquals(shopNY.calculateTax(), 1.05, .005);
    }
    
    @Test
    public void dcTax() {
        BearWorkshop shopNY = new BearWorkshop("DC");
        assertEquals(shopNY.calculateTax(), 1.105, .005);
    }
    
    @Test
    public void californiaTax() {
        BearWorkshop shopNY = new BearWorkshop("CA");
        assertEquals(shopNY.calculateTax(), 1.1, .005);
    }
    
    @Test
    public void defaultTax() {
        BearWorkshop shopNY = new BearWorkshop("NH");
        assertEquals(shopNY.calculateTax(), 1.05, .005);
    }
    
    @Test
    public void tenAccessoriesSavings() {
        Bear bear = new Bear();
        for (int count = 0; count < 14; count++) {
            bear.clothing.add(new Clothing());
        }
        shop.addBear(bear);
        assertEquals(shop.calculateSavings(), 23.1,.005);
    }
    
    @Test
    public void noiseMakerSavings() {
        Bear bear = new Bear();
        bear.noisemakers.add(new NoiseMaker());
        shop.addBear(bear);
        assertEquals(shop.calculateSavings(), 0,.005);
    }
    
    @Test
    public void nullInkSavings() {
        Bear bear = new Bear();
        bear.ink = null;
        shop.addBear(bear);
        assertEquals(shop.calculateSavings(), 0,.005); 
    }
}
