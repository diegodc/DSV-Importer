package parsing;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * SimpleTokenizerTest
 *
 * @author diegodc 2015-04-07.
 */
class SimpleTokenizerTest {

    private SimpleTokenizer tokenizer;
    private List<String> tokensList;

    @BeforeEach
    void setUp() {
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
        assertIterableEquals(listOf("something"), tokensList);
    }

    @Test
    void doesNotTrimTokens() {
        parse(" something ");
        assertIterableEquals(listOf(" something "), tokensList);

        parse("something ");
        assertIterableEquals(listOf("something "), tokensList);

        parse(" something");
        assertIterableEquals(listOf(" something"), tokensList);
    }

    @Test
    void completeTestUsingCommaAsDelimiter() {
        parse("one,two ,three,, , six, seven ");
        assertIterableEquals(listOf("one", "two ", "three", "", " ", " six", " seven "), tokensList);
    }

    @Test
    void completeTestUsingVerticalBarAsDelimiter() {
        tokenizer = new SimpleTokenizer(TokenDelimiter.VERTICAL_BAR);
        parse("one|two|three|| |six");
        assertIterableEquals(listOf("one", "two", "three", "", " ", "six"), tokensList);
    }

    @Test
    void completeTestUsingTabAsDelimiter() {
        tokenizer = new SimpleTokenizer(TokenDelimiter.TAB);
        parse("one\ttwo\tthree\t\t \tsix");
        assertIterableEquals(listOf("one", "two", "three", "", " ", "six"), tokensList);
    }

    @Test
    void completeTestUsingWhiteSpaceAsDelimiter() {
        tokenizer = new SimpleTokenizer(TokenDelimiter.WHITESPACE);
        parse("one two three four");
        assertIterableEquals(listOf("one", "two", "three", "four"), tokensList);
    }

    private void parse(String string) {
        tokensList = tokenizer.tokenizeAsList(string);
    }

    private List<String> listOf(String... elements) {
        return Arrays.asList(elements);
    }

}
