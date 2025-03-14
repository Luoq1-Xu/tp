package seedu.address.model.patient;

import static java.util.Objects.requireNonNull;

/**
 * Represents a patient's address in the address book.
 * Guarantees: immutable; is always valid
 */
public class LastVisit {

    public static final String MESSAGE_CONSTRAINTS =
            "Last visit can take any value, and it should not be blank";

    /*
     * The first character of the address must not be a whitespace,
     * otherwise " " (a blank string) becomes a valid input.
     */
    public static final String VALIDATION_REGEX = "[^\\s].*";

    public final String value;

    /**
     * Constructs an {@code Address}.
     *
     * @param remark A valid address.
     */
    public LastVisit(String remark) {
        requireNonNull(remark);
        value = remark;
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof LastVisit)) {
            return false;
        }

        LastVisit otherLastVisit = (LastVisit) other;
        return value.equals(otherLastVisit.value);
    }

    /**
     * Returns if a given string is a valid lastVisit.
     */
    public static boolean isValidLastVisit(String test) {
        return test.matches(VALIDATION_REGEX);
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

}
