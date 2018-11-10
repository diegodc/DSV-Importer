package functional;

/**
 * Car class.
 *
 * @author diegodc 2018-02-13.
 */
public class Car {

    private String make;
    private String model;
    private int year;
    private String engine;
    private  String displacement;
    private double fuelCapacity;

    Car(String make, String model, int year, String engine, String displacement, double fuelCapacity) {

        this.make = make;
        this.model = model;
        this.year = year;
        this.engine = engine;
        this.displacement = displacement;
        this.fuelCapacity = fuelCapacity;
    }

    @Override
    public String toString() {
        return make + " " + model + " [" + year + "] - Engine: "
                + engine + " " + displacement
                + " - Fuel Capacity: " + fuelCapacity + " gal";
    }

}
