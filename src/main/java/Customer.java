package main.java;

import java.util.UUID;

/**
 * This a class for Customer in the Bear Workshop.
 */
public class Customer {
    int age;

    // customer has a name and a customer id
    Customer parent;
    String customer_id;

    // Customer lives in a state
    public String state;

    /**
     * Default ctor with state
     */
    public Customer(String state) {
        this.state = state;
        this.age = 18;
    }

    /**
     * Parameterized ctor for Customers
     * @param age int age of customer
     * @param custumer reference to guardian or null
     */
    public Customer(int age, String state, Customer custumer) {
        this.parent = custumer;
        this.age = age;
        this.customer_id = UUID.randomUUID().toString();

        this.state = state;
    }



}