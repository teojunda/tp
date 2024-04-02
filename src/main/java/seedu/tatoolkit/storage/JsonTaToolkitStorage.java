package seedu.tatoolkit.storage;

import static java.util.Objects.requireNonNull;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;
import java.util.logging.Logger;

import seedu.tatoolkit.commons.core.LogsCenter;
import seedu.tatoolkit.commons.exceptions.DataLoadingException;
import seedu.tatoolkit.commons.exceptions.IllegalValueException;
import seedu.tatoolkit.commons.util.FileUtil;
import seedu.tatoolkit.commons.util.JsonUtil;
import seedu.tatoolkit.model.ReadOnlyTaToolkit;

/**
 * A class to access TaToolkit data stored as a json file on the hard disk.
 */
public class JsonTaToolkitStorage implements TaToolkitStorage {

    private static final Logger logger = LogsCenter.getLogger(JsonTaToolkitStorage.class);

    private Path filePath;

    public JsonTaToolkitStorage(Path filePath) {
        this.filePath = filePath;
    }

    public Path getTaToolkitFilePath() {
        return filePath;
    }

    @Override
    public Optional<ReadOnlyTaToolkit> readTaToolkit() throws DataLoadingException {
        return readTaToolkit(filePath);
    }

    /**
     * Similar to {@link #readTaToolkit()}.
     *
     * @param filePath location of the data. Cannot be null.
     * @throws DataLoadingException if loading the data from storage failed.
     */
    public Optional<ReadOnlyTaToolkit> readTaToolkit(Path filePath) throws DataLoadingException {
        requireNonNull(filePath);

        Optional<JsonSerializableTaToolkit> jsonTaToolkit = JsonUtil.readJsonFile(
                filePath, JsonSerializableTaToolkit.class);
        if (!jsonTaToolkit.isPresent()) {
            return Optional.empty();
        }

        try {
            return Optional.of(jsonTaToolkit.get().toModelType());
        } catch (IllegalValueException ive) {
            logger.info("Illegal values found in " + filePath + ": " + ive.getMessage());
            throw new DataLoadingException(ive);
        }
    }

    @Override
    public void saveTaToolkit(ReadOnlyTaToolkit taToolkit) throws IOException {
        saveTaToolkit(taToolkit, filePath);
    }

    /**
     * Similar to {@link #saveTaToolkit(ReadOnlyTaToolkit)}.
     *
     * @param filePath location of the data. Cannot be null.
     */
    public void saveTaToolkit(ReadOnlyTaToolkit taToolkit, Path filePath) throws IOException {
        requireNonNull(taToolkit);
        requireNonNull(filePath);

        FileUtil.createIfMissing(filePath);
        JsonUtil.saveJsonFile(new JsonSerializableTaToolkit(taToolkit), filePath);
    }

}
