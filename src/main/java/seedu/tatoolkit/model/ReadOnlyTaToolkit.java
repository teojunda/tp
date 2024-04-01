package seedu.tatoolkit.model;

import javafx.collections.ObservableList;
import seedu.tatoolkit.model.person.Person;

/**
 * Unmodifiable view of an tatoolkit book
 */
public interface ReadOnlyTaToolkit {

    /**
     * Returns an unmodifiable view of the persons list.
     * This list will not contain any duplicate persons.
     */
    ObservableList<Person> getPersonList();

}
