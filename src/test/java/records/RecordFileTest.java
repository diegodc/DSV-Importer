package records;

import org.junit.jupiter.api.Test;
import parsing.TokenDelimiter;
import readers.MockTextReader;
import records.exceptions.ConversionError;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

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
        assertThrows(ConversionError.class, () -> makeReader(List.of()));
    }

    @Test
    void readerGetsClosedAfterConversion() {
        List<String> lines = List.of("field 1, field 2, field 3", "value 1, value 2, value 3");

        makeReader(lines);
        assertTrue(reader.isClosed());
    }

    @Test
    void readerGetsClosedIfExceptionIsThrown() {
        try {
            makeReader(List.of());
        } catch (ConversionError e) {
            assertTrue(reader.isClosed());
        }
    }

    @Test
    void convertOneRecord() {
        List<String> lines = List.of("field 1, field 2, field 3", "value 1, value 2, value 3");
        List<String> expected = List.of("[value 1][value 2][value 3]");

        makeReader(lines);
        assertIterableEquals(expected, file.getRecords());
    }

    @Test
    void convertMultipleRecords() {
        List<String> lines = List.of("field 1, field 2, field 3",
                "line 1 value 1, line 1 value 2, line 1 value 3",
                "line 2 value 1, line 2 value 2, line 2 value 3",
                "line 3 value 1, line 3 value 2, line 3 value 3");
        List<String> expected = List.of(
                "[line 1 value 1][line 1 value 2][line 1 value 3]",
                "[line 2 value 1][line 2 value 2][line 2 value 3]",
                "[line 3 value 1][line 3 value 2][line 3 value 3]");

        makeReader(lines);
        assertIterableEquals(expected, file.getRecords());
    }

    @Test
    void ignoresEmptyLines() {
        List<String> lines = List.of("field 1, field 2, field 3",
                "line 1 value 1, line 1 value 2, line 1 value 3",
                "line 2 value 1, line 2 value 2, line 2 value 3",
                "",
                "line 3 value 1, line 3 value 2, line 3 value 3");

        List<String> expected = List.of(
                "[line 1 value 1][line 1 value 2][line 1 value 3]",
                "[line 2 value 1][line 2 value 2][line 2 value 3]",
                "[line 3 value 1][line 3 value 2][line 3 value 3]");

        makeReader(lines);
        assertIterableEquals(expected, file.getRecords());
    }

}
