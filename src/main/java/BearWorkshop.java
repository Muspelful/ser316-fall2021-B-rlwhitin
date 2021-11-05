package main.java;

import java.util.*;

// This class provides functionality for a BearWorkshop class.
public class BearWorkshop implements BearWorkshopInterface{
	// Workshop has a collection of bears
	// Workshop has a customer
	Customer customer;
	List<Bear> BearCart;
	private static final int GET_ONE_BEAR_FREE = 3;
	private static final int GET_ONE_CLOTHES_FREE = 3;
	private static final int TEN_PERCENT_OFF = 10;

	/**
	 * Default constructor for the Bear Workshop
	 */
	public BearWorkshop() {
		this("AZ");
	}

	/**
	 * This is a parameterized ctor for a BearWorkshop
	 * @param state customer is in
	 */
	public BearWorkshop(String state) {
		BearCart = new LinkedList<>();
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
		int numFree = bear.clothing.size() / 3;
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
		
		if(accessoryCount >= 10) {
			cost *= .9;
		}

		return cost;
	}

	// Function to get the raw cost of a bear without any discounts
	// Fixed in assignment 3: No longer resets the bear's price to zero. Now it just returns the value.
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
	public boolean addBear(Bear bear)		{
		return BearCart.add(bear);
	}

	// Simple means to remove a bear from the shopping cart
	// Fixed in assignment 3-- remove() from a linkedlist already returns a boolean, so the old version was redundant.
	@Override
	public boolean removeBear(Bear bear)	{
		return(BearCart.remove(bear));
	}

	/**
	 * This is a function to allow customers to checkout their shopping cart
	 * It should return the total cost of they purchase. 
	 * @return The checkout total
	 */
	// Fixed in assignment 3: massively simplified by using other functions and an enhanced for loop.
	@Override
	public double checkout() {
		if (this.customer.age <= 13 && this.customer.parent.age < 18) {
			System.out.println("Guardian is too young");
			return -1;
		}
		double totalRawCost = 0;
		for(Bear thisBear : BearCart) {
			totalRawCost += getRawCost(thisBear);
		}
		

		return (totalRawCost - calculateSavings())* calculateTax();
	}


	/**
	 * This method returns the savings for advertised bundle savings.
	 * Specifically, 
	 * - Bears are Buy 2 bears, get a third one free. It is always the cheapest bear that is free. The price here is meant when all discounts for a single bear are applied
	 * - It is 10% off the cost of a bear when a single bear has 10 or more accessories (anything on a bear is an accessory) that the customer pays for
	 * (so if clothes are free these do not count).
	 *  
	 * - Clothes are buy 2, get one free on each bear. Always the cheapest clothes are free. So if a bear has 6 clothes, 
	 * then the two cheapest ones will be free and it would count as 4 accessories (see above).
	 * 
	 * - Inking on a specific bear is free if and only if the bear without discounts applied to it costs more than $70.
	 *  
	 * TIP: the implemented savings method in the BearWorkshop1-5 do not use the getCost method implemented in this base class. They implement their own savings calculation
	 *  		 All of them do however use the getRawCost method implemented in this base class. 
	 *  
	 * EXAMPLE: You buy 3 bears, one bear has 3 clothing items, the other two have 4 clothing items. Non of them have embroidery or noise makers and they have the same stuffing. 
	 * Now, on each bear one clothing item will be free, since buy 2 get 1 free on a bear. So for costs we have the bear with stuffing. For one we pay only 2 clothing items, 
	 * for 2 we still pay for 3 clothing items.
	 *  
	 * Since all clothing is the same priece the bear with only 2 paid clothing items is cheapest. So we will get that bear for free. 
	 * We will only have to pay for 2 bears, with 3 clothing items each. 
	 * @return the savings if the customer would check with the current bears in the workshop out as double
	 */
	public double calculateSavings() {
		double savings = 0;
		

		for(int count = 0; count < BearCart.size(); count++) {
			//For some reason, getRawCost() feels the need to reset the bear's price to zero.
			//So we've added some variables to the bears to store their discounted and raw costs, rather than using it.
			Bear thisBear = BearCart.get(count);
			double rawCost = 0;
			for (int i = 0; i < thisBear.clothing.size(); i++) {
				Clothing clothes = thisBear.clothing.get(i);
				rawCost += clothes.price;

			}

			for (NoiseMaker noise: thisBear.noisemakers) {
				rawCost += noise.price;
			}

			if (thisBear.ink != null) {
				rawCost += thisBear.ink.price;
			}

			rawCost += thisBear.stuff.price;
			rawCost += thisBear.casing.priceModifier;
			thisBear.rawCost = rawCost;
			
			double discountedPrice = rawCost;
			//Embroidery is free if the bear costs more than $70.
			if(rawCost > 70) {
				discountedPrice -= thisBear.ink.price;
			}
			
			//Buy three clothes, get one free.
			int freeClothes = thisBear.clothing.size() / GET_ONE_CLOTHES_FREE;
			if(thisBear.clothing.size() > 2) {
				Collections.sort(thisBear.clothing);
				for(int iterations = 0; iterations < freeClothes; iterations++) {
					discountedPrice -= thisBear.clothing.get(iterations).price;
				}
			}
			
			//And if you have 10 or more non-free accessories, you get 10% off.
			int accessoryCount = thisBear.clothing.size() + thisBear.noisemakers.size();
			if(accessoryCount - freeClothes >= TEN_PERCENT_OFF) {
				discountedPrice = discountedPrice * .9;
			}
			thisBear.discountedCost = discountedPrice;
			thisBear.price = discountedPrice;
		}
		
		//Now, we sort the bears by adjusted price, make the cheapest one(s) free if applicable, and add any other savings.  
		Collections.sort(BearCart);
		int freeBears = BearCart.size() / GET_ONE_BEAR_FREE;
		
		for(int count = 0; count < BearCart.size(); count++) {
			Bear thisBear = BearCart.get(count);
			if(count < freeBears) {
				savings += thisBear.rawCost;
			}
			else {
				savings += thisBear.rawCost - thisBear.discountedCost;
			}
		}
		
		return savings;
	}
	
 
}