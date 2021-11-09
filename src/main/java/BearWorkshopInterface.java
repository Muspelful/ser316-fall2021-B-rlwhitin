package main.java;

public interface BearWorkshopInterface {
    
    public double getCost(Bear bear);
    
    public boolean addBear(Bear bear);
    
    public boolean removeBear(Bear bear);
    
    public double checkout();
    
    public double calculateTax();

}