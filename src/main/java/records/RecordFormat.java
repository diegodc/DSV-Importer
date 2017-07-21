package records;

import parsing.TokenDelimiter;
import records.exceptions.NoSuchField;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Representation of the record format.
 *
 * @author diegodc 2015-04-07.
 */
public class RecordFormat {

    private Map<String, Integer> fieldNamesMap = new HashMap<>();
    private TokenDelimiter tokenDelimiter;

    public RecordFormat(List<String> fieldNames, TokenDelimiter tokenSeparator) {
        setFieldNamesMap(fieldNames);
        this.tokenDelimiter = tokenSeparator;
    }

    private void setFieldNamesMap(List<String> fieldNames) {
        int fieldNumber = 0;
        for (String fieldName : fieldNames) {
            fieldNamesMap.put(fieldName.trim(), fieldNumber);
            fieldNumber++;
        }
    }

    public TokenDelimiter getTokenDelimiter() {
        return tokenDelimiter;
    }

    public Set<String> getFieldNames() {
        return fieldNamesMap.keySet();
    }

    public int getFieldNumber(String fieldName) {
        checkFieldName(fieldName);
        return fieldNamesMap.get(fieldName);
    }

    private void checkFieldName(String fieldName) {
        if (!fieldNamesMap.containsKey(fieldName)) {
            throw new NoSuchField(fieldName);
        }
    }

}
