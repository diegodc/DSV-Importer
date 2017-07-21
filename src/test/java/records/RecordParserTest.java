package records;

import org.junit.jupiter.api.Test;
import parsing.TokenDelimiter;
import records.exceptions.NoSuchField;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * RecordParserTest
 *
 * @author diegodc 2015-04-07.
 */
class RecordParserTest {

    private RecordParser record;

    private void makeRecord(List<String> fieldNames, String fields) {
        RecordFormat format = new RecordFormat(fieldNames, TokenDelimiter.COMMA);
        record = new RecordParser(fields, format);
    }

    @Test
    void numberOfFieldsMatchesGivenLine() {
        List<String> fieldNames = new ArrayList<>();
        fieldNames.add("field");

        makeRecord(fieldNames, "value");
        assertEquals(1, record.numberOfFields());

        List<String> moreFieldNames = new ArrayList<>();
        moreFieldNames.add("field 1");
        moreFieldNames.add("field 2");
        moreFieldNames.add("field 3");

        makeRecord(moreFieldNames, "value 1,value 2,value 3");
        assertEquals(3, record.numberOfFields());
    }

    @Test
    void getStringFieldByName() {
        List<String> fieldNames = new ArrayList<>();
        fieldNames.add("string 1");
        fieldNames.add("string 2");

        makeRecord(fieldNames, "value 1,value 2");
        assertEquals("value 1", record.field("string 1").stringValue());
        assertEquals("value 2", record.field("string 2").stringValue());
    }

    @Test
    void getIntegerFieldByName() {
        List<String> fieldNames = new ArrayList<>();
        fieldNames.add("int 1");
        fieldNames.add("int 2");

        makeRecord(fieldNames, "1,3");
        assertEquals(1, record.field("int 1").intValue());
        assertEquals(3, record.field("int 2").intValue());
    }

    @Test
    void getDoubleFieldByName() {
        List<String> fieldNames = new ArrayList<>();
        fieldNames.add("double 1");
        fieldNames.add("double 2");
        fieldNames.add("double 3");

        makeRecord(fieldNames, "1.2,3.4,4.5");
        assertEquals(1.2, record.field("double 1").doubleValue());
        assertEquals(3.4, record.field("double 2").doubleValue());
        assertEquals(4.5, record.field("double 3").doubleValue());
    }

    @Test
    void getDifferentTypeOfFieldsByName() {
        List<String> fieldNames = new ArrayList<>();
        fieldNames.add("string");
        fieldNames.add("double");
        fieldNames.add("int");

        makeRecord(fieldNames, "value,3.51,47");
        assertEquals("value", record.field("string").stringValue());
        assertEquals(3.51, record.field("double").doubleValue());
        assertEquals(47, record.field("int").intValue());
    }

    @Test
    void acceptsDifferentDelimiters() {
        List<String> fieldNames = new ArrayList<>();
        fieldNames.add("string");
        fieldNames.add("double");
        fieldNames.add("int");

        String commaSeparatedValues = "value,2.34,57";
        String tabSeparatedValues = "value\t2.34\t57";
        String verticalBarSeparatedValues = "value|2.34|57";

        RecordFormat commaFormat = new RecordFormat(fieldNames, TokenDelimiter.COMMA);
        RecordFormat tabFormat = new RecordFormat(fieldNames, TokenDelimiter.TAB);
        RecordFormat verticalBarFormat = new RecordFormat(fieldNames, TokenDelimiter.VERTICAL_BAR);

        assertFields(new RecordParser(commaSeparatedValues, commaFormat));
        assertFields(new RecordParser(tabSeparatedValues, tabFormat));
        assertFields(new RecordParser(verticalBarSeparatedValues, verticalBarFormat));
    }

    private void assertFields(RecordParser recordParser) {
        assertEquals("value", recordParser.field("string").stringValue());
        assertEquals(2.34, recordParser.field("double").doubleValue());
        assertEquals(57, recordParser.field("int").intValue());
    }

    @Test
    void fieldsAreTrimmed() {
        List<String> fieldNames = new ArrayList<>();
        fieldNames.add("string");
        fieldNames.add("int");
        fieldNames.add("double");

        makeRecord(fieldNames, " value   ,  2 ,  4.7 ");
        assertEquals("value", record.field("string").stringValue());
        assertEquals(2, record.field("int").intValue());
        assertEquals(4.7, record.field("double").doubleValue());
    }

    @Test
    void throwsNoSuchFieldIfFieldNameIsIncorrect() {
        List<String> fieldNames = new ArrayList<>();
        fieldNames.add("string");
        fieldNames.add("int");

        makeRecord(fieldNames, "value,1");

        assertThrows(NoSuchField.class, () -> record.field("incorrect name"));
    }

    @Test
    void throwsFormatExceptionIfFieldTypeIsIncorrect() {
        List<String> fieldNames = new ArrayList<>();
        fieldNames.add("field");

        makeRecord(fieldNames, "value");

        assertThrows(NumberFormatException.class, () -> record.field("field").intValue());
    }

    @Test
    void throwsExceptionIfNoFieldsAreGiven() {
        List<String> fieldNames = new ArrayList<>();
        fieldNames.add("field");

        assertThrows(RecordParser.InvalidNumberOfFields.class, () -> makeRecord(fieldNames, ""));
    }

    @Test
    void throwsExceptionIfLessFieldsThanExpectedAreGiven() {
        List<String> fieldNames = new ArrayList<>();
        fieldNames.add("field 1");
        fieldNames.add("field 2");
        fieldNames.add("field 3");

        assertThrows(RecordParser.InvalidNumberOfFields.class, () -> makeRecord(fieldNames, "value 1,value 2"));
    }

    @Test
    void throwsExceptionIfMoreFieldsThanExpectedAreGiven() {
        List<String> fieldNames = new ArrayList<>();
        fieldNames.add("field 1");
        fieldNames.add("field 2");
        fieldNames.add("field 3");

        assertThrows(RecordParser.InvalidNumberOfFields.class, () -> makeRecord(fieldNames, "value 1,value 2,value 3,value 4"));
    }

    @Test
    void throwsExceptionIfNoFieldNamesAreGiven() {
        List<String> fieldNames = new ArrayList<>();

        assertThrows(RecordParser.InvalidNumberOfFields.class, () -> makeRecord(fieldNames, "value"));
    }

}
