package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ABSENT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PRESENT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_WEEK;

import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.attendance.Week;
import seedu.address.model.person.Person;

/**
 * Adds a person to the address book.
 */
public class MarkCommand extends Command {

    public static final String COMMAND_WORD = "ma";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Marks students' attendance in the TA Toolkit "
            + "by their index in the displayed list for the specified week. "
            + "Only absentees / attendees need to be specified, "
            + "the rest are assumed to be present.\n"
            + "Parameters: "
            + PREFIX_WEEK + "WEEK "
            + PREFIX_PRESENT + "PRESENT_INDEX [MORE_INDICES]...\n"
            + PREFIX_ABSENT + "ABSENT_INDEX [MORE_INDICES]...\n"
            + "Example: " + COMMAND_WORD + " w/1 p/1, 2, 3 a/4, 5";

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
