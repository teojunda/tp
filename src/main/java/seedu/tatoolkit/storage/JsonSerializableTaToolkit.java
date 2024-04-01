package seedu.tatoolkit.storage;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

import seedu.tatoolkit.commons.exceptions.IllegalValueException;
import seedu.tatoolkit.model.ReadOnlyTaToolkit;
import seedu.tatoolkit.model.TaToolkit;
import seedu.tatoolkit.model.person.Person;

/**
 * An Immutable TaToolkit that is serializable to JSON format.
 */
@JsonRootName(value = "tatoolkit")
class JsonSerializableTaToolkit {

    public static final String MESSAGE_DUPLICATE_PERSON = "Persons list contains duplicate person(s).";

    private final List<JsonAdaptedPerson> persons = new ArrayList<>();

    /**
     * Constructs a {@code JsonSerializableTaToolkit} with the given persons.
     */
    @JsonCreator
    public JsonSerializableTaToolkit(@JsonProperty("persons") List<JsonAdaptedPerson> persons) {
        this.persons.addAll(persons);
    }

    /**
     * Converts a given {@code ReadOnlyTaToolkit} into this class for Jackson use.
     *
     * @param source future changes to this will not affect the created {@code JsonSerializableTaToolkit}.
     */
    public JsonSerializableTaToolkit(ReadOnlyTaToolkit source) {
        persons.addAll(source.getPersonList().stream().map(JsonAdaptedPerson::new).collect(Collectors.toList()));
    }

    /**
     * Converts this TA Toolkit into the model's {@code TaToolkit} object.
     *
     * @throws IllegalValueException if there were any data constraints violated.
     */
    public TaToolkit toModelType() throws IllegalValueException {
        TaToolkit taToolkit = new TaToolkit();
        for (JsonAdaptedPerson jsonAdaptedPerson : persons) {
            Person person = jsonAdaptedPerson.toModelType();
            if (taToolkit.hasPerson(person)) {
                throw new IllegalValueException(MESSAGE_DUPLICATE_PERSON);
            }
            taToolkit.addPerson(person);
        }
        return taToolkit;
    }

}
