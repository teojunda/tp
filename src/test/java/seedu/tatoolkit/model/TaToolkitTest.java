package seedu.tatoolkit.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.tatoolkit.testutil.Assert.assertThrows;
import static seedu.tatoolkit.testutil.TypicalPersons.ALICE;
import static seedu.tatoolkit.testutil.TypicalPersons.getTypicalTaToolkit;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.tatoolkit.model.person.Person;
import seedu.tatoolkit.model.person.exceptions.DuplicatePersonException;
import seedu.tatoolkit.testutil.PersonBuilder;

public class TaToolkitTest {

    private final TaToolkit taToolkit = new TaToolkit();

    @Test
    public void constructor() {
        assertEquals(Collections.emptyList(), taToolkit.getPersonList());
    }

    @Test
    public void resetData_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> taToolkit.resetData(null));
    }

    @Test
    public void resetData_withValidReadOnlyTaToolkit_replacesData() {
        TaToolkit newData = getTypicalTaToolkit();
        taToolkit.resetData(newData);
        assertEquals(newData, taToolkit);
    }

    @Test
    public void resetData_withDuplicatePersons_throwsDuplicatePersonException() {
        // Two persons with the same identity fields
        Person editedAlice = new PersonBuilder(ALICE).build();
        List<Person> newPersons = Arrays.asList(ALICE, editedAlice);
        TaToolkitStub newData = new TaToolkitStub(newPersons);

        assertThrows(DuplicatePersonException.class, () -> taToolkit.resetData(newData));
    }

    @Test
    public void hasPerson_nullPerson_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> taToolkit.hasPerson(null));
    }

    @Test
    public void hasPerson_personNotInTaToolkit_returnsFalse() {
        assertFalse(taToolkit.hasPerson(ALICE));
    }

    @Test
    public void hasPerson_personInTaToolkit_returnsTrue() {
        taToolkit.addPerson(ALICE);
        assertTrue(taToolkit.hasPerson(ALICE));
    }

    @Test
    public void hasPerson_personWithSameIdentityFieldsInTaToolkit_returnsTrue() {
        taToolkit.addPerson(ALICE);
        Person editedAlice = new PersonBuilder(ALICE).build();
        assertTrue(taToolkit.hasPerson(editedAlice));
    }

    @Test
    public void getPersonList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, () -> taToolkit.getPersonList().remove(0));
    }

    @Test
    public void toStringMethod() {
        String expected = TaToolkit.class.getCanonicalName() + "{persons=" + taToolkit.getPersonList() + "}";
        assertEquals(expected, taToolkit.toString());
    }

    /**
     * A stub ReadOnlyTaToolkit whose persons list can violate interface constraints.
     */
    private static class TaToolkitStub implements ReadOnlyTaToolkit {
        private final ObservableList<Person> persons = FXCollections.observableArrayList();

        TaToolkitStub(Collection<Person> persons) {
            this.persons.setAll(persons);
        }

        @Override
        public ObservableList<Person> getPersonList() {
            return persons;
        }
    }

}
