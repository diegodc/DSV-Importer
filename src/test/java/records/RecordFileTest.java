package records;


import org.junit.jupiter.api.Test;
import parsing.TokenDelimiter;
import readers.MockTextReader;
import records.exceptions.ConversionError;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * RecordFileTest
 *
 * @author diegodc 2015-04-07.
 */
class RecordFileTest {

    private RecordFile<String> file;
    private MockTextReader reader;

    private void makeReader(List<String> lines) {
        reader = new MockTextReader(lines);
        file = new RecordFile<>(reader, new TestRecordConverter(), TokenDelimiter.COMMA);
    }

    @Test
    void convertEmptyReader() {
        assertThrows(ConversionError.class, () -> makeReader(new ArrayList<>()));
    }

    @Test
    void readerGetsClosedAfterConversion() {
        List<String> lines = new ArrayList<>();
        lines.add("field 1, field 2, field 3");
        lines.add("value 1, value 2, value 3");
        makeReader(lines);
        assertTrue(reader.isClosed());
    }

    @Test
    void readerGetsClosedIfExceptionIsThrown() {
        List<String> lines = new ArrayList<>();
        try {
            makeReader(lines);
        } catch (ConversionError e) {
            assertTrue(reader.isClosed());
        }
    }

    @Test
    void convertOneRecord() {
        List<String> lines = new ArrayList<>();
        lines.add("field 1, field 2, field 3");
        lines.add("value 1, value 2, value 3");
        makeReader(lines);

        List<String> records = file.getRecords();
        assertEquals(1, records.size());
        assertEquals("[value 1][value 2][value 3]", records.get(0));
    }

    @Test
    void convertMultipleRecords() {
        List<String> lines = new ArrayList<>();
        lines.add("field 1, field 2, field 3");
        lines.add("line 1 value 1, line 1 value 2, line 1 value 3");
        lines.add("line 2 value 1, line 2 value 2, line 2 value 3");
        lines.add("line 3 value 1, line 3 value 2, line 3 value 3");
        makeReader(lines);

        List<String> records = file.getRecords();
        assertEquals(3, records.size());

        List<String> expected =  new ArrayList<>();
        expected.add("[line 1 value 1][line 1 value 2][line 1 value 3]");
        expected.add("[line 2 value 1][line 2 value 2][line 2 value 3]");
        expected.add("[line 3 value 1][line 3 value 2][line 3 value 3]");
        assertEquals(expected, file.getRecords());
    }

    @Test
    void ignoresEmptyLines() {
        List<String> lines = new ArrayList<>();
        lines.add("field 1, field 2, field 3");
        lines.add("line 1 value 1, line 1 value 2, line 1 value 3");
        lines.add("line 2 value 1, line 2 value 2, line 2 value 3");
        lines.add("");
        lines.add("line 3 value 1, line 3 value 2, line 3 value 3");
        makeReader(lines);

        List<String> records = file.getRecords();
        assertEquals(3, records.size());

        List<String> expected =  new ArrayList<>();
        expected.add("[line 1 value 1][line 1 value 2][line 1 value 3]");
        expected.add("[line 2 value 1][line 2 value 2][line 2 value 3]");
        expected.add("[line 3 value 1][line 3 value 2][line 3 value 3]");
        assertEquals(expected, file.getRecords());
    }

}
