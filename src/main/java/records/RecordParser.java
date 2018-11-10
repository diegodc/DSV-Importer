package records;

import parsing.SimpleTokenizer;

import java.util.List;

/**
 * This object wraps each record, providing methods to retrieve and convert it's fields.
 *
 * @author diegodc 2015-04-07.
 */
public class RecordParser {

    private RecordFormat format;
    private List<String> fields;

    public RecordParser(String fieldsLine, RecordFormat format) {
        this.format = format;
        extractFields(fieldsLine);
    }

    private void extractFields(String fieldsLine) {
        fields = parseFields(fieldsLine);
        checkNumberOfFields();
    }

    private List<String> parseFields(String fieldsString) {
        return new SimpleTokenizer(format.getTokenDelimiter()).tokenizeAsList(fieldsString);
    }

    private void checkNumberOfFields() {
        int numberOfFields = fields.size();
        int expectedNumberOfFields = format.getFieldNames().size();

        if (numberOfFields != expectedNumberOfFields) {
            String message = "Expected " + expectedNumberOfFields
                            + " but was " + numberOfFields;
            throw new InvalidNumberOfFields(message);
        }
    }

    public final int numberOfFields() {
        return fields.size();
    }

    public Field field(String fieldName) {
        String fieldValue = fields.get(getFieldNumber(fieldName));
        return new Field(fieldValue.trim());
    }

    private int getFieldNumber(String fieldName) {
        return format.getFieldNumber(fieldName);
    }

    public static class Field {

        private final String value;

        private Field(String value) {
            this.value = value;
        }

        public String stringValue() {
            return value;
        }

        public int intValue() {
            return Integer.parseInt(value);
        }

        public double doubleValue() {
            return Double.parseDouble(value);
        }

    }

    public class InvalidNumberOfFields extends RuntimeException {
        public InvalidNumberOfFields(String message) {
            super(message);
        }
    }

}
