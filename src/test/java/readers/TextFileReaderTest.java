package readers;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * TextFileReaderTest
 *
 * @author diegodc 2015-04-07.
 */
class TextFileReaderTest {

    private BufferedReader mockReader;
    private TextFileReader textFileReader;

    @BeforeEach
    void setUp() throws Exception {
        mockReader = mock(BufferedReader.class);
        when(mockReader.readLine()).thenReturn("a line");
        textFileReader = new TextFileReader(mockReader);
    }

    @Test
    void readsLineFromBufferedReaderOnConstruction() throws Exception {
        verify(mockReader, times(1)).readLine();
        verifyNoMoreInteractions(mockReader);
    }

    @Test
    void readsLineFromBufferedReaderWhenReadingNewLine() throws Exception {
        textFileReader.readLine();
        verify(mockReader, times(2)).readLine();
        verifyNoMoreInteractions(mockReader);
    }

    @Test
    void hasNextLineIfBufferedReaderReturnsNonNullString() throws Exception {
        assertTrue(textFileReader.hasNextLine());
    }

    @Test
    void hasNotNextLineIfBufferedReaderReturnsNullString() throws Exception {
        when(mockReader.readLine()).thenReturn(null);
        textFileReader = new TextFileReader(mockReader);
        assertFalse(textFileReader.hasNextLine());
    }

    @Test
    void closesBufferedReader() throws Exception {
        textFileReader.close();
        verify(mockReader, times(1)).readLine();
        verify(mockReader, times(1)).close();
        verifyNoMoreInteractions(mockReader);
    }

    @Test
    void implementsAutoCloseable() throws Exception {
        try (TextFileReader reader = new TextFileReader(mockReader)) {
            reader.hasNextLine();
        }
        verify(mockReader).close();
    }

    @Test
    void closesBufferedReaderIfExceptionIsThrown() throws Exception {
        when(mockReader.readLine()).thenThrow(new IOException());
        try {
            new TextFileReader(mockReader);
        } catch (TextFileReaderException e) {
            verify(mockReader).close();
        }
    }

    @Test
    void returnsLineReadFromBufferedReader() throws Exception {
        assertEquals("a line", textFileReader.readLine());
    }

    @Test
    void throwsExceptionIfHasNotNextLine() throws Exception {

        /* the TextFileReader pre-reads the line from the BufferedReader */
        when(mockReader.readLine()).thenReturn(null);
        assertEquals("a line", textFileReader.readLine());

        assertThrows(NoLineException.class, () -> textFileReader.readLine());
    }

    @Test
    void throwsExceptionIfHasNotNextLineOnConstruction() throws Exception {
        when(mockReader.readLine()).thenReturn(null);

        textFileReader = new TextFileReader(mockReader);

        assertThrows(NoLineException.class, () -> textFileReader.readLine());

        verify(mockReader).close();
    }

    @Test
    void closesBufferedReaderExceptionIsThrownOnReadLine() throws Exception {
        when(mockReader.readLine()).thenReturn(null);

        textFileReader = new TextFileReader(mockReader);
        try {
            textFileReader.readLine();
        } catch (NoLineException e) {
            verify(mockReader).close();
        }
    }

    @Test
    void catchesExceptionThrownByBufferedReaderWhenReadingLineAndChainsIt() throws Exception {
        Exception expected = new IOException();
        when(mockReader.readLine()).thenThrow(expected);

        try {
            new TextFileReader(mockReader);
        } catch (TextFileReaderException thrown) {
            assertSame(expected, thrown.getCause());
        }
    }

    @Test
    void catchesExceptionThrownByBufferedReaderWhenClosingAndChainsIt() throws Exception {
        Exception expected = new IOException();
        doThrow(expected).when(mockReader).close();
        TextFileReader reader = new TextFileReader(mockReader);

        try {
            reader.close();
        } catch (TextFileReaderException thrown) {
            assertSame(expected, thrown.getCause());
        }
    }

}
