package seedu.tatoolkit.testutil;

import seedu.tatoolkit.model.TaToolkit;
import seedu.tatoolkit.model.person.Person;

/**
 * A utility class to help with building TaToolkit objects.
 * Example usage: <br>
 *     {@code TaToolkit toolkit = new TaToolkitBuilder().withPerson("John", "Doe").build();}
 */
public class TaToolkitBuilder {

    private TaToolkit taToolkit;

    public TaToolkitBuilder() {
        taToolkit = new TaToolkit();
    }

    public TaToolkitBuilder(TaToolkit taToolkit) {
        this.taToolkit = taToolkit;
    }

    /**
     * Adds a new {@code Person} to the {@code TaToolkit} that we are building.
     */
    public TaToolkitBuilder withPerson(Person person) {
        taToolkit.addPerson(person);
        return this;
    }

    public TaToolkit build() {
        return taToolkit;
    }
}
