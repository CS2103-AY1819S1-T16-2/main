package seedu.superta.model.assignment;

import static java.util.Objects.requireNonNull;
import static seedu.superta.commons.util.AppUtil.checkArgument;

/**
 * Represents a Student's score in the SuperTA client.
 * Guarantees: immutable; is valid as declared in {@link #isValidScore(String)}
 */
public class Score {
    public static final String MESSAGE_SCORE_CONSTRAINTS =
        "Score should only contain numbers and at least 1 digit long";
    private static final String SCORE_VALIDATION_REGEX = "\\d{1,}";
    public final String value;

    /**
     * Constructs a {@code score}.
     *
     * @param score A valid score.
     */
    public Score(String score) {
        requireNonNull(score);
        checkArgument(isValidScore(score), MESSAGE_SCORE_CONSTRAINTS);
        this.value = score;
    }

    /**
     * Returns true if a given string is a valid score.
     */
    public static boolean isValidScore(String test) {
        return test.matches(SCORE_VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this
            || (other instanceof Score
            && value.equals(((Score) other).value));
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }
}
