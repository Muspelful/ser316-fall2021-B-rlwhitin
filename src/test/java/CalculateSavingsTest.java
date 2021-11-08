package test.java;

import static org.junit.Assert.assertEquals;

import main.java.Bear;
import main.java.BearWorkshop;
import main.java.Clothing;
import main.java.Embroidery;
import main.java.NoiseMaker;
import main.java.Stuffing;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;




public class CalculateSavingsTest {
    
    private BearWorkshop bearShop;
    
    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
    }

    @AfterClass
    public static void tearDownAfterClass() throws Exception {
    }

    @Before
    public void setUp() throws Exception {
        bearShop = new BearWorkshop();
    }

    @After
    public void tearDown() throws Exception {
    }

    /**
     * Savings should be zero when no bears are being purchased.
     */
    @Test
    public void zeroBears() {
        assertEquals(bearShop.calculateSavings(), 0, .005);
    }
    
    /**
     * Ink should not be free if the bear costs exactly $70.
     */
    @Test
    public void noFreeInkAtSeventy() {
        Bear inkBear = new Bear(Stuffing.StuffingTypes.BASE);
        bearShop.addBear(inkBear);
        inkBear.clothing.add(new Clothing(35, "very fancy hat"));
        inkBear.ink = new Embroidery("test");
        
        assertEquals(0, bearShop.calculateSavings(), .005);
    }
    
    /**
     * Ink should not be free if the bear costs less than $70.
     */
    @Test
    public void noFreeInkBelowSeventy() {
        Bear inkBear = new Bear(Stuffing.StuffingTypes.BASE);
        bearShop.addBear(inkBear);
        inkBear.clothing.add(new Clothing(34, "very fancy hat"));
        inkBear.ink = new Embroidery("test");
        
        assertEquals(0, bearShop.calculateSavings(), .005);
    }
    
    /**
     * Ink should be free if the bear costs more than $70 before discounts,
     * even if discounts take the price to 70 or below.
     */
    @Test
    public void freeInkAboveSeventy() {
        Bear inkBear = new Bear(Stuffing.StuffingTypes.BASE);
        bearShop.addBear(inkBear);
        inkBear.clothing.add(new Clothing(26, "fancy hat"));
        inkBear.clothing.add(new Clothing(5, "shoddy boots"));
        inkBear.clothing.add(new Clothing(5, "glasses"));
        inkBear.ink = new Embroidery("test");
        
        assertEquals(9, bearShop.calculateSavings(), .005);
    }
    
    
    /**
     * Test three bears such that one bear would be the cheapest,
     * were it not for discounts on another bear.
     * Make sure that the cheapest bear, post-discount, is free.
     */
    @Test
    public void threeBearsWithClothingDiscounts() {
        Bear[] bears = new Bear[3];
        //bears[0] will have a raw cost of $75 but a discounted cost of $71, since the ink is free.
        bears[0] = new Bear(Stuffing.StuffingTypes.BASE);
        bears[0].clothing.add(new Clothing(40, "very, VERY fancy hat"));
        bears[0].ink = new Embroidery("test");
        //bears[1] will cost $73
        bears[1] = new Bear(Stuffing.StuffingTypes.BASE);
        bears[1].clothing.add(new Clothing(42, "very fancy hat"));
        //bears[2] will also cost $73
        bears[2] = new Bear(Stuffing.StuffingTypes.BASE);
        bears[2].clothing.add(new Clothing(42, "very fancy hat"));
        
        for (Bear thisBear : bears) {
            bearShop.addBear(thisBear);
        }
        
        
        assertEquals(75, bearShop.calculateSavings(), .005);
    }
    
    /**
     * Test that a bear is not discounted by 10% if it has ten accessories,
     * but some of them are free. 
     */
    @Test
    public void tenAccessoriesSomeFree() {
        Bear bear = new Bear(Stuffing.StuffingTypes.BASE);
        for (int count = 1; count <= 10; count++) {
            bear.clothing.add(new Clothing(5, "ring"));
        }
        
        bearShop.addBear(bear);
        
        assertEquals(15, bearShop.calculateSavings(), .005);
    }
    
    /**
     * Test that a bear is discounted by 10% if it has 10 non-free accessories.
     */
    @Test
    public void tenAccessories() {
        Bear bear = new Bear(Stuffing.StuffingTypes.BASE);
        for (int count = 1; count <= 14; count++) {
            bear.clothing.add(new Clothing());
        }
        
        bearShop.addBear(bear);
        
        assertEquals(23.1, bearShop.calculateSavings(), .005);
    }
    
    /**
     * Inking is not an accessory.
     * So, adding it as the tenth non-free item should not give a 10% discount.
     */
    @Test
    public void inkNotAccessory() {
        Bear bear = new Bear(Stuffing.StuffingTypes.BASE);
        for (int count = 1; count <= 13; count++) {
            bear.clothing.add(new Clothing());
        }
        
        bear.ink = new Embroidery("test");
        
        bearShop.addBear(bear);
        
        assertEquals(20, bearShop.calculateSavings(), .005);
    }
    
    /**
     * Noisemakers are accessories.
     * So, adding one as the tenth non-free accessory should give a 10% discount.
     */
    @Test
    public void noiseMakersAreAccessories() {
        Bear bear = new Bear(Stuffing.StuffingTypes.BASE);
        for (int count = 1; count <= 13; count++) {
            bear.clothing.add(new Clothing());
        }
        
        bear.addNoise(new NoiseMaker());
        
        bearShop.addBear(bear);
        
        assertEquals(23.7, bearShop.calculateSavings(), .005);
    }

}
