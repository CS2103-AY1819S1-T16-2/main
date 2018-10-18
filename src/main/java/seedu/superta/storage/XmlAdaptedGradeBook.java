package seedu.superta.storage;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;

import seedu.superta.logic.parser.ParserUtil;
import seedu.superta.logic.parser.exceptions.ParseException;
import seedu.superta.model.assignment.GradeBook;

/**
 * Class for XML-Adapted GradeBook
 */
public class XmlAdaptedGradeBook implements XmlAdapted<GradeBook> {
    public static final String MISSING_FIELD_MESSAGE_FORMAT = "GradeBook's %s field is missing!";

    @XmlElement
    private List<XmlGrade> grades = new ArrayList<>();

    public XmlAdaptedGradeBook() { }

    public XmlAdaptedGradeBook(GradeBook source) {
        source.stream()
            .map(XmlGrade::new)
            .forEach(grades::add);
    }

    @Override
    public GradeBook toModelType() {
        GradeBook gradebook = new GradeBook();

        grades.stream()
            .forEach(g -> {
                try {
                    gradebook.addGrade(
                        ParserUtil.parseStudentId(g.getStudentId()), ParserUtil.parseScore(g.getScore()));
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            });

        return gradebook;
    }
}
