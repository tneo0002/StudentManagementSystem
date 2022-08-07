package com.tzeloonneoh;

/**
 * The Student class represents a single Student object that can hold information about a student,
 * i.e., student's name, identity number, current suspension status, and enrolled subjects. The
 * student's name must only be alphabetic and may contain multiple words. The identity number must
 * be a 3-digit number between 111 and 999 (inclusive). The current suspension is a boolean value.
 * The student object also stores a collection of subjects a particular student is enrolled in.
 *
 * @author Tze Loon Neoh
 * @version 21 Sep 2021
 */
public class Student
{
    private String name;
    private int identityNumber;
    private boolean suspension;
    private SubjectDatabase subjectsEnrolled;

    /**
     * Construct and initialise a Student object.
     */
    public Student()
    {
        name = "None";
        identityNumber = 0;
        suspension = true;
        subjectsEnrolled = new SubjectDatabase();
    }

    /**
     * Empty the list of enrolled subjects of a Student object.
     */
    public void clearSubjectsEnrolled()
    {
        subjectsEnrolled.clearSubjectDatabase();
    }

    /**
     * Given a name, check if the name of the Student object matches the actual parameter of name.
     *
     * @param   inputName   The student name to be processed.
     * @return  true    If the name of the Student object matches the given name, false otherwise.
     */
    public boolean compareStudentNames(String inputName)
    {
        return (getName().toLowerCase().equals(inputName.toLowerCase()));
    }

    /**
     * Print out the basic information regarding a Student object.
     */
    public void displayStudent()
    {
        System.out.println(name + " (ID: " +
                identityNumber +
                ", " +
                (suspension ? "Unsuspended" : "Suspended") +
                ")");
    }

    /**
     * Given a subject (a Subject object), add the subject to the SubjectDatabase object of
     * enrolled subjects of a Student object.
     *
     * @param   The subject to be enrolled in.
     */
    public void enrolSubject(Subject newSubject)
    {
        subjectsEnrolled.addSubject(newSubject);
    }

    /**
     * Given a name (a string of words), capitalise the first letter of each word in the name.
     *
     * @return  The formatted name.
     */
    private String formatName(String newName)
    {
        String formattedFullName = "";
        String[] fullName = newName.split(" ");
        for (int index = 0; index < fullName.length; index++)
        {
            String formattedName = "";
            if (fullName[index].length() > 1)
            {
                formattedName = fullName[index].trim().substring(0, 1).toUpperCase() +
                        fullName[index].trim().substring(1, fullName[index].
                                length()).toLowerCase() +
                        " ";
            }
            else if (fullName[index].length() == 1)
                formattedName = fullName[index].trim().substring(0, 1).toUpperCase() +
                        " ";
            else
                formattedName = "";
            formattedFullName += formattedName;
        }
        return formattedFullName.trim();
    }

    /**
     * Return the identity number a Student object.
     *
     * @return  The identity number of a student.
     */
    public int getIdentityNumber()
    {
        return identityNumber;
    }

    /**
     * Return the name of a Student object.
     *
     * @return  The name of a student.
     */
    public String getName()
    {
        return name;
    }

    /**
     * Return the the SubjectDatabase object of enrolled subjects of a Student object.
     *
     * @return  The list of enrolled subjects of a student.
     */
    public SubjectDatabase getSubjectsEnrolled()
    {
        return subjectsEnrolled;
    }

    /**
     * Return the current suspension status a Student object.
     *
     * @return  true    If the student is being unsuspended, false otherwise.
     */
    public boolean getSuspension()
    {
        return suspension;
    }

    /**
     * Given an integer of identity number and check if the integer falls within the defined range.
     *
     * @param   inputIdentityNumber   The new identity number to be processed.
     * @return  true    If the identity number falls with the range, false otherwise.
     */
    private boolean identityNumberInRange(int inputIdentityNumber)
    {
        if (inputIdentityNumber >= 111 && inputIdentityNumber <= 999)
            return true;
        else
            return false;
    }

    /**
     * Given a name (a string of words), check if the string is not empty and comprises only
     * alphabets.
     *
     * @param   newName The student name to be processed.
     * @return  true    If the name comprises only alphabets, false otherwise or if the name is an
     *                  empty string.
     */
    private boolean nameIsAlphabetic(String newName)
    {
        if (newName.trim().isEmpty())
            return false;
        else
        {
            String[] splitName = newName.split(" ");
            for (String name : splitName)
            {
                String trimmedName = name.trim();
                if (!trimmedName.trim().isEmpty())
                {
                    for (int index = 0; index < trimmedName.length(); index++)
                    {
                        char character = trimmedName.charAt(index);
                        if (!Character.isLetter(character))
                            return false;
                    }
                }
            }
        }
        return true;
    }

    /**
     * Take an integer of identity number and check if the integer falls within the defined range.
     * Modify the current student identity number to the value of the integer if it is within the
     * range, otherwise print out an error message.
     *
     * @param   newIdentityNumber   The new identity number to be processed.
     */
    public void setIdentityNumber(int newIdentityNumber)
    {
        if (identityNumberInRange(newIdentityNumber))
            identityNumber = newIdentityNumber;
        else
            System.out.println("\nNo changes have been made to the identity number!");
    }

    /**
     * Take a string of name and check if the string consists of only alphabets. Modify the
     * current student name to the name if it consists of only alphabets, otherwise print out an
     * error message.
     *
     * @param  newName  The new student name.
     */
    public void setName(String newName)
    {
        if (nameIsAlphabetic(newName))
            name = formatName(newName);
        else
            System.out.println("\nNo changes have been made to the name!");
    }

    /**
     * Take a string and check if the string is "true" or "false". Cast the string into boolean
     * data type if the string is either "true" or "false" and modify the current suspension status
     * to the boolean value, otherwise print out an error message.
     *
     * @param   newSuspension   The new suspension status.
     */
    public void setSuspension(String newSuspension)
    {
        if (suspensionIsBoolean(newSuspension))
            suspension = Boolean.parseBoolean(newSuspension);
        else
            System.out.println("\nNo changes have been made to the suspension status!");
    }

    /**
     * Given a string, check if the string is a boolean value of true or false.
     *
     * @param   suspension  The word to be processed.
     * @return  true    If the word is either "true" or "false", false otherwise.
     */
    private boolean suspensionIsBoolean(String suspension)
    {
        if (suspension.toLowerCase().equals("true") || suspension.toLowerCase().equals("false"))
            return true;
        else
            return false;
    }

    /**
     * Return basic information regarding a Student object as a string.
     *
     * @return  The information of a student.
     */
    public String toString()
    {
        return (name +
                " (ID: " +
                identityNumber +
                ", " +
                (suspension ? "Unsuspended" : "Suspended") +
                ")");
    }

    /**
     * Given a subject (a Subject object), remove the subject from the SubjectDatabase object of
     * enrolled subjects of a Student object.
     *
     * @param   The subjec to be unenrolled from.
     */
    public void unenrolSubject(Subject newSubject)
    {
        subjectsEnrolled.removeSubject(newSubject);
    }
}
