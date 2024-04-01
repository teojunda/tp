package seedu.tatoolkit.model;

import java.nio.file.Path;

import seedu.tatoolkit.commons.core.GuiSettings;

/**
 * Unmodifiable view of user prefs.
 */
public interface ReadOnlyUserPrefs {

    GuiSettings getGuiSettings();

    Path getTaToolkitFilePath();

}
