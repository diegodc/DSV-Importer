# DSV-importer

An easy way to read and convert Delimiter-separated values files.



Example (see [ExampleTest](src/test/java/functional/ExampleTest.java)):

We have a '[cars.dsv](src/test/java/functional/cars.dsv)' file (fields delimited by a vertical bar '|'):

    MAKE    MODEL           YEAR    ENGINE              DISPLACEMENT    FUEL_CAPACITY

    BMW     3-Series        2014    Intercooled I-6     3.0 L/182       15
    Ford    F-150           2010    Gas/Ethanol V8      5.4L/330        36
    Toyota  Camry Hybrid    2010    Gas/Electric I4     2.4L/144        17.2
    Toyota  Camry           2018    Regular V-6         3.5 L/211       16

We want to read the file and convert each record into a [Car](src/test/java/functional/Car.java) object.



First we need to define a [RecordConverter](src/main/java/records/conversion/RecordConverter.java).
DSV-importer will read the file, parse each record and use the RecordConverter to convert them.


Our custom converter looks like this:

    public class CarConverter implements RecordConverter<Car>


This converter has only one method that takes in a [RecordParser](src/main/java/records/RecordParser.java), and returns the class we want.

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


Now we only need to create a [RecordFile](src/main/java/records/RecordFile.java), and pass a [TextReader](src/main/java/readers/TextReader.java), the CarConverter, and the delimiter used in the file.

    TextReader reader = ReaderFactory.openReaderFromFile("cars.dsv");

    RecordFile<Car> file = new RecordFile<>(reader, new CarConverter(), TokenDelimiter.VERTICAL_BAR);

    List<Car> cars = file.getRecords();
