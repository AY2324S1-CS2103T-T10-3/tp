package seedu.address.logic.parser;

import org.junit.jupiter.api.Test;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.*;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.Time;
import seedu.address.model.TimeInterval;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.testutil.PersonBuilder;

import java.util.ArrayList;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.*;
import static seedu.address.logic.parser.CliSyntax.PREFIX_FREETIME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalPersons.AMY;

public class DeleteTimeCommandParserTest {
    private DeleteTimeCommandParser parser = new DeleteTimeCommandParser();
    Person samplePerson = new PersonBuilder(AMY).build();
    ArrayList<TimeInterval> list = new ArrayList<>();

    // whitespace only preamble
    @Test
    public void parse_validArgs_returnsDeleteTimeCommand() throws ParseException {
        TimeInterval validTime = ParserUtil.parseEachInterval("mon 1300 - mon 1400");
        list.add(validTime);

        // valid person with time
        assertParseSuccess(parser, PREAMBLE_WHITESPACE + NAME_DESC_AMY
                + VALID_TIME_DESC_MON , new DeletePersonTimeCommand(AMY.getName(), list));

        // valid person with time
        assertParseSuccess(parser, PREAMBLE_WHITESPACE + NAME_DESC_AMY + VALID_TIME_DESC_MON_2,
                new DeletePersonTimeCommand(AMY.getName(), list));
    }

    @Test
    public void parse_compulsoryFieldMissingPrefix_failure() {
        // missing name prefix
        assertParseFailure(parser, VALID_NAME_BOB + VALID_TIME_DESC_MON,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteTimeCommand.MESSAGE_USAGE));

        // missing time prefix
        assertParseFailure(parser,  NAME_DESC_BOB + VALID_TIME_MON,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteTimeCommand.MESSAGE_USAGE));
    }

    // multiple tags - all accepted
    @Test
    public void parse_multipleValidArgs_returnsDeleteTimeCommand() throws ParseException {
        TimeInterval validTimeMon = ParserUtil.parseEachInterval(VALID_TIME_MON);
        TimeInterval validTimeTue = ParserUtil.parseEachInterval(VALID_TIME_TUE);
        list.add(validTimeMon);
        list.add(validTimeTue);
        assertParseSuccess(parser, PREAMBLE_WHITESPACE + NAME_DESC_AMY
                + VALID_TIME_DESC_MON + VALID_TIME_DESC_TUE , new DeletePersonTimeCommand(AMY.getName(), list));
    }

    @Test
    public void parse_repeatedNonTagValue_failure() {

        // multiple names
        assertParseFailure(parser, PREAMBLE_WHITESPACE + NAME_DESC_AMY + NAME_DESC_BOB
                        + VALID_TIME_DESC_MON,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_NAME));

        // name and group
        assertParseFailure(parser, PREAMBLE_WHITESPACE + NAME_DESC_AMY + GROUP_DESC_AMY
                        + VALID_TIME_DESC_MON,
                String.format(DeleteCommand.MESSAGE_TWO_PARAMETERS, DeleteCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_invalidValue_failure() {
//        // Invalid name
//        assertParseFailure(parser, PREAMBLE_WHITESPACE + INVALID_NAME_DESC + VALID_TIME_DESC_MON,
//                Name.MESSAGE_CONSTRAINTS);
//
//        // 1 Valid & 1 invalid time ( Day more than first 3 letters )
//        assertParseFailure(parser, PREAMBLE_WHITESPACE + NAME_DESC_AMY + INVALID_TIME_DESC_MON + VALID_TIME_DESC_MON,
//                Time.MESSAGE_CONSTRAINTS);
//
//        // Invalid time ( Day more than first 3 letters )
//        assertParseFailure(parser, PREAMBLE_WHITESPACE + NAME_DESC_AMY + INVALID_TIME_DESC_MON,
//                Time.MESSAGE_CONSTRAINTS);
//
//        // Invalid time ( Time was not 24 hour format (2500))
//        assertParseFailure(parser, PREAMBLE_WHITESPACE + NAME_DESC_AMY + INVALID_TIME_DESC_TUE,
//                Time.MESSAGE_CONSTRAINTS);
//
//        // Invalid time ( Start time after end time )
//        assertParseFailure(parser, PREAMBLE_WHITESPACE + NAME_DESC_AMY + INVALID_TIME_DESC_START_AFTER_END,
//                TimeInterval.MESSAGE_CONSTRAINTS_LOGIC);
//
        // Only prefix without name
        assertParseFailure(parser, PREAMBLE_WHITESPACE + PREFIX_NAME + "  " + VALID_TIME_DESC_MON,
                Name.MESSAGE_CONSTRAINTS);
    }
}
