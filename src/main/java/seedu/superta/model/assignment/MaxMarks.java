package seedu.superta.model.assignment;

import static java.util.Objects.requireNonNull;
import static seedu.superta.commons.util.AppUtil.checkArgument;

/**
 * Represents an Assignment's maximum marks in the SuperTA client.
 * Guarantees: immutable; is valid as declared in {@link #isValidMaxMarks(String)}
 */
public class MaxMarks {

    public static final String MESSAGE_MAX_MARKS_CONSTRAINTS =
            "Max marks should only contain numbers and at least 1 digit long";
    private static final String MAX_MARKS_VALIDATION_REGEX = "\\d{1,}";
    public final String value;

    /**
     * Constructs a {@code maximum mark}.
     *
     * @param maxMarks A valid maximum mark.
     */
    public MaxMarks(String maxMarks) {
        requireNonNull(maxMarks);
        checkArgument(isValidMaxMarks(maxMarks), MESSAGE_MAX_MARKS_CONSTRAINTS);
        this.value = maxMarks;
    }

    /**
     * Returns true if a given string is a valid maximum mark.
     */
    public static boolean isValidMaxMarks(String test) {
        return test.matches(MAX_MARKS_VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this
            || (other instanceof MaxMarks
            && value.equals(((MaxMarks) other).value));
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }
}
