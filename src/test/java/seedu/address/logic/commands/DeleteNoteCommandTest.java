package seedu.address.logic.commands;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.showPersonAtIndex;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.Note;
import seedu.address.model.person.Person;
import seedu.address.testutil.PersonBuilder;

public class DeleteNoteCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_deleteNoteFromUnfilteredList_success() {
        DeleteNoteCommand deleteNoteCommand = new DeleteNoteCommand(INDEX_FIRST_PERSON,
                new ArrayList<>(List.of(Index.fromOneBased(1), Index.fromOneBased(2))));
        Person personToDeleteNote = model.getFilteredPersonList().get(0);

        String expectedMessage = String.format(DeleteNoteCommand.MESSAGE_SUCCESS, Messages.format(personToDeleteNote),
                "\nExcited to learn Java\nAsked a good question about general relativity");

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.deletePerson(model.getFilteredPersonList().get(0));
        Person expectedPerson = new PersonBuilder().withName("Alice Pauline")
                .withClassGroup("A-1").withEmail("alice@example.com")
                .withPhone("94351253")
                .withTelegram("@alicepauline").withGithub("alicep").build();
        expectedModel.addPerson(expectedPerson);

        assertCommandSuccess(deleteNoteCommand, model, expectedMessage, expectedModel);

        // Adds the note back after the test to keep the model unchanged.
        personToDeleteNote.addNote(new Note("Asked a good question about general relativity"));
        personToDeleteNote.addNote(new Note("Excited to learn Java"));
    }

    @Test
    public void execute_deleteNoteFromFilteredList_success() {
        showPersonAtIndex(model, INDEX_FIRST_PERSON);

        Person personInFilteredList = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());

        DeleteNoteCommand deleteNoteCommand = new DeleteNoteCommand(INDEX_FIRST_PERSON,
                new ArrayList<>(List.of(Index.fromOneBased(1), Index.fromOneBased(2))));

        String expectedMessage = String.format(DeleteNoteCommand.MESSAGE_SUCCESS, Messages.format(personInFilteredList),
                "\nExcited to learn Java\nAsked a good question about general relativity");

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        showPersonAtIndex(expectedModel, INDEX_FIRST_PERSON);
        expectedModel.deletePerson(model.getFilteredPersonList().get(0));
        Person expectedPerson = new PersonBuilder().withName("Alice Pauline")
                .withClassGroup("A-1").withEmail("alice@example.com")
                .withPhone("94351253")
                .withTelegram("@alicepauline").withGithub("alicep").build();
        expectedModel.addPersonKeepFilter(expectedPerson);

        assertCommandSuccess(deleteNoteCommand, model, expectedMessage, expectedModel);

        // Adds the note back after the test to keep the model unchanged.
        personInFilteredList.addNote(new Note("Asked a good question about general relativity"));
        personInFilteredList.addNote(new Note("Excited to learn Java"));
    }

    @Test
    public void execute_invalidPersonIndex_failure() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredPersonList().size() + 1);
        DeleteNoteCommand deleteNoteCommand = new DeleteNoteCommand(outOfBoundIndex,
                new ArrayList<>(List.of(Index.fromOneBased(1), Index.fromOneBased(2))));

        assertCommandFailure(deleteNoteCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void execute_invalidNoteIndex_failure() {
        Person personToDeleteNote = model.getFilteredPersonList().get(0);
        DeleteNoteCommand deleteNoteCommand = new DeleteNoteCommand(Index.fromOneBased(1),
                new ArrayList<>(List.of(Index.fromOneBased(1),
                        Index.fromOneBased(personToDeleteNote.getNoteSize() + 1))));

        assertCommandFailure(deleteNoteCommand, model, Messages.MESSAGE_INVALID_NOTE_DISPLAYED_INDEX);
    }
}
