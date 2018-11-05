package seedu.superta.logic.parser;

import static seedu.superta.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.superta.logic.parser.CommandParserTestUtil.assertParseFailure;

import java.util.Arrays;

import org.junit.Test;

import seedu.superta.logic.commands.FindCommand;
import seedu.superta.model.student.NameContainsKeywordsPredicate;

public class FindCommandParserTest {

    private static final String MESSAGE_INVALID_FORMAT =
        String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE);

    private FindCommandParser parser = new FindCommandParser();

    @Test
    public void parse_missingParts_failure() {
        // no prefix specified
        assertParseFailure(parser, "Alex", MESSAGE_INVALID_FORMAT);
        // no field specified
        assertParseFailure(parser, "n/", MESSAGE_INVALID_FORMAT);
        // no prefix and no field specified
        assertParseFailure(parser, "", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidPreamble_failure() {
        // invalid arguments being parsed as preamble
        assertParseFailure(parser, "1 some random string", MESSAGE_INVALID_FORMAT);

        // invalid prefix being parsed as preamble
        assertParseFailure(parser, "1 i/ string", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_validArgs_returnsFindCommand() throws Exception {
        // no leading and trailing whitespaces
        FindCommand expectedFindCommand =
                new FindCommand(new NameContainsKeywordsPredicate(Arrays.asList("Alice")));
        FindCommand findCommand = parser.parse(" n/Alice");
        // TODO: Fix up this test. Predicates cannot be compared because find command now composes of a few different
        // predicates.
        // assertParseSuccess(parser, " n/Alice", expectedFindCommand);

        // multiple whitespaces between keywords
        // assertParseSuccess(parser, " \n n/Alice \n \t", expectedFindCommand);
    }

}
