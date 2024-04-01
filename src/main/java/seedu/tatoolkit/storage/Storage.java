package seedu.tatoolkit.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

import seedu.tatoolkit.commons.exceptions.DataLoadingException;
import seedu.tatoolkit.model.ReadOnlyTaToolkit;
import seedu.tatoolkit.model.ReadOnlyUserPrefs;
import seedu.tatoolkit.model.UserPrefs;

/**
 * API of the Storage component
 */
public interface Storage extends TaToolkitStorage, UserPrefsStorage {

    @Override
    Optional<UserPrefs> readUserPrefs() throws DataLoadingException;

    @Override
    void saveUserPrefs(ReadOnlyUserPrefs userPrefs) throws IOException;

    @Override
    Path getTaToolkitFilePath();

    @Override
    Optional<ReadOnlyTaToolkit> readTaToolkit() throws DataLoadingException;

    @Override
    void saveTaToolkit(ReadOnlyTaToolkit taToolkit) throws IOException;

}
