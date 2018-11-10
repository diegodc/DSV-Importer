package readers;

import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;

/**
 * This implementation uses a list as the text source.
 *
 * @author diegodc 2015-04-07.
 */
public class MockTextReader implements TextReader {

    private Iterator<String> iterator;
    private boolean isClosed;

    public MockTextReader(List<String> lines) {
        iterator = lines.iterator();
        isClosed = false;
    }

    @Override
    public String readLine() {
        try {
            return iterator.next();
        } catch (NoSuchElementException e) {
            throw new NoLineException();
        }
    }

    @Override
    public boolean hasNextLine() {
        return iterator.hasNext();
    }

    @Override
    public void close() {
        isClosed = true;
    }

    public boolean isClosed() {
        return isClosed;
    }

}
