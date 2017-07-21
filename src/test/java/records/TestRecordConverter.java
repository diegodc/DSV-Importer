package records;

import records.conversion.RecordConverter;

/**
 * Used for tests purposes.
 *
 * @author diegodc 2015-04-07.
 */
public class TestRecordConverter implements RecordConverter<String> {

    @Override
    public String convert(RecordParser record) {
        return "[" + record.field("field 1").stringValue() + "]"
                + "[" + record.field("field 2").stringValue() + "]"
                + "[" + record.field("field 3").stringValue() + "]";
    }

}
