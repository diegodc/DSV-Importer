package readers;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * TextReaderTest
 *
 * @author diegodc 2015-04-07.
 */
class TextReaderTest {

    private static final String testFile = "src/test/java/test_file.txt";
    private static final String emptyFile = "src/test/java/empty_file.txt";
    private static final String singleLineFile = "src/test/java/single_line_file.txt";

    @Test
    void emptyFileHasNoLines() {
        try (TextReader reader = ReaderFactory.openReaderFromFile(emptyFile)) {
            assertFalse(reader.hasNextLine());
        }
    }

    @Test
    void singleLineFileHasNextLine() {
        try (TextReader reader = ReaderFactory.openReaderFromFile(singleLineFile)) {
            assertTrue(reader.hasNextLine());
        }
    }

    @Test
    void testFileHasSevenLines() {
        try (TextReader reader = ReaderFactory.openReaderFromFile(testFile)) {
            for (int i = 0; i < 7; i++) {
                assertTrue(reader.hasNextLine());
                reader.readLine();
            }
            assertFalse(reader.hasNextLine());
        }
    }

    @Test
    void readsLines() {
        try (TextReader reader = ReaderFactory.openReaderFromFile(singleLineFile)) {
            assertEquals("single line", reader.readLine());
        }
        try (TextReader reader = ReaderFactory.openReaderFromFile(testFile)) {
            assertEquals("first line", reader.readLine());
            assertEquals("line 2", reader.readLine());
            assertEquals("line 3", reader.readLine());
            assertEquals("line 4", reader.readLine());
            assertEquals("next line is empty", reader.readLine());
            assertEquals("", reader.readLine());
            assertEquals("last line", reader.readLine());
        }
    }

    @Test
    void throwsExceptionIfFileDoesNotExists() {
        assertThrows(NoSuchFileException.class, () ->
            ReaderFactory.openReaderFromFile("no_file")
        );
    }

    @Test
    void throwsExceptionIfHasNotNextLine() {
        try (TextReader reader = ReaderFactory.openReaderFromFile(emptyFile)) {
            assertThrows(NoLineException.class, reader::readLine);
        }
    }

}
