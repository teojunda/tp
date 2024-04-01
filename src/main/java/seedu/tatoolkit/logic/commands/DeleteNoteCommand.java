package seedu.tatoolkit.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.tatoolkit.logic.parser.CliSyntax.PREFIX_INDICES;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import seedu.tatoolkit.commons.core.index.Index;
import seedu.tatoolkit.logic.Messages;
import seedu.tatoolkit.logic.commands.exceptions.CommandException;
import seedu.tatoolkit.model.Model;
import seedu.tatoolkit.model.person.Note;
import seedu.tatoolkit.model.person.Person;

/**
 * Deletes notes for a person identified using the person's displayed index from the TA Toolkit.
 * The note to delete is identified by its index number in the side panel.
 */
public class DeleteNoteCommand extends Command {
    public static final String COMMAND_WORD = "dn";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deletes a note for a person identified by the index number used in the displayed person list.\n"
            + "Notes are identified by the index number in the side panel.\n"
            + "Parameters:"
            + " PERSON_INDEX (must be a positive integer) "
            + PREFIX_INDICES + "NOTE_INDEX\n"
            + "Example: "
            + COMMAND_WORD + " "
            + "1 "
            + PREFIX_INDICES + "1, 2";

    public static final String MESSAGE_SUCCESS = "Note(s) deleted from %1$s: %2$s";
    private static final Logger logger = Logger.getLogger(DeleteNoteCommand.class.getName());
    private final Index personIndex;
    private final List<Index> notes;

    /**
     * @param personIndex of the person in the filtered person list to delete notes
     * @param notes the list of notes' indices that should be deleted
     */
    public DeleteNoteCommand(Index personIndex, List<Index> notes) {
        requireNonNull(personIndex);
        requireNonNull(notes);

        this.personIndex = personIndex;
        this.notes = notes;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Person> lastShownList = model.getFilteredPersonList();
        List<Integer> indices = new ArrayList<>();

        if (personIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        Person personToDeleteNote = lastShownList.get(personIndex.getZeroBased());

        // We don't proceed even if the other indices are valid since it's likely that the user
        // has made a mistake in the other inputs as well.
        for (Index noteIndex : notes) {
            int zeroBasedIndex = noteIndex.getZeroBased();
            // Check if the index is valid
            if (zeroBasedIndex >= personToDeleteNote.getNoteSize()) {
                throw new CommandException(Messages.MESSAGE_INVALID_NOTE_DISPLAYED_INDEX);
            }
            indices.add(zeroBasedIndex);
        }

        Collections.sort(indices, Collections.reverseOrder());

        StringBuilder deletedNotes = new StringBuilder();

        for (int i: indices) {
            Note note = personToDeleteNote.getNotes().get(i);
            personToDeleteNote.deleteNote(i);
            deletedNotes.append("\n" + note);
        }

        String message = String.format(MESSAGE_SUCCESS, Messages.format(personToDeleteNote), deletedNotes.toString());

        logger.log(Level.INFO, "Notes deleted from " + personToDeleteNote.getName() + ": "
                + deletedNotes.toString());
        return new CommandResult(message);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof DeleteNoteCommand)) {
            return false;
        }

        DeleteNoteCommand otherDeleteNoteCommand = (DeleteNoteCommand) other;
        return personIndex.equals(otherDeleteNoteCommand.personIndex) && notes.equals(otherDeleteNoteCommand.notes);
    }

}
