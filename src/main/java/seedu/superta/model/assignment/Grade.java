package seedu.superta.model.assignment;

import seedu.superta.model.student.StudentId;

/**
 * Model for Grading details.
 */
public class Grade {
    private final String tutorialGroupId;
    private final Title title;
    private final StudentId studentId;
    private final Score score;

    public Grade(String tutorialGroupId, Title title, StudentId studentId, Score score) {
        this.tutorialGroupId = tutorialGroupId;
        this.title = title;
        this.studentId = studentId;
        this.score = score;
    }

    public String getTutorialGroupId() {
        return tutorialGroupId;
    }

    public Title getTitle() {
        return title;
    }

    public StudentId getStudentId() {
        return studentId;
    }

    public Score getScore() {
        return score;
    }
}
