package seedu.superta.storage;

import java.util.Map;

import javax.xml.bind.annotation.XmlElement;

import seedu.superta.model.assignment.Score;
import seedu.superta.model.student.StudentId;

/**
 * An XML Representation of a Grade in a gradebook.
 */
public class XmlGrade {
    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Grade's %s field is missing!";

    @XmlElement(required = true)
    private String studentId;
    @XmlElement(required = true)
    private String score;

    public XmlGrade() { }

    public XmlGrade(String studentId, String score) {
        this.studentId = studentId;
        this.score = score;
    }

    public XmlGrade(Map.Entry<StudentId, Score> entry) {
        studentId = entry.getKey().toString();
        score = entry.getValue().toString();
    }

    public String getStudentId() {
        return studentId;
    }

    public String getScore() {
        return score;
    }
}
