package seedu.tatoolkit.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static seedu.tatoolkit.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.tatoolkit.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.tatoolkit.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.tatoolkit.testutil.TypicalIndexes.INDEX_SECOND_PERSON;
import static seedu.tatoolkit.testutil.TypicalIndexes.INDEX_THIRD_PERSON;
import static seedu.tatoolkit.testutil.TypicalPersons.getTypicalTaToolkit;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.tatoolkit.commons.core.index.Index;
import seedu.tatoolkit.logic.Messages;
import seedu.tatoolkit.model.Model;
import seedu.tatoolkit.model.ModelManager;
import seedu.tatoolkit.model.UserPrefs;
import seedu.tatoolkit.model.attendance.Week;

/**
 * Contains integration tests (interaction with the Model) and unit tests for {@code MarkCommand}.
 */
public class MarkCommandTest {

    private Model model;

    @BeforeEach
    public void setUp() {
        model = new ModelManager(getTypicalTaToolkit(), new UserPrefs());
    }

    @Test
    public void execute_validIndicesUnfilteredList_success() {
        Week week = new Week(Index.fromOneBased(1));
        List<Index> presentIndices = Arrays.asList(INDEX_FIRST_PERSON, INDEX_SECOND_PERSON);
        List<Index> absentIndices = Arrays.asList(INDEX_THIRD_PERSON);
        MarkCommand markCommand = new MarkCommand(week, presentIndices, absentIndices);

        String presentNames = presentIndices.stream()
                .map(index -> model.getFilteredPersonList().get(index.getZeroBased()).getName().toString())
                .collect(Collectors.joining(", "));

        String absentNames = absentIndices.stream()
                .map(index -> model.getFilteredPersonList().get(index.getZeroBased()).getName().toString())
                .collect(Collectors.joining(", "));

        String expectedMessage = String.format(
                MarkCommand.MESSAGE_SUCCESS,
                week.toString(),
                presentNames,
                week.toString(),
                absentNames
        );

        ModelManager expectedModel = new ModelManager(model.getTaToolkit(), new UserPrefs());

        assertCommandSuccess(markCommand, model, expectedMessage, expectedModel);
    }


    @Test
    public void execute_invalidIndicesUnfilteredList_failure() {
        Index weekIndex = Index.fromOneBased(1);
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredPersonList().size() + 1);
        List<Index> presentIndices = Arrays.asList(outOfBoundIndex);
        List<Index> absentIndices = Arrays.asList(INDEX_FIRST_PERSON);

        MarkCommand markCommand = new MarkCommand(new Week(weekIndex), presentIndices, absentIndices);

        assertCommandFailure(markCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void equals() {
        Index weekIndex = Index.fromOneBased(1);
        List<Index> presentIndices = Arrays.asList(INDEX_FIRST_PERSON);
        List<Index> absentIndices = Arrays.asList(INDEX_SECOND_PERSON);
        MarkCommand markFirstCommand = new MarkCommand(new Week(weekIndex), presentIndices, absentIndices);

        assertEquals(markFirstCommand, markFirstCommand);

        MarkCommand markFirstCommandCopy = new MarkCommand(new Week(weekIndex), presentIndices, absentIndices);
        assertEquals(markFirstCommand, markFirstCommandCopy);

        assertNotEquals(markFirstCommand, new Object());

        assertNotEquals(null, markFirstCommand);

        List<Index> differentPresentIndices = Arrays.asList(INDEX_THIRD_PERSON);
        List<Index> differentAbsentIndices = Arrays.asList(INDEX_FIRST_PERSON);
        MarkCommand markSecondCommand = new MarkCommand(new Week(weekIndex),
                differentPresentIndices, differentAbsentIndices);
        assertNotEquals(markFirstCommand, markSecondCommand);
    }
}
