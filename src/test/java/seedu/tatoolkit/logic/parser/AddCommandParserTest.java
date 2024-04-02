package seedu.tatoolkit.logic.parser;

import static seedu.tatoolkit.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.tatoolkit.logic.commands.CommandTestUtil.CLASS_GROUP_DESC_AMY;
import static seedu.tatoolkit.logic.commands.CommandTestUtil.CLASS_GROUP_DESC_BOB;
import static seedu.tatoolkit.logic.commands.CommandTestUtil.EMAIL_DESC_AMY;
import static seedu.tatoolkit.logic.commands.CommandTestUtil.EMAIL_DESC_BOB;
import static seedu.tatoolkit.logic.commands.CommandTestUtil.GITHUB_DESC_AMY;
import static seedu.tatoolkit.logic.commands.CommandTestUtil.GITHUB_DESC_BOB;
import static seedu.tatoolkit.logic.commands.CommandTestUtil.INVALID_CLASS_GROUP_DESC;
import static seedu.tatoolkit.logic.commands.CommandTestUtil.INVALID_EMAIL_DESC;
import static seedu.tatoolkit.logic.commands.CommandTestUtil.INVALID_NAME_DESC;
import static seedu.tatoolkit.logic.commands.CommandTestUtil.INVALID_PHONE_DESC;
import static seedu.tatoolkit.logic.commands.CommandTestUtil.INVALID_TELEGRAM_DESC;
import static seedu.tatoolkit.logic.commands.CommandTestUtil.NAME_DESC_AMY;
import static seedu.tatoolkit.logic.commands.CommandTestUtil.NAME_DESC_BOB;
import static seedu.tatoolkit.logic.commands.CommandTestUtil.PHONE_DESC_AMY;
import static seedu.tatoolkit.logic.commands.CommandTestUtil.PHONE_DESC_BOB;
import static seedu.tatoolkit.logic.commands.CommandTestUtil.PREAMBLE_NON_EMPTY;
import static seedu.tatoolkit.logic.commands.CommandTestUtil.PREAMBLE_WHITESPACE;
import static seedu.tatoolkit.logic.commands.CommandTestUtil.TELEGRAM_DESC_AMY;
import static seedu.tatoolkit.logic.commands.CommandTestUtil.TELEGRAM_DESC_BOB;
import static seedu.tatoolkit.logic.commands.CommandTestUtil.VALID_EMAIL_BOB;
import static seedu.tatoolkit.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.tatoolkit.logic.commands.CommandTestUtil.VALID_PHONE_BOB;
import static seedu.tatoolkit.logic.parser.CliSyntax.PREFIX_CLASS_GROUP;
import static seedu.tatoolkit.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.tatoolkit.logic.parser.CliSyntax.PREFIX_GITHUB;
import static seedu.tatoolkit.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.tatoolkit.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.tatoolkit.logic.parser.CliSyntax.PREFIX_TELEGRAM;
import static seedu.tatoolkit.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.tatoolkit.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.tatoolkit.testutil.TypicalPersons.AMY;
import static seedu.tatoolkit.testutil.TypicalPersons.BOB;

import org.junit.jupiter.api.Test;

import seedu.tatoolkit.logic.Messages;
import seedu.tatoolkit.logic.commands.AddCommand;
import seedu.tatoolkit.model.person.ClassGroup;
import seedu.tatoolkit.model.person.Email;
import seedu.tatoolkit.model.person.Name;
import seedu.tatoolkit.model.person.Person;
import seedu.tatoolkit.model.person.Phone;
import seedu.tatoolkit.model.person.Telegram;
import seedu.tatoolkit.testutil.PersonBuilder;

public class AddCommandParserTest {
    private AddCommandParser parser = new AddCommandParser();

    @Test
    public void parse_allFieldsPresent_success() {
        Person expectedPerson = new PersonBuilder(BOB).build();

        // whitespace only preamble
        assertParseSuccess(parser, PREAMBLE_WHITESPACE + NAME_DESC_BOB + CLASS_GROUP_DESC_BOB
                + EMAIL_DESC_BOB + PHONE_DESC_BOB + TELEGRAM_DESC_BOB + GITHUB_DESC_BOB,
                new AddCommand(expectedPerson));
    }

    @Test
    public void parse_repeatedValue_failure() {
        String validExpectedPersonString = NAME_DESC_BOB + CLASS_GROUP_DESC_BOB + EMAIL_DESC_BOB
                + PHONE_DESC_BOB + TELEGRAM_DESC_BOB + GITHUB_DESC_BOB;

        // multiple names
        assertParseFailure(parser, NAME_DESC_AMY + validExpectedPersonString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_NAME));

        // multiple phones
        assertParseFailure(parser, PHONE_DESC_AMY + validExpectedPersonString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_PHONE));

        // multiple emails
        assertParseFailure(parser, EMAIL_DESC_AMY + validExpectedPersonString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_EMAIL));

        // multiple fields repeated
        assertParseFailure(parser,
                validExpectedPersonString + PHONE_DESC_AMY + EMAIL_DESC_AMY + NAME_DESC_AMY
                        + validExpectedPersonString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_NAME, PREFIX_CLASS_GROUP,
                        PREFIX_EMAIL, PREFIX_PHONE, PREFIX_GITHUB, PREFIX_TELEGRAM));

        // invalid value followed by valid value

        // invalid name
        assertParseFailure(parser, INVALID_NAME_DESC + validExpectedPersonString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_NAME));

        // invalid email
        assertParseFailure(parser, INVALID_EMAIL_DESC + validExpectedPersonString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_EMAIL));

        // invalid phone
        assertParseFailure(parser, INVALID_PHONE_DESC + validExpectedPersonString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_PHONE));

        // valid value followed by invalid value

        // invalid name
        assertParseFailure(parser, validExpectedPersonString + INVALID_NAME_DESC,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_NAME));

        // invalid email
        assertParseFailure(parser, validExpectedPersonString + INVALID_EMAIL_DESC,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_EMAIL));

        // invalid phone
        assertParseFailure(parser, validExpectedPersonString + INVALID_PHONE_DESC,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_PHONE));
    }

    @Test
    public void parse_optionalFieldsMissing_success() {
        Person expectedPerson = new PersonBuilder(AMY).build();
        assertParseSuccess(parser, NAME_DESC_AMY + CLASS_GROUP_DESC_AMY + EMAIL_DESC_AMY + PHONE_DESC_AMY
                + TELEGRAM_DESC_AMY + GITHUB_DESC_AMY, new AddCommand(expectedPerson));
    }

    @Test
    public void parse_compulsoryFieldMissing_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE);

        // missing name prefix
        assertParseFailure(parser, VALID_NAME_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB,
                expectedMessage);

        // missing phone prefix
        assertParseFailure(parser, NAME_DESC_BOB + VALID_PHONE_BOB + EMAIL_DESC_BOB,
                expectedMessage);

        // missing email prefix
        assertParseFailure(parser, NAME_DESC_BOB + PHONE_DESC_BOB + VALID_EMAIL_BOB,
                expectedMessage);

        // all prefixes missing
        assertParseFailure(parser, VALID_NAME_BOB + VALID_PHONE_BOB + VALID_EMAIL_BOB,
                expectedMessage);
    }

    @Test
    public void parse_invalidValue_failure() {
        // invalid name
        assertParseFailure(parser, INVALID_NAME_DESC + CLASS_GROUP_DESC_BOB
                + EMAIL_DESC_BOB + PHONE_DESC_BOB, Name.MESSAGE_CONSTRAINTS);

        // invalid phone
        assertParseFailure(parser, NAME_DESC_BOB + CLASS_GROUP_DESC_BOB
                + EMAIL_DESC_BOB + INVALID_PHONE_DESC, Phone.MESSAGE_CONSTRAINTS);

        // invalid email
        assertParseFailure(parser, NAME_DESC_BOB + CLASS_GROUP_DESC_BOB
                + INVALID_EMAIL_DESC + PHONE_DESC_BOB, Email.MESSAGE_CONSTRAINTS);

        // invalid class group
        assertParseFailure(parser, NAME_DESC_BOB + INVALID_CLASS_GROUP_DESC
                + INVALID_EMAIL_DESC + PHONE_DESC_BOB, ClassGroup.MESSAGE_CONSTRAINTS);

        // invalid telegram
        assertParseFailure(parser, NAME_DESC_BOB + CLASS_GROUP_DESC_BOB
                + EMAIL_DESC_BOB + PHONE_DESC_BOB + INVALID_TELEGRAM_DESC, Telegram.MESSAGE_CONSTRAINTS);


        // two invalid values, only first invalid value reported
        assertParseFailure(parser, INVALID_NAME_DESC + INVALID_CLASS_GROUP_DESC
                        + EMAIL_DESC_BOB + PHONE_DESC_BOB, Name.MESSAGE_CONSTRAINTS);

        // non-empty preamble
        assertParseFailure(parser, PREAMBLE_NON_EMPTY + NAME_DESC_BOB + CLASS_GROUP_DESC_BOB
                        + EMAIL_DESC_BOB + PHONE_DESC_BOB,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));
    }
}
