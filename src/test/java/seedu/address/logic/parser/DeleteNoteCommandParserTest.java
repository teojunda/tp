package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.jupiter.api.Test;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.AddNoteCommand;
import seedu.address.logic.commands.DeleteNoteCommand;
import seedu.address.model.person.Note;

import java.util.ArrayList;
import java.util.List;

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
        assertParseSuccess(parser, "1 i/1, 2, 2", new DeleteNoteCommand(expectedIndex, expectedNoteIndex));
    }
}
