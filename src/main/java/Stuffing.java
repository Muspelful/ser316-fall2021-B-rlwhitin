package main.java;

public class Stuffing {
    public enum StuffingTypes {
        BASE,
        DOWN,
        FOAM
    }

    StuffingTypes polyStuffing;
    int price;

    public Stuffing (StuffingTypes interiorStuffing) {

        switch (interiorStuffing) {
            case BASE:
                this.polyStuffing = StuffingTypes.BASE;
                this.price = 30;
                break;
            case DOWN:
                this.polyStuffing = StuffingTypes.DOWN;
                this.price = 40;
                break;
            case FOAM:
                this.polyStuffing = StuffingTypes.FOAM;
                this.price = 50;
                break;
        }
    }
    
    /**
     * @return the polyStuffing
     */
    public StuffingTypes getPolyStuffing() {
        return polyStuffing;
    }
}
