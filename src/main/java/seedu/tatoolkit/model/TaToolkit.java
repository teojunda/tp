package seedu.tatoolkit.model;

import static java.util.Objects.requireNonNull;

import java.util.List;

import javafx.collections.ObservableList;
import seedu.tatoolkit.commons.util.ToStringBuilder;
import seedu.tatoolkit.model.person.Person;
import seedu.tatoolkit.model.person.UniquePersonList;

/**
 * Wraps all data at the tatoolkit-book level
 * Duplicates are not allowed (by .isSamePerson comparison)
 */
public class TaToolkit implements ReadOnlyTaToolkit {

    private final UniquePersonList persons;

    /*
     * The 'unusual' code block below is a non-static initialization block, sometimes used to avoid duplication
     * between constructors. See https://docs.oracle.com/javase/tutorial/java/javaOO/initial.html
     *
     * Note that non-static init blocks are not recommended to use. There are other ways to avoid duplication
     *   among constructors.
     */
    {
        persons = new UniquePersonList();
    }

    public TaToolkit() {}

    /**
     * Creates a TaToolkit using the Persons in the {@code toBeCopied}
     */
    public TaToolkit(ReadOnlyTaToolkit toBeCopied) {
        this();
        resetData(toBeCopied);
    }

    //// list overwrite operations

    /**
     * Replaces the contents of the person list with {@code persons}.
     * {@code persons} must not contain duplicate persons.
     */
    public void setPersons(List<Person> persons) {
        this.persons.setPersons(persons);
    }

    /**
     * Resets the existing data of this {@code TaToolkit} with {@code newData}.
     */
    public void resetData(ReadOnlyTaToolkit newData) {
        requireNonNull(newData);

        setPersons(newData.getPersonList());
    }

    //// person-level operations

    /**
     * Returns true if a person with the same identity as {@code person} exists in the tatoolkit book.
     */
    public boolean hasPerson(Person person) {
        requireNonNull(person);
        return persons.contains(person);
    }

    /**
     * Adds a person to the tatoolkit book.
     * The person must not already exist in the tatoolkit book.
     */
    public void addPerson(Person p) {
        persons.add(p);
    }

    /**
     * Removes {@code key} from this {@code TaToolkit}.
     * {@code key} must exist in the tatoolkit book.
     */
    public void removePerson(Person key) {
        persons.remove(key);
    }

    //// util methods

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("persons", persons)
                .toString();
    }

    @Override
    public ObservableList<Person> getPersonList() {
        return persons.asUnmodifiableObservableList();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof TaToolkit)) {
            return false;
        }

        TaToolkit otherTaToolkit = (TaToolkit) other;
        return persons.equals(otherTaToolkit.persons);
    }

    @Override
    public int hashCode() {
        return persons.hashCode();
    }
}
