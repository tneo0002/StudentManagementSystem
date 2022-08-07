package com.tzeloonneoh;

import java.util.*;

/**
 * The Subject class represents a single subject object that can hold information about a subject,
 * i.e., subject name and credit points. A subject name can be numeric or alphabetic but must not
 * be a blank string. An identity number must be a number between 1 and 6 (inclusive). Student
 * objects are created by reading from a text file ("subjects.txt") and are not editable while the
 * program is running.
 *
 * @author Tze Loon Neoh
 * @version 21 Sep 2021
 */
public class Subject
{
    private String name;
    private int credit;

    /**
     * Construct and initialise a Subject object.
     */
    public Subject()
    {
        name = "None";
        credit = 1;
    }

    /**
     * Construct and initialise a Subject object with input values from user.
     *
     * @param   subjectName The new subject name.
     * @param   subjectCredit   The new credit point of the subject.
     */
    public Subject(String subjectName, int subjectCredit)
    {
        setName(subjectName);
        setCredit(subjectCredit);
    }

    /**
     * Given a credit point, check if the credit points is within a defined range.
     *
     * @param   inputCredit The credit points to be processed.
     * @return  true        If the credit points is within the range, false otherwise.
     */
    public boolean creditInRange(int inputCredit)
    {
        if (inputCredit >= 1 && inputCredit <= 6)
            return true;
        else
            return false;
    }

    /**
     * Print out the information regarding a Subject object.
     */
    public void displaySubject()
    {
        System.out.println(name + ", " + credit);
    }

    /**
     * Given a name (a string of words), capitalise the first letter of each word in the name.
     *
     * @return The formatted name.
     */
    public String formatName(String newName)
    {
        String formattedFullName = "";
        String[] fullName = newName.split(" ");
        for (int index = 0; index < fullName.length; index++)
        {
            String formattedName = "";
            if (fullName[index].length() > 1)
            {
                formattedName = fullName[index].trim().substring(0, 1).toUpperCase() +
                        fullName[index].trim().substring(1, fullName[index].length()).
                                toLowerCase() +
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
     * Return the current credit points of a Subject object.
     *
     * @return  The current credit points.
     */
    public int getCredit()
    {
        return credit;
    }

    /**
     * Return the current subject name of the Subject object.
     *
     * @return  The current subject name.
     */
    public String getName()
    {
        return name;
    }

    /**
     * Given a name (a string of words), check if the string is not empty and comprises only
     * alphabets and number.
     *
     * @param   newName The subject name to be processed.
     * @return  true    If the name comprises only alphabets and number, false otherwise or if the
     *                  name is an empty string.
     */
    public boolean nameIsAlphaNumeric(String newName)
    {
        if (newName.trim().isEmpty())
            return false;
        else
        {
            String[] splitName = newName.split(" ");
            for (String name : splitName)
            {
                String trimmedName = name.trim();
                if (!trimmedName.isEmpty())
                {
                    for (int index1 = 0; index1 < trimmedName.length(); index1++)
                    {
                        char character = trimmedName.charAt(index1);
                        if (!Character.isLetter(character) &&
                                !Character.isDigit(character))
                            return false;
                    }
                }
            }
        }
        return true;
    }

    /**
     * Take a integer and check if the integer is within the defined range. Set the current credit
     * points of subject to the value of the integer if it is within the range, otherwise print
     * out an error message.
     *
     * @param  newCredit  The new credit points.
     */
    public void setCredit(int newCredit)
    {
        if (creditInRange(newCredit))
            credit = newCredit;
        else
        {
            credit = 1;
            System.out.println("Invalid credit point! The subject credit point is set to '1'!\n");
        }
    }

    /**
     * Take a string of name and check if the string consists of only alphabets and numbers.
     * Modify the current subject name to the name if it consists of only alphabets and numbers,
     * otherwise print out an error message.
     *
     * @param  newName  The new subject name.
     */
    public void setName(String newName)
    {
        if (nameIsAlphaNumeric(newName))
            name = formatName(newName);
        else
        {
            name = "None";
            System.out.println("Invalid name! The subject name is set to 'None'!\n");
        }
    }

    /**
     * Return information regarding a Subject object as a string.
     *
     * @return  The information of a subject.
     */
    public String toString()
    {
        return (name + "," + credit);
    }
}