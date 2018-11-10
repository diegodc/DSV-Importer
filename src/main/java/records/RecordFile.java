package records;

import readers.TextReader;
import records.conversion.RecordConverter;
import parsing.TokenDelimiter;
import parsing.SimpleTokenizer;
import records.exceptions.ConversionError;

import java.util.*;

/**
 * A RecordFile converts all the records from the a given file using the provided record converter.
 *
 * @author diegodc 2015-04-07.
 */
public class RecordFile<T> {

    private List<T> records = new ArrayList<>();
    private final RecordConverter<T> converter;
    private final TokenDelimiter tokenDelimiter;
    private TextReader reader;

    public RecordFile(TextReader reader, RecordConverter<T> converter, TokenDelimiter tokenDelimiter) {

        this.converter = converter;
        this.tokenDelimiter = tokenDelimiter;
        this.reader = reader;

        parseRecordsFromReader();
    }

    public List<T> getRecords() {
        return records;
    }

    private void parseRecordsFromReader() {
        try (TextReader reader = this.reader) {
            RecordFormat format = buildRecordFormat(reader);
            parseRecords(format);
        }
    }

    private RecordFormat buildRecordFormat(TextReader reader) {
        if (reader.hasNextLine()) {
            String header = reader.readLine();
            List<String> fieldNames = parseFieldNames(header);
            return new RecordFormat(fieldNames, tokenDelimiter);
        } else {
            throw new ConversionError("No header line");
        }
    }

    private List<String> parseFieldNames(String header) {
        return new SimpleTokenizer(tokenDelimiter).tokenizeAsList(header);
    }

    private void parseRecords(RecordFormat format) {
        while (reader.hasNextLine()) {
            String line = reader.readLine();
            parseLine(line, format);
        }
    }

    private void parseLine(String line, RecordFormat format) {
        if (!line.isEmpty()) {
            T record = convertRecord(line, format);
            records.add(record);
        }
    }

    private T convertRecord(String line, RecordFormat format) {
        RecordParser recordParser = new RecordParser(line, format);
        return converter.convert(recordParser);
    }

}
