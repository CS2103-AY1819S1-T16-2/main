package seedu.superta.model;

import static java.util.Objects.requireNonNull;

import java.util.List;
import java.util.Optional;

import javafx.collections.ObservableList;
import seedu.superta.model.assignment.Assignment;
import seedu.superta.model.assignment.Grade;
import seedu.superta.model.assignment.exceptions.AssignmentNotFoundException;
import seedu.superta.model.student.Feedback;
import seedu.superta.model.student.Student;
import seedu.superta.model.student.StudentId;
import seedu.superta.model.student.UniqueStudentList;
import seedu.superta.model.student.exceptions.StudentNotFoundException;
import seedu.superta.model.tutorialgroup.TutorialGroup;
import seedu.superta.model.tutorialgroup.TutorialGroupMaster;
import seedu.superta.model.tutorialgroup.exceptions.TutorialGroupNotFoundException;

/**
 * Wraps all data at the SuperTA client level
 * Duplicates are not allowed (by .isSameStudent comparison)
 */
public class SuperTaClient implements ReadOnlySuperTaClient {

    private final UniqueStudentList students;
    private final TutorialGroupMaster tutorialGroupMaster;

    /*
     * The 'unusual' code block below is an non-static initialization block, sometimes used to avoid duplication
     * between constructors. See https://docs.oracle.com/javase/tutorial/java/javaOO/initial.html
     *
     * Note that non-static init blocks are not recommended to use. There are other ways to avoid duplication
     *   among constructors.
     */
    {
        students = new UniqueStudentList();
        tutorialGroupMaster = new TutorialGroupMaster();
    }

    public SuperTaClient() {}

    /**
     * Creates an SuperTaClient using the Persons in the {@code toBeCopied}
     */
    public SuperTaClient(ReadOnlySuperTaClient toBeCopied) {
        this();
        resetData(toBeCopied);
    }

    //// list overwrite operations

    /**
     * Replaces the contents of the student list with {@code students}.
     * {@code students} must not contain duplicate students.
     */
    public void setStudents(List<Student> students) {
        this.students.setStudents(students);
    }

    public void setTutorialGroups(List<TutorialGroup> tutorialGroups) {
        this.tutorialGroupMaster.setTutorialGroups(tutorialGroups);
    }

    /**
     * Resets the existing data of this {@code SuperTaClient} with {@code newData}.
     */
    public void resetData(ReadOnlySuperTaClient newData) {
        requireNonNull(newData);

        setStudents(newData.getStudentList());
        setTutorialGroups(newData.getTutorialGroupList());
    }

    //// student-level operations

    /**
     * Returns true if a student with the same identity as {@code student} exists in the address book.
     */
    public boolean hasStudent(Student student) {
        requireNonNull(student);
        return students.contains(student);
    }

    /**
     * Returns true if a tutorial group with the same id as {@code tutorialGroup} exists in the directory.
     */
    public boolean hasTutorialGroup(String tutorialGroupId) {
        return tutorialGroupMaster.contains(tutorialGroupId);
    }

    public Optional<TutorialGroup> getTutorialGroup(String tutorialGroupId) {
        return tutorialGroupMaster.getTutorialGroup(tutorialGroupId);
    }

    public Optional<Student> getStudentWithId(StudentId studentId) {
        return students.getStudentWithId(studentId);
    }

    public void addStudentToTutorialGroup(TutorialGroup tutorialGroup, Student student) {
        tutorialGroup.addStudent(student);
    }

    /**
     * Adds a tutorial group to the directory.
     */
    public void addTutorialGroup(TutorialGroup tutorialGroup) {
        tutorialGroupMaster.addTutorialGroup(tutorialGroup);
    }

    /**
     * Replaces the given tutorial group {@code target} in the list with {@code edited}.
     * {@code target} must exist in the client.
     */
    public void updateTutorialGroup(TutorialGroup edited) {
        requireNonNull(edited);

        tutorialGroupMaster.setTutorialGroup(edited);
    }

    /**
     * Removes a tutorial group from the directory.
     * The tutorial group must exist in the directory.
     */
    public void removeTutorialGroup(TutorialGroup key) {
        tutorialGroupMaster.removeTutorialGroup(key);
    }

    /**
     * Adds an assignment to a tutorial group.
     */
    public void addAssignment(TutorialGroup tutorialGroup, Assignment assignment) {
        requireNonNull(assignment);
        requireNonNull(tutorialGroup);

        tutorialGroup.addAssignment(assignment);
    }

    /**
     * Performs an addition of a grade to an assignment gradebook, if possible.
     */
    public void grade(Grade grade) {
        Optional<TutorialGroup> tutorialGroupOptional =
                tutorialGroupMaster.getTutorialGroup(grade.getTutorialGroupId());
        if (!tutorialGroupOptional.isPresent()) {
            throw new TutorialGroupNotFoundException();
        }
        TutorialGroup tutorialGroup = tutorialGroupOptional.get();

        Optional<Assignment> assignmentOptional = tutorialGroup.getAssignment(grade.getTitle());
        if (!assignmentOptional.isPresent()) {
            throw new AssignmentNotFoundException();
        }
        Assignment assignment = assignmentOptional.get();

        Optional<Student> studentOptional = students.getStudentWithId(grade.getStudentId());
        if (!studentOptional.isPresent()) {
            throw new StudentNotFoundException();
        }
        Student student = studentOptional.get();
        assignment.grade(student.getStudentId(), grade.getScore());
    }

    /**
     * Adds feedback to a student.
     */
    public void addFeedback(Feedback feedback, StudentId studentId) {
        Optional<Student> studentOptional = students.getStudentWithId(studentId);
        if (!studentOptional.isPresent()) {
            throw new StudentNotFoundException();
        }
        Student student = studentOptional.get();
        List<Feedback> studentFeedback = student.getFeedback();
        studentFeedback.add(feedback);
        Student editedStudent =
                new Student(student.getName(), student.getPhone(), student.getEmail(), student.getAddress(),
                    student.getStudentId(), student.getTags(), studentFeedback);
        updateStudent(student, editedStudent);
    }

    /**
     * Adds a student to the address book.
     * The student must not already exist in the address book.
     */
    public void addStudent(Student student) {
        students.add(student);
    }

    /**
     * Replaces the given student {@code target} in the list with {@code editedStudent}.
     * {@code target} must exist in the address book.
     * The student identity of {@code editedStudent} must not be the same as another existing student in the address
     * book.
     */
    public void updateStudent(Student target, Student editedStudent) {
        requireNonNull(editedStudent);

        students.setStudent(target, editedStudent);
    }

    /**
     * Removes {@code key} from this {@code SuperTaClient}.
     * {@code key} must exist in the address book.
     */
    public void removeStudent(Student key) {
        students.remove(key);
        tutorialGroupMaster.removeStudentReferences(key);
    }

    //// util methods

    @Override
    public String toString() {
        return students.asUnmodifiableObservableList().size() + " students";
        // TODO: refine later
    }

    @Override
    public ObservableList<Student> getStudentList() {
        return students.asUnmodifiableObservableList();
    }

    @Override
    public ObservableList<TutorialGroup> getTutorialGroupList() {
        return tutorialGroupMaster.asUnmodifiableObservableList();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof SuperTaClient // instanceof handles nulls
                && students.equals(((SuperTaClient) other).students)
                && tutorialGroupMaster.equals(((SuperTaClient) other).tutorialGroupMaster));
    }

    @Override
    public int hashCode() {
        return students.hashCode();
    }
}
