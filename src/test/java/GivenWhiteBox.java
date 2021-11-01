package test.java;

import main.java.*;

import static org.junit.Assert.*;

import org.junit.Before;

import org.junit.Test;

import main.java.BearWorkshop;


public class GivenWhiteBox {
    BearWorkshop oneBear;

    @Before
    public void setUp() throws Exception {
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
}
