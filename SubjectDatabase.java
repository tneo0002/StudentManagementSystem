import java.util.*;

/**
 * The SubjectDatabase class represent a list of Subject objects.
 *
 * @author  Tze Loon Neoh
 * @version 31 Oct 2021
 */
public class SubjectDatabase
{
    private ArrayList<Subject> subjectDatabase;
    
    /**
     * Construct an empty list of subjects.
     */
    public SubjectDatabase()
    {
        subjectDatabase = new ArrayList<>();
    }
    
    /**
     * Add a Subject object to a SubjectDatabase object to be processed.
     * 
     * @param   newSubject  The Subject object to be added.
     */
    public void addSubject(Subject newSubject)
    {
        subjectDatabase.add(newSubject);
    }
    
    /**
     * Empty a SubjectDatabase object to be processed.
     */
    public void clearSubjectDatabase()
    {
        subjectDatabase.clear();
    }
    
    /**
     * Print out all subjects in the SubjectDatabase object of all the subjects offered at the 
     * school.
     */
    public void displaySubjectDatabaseAsOptions()
    {
        int index = 0;
        while (index < subjectDatabase.size())
        {
            Subject subject = subjectDatabase.get(index);
            System.out.println("(" + (index + 1) + ") " + subject.toString());
            index++;
        }
    }
    
    /**
     * Print out all subjects in a SubjectDatabase object to be processed.
     */
    public void displaySubjectDatabaseAsResults()
    {
        int index = 0;
        while (index < subjectDatabase.size())
        {
            Subject subject = subjectDatabase.get(index);
            System.out.println((index + 1) + ". " + subject.toString());
            index++;
        }
    }
    
    /**
     * Return the number of Subject objects in the SubjectDatabase object to be processed.
     * 
     * @return  The number of subjects recorded on the list.
     */
    public int getSize()
    {
        return subjectDatabase.size();
    }
                                                                                                    
    /**
     * Given an integer, return a Subject object at the (integer)th index in a SubjectDatabase 
     * object to be processed.
     * 
     * @return  The subject at the given index on the list.
     */
    public Subject getSubject(int index)
    {
        return subjectDatabase.get(index);
    }
    
    /**
     * Given an ArrayList of integers, iterate through the list and create a SubjectDatabase object 
     * to store the Subject object(s) at the (integer - 1)th index in the SubjectDatabase object 
     * of all the subjects offered at the school.
     * 
     * @param   subjectIndexes  The list of integer(s) to be processed.
     * @return  The list of selected subjects .
     */
    public SubjectDatabase matchSubjectsByIndexes(ArrayList<Integer> subjectIndexes)
    {
        SubjectDatabase selectedSubjects = new SubjectDatabase();
        for (int index = 0; index < subjectIndexes.size(); index++)
        {
            if (subjectIndexes.get(index) <= subjectDatabase.size() && 
                subjectIndexes.get(index) > 0)
                selectedSubjects.addSubject(subjectDatabase.get(subjectIndexes.get(index) - 1));
        }
        return selectedSubjects;
    }
    
    /**
     * Remove a Subject object from a SubjectDatabase object to be processed.
     * 
     * @param   newSubject  The Subject object to be removed.
     */
    public void removeSubject(Subject newSubject)
    {
        subjectDatabase.remove(newSubject);
    }
}