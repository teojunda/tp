package seedu.tatoolkit.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.tatoolkit.logic.parser.CliSyntax.PREFIX_ABSENT;
import static seedu.tatoolkit.logic.parser.CliSyntax.PREFIX_PRESENT;
import static seedu.tatoolkit.logic.parser.CliSyntax.PREFIX_WEEK;

import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import seedu.tatoolkit.commons.core.LogsCenter;
import seedu.tatoolkit.commons.core.index.Index;
import seedu.tatoolkit.commons.util.ToStringBuilder;
import seedu.tatoolkit.logic.Messages;
import seedu.tatoolkit.logic.commands.exceptions.CommandException;
import seedu.tatoolkit.model.Model;
import seedu.tatoolkit.model.attendance.Week;
import seedu.tatoolkit.model.person.Person;

/**
 * Adds a person to the TA Toolkit.
 */
public class MarkCommand extends Command {

    public static final String COMMAND_WORD = "ma";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Marks students' attendance in the TA Toolkit "
            + "by their index in the displayed list for the specified week. "
            + "At least one of (" + PREFIX_PRESENT + "/" + PREFIX_ABSENT + ") need to be specified, "
            + "All students are assumed to be present initially.\n"
            + "Parameters: "
            + PREFIX_WEEK + "WEEK "
            + PREFIX_PRESENT + "PRESENT_INDEX [MORE_INDICES]...\n"
            + PREFIX_ABSENT + "ABSENT_INDEX [MORE_INDICES]...\n"
            + "Example: " + COMMAND_WORD + " w/1 " + PREFIX_PRESENT + "1, 2, 3 " + PREFIX_ABSENT + "4, 5"
            + "Example: " + COMMAND_WORD + " w/1 " + PREFIX_ABSENT + "1, 4";

    public static final String MESSAGE_SUCCESS = "Marked the following students as present for %1$s: %2$s"
        + "\nMarked the following students as absent for %3$s: %4$s";

    private static final Logger logger = LogsCenter.getLogger(MarkCommand.class);
    private final Week week;
    private final List<Index> absentees;
    private final List<Index> attendees;

    /**
     * Creates an MarkCommand to add the specified {@code Person}
     */
    public MarkCommand(Week week, List<Index> attendees, List<Index> absentees) {
        requireNonNull(week);
        requireNonNull(attendees);
        requireNonNull(absentees);
        this.week = week;
        this.attendees = attendees;
        this.absentees = absentees;
        logger.info("MarkCommand created for " + week
                + " with attendees: " + attendees + " and absentees: " + absentees);
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Person> lastShownList = model.getFilteredPersonList();

        // We don't proceed even if the other indices are valid since it's likely that the user
        // has made a mistake in the other inputs as well.
        for (Index index : absentees) {
            if (index.getZeroBased() >= lastShownList.size()) {
                throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
            }
        }

        // We don't proceed even if the other indices are valid since it's likely that the user
        // has made a mistake in the other inputs as well.
        for (Index index : attendees) {
            if (index.getZeroBased() >= lastShownList.size()) {
                throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
            }
            if (absentees.contains(index)) {
                throw new CommandException(Messages.MESSAGE_DUPLICATES_IN_ABSENTEES_AND_ATTENDEES);
            }
        }

        List<Person> absentStudents = this.absentees.stream()
                .map(index -> lastShownList.get(index.getZeroBased()))
                .collect(Collectors.toList());

        List<Person> presentStudents = this.attendees.stream()
                .map(index -> lastShownList.get(index.getZeroBased()))
                .collect(Collectors.toList());

        for (Person student : absentStudents) {
            student.markAbsent(week);
        }

        for (Person student : presentStudents) {
            student.markPresent(week);
        }

        String absenteeNames = absentStudents.stream()
                .map(Person::getName)
                .map(Object::toString)
                .collect(Collectors.joining(", "));

        String attendeeNames = presentStudents.stream()
                .map(Person::getName)
                .map(Object::toString)
                .collect(Collectors.joining(", "));

        return new CommandResult(String.format(MESSAGE_SUCCESS, week, attendeeNames, week, absenteeNames));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof MarkCommand)) {
            return false;
        }

        MarkCommand otherMarkCommand = (MarkCommand) other;
        return week.equals(otherMarkCommand.week) && absentees.equals(otherMarkCommand.absentees)
                && attendees.equals(otherMarkCommand.attendees);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("week", week)
                .add("attendees", attendees)
                .add("absentees", absentees)
                .toString();
    }
}
