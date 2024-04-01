package seedu.tatoolkit.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.tatoolkit.logic.commands.CommandTestUtil.DESC_AMY;
import static seedu.tatoolkit.logic.commands.CommandTestUtil.DESC_BOB;
import static seedu.tatoolkit.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.tatoolkit.logic.commands.CommandTestUtil.VALID_PHONE_BOB;
import static seedu.tatoolkit.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.tatoolkit.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.tatoolkit.logic.commands.CommandTestUtil.showPersonAtIndex;
import static seedu.tatoolkit.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.tatoolkit.testutil.TypicalIndexes.INDEX_SECOND_PERSON;
import static seedu.tatoolkit.testutil.TypicalPersons.getTypicalTaToolkit;

import org.junit.jupiter.api.Test;

import seedu.tatoolkit.commons.core.index.Index;
import seedu.tatoolkit.logic.Messages;
import seedu.tatoolkit.logic.commands.EditCommand.EditPersonDescriptor;
import seedu.tatoolkit.model.Model;
import seedu.tatoolkit.model.ModelManager;
import seedu.tatoolkit.model.TaToolkit;
import seedu.tatoolkit.model.UserPrefs;
import seedu.tatoolkit.model.person.Note;
import seedu.tatoolkit.model.person.Person;
import seedu.tatoolkit.testutil.EditPersonDescriptorBuilder;
import seedu.tatoolkit.testutil.PersonBuilder;

/**
 * Contains integration tests (interaction with the Model) and unit tests for EditCommand.
 */
public class EditCommandTest {

    private Model model = new ModelManager(getTypicalTaToolkit(), new UserPrefs());

    @Test
    public void execute_allFieldsSpecifiedUnfilteredList_success() {
        Person editedPerson = new PersonBuilder().build();
        EditPersonDescriptor descriptor = new EditPersonDescriptorBuilder(editedPerson).build();
        EditCommand editCommand = new EditCommand(INDEX_FIRST_PERSON, descriptor);

        String expectedMessage = String.format(EditCommand.MESSAGE_EDIT_PERSON_SUCCESS, Messages.format(editedPerson));

        Model expectedModel = new ModelManager(new TaToolkit(model.getTaToolkit()), new UserPrefs());
        Person personToEdit = model.getFilteredPersonList().get(0);
        expectedModel.deletePerson(personToEdit);
        editedPerson.addNote(new Note("Asked a good question about general relativity"));
        editedPerson.addNote(new Note("Excited to learn Java"));
        expectedModel.addPerson(editedPerson);

        assertCommandSuccess(editCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_someFieldsSpecifiedUnfilteredList_success() {
        Index indexLastPerson = Index.fromOneBased(model.getFilteredPersonList().size());
        Person lastPerson = model.getFilteredPersonList().get(indexLastPerson.getZeroBased());

        PersonBuilder personInList = new PersonBuilder(lastPerson);
        Person editedPerson = personInList.withName(VALID_NAME_BOB).withPhone(VALID_PHONE_BOB).build();

        EditPersonDescriptor descriptor = new EditPersonDescriptorBuilder().withName(VALID_NAME_BOB)
                .withPhone(VALID_PHONE_BOB).build();
        EditCommand editCommand = new EditCommand(indexLastPerson, descriptor);

        String expectedMessage = String.format(EditCommand.MESSAGE_EDIT_PERSON_SUCCESS, Messages.format(editedPerson));

        Model expectedModel = new ModelManager(new TaToolkit(model.getTaToolkit()), new UserPrefs());
        expectedModel.deletePerson(lastPerson);
        expectedModel.addPerson(editedPerson);


        assertCommandSuccess(editCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_noFieldSpecifiedUnfilteredList_failure() {
        EditCommand editCommand = new EditCommand(INDEX_FIRST_PERSON, new EditPersonDescriptor());
        Person editedPerson = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());

        String expectedMessage = EditCommand.MESSAGE_NO_CHANGE;

        Model expectedModel = new ModelManager(new TaToolkit(model.getTaToolkit()), new UserPrefs());

        assertCommandFailure(editCommand, model, expectedMessage);
    }

    @Test
    public void execute_filteredList_success() {
        showPersonAtIndex(model, INDEX_FIRST_PERSON);

        Person personInFilteredList = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        Person editedPerson = new PersonBuilder(personInFilteredList).withName(VALID_NAME_BOB).build();
        EditCommand editCommand = new EditCommand(INDEX_FIRST_PERSON,
                new EditPersonDescriptorBuilder().withName(VALID_NAME_BOB).build());

        String expectedMessage = String.format(EditCommand.MESSAGE_EDIT_PERSON_SUCCESS, Messages.format(editedPerson));

        Model expectedModel = new ModelManager(new TaToolkit(model.getTaToolkit()), new UserPrefs());
        showPersonAtIndex(expectedModel, INDEX_FIRST_PERSON);
        expectedModel.deletePerson(model.getFilteredPersonList().get(0));
        expectedModel.addPersonKeepFilter(editedPerson);

        assertCommandSuccess(editCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_editLastViewedPerson_success() {
        Person personToEdit = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        model.updateLastViewedPerson(personToEdit);
        Person editedPerson = new PersonBuilder(personToEdit).withName(VALID_NAME_BOB).build();
        EditCommand editCommand = new EditCommand(INDEX_FIRST_PERSON,
                new EditPersonDescriptorBuilder().withName(VALID_NAME_BOB).build());

        String expectedMessage = String.format(EditCommand.MESSAGE_EDIT_PERSON_SUCCESS, Messages.format(editedPerson));

        Model expectedModel = new ModelManager(new TaToolkit(model.getTaToolkit()), new UserPrefs());
        expectedModel.updateLastViewedPerson(editedPerson);
        expectedModel.deletePerson(model.getFilteredPersonList().get(0));
        expectedModel.addPersonKeepFilter(editedPerson);

        assertCommandSuccess(editCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_duplicatePersonUnfilteredList_failure() {
        Person firstPerson = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        EditPersonDescriptor descriptor = new EditPersonDescriptorBuilder(firstPerson).build();
        EditCommand editCommand = new EditCommand(INDEX_SECOND_PERSON, descriptor);

        assertCommandFailure(editCommand, model, EditCommand.MESSAGE_DUPLICATE_FIELD);
    }

    @Test
    public void execute_duplicatePersonFilteredList_failure() {
        showPersonAtIndex(model, INDEX_FIRST_PERSON);

        // edit person in filtered list into a duplicate in TA Toolkit
        Person personInList = model.getTaToolkit().getPersonList().get(INDEX_SECOND_PERSON.getZeroBased());
        EditCommand editCommand = new EditCommand(INDEX_FIRST_PERSON,
                new EditPersonDescriptorBuilder(personInList).build());

        assertCommandFailure(editCommand, model, EditCommand.MESSAGE_DUPLICATE_FIELD);
    }

    @Test
    public void execute_invalidPersonIndexUnfilteredList_failure() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredPersonList().size() + 1);
        EditPersonDescriptor descriptor = new EditPersonDescriptorBuilder().withName(VALID_NAME_BOB).build();
        EditCommand editCommand = new EditCommand(outOfBoundIndex, descriptor);

        assertCommandFailure(editCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    /**
     * Edit filtered list where index is larger than size of filtered list,
     * but smaller than size of TA Toolkit
     */
    @Test
    public void execute_invalidPersonIndexFilteredList_failure() {
        showPersonAtIndex(model, INDEX_FIRST_PERSON);
        Index outOfBoundIndex = INDEX_SECOND_PERSON;
        // ensures that outOfBoundIndex is still in bounds of TA Toolkit list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getTaToolkit().getPersonList().size());

        EditCommand editCommand = new EditCommand(outOfBoundIndex,
                new EditPersonDescriptorBuilder().withName(VALID_NAME_BOB).build());

        assertCommandFailure(editCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void equals() {
        final EditCommand standardCommand = new EditCommand(INDEX_FIRST_PERSON, DESC_AMY);

        // same values -> returns true
        EditPersonDescriptor copyDescriptor = new EditPersonDescriptor(DESC_AMY);
        EditCommand commandWithSameValues = new EditCommand(INDEX_FIRST_PERSON, copyDescriptor);
        assertTrue(standardCommand.equals(commandWithSameValues));

        // same object -> returns true
        assertTrue(standardCommand.equals(standardCommand));

        // null -> returns false
        assertFalse(standardCommand.equals(null));

        // different types -> returns false
        assertFalse(standardCommand.equals(new ClearCommand()));

        // different index -> returns false
        assertFalse(standardCommand.equals(new EditCommand(INDEX_SECOND_PERSON, DESC_AMY)));

        // different descriptor -> returns false
        assertFalse(standardCommand.equals(new EditCommand(INDEX_FIRST_PERSON, DESC_BOB)));
    }

    @Test
    public void toStringMethod() {
        Index index = Index.fromOneBased(1);
        EditPersonDescriptor editPersonDescriptor = new EditPersonDescriptor();
        EditCommand editCommand = new EditCommand(index, editPersonDescriptor);
        String expected = EditCommand.class.getCanonicalName() + "{index=" + index + ", editPersonDescriptor="
                + editPersonDescriptor + "}";
        assertEquals(expected, editCommand.toString());
    }

}
