package seedu.tatoolkit.logic;

import java.io.IOException;
import java.nio.file.AccessDeniedException;
import java.nio.file.Path;
import java.util.Optional;
import java.util.logging.Logger;

import javafx.collections.ObservableList;
import seedu.tatoolkit.commons.core.GuiSettings;
import seedu.tatoolkit.commons.core.LogsCenter;
import seedu.tatoolkit.logic.commands.Command;
import seedu.tatoolkit.logic.commands.CommandResult;
import seedu.tatoolkit.logic.commands.ListCommand;
import seedu.tatoolkit.logic.commands.MarkCommand;
import seedu.tatoolkit.logic.commands.ViewCommand;
import seedu.tatoolkit.logic.commands.exceptions.CommandException;
import seedu.tatoolkit.logic.parser.TaToolkitParser;
import seedu.tatoolkit.logic.parser.exceptions.ParseException;
import seedu.tatoolkit.model.Model;
import seedu.tatoolkit.model.ReadOnlyTaToolkit;
import seedu.tatoolkit.model.person.Person;
import seedu.tatoolkit.storage.Storage;

/**
 * The main LogicManager of the app.
 */
public class LogicManager implements Logic {
    public static final String FILE_OPS_ERROR_FORMAT = "Could not save data due to the following error: %s";

    public static final String FILE_OPS_PERMISSION_ERROR_FORMAT =
            "Could not save data to file %s due to insufficient permissions to write to the file or the folder.";

    private final Logger logger = LogsCenter.getLogger(LogicManager.class);

    private final Model model;
    private final Storage storage;
    private final TaToolkitParser taToolkitParser;
    private Optional<Command> lastSidePanelCommand;

    /**
     * Constructs a {@code LogicManager} with the given {@code Model} and {@code Storage}.
     */
    public LogicManager(Model model, Storage storage) {
        this.model = model;
        this.storage = storage;
        taToolkitParser = new TaToolkitParser();
        lastSidePanelCommand = Optional.empty();
    }

    @Override
    public CommandResult execute(String commandText) throws CommandException, ParseException {
        logger.info("----------------[USER COMMAND][" + commandText + "]");

        CommandResult commandResult;
        Command command = taToolkitParser.parseCommand(commandText);
        commandResult = command.execute(model);

        try {
            storage.saveTaToolkit(model.getTaToolkit());
        } catch (AccessDeniedException e) {
            throw new CommandException(String.format(FILE_OPS_PERMISSION_ERROR_FORMAT, e.getMessage()), e);
        } catch (IOException ioe) {
            throw new CommandException(String.format(FILE_OPS_ERROR_FORMAT, ioe.getMessage()), ioe);
        }

        if (command instanceof ViewCommand || command instanceof ListCommand) {
            lastSidePanelCommand = Optional.of(command);
        }

        if (command instanceof MarkCommand) {
            model.updateObservableAttendanceList();
        }
        return commandResult;
    }

    @Override
    public ReadOnlyTaToolkit getTaToolkit() {
        return model.getTaToolkit();
    }

    @Override
    public ObservableList<Person> getFilteredPersonList() {
        return model.getFilteredPersonList();
    }

    @Override
    public Optional<Person> getLastViewedPerson() {
        return model.getLastViewedPerson();
    }

    public Optional<Command> getlastSidePanelCommand() {
        return lastSidePanelCommand;
    }
    @Override
    public ObservableList<String> getFilteredPersonAttendanceList() {
        return model.getFilteredPersonAttendanceList();
    }

    @Override
    public Model getModel() {
        return this.model;
    }

    @Override
    public Path getTaToolkitFilePath() {
        return model.getTaToolkitFilePath();
    }

    @Override
    public GuiSettings getGuiSettings() {
        return model.getGuiSettings();
    }

    @Override
    public void setGuiSettings(GuiSettings guiSettings) {
        model.setGuiSettings(guiSettings);
    }
}
