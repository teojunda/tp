package seedu.tatoolkit.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.tatoolkit.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.tatoolkit.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.tatoolkit.logic.commands.CommandTestUtil.showPersonAtIndex;
import static seedu.tatoolkit.logic.commands.ViewCommand.MESSAGE_VIEW_PERSON_SUCCESS;
import static seedu.tatoolkit.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.tatoolkit.testutil.TypicalIndexes.INDEX_SECOND_PERSON;
import static seedu.tatoolkit.testutil.TypicalPersons.getTypicalTaToolkit;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.tatoolkit.commons.core.index.Index;
import seedu.tatoolkit.logic.Messages;
import seedu.tatoolkit.model.Model;
import seedu.tatoolkit.model.ModelManager;
import seedu.tatoolkit.model.UserPrefs;
import seedu.tatoolkit.model.person.Person;

/**
 * Contains integration tests (interaction with the Model) and unit tests for
 * {@code DeleteCommand}.
 */
public class ViewCommandTest {

    private Model model;

    @BeforeEach
    public void setUp() {
        model = new ModelManager(getTypicalTaToolkit(), new UserPrefs());
    }

    @Test
    public void execute_validIndexUnfilteredList_success() {
        Person personToView = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        ViewCommand viewCommand = new ViewCommand(INDEX_FIRST_PERSON);

        String expectedMessage = String.format(MESSAGE_VIEW_PERSON_SUCCESS,
                Messages.format(personToView));

        ModelManager expectedModel = new ModelManager(model.getTaToolkit(), new UserPrefs());

        assertCommandSuccess(viewCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexUnfilteredList_throwsCommandException() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredPersonList().size() + 1);
        ViewCommand viewCommand = new ViewCommand(outOfBoundIndex);

        assertCommandFailure(viewCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void execute_invalidIndexFilteredList_throwsCommandException() {
        showPersonAtIndex(model, INDEX_FIRST_PERSON);

        Index outOfBoundIndex = INDEX_SECOND_PERSON;
        // ensures that outOfBoundIndex is still in bounds of TA Toolkit list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getTaToolkit().getPersonList().size());

        ViewCommand viewCommand = new ViewCommand(outOfBoundIndex);

        assertCommandFailure(viewCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void equals() {
        ViewCommand viewFirstCommand = new ViewCommand(INDEX_FIRST_PERSON);
        ViewCommand viewSecondCommand = new ViewCommand(INDEX_SECOND_PERSON);

        // same object -> returns true
        assertEquals(viewFirstCommand, viewFirstCommand);

        // same values -> returns true
        ViewCommand viewFirstCommandCopy = new ViewCommand(INDEX_FIRST_PERSON);
        assertEquals(viewFirstCommand, viewFirstCommandCopy);

        // different types -> returns false
        assertNotEquals(1, viewFirstCommand);

        // null -> returns false
        assertNotEquals(null, viewFirstCommand);

        // different person -> returns false
        assertNotEquals(viewFirstCommand, viewSecondCommand);
    }

    @Test
    public void execute_emptyTaToolkit_throwsCommandException() {
        // Empty the TA Toolkit
        model = new ModelManager();
        ViewCommand viewCommand = new ViewCommand(Index.fromOneBased(1));

        assertCommandFailure(viewCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void equals_otherObjectNotInstanceOfViewCommand_returnsFalse() {
        // Create a ViewCommand object
        ViewCommand viewCommand = new ViewCommand(Index.fromOneBased(1));

        // Create another object of a different class
        Object otherObject = new Object();

        // Check if they are equal
        assertFalse(viewCommand.equals(otherObject));
    }

    @Test
    public void toStringMethod() {
        Index targetIndex = Index.fromOneBased(1);
        ViewCommand viewCommand = new ViewCommand(targetIndex);
        String expected = ViewCommand.class.getCanonicalName() + "{targetIndex=" + targetIndex + "}";
        assertEquals(expected, viewCommand.toString());
    }
}
