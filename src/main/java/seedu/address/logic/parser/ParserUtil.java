package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.Messages.MESSAGE_FUTURE_LAST_VISIT_DATE;
import static seedu.address.logic.Messages.MESSAGE_INVALID_DATE_FORMAT;
import static seedu.address.logic.commands.UnprescribeCommand.REMOVE_ALL_PLACEHOLDER;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.StringUtil;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.medicine.Medicine;
import seedu.address.model.patient.Address;
import seedu.address.model.patient.Email;
import seedu.address.model.patient.LastVisit;
import seedu.address.model.patient.Name;
import seedu.address.model.patient.Phone;
import seedu.address.model.tag.Tag;

/**
 * Contains utility methods used for parsing strings in the various *Parser classes.
 */
public class ParserUtil {

    public static final String MESSAGE_INVALID_INDEX = "Index is not a non-zero unsigned integer.";

    /**
     * Parses {@code oneBasedIndex} into an {@code Index} and returns it. Leading and trailing whitespaces will be
     * trimmed.
     * @throws ParseException if the specified index is invalid (not non-zero unsigned integer).
     */
    public static Index parseIndex(String oneBasedIndex) throws ParseException {
        String trimmedIndex = oneBasedIndex.trim();
        if (!StringUtil.isNonZeroUnsignedInteger(trimmedIndex)) {
            throw new ParseException(MESSAGE_INVALID_INDEX);
        }
        return Index.fromOneBased(Integer.parseInt(trimmedIndex));
    }

    /**
     * Parses a {@code String name} into a {@code Name}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code name} is invalid.
     */
    public static Name parseName(String name) throws ParseException {
        requireNonNull(name);
        String trimmedName = name.trim();
        if (!Name.isValidName(trimmedName)) {
            throw new ParseException(Name.MESSAGE_CONSTRAINTS);
        }
        return new Name(trimmedName);
    }

    /**
     * Parses a {@code String phone} into a {@code Phone}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code phone} is invalid.
     */
    public static Phone parsePhone(String phone) throws ParseException {
        requireNonNull(phone);
        String trimmedPhone = phone.trim();
        if (!Phone.isValidPhone(trimmedPhone)) {
            throw new ParseException(Phone.MESSAGE_CONSTRAINTS);
        }
        return new Phone(trimmedPhone);
    }

    /**
     * Parses a {@code String address} into an {@code Address}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code address} is invalid.
     */
    public static Address parseAddress(String address) throws ParseException {
        requireNonNull(address);
        String trimmedAddress = address.trim();
        if (!Address.isValidAddress(trimmedAddress)) {
            throw new ParseException(Address.MESSAGE_CONSTRAINTS);
        }
        return new Address(trimmedAddress);
    }

    /**
     * Parses a {@code String lastVisitDate} into a {@code LastVisit}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code lastVisitDate} is invalid.
     */
    public static LastVisit parseLastVisit(String lastVisitDateString) throws ParseException {
        requireNonNull(lastVisitDateString);
        String trimmedLastVisitDate = lastVisitDateString.trim();
        LocalDate lastVisitDate;
        try {
            lastVisitDate = LocalDate.parse(trimmedLastVisitDate);
        } catch (DateTimeParseException e) {
            throw new ParseException(MESSAGE_INVALID_DATE_FORMAT);
        }

        if (!LastVisit.isValidLastVisit(lastVisitDate)) {
            throw new ParseException(MESSAGE_FUTURE_LAST_VISIT_DATE);
        }
        return new LastVisit(lastVisitDate);
    }

    /**
     * Parses a {@code String email} into an {@code Email}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code email} is invalid.
     */
    public static Email parseEmail(String email) throws ParseException {
        requireNonNull(email);
        String trimmedEmail = email.trim();
        if (!Email.isValidEmail(trimmedEmail)) {
            throw new ParseException(Email.MESSAGE_CONSTRAINTS);
        }
        return new Email(trimmedEmail);
    }

    /**
     * Parses a {@code String tag} into a {@code Tag}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code tag} is invalid.
     */
    public static Tag parseTag(String tag) throws ParseException {
        requireNonNull(tag);
        String trimmedTag = tag.trim();
        if (!Tag.isValidTagName(trimmedTag)) {
            throw new ParseException(Tag.MESSAGE_CONSTRAINTS);
        }
        return new Tag(trimmedTag);
    }

    /**
     * Parses a {@code String medName} into a {@code Medicine}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code medName} is invalid.
     */
    public static Medicine parseMed(String medName) throws ParseException {
        requireNonNull(medName);
        String trimmedMedName = medName.trim();
        if (!Medicine.isValidMedName(trimmedMedName)) {
            throw new ParseException(Medicine.MESSAGE_CONSTRAINTS);
        }
        return new Medicine(trimmedMedName);
    }

    /**
     * Parses {@code Collection<String> medNames} into a {@code Set<Medicine>}.
     */
    public static Set<Medicine> parseMeds(Collection<String> medNames) throws ParseException {
        requireNonNull(medNames);
        final Set<Medicine> medSet = new HashSet<>();
        for (String medName : medNames) {
            medSet.add(parseMed(medName));
        }
        return medSet;
    }

    /**
     * Parses {@code Collection<String> medNames} into a {@code Set<Medicine>}.
     */
    public static Set<Medicine> parseMedsUnprescribe(Collection<String> medNames) throws ParseException {
        requireNonNull(medNames);
        final Set<Medicine> medSet = new HashSet<>();
        for (String medName : medNames) {
            // If a user specifies "all" in any of the prefixes, then add placeholder to signal to remove all meds
            if (medName.equalsIgnoreCase("all")) {
                medSet.add(REMOVE_ALL_PLACEHOLDER);
                break;
            }
            medSet.add(parseMed(medName));
        }
        return medSet;
    }

    /**
     * Parses {@code Collection<String> tags} into a {@code Set<Tag>}.
     */
    public static Set<Tag> parseTags(Collection<String> tags) throws ParseException {
        requireNonNull(tags);
        final Set<Tag> tagSet = new HashSet<>();
        for (String tagName : tags) {
            tagSet.add(parseTag(tagName));
        }
        return tagSet;
    }

    /**
     * Finds and returns the first duplicate in a list of strings if it exists
     * @param listStrsToAdd List of strings to check
     * @return the duplicate input, if found
     */
    public static String findDuplicateInputs(List<String> listStrsToAdd) {
        Set<String> seen = new HashSet<>();
        return listStrsToAdd.stream()
                .filter(s -> !seen.add(s.toLowerCase()))
                .findFirst()
                .orElse(null);
    }
}
