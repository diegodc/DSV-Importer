package parsing;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Iterator;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * SimpleTokenizerTest
 *
 * @author diegodc 2015-04-07.
 */
class SimpleTokenizerTest {

    private SimpleTokenizer tokenizer;
    private List<String> tokensList;

    @BeforeEach
    void setUp() throws Exception {
        tokenizer = new SimpleTokenizer(TokenDelimiter.COMMA);
    }

    @Test
    void emptyStringReturnsEmptyList() {
        parse("");
        assertTrue(tokensList.isEmpty());
    }

    @Test
    void nullStringReturnsEmptyList() {
        parse(null);
        assertTrue(tokensList.isEmpty());
    }

    @Test
    void tokensListSizeMatchesTokensInString() {
        parse(" ");
        assertEquals(1, tokensList.size());

        parse(" , ");
        assertEquals(2, tokensList.size());

        parse(" , , ");
        assertEquals(3, tokensList.size());

        parse(" , , , ");
        assertEquals(4, tokensList.size());
    }

    @Test
    void consecutiveDelimitersAreNotTreatedAsOne() {
        parse(",,");
        assertEquals(3, tokensList.size());

        parse(", ,");
        assertEquals(3, tokensList.size());
    }

    @Test
    void stringWithNoDelimiterReturnsSameString() {
        parse("something");
        assertEquals("something", tokensList.iterator().next());
    }

    @Test
    void doesNotTrimTokens() {
        parse(" something ");
        assertEquals(" something ", tokensList.iterator().next());

        parse("something ");
        assertEquals("something ", tokensList.iterator().next());

        parse(" something");
        assertEquals(" something", tokensList.iterator().next());
    }

    @Test
    void completeTestUsingCommaAsDelimiter() {

        parse("one,two ,three,, , six, seven ");
        Iterator<String> it = tokensList.iterator();

        assertEquals("one", it.next());
        assertEquals("two ", it.next());
        assertEquals("three", it.next());
        assertEquals("", it.next());
        assertEquals(" ", it.next());
        assertEquals(" six", it.next());
        assertEquals(" seven ", it.next());
    }

    @Test
    void completeTestUsingVerticalBarAsDelimiter() {

        tokenizer = new SimpleTokenizer(TokenDelimiter.VERTICAL_BAR);

        parse("one|two|three|| |six");
        Iterator<String> it = tokensList.iterator();

        assertEquals("one", it.next());
        assertEquals("two", it.next());
        assertEquals("three", it.next());
        assertEquals("", it.next());
        assertEquals(" ", it.next());
        assertEquals("six", it.next());
    }

    @Test
    void completeTestUsingTabAsDelimiter() {

        tokenizer = new SimpleTokenizer(TokenDelimiter.TAB);

        parse("one\ttwo\tthree\t\t \tsix");
        Iterator<String> it = tokensList.iterator();

        assertEquals("one", it.next());
        assertEquals("two", it.next());
        assertEquals("three", it.next());
        assertEquals("", it.next());
        assertEquals(" ", it.next());
        assertEquals("six", it.next());
    }

    @Test
    void completeTestUsingWhiteSpaceAsDelimiter() {

        tokenizer = new SimpleTokenizer(TokenDelimiter.WHITESPACE);

        parse("one two three four");
        Iterator<String> it = tokensList.iterator();

        assertEquals("one", it.next());
        assertEquals("two", it.next());
        assertEquals("three", it.next());
        assertEquals("four", it.next());
    }

    private void parse(String string) {
        tokensList = tokenizer.tokenizeAsList(string);
    }

}
