package parsing;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple and fast string tokenizer.
 *
 * @author diegodc 2015-04-07.
 */
public class SimpleTokenizer {

    private final char delimiter;
    private String stringToTokenize;
    private List<String> tokensList;

    /**
     * Creates a tokenizer that uses the given delimiter.
     * *
     * @param delimiter the delimiter used by this tokenizer
     */
    public SimpleTokenizer(TokenDelimiter delimiter) {
        this.delimiter = delimiter.getDelimiter();
    }

    /**
     * Breaks the string into tokens using the given delimiter.
     * Consecutive separators are NOT treated as one.
     *
     * Example (using ',' as delimiter):
     * "this,are,tokens" results in {"this", "are", "tokens"}
     * "consecutive,,delimiters" results in {"consecutive", "", "delimiters"}
     * "no delimiter" results in  {"no delimiter"}
     *
     * A null or an empty string results in an empty list.
     *
     * @param string the string to tokenize
     * @return the list of tokens
     */
    public List<String> tokenizeAsList(String string) {

        stringToTokenize = string;
        return tokenizeString();
    }

    private List<String> tokenizeString() {

        if (nullOrEmptyString()) {
            return new ArrayList<>();
        }

        return extractTokens();
    }

    private boolean nullOrEmptyString() {
        return stringToTokenize == null || stringToTokenize.isEmpty();
    }

    private List<String> extractTokens() {
        initializeList();
        extract();
        return tokensList;
    }

    private void initializeList() {
        tokensList = new ArrayList<>();
    }

    private void extract() {
        int tokenInitialIndex = 0;
        int tokenEndIndex = nextDelimiterIndexAfter(tokenInitialIndex);

        while (thereAreMoreTokensAfter(tokenInitialIndex)) {

            extractAndAddToken(tokenInitialIndex, tokenEndIndex);
            tokenInitialIndex = tokenEndIndex + 1;
            tokenEndIndex = nextDelimiterIndexAfter(tokenInitialIndex);
        }
    }

    private int nextDelimiterIndexAfter(int tokenInitialIndex) {
        int delimiterIndex = findDelimiterIndexAfter(tokenInitialIndex);
        if (delimiterIndex == -1) {
            delimiterIndex = stringToTokenize.length();
        }
        return delimiterIndex;
    }

    private int findDelimiterIndexAfter(int tokenInitialIndex) {
        return stringToTokenize.indexOf(delimiter, tokenInitialIndex);
    }

    private boolean thereAreMoreTokensAfter(int tokenInitialIndex) {
        return tokenInitialIndex <= stringToTokenize.length();
    }

    private void extractAndAddToken(int initialIndex, int endIndex) {
        String token = stringToTokenize.substring(initialIndex, endIndex);
        tokensList.add(token);
    }

}
