package parsing;

/**
 * Delimiter used by the tokenizer.
 *
 * @author diegodc 2015-04-07.
 */
public enum TokenDelimiter {
	
	COMMA(','),
	WHITESPACE(' '),
	TAB('\t'),
	VERTICAL_BAR('|');
	
	private final char delimiter;

	TokenDelimiter(char delimiter) {
		this.delimiter = delimiter;
	}
	
	public char getDelimiter() {
		return this.delimiter;
	}

}
