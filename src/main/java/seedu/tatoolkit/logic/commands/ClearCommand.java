package seedu.tatoolkit.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.tatoolkit.model.Model;
import seedu.tatoolkit.model.TaToolkit;

/**
 * Clears the TA Toolkit.
 */
public class ClearCommand extends Command {

    public static final String COMMAND_WORD = "clear";
    public static final String MESSAGE_SUCCESS = "TA Toolkit has been cleared!";


    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.setTaToolkit(new TaToolkit());
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
