package seedu.tatoolkit.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.tatoolkit.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.tatoolkit.logic.parser.CliSyntax.PREFIX_NOTE;

import seedu.tatoolkit.commons.core.index.Index;
import seedu.tatoolkit.logic.commands.AddNoteCommand;
import seedu.tatoolkit.logic.parser.exceptions.ParseException;
import seedu.tatoolkit.model.person.Note;

/**
 * Parses input arguments and creates a new AddNote object
 */
public class AddNoteCommandParser implements Parser<AddNoteCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the AddNoteCommand
     * and returns an AddNoteCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public AddNoteCommand parse(String args) throws ParseException {
        requireNonNull(args);

        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_NOTE);

        Index index;
        try {
            index = ParserUtil.parseIndex(argMultimap.getPreamble());
        } catch (ParseException pe) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddNoteCommand.MESSAGE_USAGE), pe);
        }

        if (!ParserUtil.arePrefixesPresent(argMultimap, PREFIX_NOTE)) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddNoteCommand.MESSAGE_USAGE));
        }

        argMultimap.verifyNoDuplicatePrefixesFor(PREFIX_NOTE);
        Note note = ParserUtil.parseNote(argMultimap.getValue(PREFIX_NOTE).get());

        return new AddNoteCommand(index, note);
    }
}
