# DSV-importer

An easy way to read and convert Delimiter-separated values files.

Example (see ExampleTest.class):

We have a 'cars.dsv' file (fields delimited by a vertical bar '|'):

    MAKE    MODEL           YEAR    ENGINE              DISPLACEMENT    FUEL_CAPACITY

    BMW     3-Series        2014    Intercooled I-6     3.0 L/182       15
    Ford    F-150           2010    Gas/Ethanol V8      5.4L/330        36
    Toyota  Camry Hybrid    2010    Gas/Electric I4     2.4L/144        17.2
    Toyota  Camry           2018    Regular V-6         3.5 L/211       16

We want to import and convert each record into a Car object (see Car.class).

First we define a CarConverter object:

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

This converter has only one method that takes in a RecordParser, and returns the class we want.


Then:

    TextReader reader = ReaderFactory.openReaderFromFile("cars.dsv");

    RecordFile<Car> file = new RecordFile<>(reader, new CarConverter(), TokenDelimiter.VERTICAL_BAR);

    List<Car> cars = file.getRecords();