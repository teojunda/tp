package seedu.tatoolkit;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;
import java.util.logging.Logger;

import javafx.application.Application;
import javafx.stage.Stage;
import seedu.tatoolkit.commons.core.Config;
import seedu.tatoolkit.commons.core.LogsCenter;
import seedu.tatoolkit.commons.core.Version;
import seedu.tatoolkit.commons.exceptions.DataLoadingException;
import seedu.tatoolkit.commons.util.ConfigUtil;
import seedu.tatoolkit.commons.util.StringUtil;
import seedu.tatoolkit.logic.Logic;
import seedu.tatoolkit.logic.LogicManager;
import seedu.tatoolkit.model.Model;
import seedu.tatoolkit.model.ModelManager;
import seedu.tatoolkit.model.ReadOnlyTaToolkit;
import seedu.tatoolkit.model.ReadOnlyUserPrefs;
import seedu.tatoolkit.model.TaToolkit;
import seedu.tatoolkit.model.UserPrefs;
import seedu.tatoolkit.model.util.SampleDataUtil;
import seedu.tatoolkit.storage.JsonTaToolkitStorage;
import seedu.tatoolkit.storage.JsonUserPrefsStorage;
import seedu.tatoolkit.storage.Storage;
import seedu.tatoolkit.storage.StorageManager;
import seedu.tatoolkit.storage.TaToolkitStorage;
import seedu.tatoolkit.storage.UserPrefsStorage;
import seedu.tatoolkit.ui.Ui;
import seedu.tatoolkit.ui.UiManager;

/**
 * Runs the application.
 */
public class MainApp extends Application {

    public static final Version VERSION = new Version(1, 3, 0, true);

    private static final Logger logger = LogsCenter.getLogger(MainApp.class);

    protected Ui ui;
    protected Logic logic;
    protected Storage storage;
    protected Model model;
    protected Config config;

    @Override
    public void init() throws Exception {
        logger.info("=============================[ Initializing TaToolkit ]===========================");
        super.init();

        AppParameters appParameters = AppParameters.parse(getParameters());
        config = initConfig(appParameters.getConfigPath());
        initLogging(config);

        UserPrefsStorage userPrefsStorage = new JsonUserPrefsStorage(config.getUserPrefsFilePath());
        UserPrefs userPrefs = initPrefs(userPrefsStorage);
        TaToolkitStorage taToolkitStorage = new JsonTaToolkitStorage(userPrefs.getTaToolkitFilePath());
        storage = new StorageManager(taToolkitStorage, userPrefsStorage);

        model = initModelManager(storage, userPrefs);

        logic = new LogicManager(model, storage);

        ui = new UiManager(logic);
    }

    /**
     * Returns a {@code ModelManager} with the data from {@code storage}'s TA Toolkit and {@code userPrefs}. <br>
     * The data from the sample TA Toolkit will be used instead if {@code storage}'s TA Toolkit is not found,
     * or an empty TA Toolkit will be used instead if errors occur when reading {@code storage}'s TA Toolkit.
     */
    private Model initModelManager(Storage storage, ReadOnlyUserPrefs userPrefs) {
        logger.info("Using data file : " + storage.getTaToolkitFilePath());

        Optional<ReadOnlyTaToolkit> taToolkitOptional;
        ReadOnlyTaToolkit initialData;
        try {
            taToolkitOptional = storage.readTaToolkit();
            if (!taToolkitOptional.isPresent()) {
                logger.info("Creating a new data file " + storage.getTaToolkitFilePath()
                        + " populated with a sample taToolkit.");
            }
            initialData = taToolkitOptional.orElseGet(SampleDataUtil::getSampleTaToolkit);
        } catch (DataLoadingException e) {
            logger.warning("Data file at " + storage.getTaToolkitFilePath() + " could not be loaded."
                    + " Will be starting with an empty taToolkit.");
            initialData = new TaToolkit();
        }

        return new ModelManager(initialData, userPrefs);
    }

    private void initLogging(Config config) {
        LogsCenter.init(config);
    }

    /**
     * Returns a {@code Config} using the file at {@code configFilePath}. <br>
     * The default file path {@code Config#DEFAULT_CONFIG_FILE} will be used instead
     * if {@code configFilePath} is null.
     */
    protected Config initConfig(Path configFilePath) {
        Config initializedConfig;
        Path configFilePathUsed;

        configFilePathUsed = Config.DEFAULT_CONFIG_FILE;

        if (configFilePath != null) {
            logger.info("Custom Config file specified " + configFilePath);
            configFilePathUsed = configFilePath;
        }

        logger.info("Using config file : " + configFilePathUsed);

        try {
            Optional<Config> configOptional = ConfigUtil.readConfig(configFilePathUsed);
            if (!configOptional.isPresent()) {
                logger.info("Creating new config file " + configFilePathUsed);
            }
            initializedConfig = configOptional.orElse(new Config());
        } catch (DataLoadingException e) {
            logger.warning("Config file at " + configFilePathUsed + " could not be loaded."
                    + " Using default config properties.");
            initializedConfig = new Config();
        }

        //Update config file in case it was missing to begin with or there are new/unused fields
        try {
            ConfigUtil.saveConfig(initializedConfig, configFilePathUsed);
        } catch (IOException e) {
            logger.warning("Failed to save config file : " + StringUtil.getDetails(e));
        }
        return initializedConfig;
    }

    /**
     * Returns a {@code UserPrefs} using the file at {@code storage}'s user prefs file path,
     * or a new {@code UserPrefs} with default configuration if errors occur when
     * reading from the file.
     */
    protected UserPrefs initPrefs(UserPrefsStorage storage) {
        Path prefsFilePath = storage.getUserPrefsFilePath();
        logger.info("Using preference file : " + prefsFilePath);

        UserPrefs initializedPrefs;
        try {
            Optional<UserPrefs> prefsOptional = storage.readUserPrefs();
            if (!prefsOptional.isPresent()) {
                logger.info("Creating new preference file " + prefsFilePath);
            }
            initializedPrefs = prefsOptional.orElse(new UserPrefs());
        } catch (DataLoadingException e) {
            logger.warning("Preference file at " + prefsFilePath + " could not be loaded."
                    + " Using default preferences.");
            initializedPrefs = new UserPrefs();
        }

        //Update prefs file in case it was missing to begin with or there are new/unused fields
        try {
            storage.saveUserPrefs(initializedPrefs);
        } catch (IOException e) {
            logger.warning("Failed to save config file : " + StringUtil.getDetails(e));
        }

        return initializedPrefs;
    }

    @Override
    public void start(Stage primaryStage) {
        logger.info("Starting taToolkit " + MainApp.VERSION);
        ui.start(primaryStage);
    }

    @Override
    public void stop() {
        logger.info("============================ [ Stopping TA Toolkit ] =============================");
        try {
            storage.saveUserPrefs(model.getUserPrefs());
        } catch (IOException e) {
            logger.severe("Failed to save preferences " + StringUtil.getDetails(e));
        }
    }
}
