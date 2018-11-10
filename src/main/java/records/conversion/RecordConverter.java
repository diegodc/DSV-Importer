package records.conversion;

import records.RecordParser;

/**
 * Converts the record wrapped in the RecordParser to a custom object.
 *
 * @author diegodc 2015-04-07.
 */
public interface RecordConverter<T> {

    T convert(RecordParser record);

}
