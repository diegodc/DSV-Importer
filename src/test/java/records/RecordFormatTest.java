package records;


import org.junit.jupiter.api.Test;
import parsing.TokenDelimiter;
import records.exceptions.NoSuchField;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * RecordFormatTest
 *
 * @author diegodc 2015-04-07.
 */
class RecordFormatTest {

    @Test
    void testDelimiter() {
        List<String> names = new ArrayList<>();
        names.add("field 1");
        names.add("field 2");
        names.add("field 3");

        RecordFormat format = new RecordFormat(names, TokenDelimiter.COMMA);
        assertEquals(TokenDelimiter.COMMA, format.getTokenDelimiter());
    }

    @Test
    void testFieldNames() {
        List<String> names = new ArrayList<>();
        names.add("field 1");
        names.add("field 2");
        names.add("field 3");

        RecordFormat format = new RecordFormat(names, TokenDelimiter.COMMA);

        assertEquals(3, format.getFieldNames().size());

        Iterator<String> it = format.getFieldNames().iterator();
        assertEquals("field 1", it.next());
        assertEquals("field 2", it.next());
        assertEquals("field 3", it.next());
    }

    @Test
    void trimsFieldNames() {
        List<String> names = new ArrayList<>();
        names.add(" field 1   ");
        names.add("  field 2  ");
        names.add("   field 3 ");
        RecordFormat format = new RecordFormat(names, TokenDelimiter.COMMA);

        Iterator<String> it = format.getFieldNames().iterator();
        assertEquals("field 1", it.next());
        assertEquals("field 2", it.next());
        assertEquals("field 3", it.next());
    }

    @Test
    void fieldNumbers() {
        List<String> names = new ArrayList<>();
        names.add(" field 1 ");
        names.add(" field 2 ");
        names.add(" field 3 ");
        RecordFormat format = new RecordFormat(names, TokenDelimiter.COMMA);

        assertEquals(0, format.getFieldNumber("field 1"));
        assertEquals(1, format.getFieldNumber("field 2"));
        assertEquals(2, format.getFieldNumber("field 3"));
    }

    @Test
    void throwsNoSuchFieldIfFieldNameIsIncorrect() {
        List<String> names = new ArrayList<>();
        names.add("field 1");
        names.add("field 2");

        RecordFormat format = new RecordFormat(names, TokenDelimiter.COMMA);

        assertThrows(NoSuchField.class, () -> format.getFieldNumber("incorrect name"));
    }

}
