package seedu.tatoolkit.ui;

import java.util.logging.Logger;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.Region;
import seedu.tatoolkit.commons.core.LogsCenter;
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

    /**
     * Creates a {@code SidePanel}.
     */
    public SidePanel() {
        super(FXML);
        showDefaultMessage();
    }

    /**
     * Shows the default message and hides person details.
     */
    private void showDefaultMessage() {
        defaultMessageLabel.setVisible(true);
        nameLabel.setVisible(false);
        classGroupLabel.setVisible(false);
        emailLabel.setVisible(false);
        phoneLabel.setVisible(false);
        telegramLabel.setVisible(false);
        githubLabel.setVisible(false);
        attendanceLabel.setVisible(false);
        noteLabel.setVisible(false);
    }

    /**
     * Displays the details of a {@code Person}.
     *
     * @param person The person whose details are to be displayed.
     */
    public void displayPerson(Person person) {
        if (person != null) {
            nameLabel.setVisible(true);
            nameLabel.setText("Name: " + person.getName().fullName);
            classGroupLabel.setVisible(true);
            classGroupLabel.setText("Class Group: " + person.getClassGroup().toString());
            emailLabel.setVisible(true);
            emailLabel.setText("Email: " + person.getEmail().toString());
            phoneLabel.setVisible(true);
            phoneLabel.setText("Phone: "
                    + person.getPhone().map(Phone::toString).orElse("N/A"));
            telegramLabel.setVisible(true);
            telegramLabel.setText("Telegram: "
                    + person.getTelegram().map(Telegram::toString).orElse("N/A"));
            githubLabel.setVisible(true);
            githubLabel.setText("GitHub: "
                    + person.getGithub().map(Github::toString).orElse("N/A"));
            attendanceLabel.setVisible(true);
            attendanceLabel.setText(person.getAttendance().toString());
            noteLabel.setVisible(true);
            noteLabel.setText("Notes: " + person.getNotes().toString());

            defaultMessageLabel.setVisible(false);
        } else {
            resetDetails();
        }
    }

    /**
     * Clears all the details from the side panel.
     */
    public void resetDetails() {
        nameLabel.setText("");
        classGroupLabel.setText("");
        emailLabel.setText("");
        phoneLabel.setText("");
        telegramLabel.setText("");
        githubLabel.setText("");
        attendanceLabel.setText("");
        noteLabel.setText("");

        nameLabel.setVisible(false);
        classGroupLabel.setVisible(false);
        emailLabel.setVisible(false);
        phoneLabel.setVisible(false);
        telegramLabel.setVisible(false);
        githubLabel.setVisible(false);
        noteLabel.setVisible(false);

        showDefaultMessage();
    }
}
