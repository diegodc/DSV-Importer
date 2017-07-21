package readers;

import java.io.*;

/**
 * Implementation of the TextReader interface for reading text files.
 *
 * @author diegodc 2015-04-07.
 */
public class TextFileReader implements TextReader {

    private BufferedReader reader;
    private String currentLine;

    TextFileReader(BufferedReader reader) {
        this.reader = reader;
        updateCurrentLine();
    }

    private void updateCurrentLine() {
        try {
            currentLine = reader.readLine();
        } catch (IOException cause) {
            close();
            throw new TextFileReaderException(cause);
        }
    }

    @Override
    public String readLine() {
        checkIfItIsLastLine();
        String line = currentLine;
        updateCurrentLine();
        return line;
    }

    private void checkIfItIsLastLine() {
        if (!hasNextLine()) {
            close();
            throw new NoLineException();
        }
    }

    @Override
    public boolean hasNextLine() {
        return currentLine != null;
    }

    @Override
    public void close() {
        try {
            reader.close();
        } catch (IOException cause) {
            throw new TextFileReaderException(cause);
        }
    }

}
