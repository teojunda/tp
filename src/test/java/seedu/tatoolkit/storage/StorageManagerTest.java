package seedu.tatoolkit.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static seedu.tatoolkit.testutil.TypicalPersons.getTypicalTaToolkit;

import java.nio.file.Path;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import seedu.tatoolkit.commons.core.GuiSettings;
import seedu.tatoolkit.model.ReadOnlyTaToolkit;
import seedu.tatoolkit.model.TaToolkit;
import seedu.tatoolkit.model.UserPrefs;

public class StorageManagerTest {

    @TempDir
    public Path testFolder;

    private StorageManager storageManager;

    @BeforeEach
    public void setUp() {
        JsonTaToolkitStorage taToolkitStorage = new JsonTaToolkitStorage(getTempFilePath("ab"));
        JsonUserPrefsStorage userPrefsStorage = new JsonUserPrefsStorage(getTempFilePath("prefs"));
        storageManager = new StorageManager(taToolkitStorage, userPrefsStorage);
    }

    private Path getTempFilePath(String fileName) {
        return testFolder.resolve(fileName);
    }

    @Test
    public void prefsReadSave() throws Exception {
        /*
         * Note: This is an integration test that verifies the StorageManager is properly wired to the
         * {@link JsonUserPrefsStorage} class.
         * More extensive testing of UserPref saving/reading is done in {@link JsonUserPrefsStorageTest} class.
         */
        UserPrefs original = new UserPrefs();
        original.setGuiSettings(new GuiSettings(300, 600, 4, 6));
        storageManager.saveUserPrefs(original);
        UserPrefs retrieved = storageManager.readUserPrefs().get();
        assertEquals(original, retrieved);
    }

    @Test
    public void taToolkitReadSave() throws Exception {
        /*
         * Note: This is an integration test that verifies the StorageManager is properly wired to the
         * {@link JsonTaToolkitStorage} class.
         * More extensive testing of UserPref saving/reading is done in {@link JsonTaToolkitStorageTest} class.
         */
        TaToolkit original = getTypicalTaToolkit();
        storageManager.saveTaToolkit(original);
        ReadOnlyTaToolkit retrieved = storageManager.readTaToolkit().get();
        assertEquals(original, new TaToolkit(retrieved));
    }

    @Test
    public void getTaToolkitFilePath() {
        assertNotNull(storageManager.getTaToolkitFilePath());
    }

}
