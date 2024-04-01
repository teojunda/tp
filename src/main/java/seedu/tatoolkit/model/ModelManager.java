package seedu.tatoolkit.model;

import static java.util.Objects.requireNonNull;
import static seedu.tatoolkit.commons.util.CollectionUtil.requireAllNonNull;

import java.nio.file.Path;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.logging.Logger;

import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import seedu.tatoolkit.commons.core.GuiSettings;
import seedu.tatoolkit.commons.core.LogsCenter;
import seedu.tatoolkit.model.person.Person;

/**
 * Represents the in-memory model of the TA Toolkit data.
 */
public class ModelManager implements Model {
    private static final Logger logger = LogsCenter.getLogger(ModelManager.class);

    private final TaToolkit taToolkit;
    private final UserPrefs userPrefs;
    private final FilteredList<Person> filteredPersons;
    private Optional<Person> lastViewedPerson;

    /**
     * Initializes a ModelManager with the given taToolkit and userPrefs.
     */
    public ModelManager(ReadOnlyTaToolkit taToolkit, ReadOnlyUserPrefs userPrefs) {
        requireAllNonNull(taToolkit, userPrefs);

        logger.fine("Initializing with TA Toolkit: " + taToolkit + " and user prefs " + userPrefs);

        this.taToolkit = new TaToolkit(taToolkit);
        this.userPrefs = new UserPrefs(userPrefs);
        filteredPersons = new FilteredList<>(this.taToolkit.getPersonList());
        lastViewedPerson = Optional.empty();
    }

    public ModelManager() {
        this(new TaToolkit(), new UserPrefs());
    }

    //=========== UserPrefs ==================================================================================

    @Override
    public void setUserPrefs(ReadOnlyUserPrefs userPrefs) {
        requireNonNull(userPrefs);
        this.userPrefs.resetData(userPrefs);
    }

    @Override
    public ReadOnlyUserPrefs getUserPrefs() {
        return userPrefs;
    }

    @Override
    public GuiSettings getGuiSettings() {
        return userPrefs.getGuiSettings();
    }

    @Override
    public void setGuiSettings(GuiSettings guiSettings) {
        requireNonNull(guiSettings);
        userPrefs.setGuiSettings(guiSettings);
    }

    @Override
    public Path getTaToolkitFilePath() {
        return userPrefs.getTaToolkitFilePath();
    }

    @Override
    public void setTaToolkitFilePath(Path taToolkitFilePath) {
        requireNonNull(taToolkitFilePath);
        userPrefs.setTaToolkitFilePath(taToolkitFilePath);
    }

    //=========== TaToolkit ================================================================================

    @Override
    public void setTaToolkit(ReadOnlyTaToolkit taToolkit) {
        this.taToolkit.resetData(taToolkit);
    }

    @Override
    public ReadOnlyTaToolkit getTaToolkit() {
        return taToolkit;
    }

    @Override
    public boolean hasPerson(Person person) {
        requireNonNull(person);
        return taToolkit.hasPerson(person);
    }

    @Override
    public void deletePerson(Person target) {
        taToolkit.removePerson(target);
    }

    @Override
    public void addPerson(Person person) {
        taToolkit.addPerson(person);
        updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
    }

    @Override
    public void addPersonKeepFilter(Person person) {
        taToolkit.addPerson(person);
    }

    //=========== Filtered Person List Accessors =============================================================

    /**
     * Returns an unmodifiable view of the list of {@code Person} backed by the internal list of
     * {@code versionedTaToolkit}
     */
    @Override
    public ObservableList<Person> getFilteredPersonList() {
        return filteredPersons;
    }

    @Override
    public void updateFilteredPersonList(Predicate<Person> predicate) {
        requireNonNull(predicate);
        filteredPersons.setPredicate(predicate);
    }

    //=========== Last Viewed Person Accessors =============================================================

    @Override
    public Optional<Person> getLastViewedPerson() {
        return lastViewedPerson;
    }

    @Override
    public void resetLastViewedPerson() {
        lastViewedPerson = Optional.empty();
    }

    @Override
    public void updateLastViewedPerson(Person p) {
        requireNonNull(p);
        lastViewedPerson = Optional.of(p);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof ModelManager)) {
            return false;
        }

        ModelManager otherModelManager = (ModelManager) other;
        return taToolkit.equals(otherModelManager.taToolkit)
                && userPrefs.equals(otherModelManager.userPrefs)
                && filteredPersons.equals(otherModelManager.filteredPersons);
    }
}
