package seedu.tatoolkit.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static seedu.tatoolkit.testutil.Assert.assertThrows;
import static seedu.tatoolkit.testutil.TypicalPersons.ALICE;
import static seedu.tatoolkit.testutil.TypicalPersons.HOON;
import static seedu.tatoolkit.testutil.TypicalPersons.IDA;
import static seedu.tatoolkit.testutil.TypicalPersons.getTypicalTaToolkit;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import seedu.tatoolkit.commons.exceptions.DataLoadingException;
import seedu.tatoolkit.model.ReadOnlyTaToolkit;
import seedu.tatoolkit.model.TaToolkit;

public class JsonTaToolkitStorageTest {
    private static final Path TEST_DATA_FOLDER = Paths.get("src", "test", "data", "JsonTaToolkitStorageTest");

    @TempDir
    public Path testFolder;

    @Test
    public void readTaToolkit_nullFilePath_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> readTaToolkit(null));
    }

    private java.util.Optional<ReadOnlyTaToolkit> readTaToolkit(String filePath) throws Exception {
        return new JsonTaToolkitStorage(Paths.get(filePath)).readTaToolkit(addToTestDataPathIfNotNull(filePath));
    }

    private Path addToTestDataPathIfNotNull(String prefsFileInTestDataFolder) {
        return prefsFileInTestDataFolder != null
                ? TEST_DATA_FOLDER.resolve(prefsFileInTestDataFolder)
                : null;
    }

    @Test
    public void read_missingFile_emptyResult() throws Exception {
        assertFalse(readTaToolkit("NonExistentFile.json").isPresent());
    }

    @Test
    public void read_notJsonFormat_exceptionThrown() {
        assertThrows(DataLoadingException.class, () -> readTaToolkit("notJsonFormatTaToolkit.json"));
    }

    @Test
    public void readTaToolkit_invalidPersonTaToolkit_throwDataLoadingException() {
        assertThrows(DataLoadingException.class, () -> readTaToolkit("invalidPersonTaToolkit.json"));
    }

    @Test
    public void readTaToolkit_invalidAndValidPersonTaToolkit_throwDataLoadingException() {
        assertThrows(DataLoadingException.class, () -> readTaToolkit("invalidAndValidPersonTaToolkit.json"));
    }

    @Test
    public void readAndSaveTaToolkit_allInOrder_success() throws Exception {
        Path filePath = testFolder.resolve("TempTaToolkit.json");
        TaToolkit original = getTypicalTaToolkit();
        JsonTaToolkitStorage jsonTaToolkitStorage = new JsonTaToolkitStorage(filePath);

        // Save in new file and read back
        jsonTaToolkitStorage.saveTaToolkit(original, filePath);
        ReadOnlyTaToolkit readBack = jsonTaToolkitStorage.readTaToolkit(filePath).get();
        assertEquals(original, new TaToolkit(readBack));

        // Modify data, overwrite exiting file, and read back
        original.addPerson(HOON);
        original.removePerson(ALICE);
        jsonTaToolkitStorage.saveTaToolkit(original, filePath);
        readBack = jsonTaToolkitStorage.readTaToolkit(filePath).get();
        assertEquals(original, new TaToolkit(readBack));

        // Save and read without specifying file path
        original.addPerson(IDA);
        jsonTaToolkitStorage.saveTaToolkit(original); // file path not specified
        readBack = jsonTaToolkitStorage.readTaToolkit().get(); // file path not specified
        assertEquals(original, new TaToolkit(readBack));

    }

    @Test
    public void saveTaToolkit_nullTaToolkit_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> saveTaToolkit(null, "SomeFile.json"));
    }

    /**
     * Saves {@code taToolkit} at the specified {@code filePath}.
     */
    private void saveTaToolkit(ReadOnlyTaToolkit taToolkit, String filePath) {
        try {
            new JsonTaToolkitStorage(Paths.get(filePath))
                    .saveTaToolkit(taToolkit, addToTestDataPathIfNotNull(filePath));
        } catch (IOException ioe) {
            throw new AssertionError("There should not be an error writing to the file.", ioe);
        }
    }

    @Test
    public void saveTaToolkit_nullFilePath_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> saveTaToolkit(new TaToolkit(), null));
    }
}
