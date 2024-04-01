package seedu.tatoolkit.logic.parser;

import static java.util.Objects.requireNonNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import seedu.tatoolkit.commons.core.index.Index;
import seedu.tatoolkit.commons.util.StringUtil;
import seedu.tatoolkit.logic.parser.exceptions.ParseException;
import seedu.tatoolkit.model.attendance.Week;
import seedu.tatoolkit.model.person.ClassGroup;
import seedu.tatoolkit.model.person.Email;
import seedu.tatoolkit.model.person.Github;
import seedu.tatoolkit.model.person.Name;
import seedu.tatoolkit.model.person.Note;
import seedu.tatoolkit.model.person.Phone;
import seedu.tatoolkit.model.person.Telegram;

/**
 * Contains utility methods used for parsing strings in the various *Parser classes.
 */
public class ParserUtil {

    public static final String MESSAGE_INVALID_INDEX = "Index is not a non-zero unsigned integer.";
    public static final String MESSAGE_NO_VALID_INDICES = "No valid indices were provided.";
    public static final String MESSAGE_DUPLICATE_INDICES = "Duplicate indices were provided.";

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
    public static Optional<Phone> parsePhone(String phone) throws ParseException {
        if (phone.isEmpty()) {
            return Optional.of(Phone.EMPTY);
        }
        requireNonNull(phone);
        String trimmedPhone = phone.trim();
        if (!Phone.isValidPhone(trimmedPhone)) {
            throw new ParseException(Phone.MESSAGE_CONSTRAINTS);
        }
        return Optional.of(new Phone(trimmedPhone));
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
     * Parses a {@code String classGroup} into an {@code ClassGroup}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code classGroup} is invalid.
     */
    public static ClassGroup parseClassGroup(String classGroup) throws ParseException {
        requireNonNull(classGroup);
        String trimmedClassGroup = classGroup.trim();
        if (!ClassGroup.isValidClassGroup(trimmedClassGroup)) {
            throw new ParseException(ClassGroup.MESSAGE_CONSTRAINTS);
        }
        return new ClassGroup(trimmedClassGroup);
    }

    /**
     * Parses a {@code String telegram} into an {@code Telegram}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code telegram} is invalid.
     */
    public static Optional<Telegram> parseTelegram(String telegram) throws ParseException {
        if (telegram.isEmpty()) {
            return Optional.of(Telegram.EMPTY);
        }
        String trimmedTelegram = telegram.trim();
        if (!Telegram.isValidTelegram(trimmedTelegram)) {
            throw new ParseException(Telegram.MESSAGE_CONSTRAINTS);
        }
        return Optional.of(new Telegram(trimmedTelegram));
    }

    /**
     * Parses a {@code String github} into an {@code Github}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code github} is invalid.
     */
    public static Optional<Github> parseGithub(String github) throws ParseException {
        if (github.isEmpty()) {
            return Optional.of(Github.EMPTY);
        }
        String trimmedGithub = github.trim();
        if (!Github.isValidGithub(trimmedGithub)) {
            throw new ParseException(ClassGroup.MESSAGE_CONSTRAINTS);
        }
        return Optional.of(new Github(trimmedGithub));
    }

    /**
     * Parses a {@code String note} into an {@code Note}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code note} is invalid.
     */
    public static Note parseNote(String note) throws ParseException {
        requireNonNull(note);
        String trimmedNote = note.trim();
        if (!Note.isValidNote(trimmedNote)) {
            throw new ParseException(Note.MESSAGE_CONSTRAINTS);
        }
        return new Note(note);
    }

    /**
     * Parses a {@code String week} into an {@code Week}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code week} is invalid.
     */
    public static Week parseWeek(String week) throws ParseException {
        requireNonNull(week);
        String trimmedWeek = week.trim();
        Index weekIndex;
        try {
            weekIndex = parseIndex(trimmedWeek);
        } catch (ParseException e) {
            throw new ParseException(Week.MESSAGE_CONSTRAINTS);
        }
        if (!Week.isValidWeek(weekIndex)) {
            throw new ParseException(Week.MESSAGE_CONSTRAINTS);
        }
        return new Week(weekIndex);
    }

    /**
     * Parses a {@code String indices} into a {@code List<Index>}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code indices} is invalid.
     */
    public static List<Index> parseIndices(String indices) throws ParseException {
        if (indices.isEmpty()) {
            return new ArrayList<>();
        }
        String[] trimmedIndices = Arrays.stream(indices.trim().split(","))
                .map(String::trim)
                .filter(str -> !str.isEmpty())
                .toArray(String[]::new);

        List<Index> indexList = new ArrayList<>();
        for (String index : trimmedIndices) {
            Index parsedIndex = parseIndex(index);
            if (!indexList.contains(parsedIndex)) {
                indexList.add(parsedIndex);
            } else {
                throw new ParseException(MESSAGE_DUPLICATE_INDICES);
            }
        }

        if (indexList.isEmpty()) {
            throw new ParseException(MESSAGE_NO_VALID_INDICES);
        }

        return indexList;
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    public static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }
}
