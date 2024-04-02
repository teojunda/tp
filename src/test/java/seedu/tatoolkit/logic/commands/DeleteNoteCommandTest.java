package seedu.tatoolkit.logic.commands;

import static seedu.tatoolkit.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.tatoolkit.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.tatoolkit.logic.commands.CommandTestUtil.showPersonAtIndex;
import static seedu.tatoolkit.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.tatoolkit.testutil.TypicalPersons.getTypicalTaToolkit;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.tatoolkit.commons.core.index.Index;
import seedu.tatoolkit.logic.Messages;
import seedu.tatoolkit.model.Model;
import seedu.tatoolkit.model.ModelManager;
import seedu.tatoolkit.model.TaToolkit;
import seedu.tatoolkit.model.UserPrefs;
import seedu.tatoolkit.model.person.Note;
import seedu.tatoolkit.model.person.Person;
import seedu.tatoolkit.testutil.PersonBuilder;

public class DeleteNoteCommandTest {

    private Model model = new ModelManager(getTypicalTaToolkit(), new UserPrefs());

    @Test
    public void execute_deleteNoteFromUnfilteredList_success() {
        DeleteNoteCommand deleteNoteCommand = new DeleteNoteCommand(INDEX_FIRST_PERSON,
                new ArrayList<>(List.of(Index.fromOneBased(1), Index.fromOneBased(2))));
        Person personToDeleteNote = model.getFilteredPersonList().get(0);

        String expectedMessage = String.format(DeleteNoteCommand.MESSAGE_SUCCESS, Messages.format(personToDeleteNote),
                "\nExcited to learn Java\nAsked a good question about general relativity");

        Model expectedModel = new ModelManager(new TaToolkit(model.getTaToolkit()), new UserPrefs());
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

        Model expectedModel = new ModelManager(new TaToolkit(model.getTaToolkit()), new UserPrefs());
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
