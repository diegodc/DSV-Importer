package readers;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * MockTextReaderTest
 *
 * @author diegodc 2015-04-07.
 */
class MockTextReaderTest {

    private MockTextReader reader;

    @BeforeEach
    void setUp() throws Exception {

        List<String> lines = new ArrayList<>();
        lines.add("first line");
        lines.add("line 2");
        lines.add("line 3");
        lines.add("next line is empty");
        lines.add("");
        lines.add("last line");

        reader = new MockTextReader(lines);
    }

    @Test
    void readerHasSixLines() {
        for (int i = 0; i < 6; i++) {
            assertTrue(reader.hasNextLine());
            reader.readLine();
        }
        assertFalse(reader.hasNextLine());
    }

    @Test
    void readLines() {
        assertEquals("first line", reader.readLine());
        assertEquals("line 2", reader.readLine());
        assertEquals("line 3", reader.readLine());
        assertEquals("next line is empty", reader.readLine());
        assertEquals("", reader.readLine());
        assertEquals("last line", reader.readLine());
    }

    @Test
    void emptyReaderHasNoLines() {
        reader = new MockTextReader(Collections.emptyList());
        assertFalse(reader.hasNextLine());
    }

    @Test
    void readingNoLineThrowsException() {
        reader = new MockTextReader(Collections.emptyList());
        assertThrows(NoLineException.class, () -> reader.readLine());
    }

    @Test
    void readerIsNotClosedOnConstruction() {
        assertFalse(reader.isClosed());
    }

    @Test
    void readerGetsClosed() {
        reader.close();
        assertTrue(reader.isClosed());
    }

}
