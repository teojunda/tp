package seedu.tatoolkit.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;
import java.util.logging.Logger;

import seedu.tatoolkit.commons.core.LogsCenter;
import seedu.tatoolkit.commons.exceptions.DataLoadingException;
import seedu.tatoolkit.model.ReadOnlyTaToolkit;
import seedu.tatoolkit.model.ReadOnlyUserPrefs;
import seedu.tatoolkit.model.UserPrefs;

/**
 * Manages storage of TaToolkit data in local storage.
 */
public class StorageManager implements Storage {

    private static final Logger logger = LogsCenter.getLogger(StorageManager.class);
    private TaToolkitStorage taToolkitStorage;
    private UserPrefsStorage userPrefsStorage;

    /**
     * Creates a {@code StorageManager} with the given {@code TaToolkitStorage} and {@code UserPrefStorage}.
     */
    public StorageManager(TaToolkitStorage taToolkitStorage, UserPrefsStorage userPrefsStorage) {
        this.taToolkitStorage = taToolkitStorage;
        this.userPrefsStorage = userPrefsStorage;
    }

    // ================ UserPrefs methods ==============================

    @Override
    public Path getUserPrefsFilePath() {
        return userPrefsStorage.getUserPrefsFilePath();
    }

    @Override
    public Optional<UserPrefs> readUserPrefs() throws DataLoadingException {
        return userPrefsStorage.readUserPrefs();
    }

    @Override
    public void saveUserPrefs(ReadOnlyUserPrefs userPrefs) throws IOException {
        userPrefsStorage.saveUserPrefs(userPrefs);
    }


    // ================ TaToolkit methods ==============================

    @Override
    public Path getTaToolkitFilePath() {
        return taToolkitStorage.getTaToolkitFilePath();
    }

    @Override
    public Optional<ReadOnlyTaToolkit> readTaToolkit() throws DataLoadingException {
        return readTaToolkit(taToolkitStorage.getTaToolkitFilePath());
    }

    @Override
    public Optional<ReadOnlyTaToolkit> readTaToolkit(Path filePath) throws DataLoadingException {
        logger.fine("Attempting to read data from file: " + filePath);
        return taToolkitStorage.readTaToolkit(filePath);
    }

    @Override
    public void saveTaToolkit(ReadOnlyTaToolkit taToolkit) throws IOException {
        saveTaToolkit(taToolkit, taToolkitStorage.getTaToolkitFilePath());
    }

    @Override
    public void saveTaToolkit(ReadOnlyTaToolkit taToolkit, Path filePath) throws IOException {
        logger.fine("Attempting to write to data file: " + filePath);
        taToolkitStorage.saveTaToolkit(taToolkit, filePath);
    }

}
