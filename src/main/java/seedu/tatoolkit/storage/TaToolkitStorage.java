package seedu.tatoolkit.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

import seedu.tatoolkit.commons.exceptions.DataLoadingException;
import seedu.tatoolkit.model.ReadOnlyTaToolkit;
import seedu.tatoolkit.model.TaToolkit;

/**
 * Represents a storage for {@link TaToolkit}.
 */
public interface TaToolkitStorage {

    /**
     * Returns the file path of the data file.
     */
    Path getTaToolkitFilePath();

    /**
     * Returns TaToolkit data as a {@link ReadOnlyTaToolkit}.
     * Returns {@code Optional.empty()} if storage file is not found.
     *
     * @throws DataLoadingException if loading the data from storage failed.
     */
    Optional<ReadOnlyTaToolkit> readTaToolkit() throws DataLoadingException;

    /**
     * @see #getTaToolkitFilePath()
     */
    Optional<ReadOnlyTaToolkit> readTaToolkit(Path filePath) throws DataLoadingException;

    /**
     * Saves the given {@link ReadOnlyTaToolkit} to the storage.
     * @param taToolkit cannot be null.
     * @throws IOException if there was any problem writing to the file.
     */
    void saveTaToolkit(ReadOnlyTaToolkit taToolkit) throws IOException;

    /**
     * @see #saveTaToolkit(ReadOnlyTaToolkit)
     */
    void saveTaToolkit(ReadOnlyTaToolkit taToolkit, Path filePath) throws IOException;

}
