package readers;

/**
 * A compact interface for reading text resources.
 *
 * @author diegodc 2015-04-07.
 */
public interface TextReader extends AutoCloseable {

    /**
     * Reads a new line from the source.
     * @return  the text line
     */
    String readLine();

    /**
     * Checks whether there is a new line or not.
     * @return true if a new line can be read
     */
    boolean hasNextLine();

    @Override
    void close();

}
