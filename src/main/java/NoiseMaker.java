package main.java;

public class NoiseMaker {
    public double price;
    String label;
    String recording;

    Location spot;

    /**
     * Default constructor.
     */
    public NoiseMaker() {
        this("Default Noise", "I wuv you", Location.CENTERBODY);
    }

    /**
     * Constructor with full parameters.
     * @param label The noisemaker's label
     * @param recording The recording
     * @param location The location
     */
    public NoiseMaker(String label, String recording,
                      Location location) {
        this.label = label;
        this.recording = recording;
        this.spot = location;
        switch (location) {
            // you can assume that the price given here for the noisemakers is correct
            case CENTERBODY:
                this.price = 10;
                break;
            default:
                this.price = 5;
                break;
        }
    }


    public enum Location {
        RIGHT_HAND, LEFT_HAND, RIGHT_FOOT, LEFT_FOOT, CENTERBODY
    }
}


