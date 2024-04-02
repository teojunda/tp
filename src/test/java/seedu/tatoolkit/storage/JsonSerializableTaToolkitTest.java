package seedu.tatoolkit.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.tatoolkit.testutil.Assert.assertThrows;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.jupiter.api.Test;

import seedu.tatoolkit.commons.exceptions.IllegalValueException;
import seedu.tatoolkit.commons.util.JsonUtil;
import seedu.tatoolkit.model.TaToolkit;
import seedu.tatoolkit.testutil.TypicalPersons;

public class JsonSerializableTaToolkitTest {

    private static final Path TEST_DATA_FOLDER = Paths.get("src", "test", "data", "JsonSerializableTaToolkitTest");
    private static final Path TYPICAL_PERSONS_FILE = TEST_DATA_FOLDER.resolve("typicalPersonsTaToolkit.json");
    private static final Path INVALID_PERSON_FILE = TEST_DATA_FOLDER.resolve("invalidPersonTaToolkit.json");
    private static final Path DUPLICATE_PERSON_FILE = TEST_DATA_FOLDER.resolve("duplicatePersonTaToolkit.json");

    @Test
    public void toModelType_typicalPersonsFile_success() throws Exception {
        JsonSerializableTaToolkit dataFromFile = JsonUtil.readJsonFile(TYPICAL_PERSONS_FILE,
                JsonSerializableTaToolkit.class).get();
        TaToolkit taToolkitFromFile = dataFromFile.toModelType();
        TaToolkit typicalPersonsTaToolkit = TypicalPersons.getTypicalTaToolkit();
        assertEquals(taToolkitFromFile, typicalPersonsTaToolkit);
    }

    @Test
    public void toModelType_invalidPersonFile_throwsIllegalValueException() throws Exception {
        JsonSerializableTaToolkit dataFromFile = JsonUtil.readJsonFile(INVALID_PERSON_FILE,
                JsonSerializableTaToolkit.class).get();
        assertThrows(IllegalValueException.class, dataFromFile::toModelType);
    }

    @Test
    public void toModelType_duplicatePersons_throwsIllegalValueException() throws Exception {
        JsonSerializableTaToolkit dataFromFile = JsonUtil.readJsonFile(DUPLICATE_PERSON_FILE,
                JsonSerializableTaToolkit.class).get();
        assertThrows(IllegalValueException.class, JsonSerializableTaToolkit.MESSAGE_DUPLICATE_PERSON,
                dataFromFile::toModelType);
    }

}
