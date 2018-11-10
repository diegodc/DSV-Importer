package functional;

import org.junit.jupiter.api.Test;
import parsing.TokenDelimiter;
import readers.ReaderFactory;
import readers.TextReader;
import records.RecordFile;
import records.RecordParser;
import records.conversion.RecordConverter;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertIterableEquals;

/**
 * Usage Example.
 *
 * @author diegodc 2018-02-13.
 */
class ExampleTest {

    private String[] expectedData = {
        "BMW 3-Series [2006] - Engine: Gas 6-Cyl 3.2L/195 - Fuel Capacity: 15.9 gal",
        "BMW 3-Series [2010] - Engine: Diesel I6 3.0L/183 - Fuel Capacity: 16.1 gal",
        "BMW 3-Series [2014] - Engine: Intercooled Turbo Gas/Electric I-6 3.0 L/182 - Fuel Capacity: 15.0 gal",
        "BMW 3-Series [2014] - Engine: Intercooled Turbo Premium Unleaded I-6 3.0 L/182 - Fuel Capacity: 15.8 gal",
        "BMW 3-Series [2018] - Engine: Intercooled Turbo Diesel I-4 2.0 L/122 - Fuel Capacity: 15.0 gal",
        "Ford F-150 Standard [1997] - Engine: Gas V8 4.6L/281 - Fuel Capacity: 30.0 gal",
        "Ford F-150 [2010] - Engine: Gas/Ethanol V8 5.4L/330 - Fuel Capacity: 36.0 gal",
        "Ford F-150 [2014] - Engine: Regular Unleaded V-8 6.2 L/380 - Fuel Capacity: 26.0 gal",
        "Ford F-150 [2014] - Engine: Twin Turbo Regular Unleaded V-6 3.5 L/213 - Fuel Capacity: 26.0 gal",
        "Toyota Camry Hybrid [2010] - Engine: Gas/Electric I4 2.4L/144 - Fuel Capacity: 17.2 gal",
        "Toyota Camry [1997] - Engine: Gas V6 3.0L/183 - Fuel Capacity: 18.5 gal",
        "Toyota Camry [2018] - Engine: Regular Unleaded V-6 3.5 L/211 - Fuel Capacity: 16.0 gal"
    };

    @Test
    void convertFile() {
        TextReader reader = ReaderFactory.openReaderFromFile("src/test/java/functional/cars.dsv");

        RecordFile<Car> file = new RecordFile<>(reader, new CarConverter(), TokenDelimiter.VERTICAL_BAR);

        List<Car> cars = file.getRecords();

        assertIterableEquals(Arrays.asList(expectedData),
                cars.stream().map(Car::toString).collect(Collectors.toList()));
    }

    private static class CarConverter implements RecordConverter<Car> {

        @Override
        public Car convert(RecordParser record) {
            String make = record.field("MAKE").stringValue();
            String model = record.field("MODEL").stringValue();
            int year = record.field("YEAR").intValue();
            String engine = record.field("ENGINE").stringValue();
            String displacement = record.field("DISPLACEMENT").stringValue();
            double fuelCapacity = record.field("FUEL_CAPACITY").doubleValue();

            return new Car(make, model, year, engine, displacement, fuelCapacity);
        }
    }
}
