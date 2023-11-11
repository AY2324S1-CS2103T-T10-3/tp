package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.PREAMBLE_NON_EMPTY;
import static seedu.address.logic.commands.CommandTestUtil.PREAMBLE_WHITESPACE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_GROUPTAG;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.UngroupPersonCommand;

public class UngroupPersonCommandParserTest {
    private static final String PERSON_NAME_BOB = "Bob Lee";
    private static final String GROUP_NAME_CS2100 = "CS2100";

    private static final String PERSON_NAME_DESC_BOB = " " + PREFIX_NAME + PERSON_NAME_BOB;
    private static final String GROUP_NAME_DESC_CS2100 = " " + PREFIX_GROUPTAG + GROUP_NAME_CS2100;

    private UngroupPersonCommandParser parser = new UngroupPersonCommandParser();

    @Test
    public void parse_allFieldsPresent_success() {
        assertParseSuccess(parser, PERSON_NAME_DESC_BOB + GROUP_NAME_DESC_CS2100,
                new UngroupPersonCommand(PERSON_NAME_BOB, GROUP_NAME_CS2100));

        // empty preamble
        assertParseSuccess(parser, PREAMBLE_WHITESPACE + PERSON_NAME_DESC_BOB + GROUP_NAME_DESC_CS2100,
                new UngroupPersonCommand(PERSON_NAME_BOB, GROUP_NAME_CS2100));
    }

    @Test
    public void parse_personNameMissing_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, UngroupPersonCommand.MESSAGE_USAGE);

        // missing person name prefix
        assertParseFailure(parser, PERSON_NAME_BOB + GROUP_NAME_DESC_CS2100, expectedMessage);

        // missing group name prefix
        assertParseFailure(parser, PERSON_NAME_DESC_BOB + GROUP_NAME_CS2100, expectedMessage);

        // missing both prefixes
        assertParseFailure(parser, PERSON_NAME_BOB + GROUP_NAME_CS2100, expectedMessage);
    }

    @Test
    public void parse_nonEmptyPreamble_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, UngroupPersonCommand.MESSAGE_USAGE);

        assertParseFailure(parser, PREAMBLE_NON_EMPTY + PERSON_NAME_DESC_BOB + GROUP_NAME_DESC_CS2100,
                expectedMessage);
    }

}
