package seedu.tatoolkit.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.tatoolkit.logic.parser.CliSyntax.PREFIX_NOTE;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import seedu.tatoolkit.commons.core.index.Index;
import seedu.tatoolkit.commons.util.ToStringBuilder;
import seedu.tatoolkit.logic.Messages;
import seedu.tatoolkit.logic.commands.exceptions.CommandException;
import seedu.tatoolkit.model.Model;
import seedu.tatoolkit.model.person.Note;
import seedu.tatoolkit.model.person.Person;

/**
 * Adds a note to a person identified using it's displayed index from the TA Toolkit.
 */
public class AddNoteCommand extends Command {

    public static final String COMMAND_WORD = "an";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Adds a note to a person identified by the index number used in the displayed person list.\n"
            + "Note may only contain alphanumeric characters basic punctuation "
            + "(periods, commas, exclamation marks, question marks)\n"
            + "Parameters:"
            + " INDEX (must be a positive integer) "
            + PREFIX_NOTE + "NOTE\n"
            + "Example: "
            + COMMAND_WORD + " "
            + "1 "
            + PREFIX_NOTE + "Asked a question regarding test cases.";

    public static final String MESSAGE_SUCCESS = "New note added to %1$s.\nNote: %2$s";
    private static final Logger logger = Logger.getLogger(AddNoteCommand.class.getName());


    private final Index index;

    private final Note note;

    /**
     * @param index of the person in the filtered person list to add a note
     * @param note the note that should be added
     */
    public AddNoteCommand(Index index, Note note) {
        requireNonNull(index);
        requireNonNull(note);

        this.index = index;
        this.note = note;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Person> lastShownList = model.getFilteredPersonList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        Person personToAddNote = lastShownList.get(index.getZeroBased());
        personToAddNote.addNote(note);

        String message = String.format(MESSAGE_SUCCESS, Messages.format(personToAddNote), note);

        logger.log(Level.INFO, "Note added: " + note + " to person: " + personToAddNote.getName());
        return new CommandResult(message);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof AddNoteCommand)) {
            return false;
        }

        AddNoteCommand otherAddNoteCommand = (AddNoteCommand) other;
        return index.equals(otherAddNoteCommand.index)
                && note.equals(otherAddNoteCommand.note);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("index", index)
                .add("note", note)
                .toString();
    }
}
