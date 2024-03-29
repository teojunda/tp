package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_INDICES;

import java.util.List;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.DeleteNoteCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new DeleteNoteCommand object
 */
public class DeleteNoteCommandParser implements Parser<DeleteNoteCommand> {
    /**
     * Parses the given {@code String} of arguments in the context of the DeleteNoteCommand
     * and returns an DeleteNoteCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    @Override
    public DeleteNoteCommand parse(String args) throws ParseException {
        requireNonNull(args);

        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_INDICES);

        Index personIndex;
        try {
            personIndex = ParserUtil.parseIndex(argMultimap.getPreamble());
        } catch (ParseException pe) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteNoteCommand.MESSAGE_USAGE), pe);
        }

        if (!ParserUtil.arePrefixesPresent(argMultimap, PREFIX_INDICES)) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteNoteCommand.MESSAGE_USAGE));
        }

        argMultimap.verifyNoDuplicatePrefixesFor(PREFIX_INDICES);
        List<Index> notes = ParserUtil.parseIndices(argMultimap.getValue(PREFIX_INDICES).get());

        return new DeleteNoteCommand(personIndex, notes);
    }
}
