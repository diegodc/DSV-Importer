package readers;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;

/**
 * This factory creates a ready to use TextReader.
 *
 * @author diegodc 2015-04-07.
 */
public class ReaderFactory {

    public static TextReader openReaderFromFile(String fileName) {
        BufferedReader bufferedReader = openReader(getFile(fileName));
        return new TextFileReader(bufferedReader);
    }

    private static BufferedReader openReader(File file) {
        BufferedReader reader;
        try {
            reader = new BufferedReader(new FileReader(file));
        } catch (FileNotFoundException e) {
            throw new TextFileReaderException(e);
        }
        return reader;
    }

    private static File getFile(String fileName) {
        File file = new File(fileName);
        checkIfFileExists(file);
        return file;
    }

    private static void checkIfFileExists(File file) {
        if (!file.exists()) {
            throw new NoSuchFileException(file.toString());
        }
    }

}
