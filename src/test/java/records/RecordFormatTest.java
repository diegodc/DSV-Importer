package records;

import org.junit.jupiter.api.Test;
import parsing.TokenDelimiter;
import records.exceptions.NoSuchField;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * RecordFormatTest
 *
 * @author diegodc 2015-04-07.
 */
class RecordFormatTest {

    @Test
    void testDelimiter() {
        List<String> names = listOf("field 1", "field 2", "field 3");

        RecordFormat format = new RecordFormat(names, TokenDelimiter.COMMA);
        assertEquals(TokenDelimiter.COMMA, format.getTokenDelimiter());
    }

    @Test
    void testFieldNames() {
        RecordFormat format = new RecordFormat(listOf("field 1", "field 2", "field 3"),
                TokenDelimiter.COMMA);

        List<String> expected = listOf("field 1", "field 2", "field 3");

        assertIterableEquals(expected, format.getFieldNames());
    }

    @Test
    void trimsFieldNames() {
        RecordFormat format = new RecordFormat(listOf(" field 1   ", "  field 2  ", "   field 3 "),
                TokenDelimiter.COMMA);

        List<String> expected = listOf("field 1", "field 2", "field 3");

        assertIterableEquals(expected, format.getFieldNames());
    }

    @Test
    void fieldNumbers() {
        RecordFormat format = new RecordFormat(listOf(" field 1 ", " field 2 ", " field 3 "),
                TokenDelimiter.COMMA);

        assertEquals(0, format.getFieldNumber("field 1"));
        assertEquals(1, format.getFieldNumber("field 2"));
        assertEquals(2, format.getFieldNumber("field 3"));
    }

    @Test
    void throwsNoSuchFieldIfFieldNameIsIncorrect() {
        RecordFormat format = new RecordFormat(listOf("field 1", "field 2"), TokenDelimiter.COMMA);

        assertThrows(NoSuchField.class, () -> format.getFieldNumber("incorrect name"));
    }

    private List<String> listOf(String... elements) {
        return Arrays.asList(elements);
    }

}
