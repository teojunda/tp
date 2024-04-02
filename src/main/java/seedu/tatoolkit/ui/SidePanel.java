package seedu.tatoolkit.ui;

import java.util.logging.Logger;

import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.Region;
import seedu.tatoolkit.commons.core.LogsCenter;
import seedu.tatoolkit.model.Model;
import seedu.tatoolkit.model.person.Github;
import seedu.tatoolkit.model.person.Person;
import seedu.tatoolkit.model.person.Phone;
import seedu.tatoolkit.model.person.Telegram;


/**
 * Panel displaying the details of a selected person.
 */
public class SidePanel extends UiPart<Region> {
    private static final String FXML = "SidePanel.fxml";
    private final Logger logger = LogsCenter.getLogger(SidePanel.class);
    private Model model;

    @FXML
    private Label defaultMessageLabel;
    @FXML
    private Label nameLabel;
    @FXML
    private Label classGroupLabel;
    @FXML
    private Label emailLabel;
    @FXML
    private Label phoneLabel;
    @FXML
    private Label telegramLabel;
    @FXML
    private Label githubLabel;
    @FXML
    private Label attendanceLabel;
    @FXML
    private Label noteLabel;
    @FXML
    private ListView<String> attendanceListView;
    @FXML
    private Label attendanceHeader;

    /**
     * Creates a SidePanel using the provided model.
     * It initializes the attendance list view and displays the default message.
     *
     * @param model The model from which this panel will retrieve data.
     */
    public SidePanel(Model model) {
        super(FXML);
        this.model = model;
        initializeAttendanceListView();
        showDefaultMessage();
    }

    private void initializeAttendanceListView() {
        ObservableList<String> attendanceList = model.getObservableAttendanceList();

        attendanceList.addListener((ListChangeListener.Change<? extends String> change) -> {
            while (change.next()) {
                if (change.wasUpdated() || change.wasAdded() || change.wasRemoved()) {
                    displayAttendanceList(attendanceList);
                }
            }
        });
    }

    private void showDefaultMessage() {
        defaultMessageLabel.setVisible(true);
        setDetailVisibility(false);
        setAttendanceListVisibility(false);
    }

    /**
     * Displays the details of the given person on the side panel.
     * If the provided person is null, the details are reset to default.
     *
     * @param person The person whose details are to be displayed, or null to reset.
     */
    public void displayPerson(Person person) {
        if (person != null) {
            fillPersonDetails(person);
            setDetailVisibility(true);
            setAttendanceListVisibility(false);
            attendanceHeader.setVisible(false);
            attendanceHeader.setManaged(false);
            defaultMessageLabel.setVisible(false);
        } else {
            resetDetails();
        }
        attendanceListView.setVisible(false);
        attendanceListView.setManaged(false);
    }

    /**
     * Resets the details displayed on the side panel to default values.
     * This typically involves clearing any person details and showing the default message.
     */
    public void resetDetails() {
        clearPersonDetails();
        attendanceHeader.setVisible(false);
        showDefaultMessage();
    }

    /**
     * Displays an attendance list on the side panel.
     * This method sets the attendance header and list view to visible and populates the list view with attendance data.
     * It hides personal details during the display of the attendance list.
     *
     * @param attendanceList An ObservableList of strings representing attendance information.
     */
    public void displayAttendanceList(ObservableList<String> attendanceList) {
        attendanceHeader.setVisible(true);
        attendanceHeader.setManaged(true);
        attendanceListView.setItems(attendanceList);
        setDetailVisibility(false);
        setAttendanceListVisibility(true);
        defaultMessageLabel.setVisible(false);
        attendanceListView.setMinHeight(500);
    }

    private void setDetailVisibility(boolean isVisible) {
        nameLabel.setVisible(isVisible);
        classGroupLabel.setVisible(isVisible);
        emailLabel.setVisible(isVisible);
        phoneLabel.setVisible(isVisible);
        telegramLabel.setVisible(isVisible);
        githubLabel.setVisible(isVisible);
        attendanceLabel.setVisible(isVisible);
        noteLabel.setVisible(isVisible);
        defaultMessageLabel.setVisible(!isVisible);
        setAttendanceListVisibility(false);
    }

    private void setAttendanceListVisibility(boolean isVisible) {
        attendanceListView.setVisible(isVisible);
        attendanceListView.setManaged(isVisible);
    }

    private void fillPersonDetails(Person person) {
        nameLabel.setText(person.getName().fullName);
        classGroupLabel.setText("Class Group: " + person.getClassGroup().toString());
        emailLabel.setText("Email: " + person.getEmail().toString());
        phoneLabel.setText("Phone: " + person.getPhone().map(Phone::toString).orElse("N/A"));
        telegramLabel.setText("Telegram: " + person.getTelegram().map(Telegram::toString).orElse("N/A"));
        githubLabel.setText("GitHub: " + person.getGithub().map(Github::toString).orElse("N/A"));
        attendanceLabel.setText(person.getAttendance().toString());
        noteLabel.setText("Notes: " + person.getNotes().toString());
    }

    private void clearPersonDetails() {
        nameLabel.setText("");
        classGroupLabel.setText("");
        emailLabel.setText("");
        phoneLabel.setText("");
        telegramLabel.setText("");
        githubLabel.setText("");
        attendanceLabel.setText("");
        noteLabel.setText("");
    }

    /**
     * Updates the display of the attendance list in the side panel.
     * This method retrieves the filtered attendance list from the model and displays it.
     */
    public void updateAttendanceDisplay() {
        displayAttendanceList(model.getFilteredPersonAttendanceList());
    }
}
