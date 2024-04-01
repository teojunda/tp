package seedu.tatoolkit.logic;

import java.nio.file.Path;
import java.util.Optional;

import javafx.collections.ObservableList;
import seedu.tatoolkit.commons.core.GuiSettings;
import seedu.tatoolkit.logic.commands.CommandResult;
import seedu.tatoolkit.logic.commands.exceptions.CommandException;
import seedu.tatoolkit.logic.parser.exceptions.ParseException;
import seedu.tatoolkit.model.ReadOnlyTaToolkit;
import seedu.tatoolkit.model.person.Person;

/**
 * API of the Logic component
 */
public interface Logic {
    /**
     * Executes the command and returns the result.
     * @param commandText The command as entered by the user.
     * @return the result of the command execution.
     * @throws CommandException If an error occurs during command execution.
     * @throws ParseException If an error occurs during parsing.
     */
    CommandResult execute(String commandText) throws CommandException, ParseException;

    /**
     * Returns the TaToolkit.
     *
     * @see seedu.tatoolkit.model.Model#getTaToolkit()
     */
    ReadOnlyTaToolkit getTaToolkit();

    /** Returns an unmodifiable view of the filtered list of persons */
    ObservableList<Person> getFilteredPersonList();

    /**
     * Retrieves the last viewed {@link Person} instance.
     * <p>
     * This method returns an {@link Optional} which will be empty if no person has
     * been viewed last, or will contain a reference to the {@link Person} object
     * that was last viewed.
     *
     * @return an {@link Optional} containing the last viewed {@link Person} if such
     *     a person exists, or an empty {@link Optional} if no person has been viewed last.
     */
    Optional<Person> getLastViewedPerson();

    /**
     * Returns the user prefs' TA Toolkit file path.
     */
    Path getTaToolkitFilePath();

    /**
     * Returns the user prefs' GUI settings.
     */
    GuiSettings getGuiSettings();

    /**
     * Set the user prefs' GUI settings.
     */
    void setGuiSettings(GuiSettings guiSettings);
}
