package test.java;

import java.lang.reflect.Constructor;

import java.util.Arrays;
import java.util.Collection;
import main.java.Bear;
import main.java.BearWorkshop;
import main.java.BearWorkshop1;
import main.java.BearWorkshop2;
import main.java.BearWorkshop3;
import main.java.BearWorkshop4;
import main.java.BearWorkshop5;
import main.java.Clothing;
import main.java.Embroidery;
import main.java.NoiseMaker;
import main.java.Stuffing;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

//import main.java.BearWorkshop;

import static org.junit.Assert.assertEquals;

/**
 * This class provides a framework to implement black box tests for various
 * implementations of the BearWorkshop Class. The BearWorkshop is having a
 * blowout sale and is offering the following savings.
 */
@RunWith(Parameterized.class)
public class GivenBlackBox {
    private Class<BearWorkshop> classUnderTest;

    @SuppressWarnings("unchecked")
    public GivenBlackBox(Object classUnderTest) {
        this.classUnderTest = (Class<BearWorkshop>) classUnderTest;
    }

    /**
     * I don't have any idea how this works.
     * It was never explained, and google isn't helping.
     * @return
     */
    @Parameters
    public static Collection<Object[]> courseGradesUnderTest() {
        Object[][] classes = {
                {BearWorkshop1.class},
                {BearWorkshop2.class},
                {BearWorkshop3.class},
                {BearWorkshop4.class},
                {BearWorkshop5.class}

        };
        return Arrays.asList(classes);
    }

    /**
     * Nor this.
     * @param name
     * @return
     * @throws Exception
     */
    private BearWorkshop createBearWorkshop(String name) throws Exception {
        Constructor<BearWorkshop> constructor = classUnderTest.getConstructor(String.class);
        return constructor.newInstance(name);
    }

    BearWorkshop oneBear;
    Double oneBearExpected;

    BearWorkshop threeBears;
    Double threeBearsExpected;

    BearWorkshop twoBears;
    Double twoBearsExpected;

    @Before
    public void setUp() throws Exception {
        
    }

    @After
    public void tearDown() throws Exception {
    }

    // sample test

    /**
     * Test examines a BearFactory with 1 simple bear in it. The bear costs $30
     * and there are no savings.
     */
    @Test
    public void oneBearNoSavings() {
        // One Bear base StuffingTypes, no saving expected
        
        BearWorkshop oneBear = null;
        try {
            oneBear = createBearWorkshop("NY");
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        oneBear.addBear(new Bear(Stuffing.StuffingTypes.BASE)); // $30 StuffingTypes + $1 casing -- should be no savings at all
        oneBearExpected = 0.00; // no savings since no clothing
        
        Double ans = oneBear.calculateSavings();
        assertEquals(oneBearExpected, ans);
    }


    // sample test
    @Test
    public void threeBearsSaveOnCheapest() {
         // Three Bears expected to not pay for cheapest one
        BearWorkshop threeBears = null;
        try {
            threeBears = createBearWorkshop("AZ");
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        threeBears.addBear(new Bear(Stuffing.StuffingTypes.BASE)); // this is the cheapest one
        threeBears.addBear(new Bear(Stuffing.StuffingTypes.DOWN));
        threeBears.addBear(new Bear(Stuffing.StuffingTypes.FOAM));
        threeBearsExpected = 31.00;

        Double ans = threeBears.calculateSavings();
        assertEquals(threeBearsExpected, ans);
    }

    // sample test
 
    @Test
    public void oneBearTest3clothings() {
        BearWorkshop bears = null;
        try {
            bears = createBearWorkshop("DC");
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        Bear customBear = new Bear(Stuffing.StuffingTypes.BASE); // $31
        bears.addBear(customBear);

        customBear.clothing.add(new Clothing(4, "Hat")); //$35
        customBear.clothing.add(new Clothing(4, "Sunglasses")); //$39
        customBear.clothing.add(new Clothing(4, "Shoes")); // free
        
        Double bearsExpected = 4.0; // one cloth item for free
        Double ans = bears.calculateSavings();
        assertEquals(bearsExpected, ans, 0.005);
    }
    
    /**
     * Savings should be zero when no bears are being purchased.
     */
    @Test
    public void zeroBears() {
        BearWorkshop bearShop = null;
        try {
            bearShop = createBearWorkshop("CA");
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        assertEquals(bearShop.calculateSavings(), 0, .005);
    }
    
    /**
     * Ink should not be free if the bear costs exactly $70.
     */
    @Test
    public void noFreeInkAtSeventy() {
        BearWorkshop bearShop = null;
        try {
            bearShop = createBearWorkshop("CA");
        } catch (Exception e) {
            e.printStackTrace();
        }
        
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
        BearWorkshop bearShop = null;
        try {
            bearShop = createBearWorkshop("CA");
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        Bear inkBear = new Bear(Stuffing.StuffingTypes.BASE);
        bearShop.addBear(inkBear);
        inkBear.clothing.add(new Clothing(34, "very fancy hat"));
        inkBear.ink = new Embroidery("test");
        
        assertEquals(0, bearShop.calculateSavings(), .005);
    }
    
    /**
     * Ink should be free if the bear costs more than $70 before discounts, even if discounts take the price to 70 or below.
     */
    @Test
    public void freeInkAboveSeventy() {
        BearWorkshop bearShop = null;
        try {
            bearShop = createBearWorkshop("CA");
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        Bear inkBear = new Bear(Stuffing.StuffingTypes.BASE);
        bearShop.addBear(inkBear);
        inkBear.clothing.add(new Clothing(26, "fancy hat"));
        inkBear.clothing.add(new Clothing(5, "shoddy boots"));
        inkBear.clothing.add(new Clothing(5, "glasses"));
        inkBear.ink = new Embroidery("test");
        
        assertEquals(9, bearShop.calculateSavings(), .005);
    }
    
    
    /**
     * Test three bears such that one bear would be the cheapest were it not for discounts on another bear.
     * 
     * Make sure that the cheapest bear, post-discount, is free.
     */
    @Test
    public void threeBearsWithClothingDiscounts() {
        BearWorkshop bearShop = null;
        try {
            bearShop = createBearWorkshop("CA");
        } catch (Exception e) {
            e.printStackTrace();
        }
        
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
     * Test that a bear is not discounted by 10% if it has ten accessories but some of them are free. 
     */
    @Test
    public void tenAccessoriesSomeFree() {
        BearWorkshop bearShop = null;
        try {
            bearShop = createBearWorkshop("CA");
        } catch (Exception e) {
            e.printStackTrace();
        }
        
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
        BearWorkshop bearShop = null;
        try {
            bearShop = createBearWorkshop("CA");
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        Bear bear = new Bear(Stuffing.StuffingTypes.BASE);
        for (int count = 1; count <= 14; count++) {
            bear.clothing.add(new Clothing());
        }
        
        bearShop.addBear(bear);
        
        assertEquals(23.1, bearShop.calculateSavings(), .005);
    }
    
    /**
     * Inking is not an accessory, so adding it as the tenth non-free item should not give a 10% discount.
     */
    @Test
    public void inkNotAccessory() {
        BearWorkshop bearShop = null;
        try {
            bearShop = createBearWorkshop("CA");
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        Bear bear = new Bear(Stuffing.StuffingTypes.BASE);
        for (int count = 1; count <= 13; count++) {
            bear.clothing.add(new Clothing());
        }
        
        bear.ink = new Embroidery("test");
        
        bearShop.addBear(bear);
        
        assertEquals(20, bearShop.calculateSavings(), .005);
    }
    
    /**
     * Noisemakers are accessories, so adding one as the tenth non-free accessory should give a 10% discount.
     */
    @Test
    public void noiseMakersAreAccessories() {
        BearWorkshop bearShop = null;
        try {
            bearShop = createBearWorkshop("CA");
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        Bear bear = new Bear(Stuffing.StuffingTypes.BASE);
        for (int count = 1; count <= 13; count++) {
            bear.clothing.add(new Clothing());
        }
        
        bear.addNoise(new NoiseMaker());
        
        bearShop.addBear(bear);
        
        assertEquals(23.7, bearShop.calculateSavings(), .005);
    }
}
