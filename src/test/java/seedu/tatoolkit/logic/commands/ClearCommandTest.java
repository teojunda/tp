package seedu.tatoolkit.logic.commands;

import static seedu.tatoolkit.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.tatoolkit.testutil.TypicalPersons.getTypicalTaToolkit;

import org.junit.jupiter.api.Test;

import seedu.tatoolkit.model.Model;
import seedu.tatoolkit.model.ModelManager;
import seedu.tatoolkit.model.TaToolkit;
import seedu.tatoolkit.model.UserPrefs;

public class ClearCommandTest {

    @Test
    public void execute_emptyTaToolkit_success() {
        Model model = new ModelManager();
        Model expectedModel = new ModelManager();

        assertCommandSuccess(new ClearCommand(), model, ClearCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void execute_nonEmptyTaToolkit_success() {
        Model model = new ModelManager(getTypicalTaToolkit(), new UserPrefs());
        Model expectedModel = new ModelManager(getTypicalTaToolkit(), new UserPrefs());
        expectedModel.setTaToolkit(new TaToolkit());

        assertCommandSuccess(new ClearCommand(), model, ClearCommand.MESSAGE_SUCCESS, expectedModel);
    }

}
