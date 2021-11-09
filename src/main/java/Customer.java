package main.java;

import java.util.UUID;

/**
 * This a class for Customer in the Bear Workshop.
 */
public class Customer {
    private int age;
    public static final int CHILD_AGE = 13;
    public static final int MINIMUM_GUARDIAN_AGE = 18;

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
     * @param customer reference to guardian or null
     */
    public Customer(int age, String state, Customer customer) {
        this.parent = customer;
        this.age = age;
        this.customer_id = UUID.randomUUID().toString();

        this.state = state;
    }
    
    /**
     * @return the age
     */
    public int getAge() {
        return age;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Customer)) {
            return false;
        }
        Customer c = (Customer) o;
        if (!customer_id.equals(c.customer_id)) {
            return false;
        }
        return true;
    }
    
    @Override
    public int hashCode() {
        return customer_id.hashCode();
    }

    public int compareTo(Customer otherCustomer) {
        return customer_id.compareTo(otherCustomer.customer_id);
    }
}