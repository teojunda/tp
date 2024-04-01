package seedu.tatoolkit.logic.parser;

import static seedu.tatoolkit.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.tatoolkit.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.tatoolkit.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.tatoolkit.logic.parser.ParserUtil.MESSAGE_DUPLICATE_INDICES;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.tatoolkit.commons.core.index.Index;
import seedu.tatoolkit.logic.commands.DeleteNoteCommand;

public class DeleteNoteCommandParserTest {
    private static final String MESSAGE_INVALID_FORMAT =
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteNoteCommand.MESSAGE_USAGE);

    private DeleteNoteCommandParser parser = new DeleteNoteCommandParser();

    @Test
    public void parse_allFieldsPresent_success() {
        Index expectedIndex = Index.fromOneBased(1);
        List<Index> expectedNoteIndex = new ArrayList<>();
        expectedNoteIndex.add(Index.fromOneBased(1));
        expectedNoteIndex.add(Index.fromOneBased(2));
        assertParseSuccess(parser, "1 i/1, 2", new DeleteNoteCommand(expectedIndex, expectedNoteIndex));
    }

    @Test
    public void parse_duplicateIndices_failure() {
        assertParseFailure(parser, "1 i/1, 2, 2", MESSAGE_DUPLICATE_INDICES);
    }

    @Test
    public void parse_missingArgument_throwParseException() {
        assertParseFailure(parser, "0", MESSAGE_INVALID_FORMAT);
        assertParseFailure(parser, "i/i, 2", MESSAGE_INVALID_FORMAT);
    }

}
