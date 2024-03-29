package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_INDICES;
import static seedu.address.logic.parser.CliSyntax.PREFIX_WEEK;

import java.util.List;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.MarkCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.attendance.Week;

/**
 * Parses input arguments and creates a new MarkCommand object
 */
public class MarkCommandParser implements Parser<MarkCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the MarkCommand
     * and returns an MarkCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    @Override
    public MarkCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_WEEK, PREFIX_INDICES);
        if (!ParserUtil.arePrefixesPresent(argMultimap, PREFIX_WEEK, PREFIX_INDICES)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, MarkCommand.MESSAGE_USAGE));
        }

        argMultimap.verifyNoDuplicatePrefixesFor(PREFIX_WEEK, PREFIX_INDICES);
        Week week = ParserUtil.parseWeek(argMultimap.getValue(PREFIX_WEEK).get());
        List<Index> indices = ParserUtil.parseIndices(argMultimap.getValue(PREFIX_INDICES).get());

        return new MarkCommand(week, indices);
    }
}
