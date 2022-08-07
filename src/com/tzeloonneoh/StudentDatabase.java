package com.tzeloonneoh;

import java.util.*;

/**
 * The StudentDatabase class represent a list of Student objects.
 *
 * @author  Tze Loon Neoh
 * @version 31 Oct 2021
 */
public class StudentDatabase
{
    private ArrayList<Student> studentDatabase;

    /**
     * Construct an empty list of students.
     */
    public StudentDatabase()
    {
        studentDatabase = new ArrayList<>();
    }

    /**
     * Add a Student object to a StudentDatabase object to be processed.
     *
     * @param   newStudent  The Student object to be added.
     */
    public void addStudent(Student newStudent)
    {
        studentDatabase.add(newStudent);
    }

    /**
     * Given a Student object and an identity number, check if the identity number of the Student
     * object matches the actual parameter of identity number.
     *
     * @param   student The student object, the identity number of which is to be processed.
     * @param   inputIdentityNumber The student identity number to be processed.
     * @return  true    If the identity number of the Student object and the given identity number
     *                  are the same, false otherwise.
     */
    private boolean compareIdentityNumbers(Student student, int inputIdentityNumber)
    {
        return (student.getIdentityNumber() == inputIdentityNumber);
    }

    /**
     * Given an identity number (an integer), compare the identity number with those of the
     * Student objects stored in a StudentDatabase object to be processed and return, if
     * there is, a Student object with the same identity number.
     *
     * @param   inputIdentityNumber    The user input of student identity number.
     * @return  The Student object with the same identity numbers the user input.
     */
    public Student filterStudentByIdentityNumber(int inputIdentityNumber)
    {
        Student studentByIdentityNumber = new Student();
        Iterator<Student> it = studentDatabase.iterator();
        while (it.hasNext())
        {
            Student student = it.next();
            if (compareIdentityNumbers(student, inputIdentityNumber))
                studentByIdentityNumber = student;
        }
        return studentByIdentityNumber;
    }

    /**
     * Given a name (a string of words), compare the name with the names of Student objects
     * stored in a StudentDatabase object to be processed and create a new StudentDatabase
     * object to store, if any, Student object(s) with the same name, and return the
     * StudentDabase object.
     *
     * @param   inputName    The user input of student name.
     * @return  The list of student(s) with the same name as the user input.
     */
    public StudentDatabase filterStudentsByName(String inputName)
    {
        StudentDatabase studentsByName = new StudentDatabase();
        Iterator<Student> it = studentDatabase.iterator();
        while (it.hasNext())
        {
            Student student = it.next();
            if (student.compareStudentNames(inputName))
                studentsByName.addStudent(student);
        }
        return studentsByName;
    }

    /**
     * Given a SubjectDatabase object, iterator through a StudentDatabase object to be processed,
     * store the Student object(s) who has(have) enrolled in all the subjects in the SubjectDatabase
     * object in a new StudentDatabase object, and return the newly created StudentDatabase object.
     *
     * @return  The list of student(s) enrolled in all the subjects in the given list of subjects.
     */
    public StudentDatabase filterStudentsBySubjects(SubjectDatabase subjectFilters)
    {
        StudentDatabase studentsBySubjects = new StudentDatabase();
        Iterator<Student> it = studentDatabase.iterator();
        while (it.hasNext())
        {
            Student student = it.next();
            SubjectDatabase subjectsEnrolled = student.getSubjectsEnrolled();
            boolean result = isSubset(subjectsEnrolled, subjectFilters);
            if (result)
                studentsBySubjects.addStudent(student);
        }
        return studentsBySubjects;
    }

    /**
     * Given an actual parameter (1: suspended or 2: unsuspended), create a StudentDatabase
     * objects to store Student object(s) based on suspension status.
     *
     * @param   integerInput    The user option to list (1: suspended or 2: unsuspended) students.
     * @return  The list of students based on suspension status of user's choice.
     */
    public StudentDatabase filterStudentsBySuspension(int integerInput)
    {
        StudentDatabase studentsBySuspension = new StudentDatabase();
        Iterator<Student> it = studentDatabase.iterator();
        while (it.hasNext())
        {
            Student student = it.next();
            boolean suspensionStatus = student.getSuspension();
            if (integerInput == 1)
            {
                if (suspensionStatus == false)
                    studentsBySuspension.addStudent(student);
            }
            else
            {
                if (suspensionStatus == true)
                    studentsBySuspension.addStudent(student);
            }
        }
        return studentsBySuspension;
    }

    /**
     * Return the StudentDatabase object containing Student objects not enrolled in any subjects.
     *
     * @return  The list of students who are not at all enrolled in any subjects.
     */
    public StudentDatabase filterStudentsNotEnrolled()
    {
        StudentDatabase studentsNotEnrolled = new StudentDatabase();
        Iterator<Student> it = studentDatabase.iterator();
        while (it.hasNext())
        {
            Student student = it.next();
            SubjectDatabase subjectsEnrolled = new SubjectDatabase();
            subjectsEnrolled = student.getSubjectsEnrolled();
            if (subjectsEnrolled.getSize() == 0)
                studentsNotEnrolled.addStudent(student);
        }
        return studentsNotEnrolled;
    }

    /**
     * Return the number of Student objects in a StudentDatabase object to be processed.
     *
     * @return  The number of students recorded on the list.
     */
    public int getSize()
    {
        return studentDatabase.size();
    }

    /**
     * Given an integer, return a Student object at the (integer)th index in a StudentDatabase
     * object to be processed.
     *
     * @return  The student at the given index on the list.
     */
    public Student getStudent(int index)
    {
        return studentDatabase.get(index);
    }

    /**
     * Given an identity number, check if the identity number already exists in a StudentDatabase
     * object to be processed.
     *
     * @param   inputIdentityNumber The student identity number to be processed.
     * @return  true    If the identity number has already been used for a Student object on the
     *                  list, false otherwise.
     */
    public boolean identityNumberInUse(int inputIdentityNumber)
    {
        int index = 0;
        boolean found = false;
        while(index < studentDatabase.size() && !found)
        {
            Student existingStudent = studentDatabase.get(index);
            if (existingStudent.getIdentityNumber() == (inputIdentityNumber))
            {
                found = true;
                return true;
            }
            index++;
        }
        return false;
    }

    /**
     * Compare a check SubjectDatabase object to a main SubjectDatabase object and check if all the
     * Ssubject objects in the check SubjectDatabase object exist in the main SubjectDatabase
     * object.
     *
     * @param   main    The main list of subjects to be compared against.
     * @param   check   The list of subjects to be processed.
     * @return  true    If the main list contains all the subjects in the check list, false
     *                  otherwise.
     */
    private boolean isSubset(SubjectDatabase main, SubjectDatabase check)
    {
        int index1 = 0;
        int index2 = 0;
        for (index1 = 0; index1 < check.getSize(); index1++)
        {
            for (index2 = 0; index2 < main.getSize(); index2++)
            {
                if ((check.getSubject(index1).equals(main.getSubject(index2))))
                    break;
            }
            if (index2 == main.getSize())
                return false;
        }
        return true;
    }

    /**
     * Print out all students on the list.
     */
    public void printStudentDatabase()
    {
        int index = 0;
        while (index < studentDatabase.size())
        {
            Student student = studentDatabase.get(index);
            System.out.println((index + 1) + (". ") + student.toString());
            index++;
        }
        System.out.println("");
    }

    /**
     * Remove a Student object from a StudentDatabase object to be processed.
     *
     * @param   newStudent  The Student object to be removed.
     */
    public void removeStudent(Student newStudent)
    {
        studentDatabase.remove(newStudent);
    }

    /**
     * Given a student name and an identity number, iterate through a StudentDatabase object to
     * be processed and check if there is a Student object in the StudentDatabase object that
     * matches the name and identity number.
     *
     * @param   inputStudentName  The student name against which comparison is to be made.
     * @param   inputIdentityNumber The student identity number against which comparison is to be
     *                              made
     * @return  true    If a Student object of the same name and identity number is found, false
     *                  otherwise.
     */
    public boolean studentOnDatabase(String inputStudentName, int inputIdentityNumber)
    {
        int index = 0;
        boolean found = false;
        while(index < studentDatabase.size() && !found)
        {
            Student existingStudent = studentDatabase.get(index);
            if (existingStudent.compareStudentNames(inputStudentName) &&
                    compareIdentityNumbers(existingStudent, inputIdentityNumber))
            {
                found = true;
                return true;
            }
            index++;
        }
        return false;
    }
}
