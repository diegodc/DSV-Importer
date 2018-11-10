package readers;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

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
    void setUp() {
        List<String> lines = List.of("first line", "line 2", "line 3", "next line is empty", "", "last line");

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
