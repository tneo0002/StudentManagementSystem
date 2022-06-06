import java.util.*;
import java.io.*;

/**
 * The SchoolManager class implements a School Management System that manages the students and the 
 * offered subjects within a school. It maintains 2 lists: one that keeps a record of all the  
 * students in the school and the other one stores the details of all subjects offer by the 
 * school. The 2 lists are loaded from separate text files and upon exiting the system, the system 
 * automatically saves the list of all students in memory to the text file from which the data was
 * read. 
 * 
 * The system allows its user to list students by selected criteria (i.e., all students, subjects, 
 * and suspension status), manage students (add a new student, unregister a student, suspend/
 * unsuspend a student, enrol/unenrol a student in/from multiple subjects) and list all the 
 * subjects offered at the school. 
 * 
 * @author Tze Loon Neoh
 * @version 29 Oct 2021
 */
public class SchoolManager
{
    private SubjectDatabase allSubjects;
    private StudentDatabase allStudents;

    /**
     * Create the School Management System and initialise its two embedded lists of all subjects 
     * and all students.
     */
    public SchoolManager()
    {
        allSubjects = new SubjectDatabase();
        initialiseSubjectDatabase("subjects.txt");
        allStudents = new StudentDatabase();
        initialiseStudentDatabase("students.txt");
    }

    /**
     * Calculate the total credit points of all subjects on a subject list and return the sum.
     * 
     * @param   subjectEnrolled The list of subjects to be processed.
     * @return  The total credit points.
     */    
    private int calculateTotalCredit(SubjectDatabase subjectsEnrolled)
    {
        int totalCredit = 0;
        int index = 0;
        while (index < subjectsEnrolled.getSize())
        {
            Subject subjectEnrolled = subjectsEnrolled.getSubject(index);
            int credit = subjectEnrolled.getCredit();
            totalCredit += credit;
            index++;
        }
        return totalCredit;
    }
    
    /**
     * Given a list of strings and iterate through the list. For each string, separate the 
     * comma-separated elements and create a Student object to store each element in the 
     * appropriate attribute. Retrieve Subject object(s) from the list of all subjects by the 
     * subject name(s) (from the 4th element onwards in the string, if there is any), create a 
     * SubjectDatabase object to store the retrieved Subject object(s), and store the 
     * SubjectDatabase object in the Student object.
     * 
     * @param   textLines   The list of strings to be processed.
     * @return  The list of all students.
     */
    private StudentDatabase createStudentListFromText(ArrayList<String> textLines)
    {
        StudentDatabase studentList = new StudentDatabase();
        Iterator<String> it = textLines.iterator();
        while (it.hasNext())
        {
            Student student = new Student();
            String line = it.next();
            String[] splitLine = line.split(",");
            student.setName(splitLine[0]);
            student.setIdentityNumber(Integer.parseInt(splitLine[1]));
            student.setSuspension(splitLine[2]);
            for (int index1 = 3; index1 < splitLine.length; index1+=2)
            {
                for (int index2 = 0; index2 < allSubjects.getSize(); index2++)
                    if (splitLine[index1].equals(allSubjects.getSubject(index2).getName()))
                    {
                        student.enrolSubject(allSubjects.getSubject(index2));
                    }
            }
            studentList.addStudent(student);
        }
        return studentList;
    }
    
    /**
     * Given a list of strings, iterate through the list, separate the comma-separated elements in 
     * each string, create a Subject object to store each element in the appropriate attribute, 
     * and create a SubjectDatabase object to store the Subject object.
     * 
     * @param   textLines   The list of strings to be processed.
     * @return  The list of all subjects.
     */
    private SubjectDatabase createSubjectListFromText(ArrayList<String> textLines)
    {
        SubjectDatabase subjectList = new SubjectDatabase();
        Iterator<String> it = textLines.iterator();
        while (it.hasNext())
        {
            Subject subject = new Subject();
            String line = it.next();
            String[] splitLine = line.split(",");
            subject.setName(splitLine[0]);
            subject.setCredit(Integer.parseInt(splitLine[1]));
            subjectList.addSubject(subject);
        }
        return subjectList;
    }
    
    /**
     * Display the submenu of "List students" with 4 options.
     */    
    private void displayListStudentsMenu()
    {
        System.out.println("List students" +
                           "\n(1) List students by subjects" +
                           "\n(2) List students by suspension status" +
                           "\n(3) List all students" +
                           "\n(4) Back to main menu");
    }
    
    /**
     * Display the main menu of the School Management System with 4 options.
     */    
    private void displayMainMenu()
    {
        System.out.println("Welcome to My School Manager" +
                           "\n(1) List students" +
                           "\n(2) Manage students" +
                           "\n(3) List all subjects" +
                           "\n(4) Exit system");
    }
    
    /**
     * Display the submenu of "Manage students" with 5 options.
     */    
    private void displayManageStudentsMenu()
    {
        System.out.println("Manage students" +
                           "\n(1) Add new student" +
                           "\n(2) Delete student" +
                           "\n(3) Suspend/unsuspend student" +
                           "\n(4) Edit student's subjects" +
                           "\n(5) Back to main menu");
    }
    
    /**
     * Perform set operation exception of two subject lists.
     * 
     * @param   main    The main list of subjects to be processed.
     * @param   check   The sublist of subjects to be processed.
     * @return  The relative complement of the main list in the sublist.
     */
    private SubjectDatabase exceptSubjectDatabases(SubjectDatabase main, SubjectDatabase check)
    {
        SubjectDatabase exception = check;
        for (int index1 = 0; index1 < main.getSize(); index1++)
        {
            for (int index2 = 0; index2 < check.getSize(); index2++)
            {
                if (main.getSubject(index1).equals(check.getSubject(index2)))
                    exception.removeSubject(check.getSubject(index2));
            }
        }
        return exception;
    }

    /**
     * Ask for user input of whitespace-separated integers and return only the unique integers from 
     * the string input in the form of a list of integers. All non-integers are collected in a 
     * local variable of a list.
     * 
     * @return   The list of unique integers input by user.    
     */    
    private ArrayList<Integer> extractOnlyIntegers()
    {
        System.out.print("Enter number(s) between 0 - " + allSubjects.getSize() + ": ");
        ArrayList<Integer> uniqueSubjectIndexes = new ArrayList<>();
        Scanner console = new Scanner(System.in);
        Scanner check = new Scanner(console.nextLine());
        printMessage("");
        while (check.hasNext())
        {
            int subjectNumber = 0;
            if (check.hasNextInt())
            {
                subjectNumber = check.nextInt();
                if (!uniqueSubjectIndexes.contains(subjectNumber) && subjectNumber >= 0 &&
                    subjectNumber <= allSubjects.getSize())
                    uniqueSubjectIndexes.add(subjectNumber);
                else
                {
                    ArrayList<Integer> repeatedOrOutOfRangeIndexes = new ArrayList<>();
                    repeatedOrOutOfRangeIndexes.add(subjectNumber);
                }
            }    
            else
            {
                ArrayList<String> nonInteger = new ArrayList<>();
                nonInteger.add(check.next());
            }
        }
        return uniqueSubjectIndexes;
    }
       
    /**
     * Given a list of integers, check if the list is not empty.
     * 
     * @param   subjectIndexes  The list of integer(s) to be processed.
     * @return  true    If the list is not empty, false otherwise.
     */
    private boolean indexListNotEmpty(ArrayList<Integer> subjectIndexes)
    {
        if (subjectIndexes.size() == 0)
            return false;
        else
            return true;
    }

    /**
     * Initialise the student list after its declaration with the data read from the text file.
     * 
     * @param textFilename  The name of the text file (including its format ".txt") from which 
     *                      the student data are to be read.
     */
    private void initialiseStudentDatabase(String textFilename)
    {
        ArrayList<String> textLines = readFile(textFilename);
        allStudents = createStudentListFromText(textLines);
    }
    
    /**
     * Initialise the subject list after its declaration with the data read from the text file.
     * 
     * @param textFilename  The name of the text file including its format (.txt) from which 
     *                      the subject data are to be read.
     */
    private void initialiseSubjectDatabase(String textFilename)
    {
        ArrayList<String> textLines = readFile(textFilename);
        allSubjects = createSubjectListFromText(textLines);
    }
    
    /**
     * Ask user for input of name that is only alphabetic. Check is performed on the input and the 
     * request for user input loops while the input does not satisfy the requirement. When the 
     * input satisfies the requirement, it returns the string input.
     * 
     * @return  The name input by user.
     */    
    private String inputName()
    {
        String newName = "";
        while (!stringIsAlphabetic(newName))
        {
            newName = stringInput().trim();
            if (!stringIsAlphabetic(newName))
            {
                printMessage("invalidInput");
                System.out.println("Input name must be ONLY alphabetic!");
                System.out.print("\nEnter a valid name: ");
            }
        }
        return newName;
    }
    
    /**
     * Expect to receive user input of integer within a defined range. Loop until user input an 
     * integer which falls within the range.
     * 
     * @param   lowerLimit  The lower limit of the range.
     * @param   upperLimit  The upper limit of the range.
     * @return  The user input within the defined range.
     */
    private int integerInput(int lowerLimit, int upperLimit)
    {
        int input = 0;
        boolean outOfRange = true;
        while (outOfRange)
        {
            Scanner console = new Scanner(System.in);
            try
            {   
                input = console.nextInt();
                if (input < lowerLimit || input > upperLimit)
                    System.out.print("\nInput out of range! Enter a number between " +
                                     lowerLimit + " or " + upperLimit + ": ");
                else
                    outOfRange = false;
            }
            catch (InputMismatchException e)
            {
                System.out.print("\nInput not an integer! Enter an integer again: ");
            }
        }
        return input;
    }
    
    /**
     * Perform set operation intersection of two subject lists.
     * 
     * @param   main    The main list of subjects to be processed.
     * @param   check   The sublist of subjects to be processed.
     * @return  The intersection of the main list and the sublist.
     */
    private SubjectDatabase intersectSubjectDatabases(SubjectDatabase main, SubjectDatabase check)
    {
        SubjectDatabase intersection = new SubjectDatabase();
        SubjectDatabase exception = new SubjectDatabase();
        for (int index1 = 0; index1 < main.getSize(); index1++)
        {
            for (int index2 = 0; index2 < check.getSize(); index2++)
            {
                if (main.getSubject(index1).equals(check.getSubject(index2)))
                    intersection.addSubject(check.getSubject(index2));
            }
        }
        return intersection;
    }
    
    /**
     *  Submenu of "List students". Option (1) lists only the unsuspended students based on 
     *  selected subjects, option (2) lists students by the suspension status, and option (3)
     *  lists all the students enrolled in the school including those who have been suspended. 
     *  The menu loops until option (4) is selected. 
     */    
    private void listStudents()
    {
        boolean stayInMenu = true;
        
        while (stayInMenu)
        {
            displayListStudentsMenu();
            printMessage("chooseOption");
            String option = stringInput();
            System.out.println("");
            switch(option)
            {
                case "1":
                    System.out.println("Please choose subject(s) from the below list by the " + 
                                       "preceding number(s):");
                    System.out.println("(0) Not enrolled in any subjects");
                    allSubjects.displaySubjectDatabaseAsOptions();
                    System.out.println("\nFor filtering by multiple subjects, please separate " + 
                                       "your input numbers with whitespaces.");
                    ArrayList<Integer> subjectFilter = extractOnlyIntegers();
                    if (indexListNotEmpty(subjectFilter))
                    {
                        SubjectDatabase subjectsSelected = 
                            allSubjects.matchSubjectsByIndexes(subjectFilter);
                        if (subjectFilter.get(0) == 0)
                        {   
                            System.out.println("Student(s) yet to enrol in any subjects: ");
                            StudentDatabase unsuspendedStudents = 
                                allStudents.filterStudentsBySuspension(2);
                            StudentDatabase unenrolledStudents = 
                                unsuspendedStudents.filterStudentsNotEnrolled();
                            unenrolledStudents.printStudentDatabase();
                        }
                        else
                        {
                            System.out.println("List of student(s) enrolled in: ");
                            subjectsSelected.displaySubjectDatabaseAsResults();
                            StudentDatabase unsuspendedStudents = 
                                allStudents.filterStudentsBySuspension(2);
                            StudentDatabase studentsListBySubjects = 
                                unsuspendedStudents.filterStudentsBySubjects(subjectsSelected);
                            if (studentsListBySubjects.getSize() == 0)
                            {
                                printMessage("noStudentInCategory");
                                printMessage("");
                            }
                            else
                            {
                                printMessage("");
                                studentsListBySubjects.printStudentDatabase();
                            }
                        }
                    }
                    else
                        printMessage("invalidInput");
                    break;
                    
                case "2":
                    System.out.print("List student(s) by suspension status " + 
                                     ">> (1) Suspended or (2) Unsuspended: ");
                    int userInput = integerInput(1, 2);
                    StudentDatabase studentListBySuspension = 
                        allStudents.filterStudentsBySuspension(userInput);
                    if (studentListBySuspension.getSize() == 0)
                        printMessage("noStudentInCategory");
                    else
                        if (userInput == 1)
                            System.out.println("\nList of suspended student(s):");
                        else
                            System.out.println("\nList of unsuspended student(s):");
                        studentListBySuspension.printStudentDatabase();
                    break;
            
                case "3":
                    if (allStudents.getSize() == 0)
                    {
                        printMessage("noStudentInSchool");
                        printMessage("");
                    }
                    else
                    {
                        System.out.println("All students: ");
                        allStudents.printStudentDatabase();
                    } break;
            
                case "4":
                    stayInMenu = false;
                    break;
            
                default:
                    printMessage("invalidInput");
                    break;
            }
        }
    }

    /**
     *  Submenu of "Manage students" that loops until option (5) is selected. The user may choose 
     *  option (1) to add a new student to the student list, option (2) to delete a student from 
     *  the student, option (3) to enrol/unenrol a student in/from multiple subjects offered at 
     *  the school.
     */   
    private void manageStudents()
    {
        boolean stayInMenu = true;
        
        while (stayInMenu)
        {
            displayManageStudentsMenu();
            printMessage("chooseOption");
            String option = stringInput();
            System.out.println("");
            switch(option)
            {
                case "1":                   
                    System.out.print("Enter name: ");
                    String addName = inputName();
                    System.out.print("\nEnter identity number: ");
                    int addIdentityNumber = integerInput(111, 999);
                    if (allStudents.identityNumberInUse(addIdentityNumber))
                    {
                        Student registeredStudent = 
                            allStudents.filterStudentByIdentityNumber(addIdentityNumber);
                        if (registeredStudent.compareStudentNames(addName))
                        {
                            printMessage("duplicateStudents");
                            printMessage("");
                        }
                        else
                        {
                            printMessage("existingIdentityNumber");
                            printMessage("");
                        }
                    }
                    else
                    {
                        Student newStudent = new Student();
                        newStudent.setName(addName);
                        newStudent.setIdentityNumber(addIdentityNumber);
                        boolean registered = false;
                        while (!registered)
                        {
                            System.out.println("\nWhat subjects to enrol in?");
                            System.out.println("(0) Not to enrol in any subjects for now");
                            allSubjects.displaySubjectDatabaseAsOptions();
                            printMessage("");
                            ArrayList<Integer> subjectFilter = extractOnlyIntegers();
                            SubjectDatabase subjectsSelected = 
                                allSubjects.matchSubjectsByIndexes(subjectFilter);
                            int totalCredit = calculateTotalCredit(subjectsSelected);
                            if (totalCredit < 16)
                            {
                                for (int index = 0; index < subjectsSelected.getSize(); index++)
                                {
                                    newStudent.enrolSubject(subjectsSelected.getSubject(index));
                                }
                                allStudents.addStudent(newStudent);
                                System.out.println("Successfully registered!" +
                                                   "\nName: " + 
                                                   newStudent.getName() + 
                                                   "\nIdentity Number: " + 
                                                   newStudent.getIdentityNumber() +
                                                   "\nEnrolled Subject(s): ");
                                subjectsSelected.displaySubjectDatabaseAsResults();
                                printMessage("");
                                registered = true;
                            }
                            else
                            {
                                System.out.println("Maximum credit exceeded!");
                                System.out.println("\nA student can only enrol in subjects " +
                                                   "with a total credit of not more than 15.");
                            }
                        }
                    } break;

                case "2":
                    if (allStudents.getSize() > 0)
                    {
                        System.out.print("Enter name: ");
                        String deleteName = inputName();
                        System.out.print("\nEnter identity number: ");
                        int deleteIdentityNumber = integerInput(111, 999);
                        if (allStudents.studentOnDatabase(deleteName, deleteIdentityNumber))
                        {
                            StudentDatabase studentsListByName = 
                                allStudents.filterStudentsByName(deleteName);
                            Student selectedStudent = 
                                studentsListByName.
                                filterStudentByIdentityNumber(deleteIdentityNumber);
                            allStudents.removeStudent(selectedStudent);
                            System.out.println("\nMatch found!");
                            System.out.println("\nThe following entry has been successfully " + 
                                               "deleted: ");
                            selectedStudent.displayStudent();
                            printMessage("");
                        }
                        else
                        {
                            printMessage("noStudentMatches");
                            printMessage("");
                        }
                    }
                    else
                    {
                        printMessage("noStudentInSchool");
                        printMessage("");
                    } break;

                case "3":
                    System.out.print("Select an action >> (1) Suspend or (2) Unsuspend: ");
                    int userInput = integerInput(1, 2);
                    if ((userInput == 1 && 
                         allStudents.filterStudentsBySuspension(2).getSize() > 0) ||
                        (userInput == 2 && 
                         allStudents.filterStudentsBySuspension(1).getSize() > 0))
                    {
                        System.out.print("\nEnter name: ");
                        String suspendName = inputName();
                        System.out.print("\nEnter identity number: ");
                        int suspendIdentityNumber = integerInput(111, 999);
                        if (allStudents.studentOnDatabase(suspendName, suspendIdentityNumber))
                        {
                            StudentDatabase studentsListByName = 
                                allStudents.filterStudentsByName(suspendName);
                            Student selectedStudent = 
                                studentsListByName.
                                filterStudentByIdentityNumber(suspendIdentityNumber);
                            String action = "";
                            if (userInput == 1)
                            {
                                selectedStudent.setSuspension("false");
                                selectedStudent.clearSubjectsEnrolled();
                                action = "suspended";
                            }
                            else
                            {
                                selectedStudent.setSuspension("true");
                                action = "unsuspended";
                            }
                            System.out.println("\nMatch found!");
                            System.out.println("\nThe following entry is now " + action + ":");
                            selectedStudent.displayStudent();
                            printMessage("");
                        }
                        else
                        {
                            printMessage("noStudentMatches");
                            printMessage("");
                        }
                    }
                    else
                    {
                        if (userInput == 1)
                        {
                            System.out.println("\nThere is not student to suspend!");
                            printMessage("");
                        }
                        else
                        {
                            System.out.println("\nThere is not student to unsuspend!");
                            printMessage("");
                        }
                    } break;

                case "4":
                    System.out.print("Select an action >> (1) Enrol or (2) Unenrol: ");
                    int enrolUnenrol = integerInput(1, 2);
                    System.out.print("\nEnter name: ");
                    String editByName = inputName();
                    System.out.print("\nEnter identity number: ");
                    int editByIdentityNumber = integerInput(111, 999);
                    if (allStudents.studentOnDatabase(editByName, editByIdentityNumber))
                    {
                        StudentDatabase studentsListByName = 
                            allStudents.filterStudentsByName(editByName);
                        Student selectedStudent = 
                            studentsListByName.
                            filterStudentByIdentityNumber(editByIdentityNumber);
                        SubjectDatabase enrolledSubjects = selectedStudent.getSubjectsEnrolled();
                        if (selectedStudent.getSuspension())
                        {
                            System.out.println("\nMatch found!");
                            printMessage("");
                            selectedStudent.displayStudent();
                            System.out.println("Currently enrolled in: ");
                            if (selectedStudent.getSubjectsEnrolled().getSize() > 0)
                            {
                                enrolledSubjects.displaySubjectDatabaseAsResults();
                                printMessage("");
                            }
                            else
                            {
                                System.out.println("None");
                                printMessage("");
                            }

                            if (enrolUnenrol == 2 && enrolledSubjects.getSize() == 0)
                            {
                                System.out.println("There is no subject to unenrol from!");
                                printMessage("");
                            }
                            else
                            {
                                System.out.println("Choose subject(s) from the below list by " + 
                                                   "the preceding number(s):");
                                System.out.println("(0) Not to enrol/unenrol in any subjects " +
                                                   "for now");
                                allSubjects.displaySubjectDatabaseAsOptions();
                                System.out.println("\nFor adding/removing multiple subjects, " + 
                                                   "separate input numbers with whitespaces.");
                                ArrayList<Integer> subjectFilter = extractOnlyIntegers();
                                SubjectDatabase subjectsSelected = 
                                    allSubjects.matchSubjectsByIndexes(subjectFilter);
                                int totalCredit = calculateTotalCredit(subjectsSelected) + 
                                                  calculateTotalCredit(enrolledSubjects);
                                if (enrolUnenrol == 1)
                                {
                                    if (totalCredit < 16)
                                    {
                                        SubjectDatabase subjectsToEnrol = 
                                            exceptSubjectDatabases(enrolledSubjects, 
                                                                   subjectsSelected);
                                        if (subjectsToEnrol.getSize() > 0)
                                        {
                                            System.out.println("Subject(s) to enrol in: ");
                                            subjectsToEnrol.displaySubjectDatabaseAsResults();
                                            printMessage("");
                                            for (int index = 0; index < subjectsToEnrol.
                                                 getSize(); index++)
                                            {
                                                Subject subjectToEnrol = subjectsToEnrol.
                                                    getSubject(index);
                                                selectedStudent.enrolSubject(subjectToEnrol);
                                            }
                                            selectedStudent.displayStudent();
                                            System.out.println("Now enrolled in: ");
                                            selectedStudent.getSubjectsEnrolled().
                                                displaySubjectDatabaseAsResults();
                                            printMessage("");
                                        }
                                        else
                                        {
                                            System.out.println("No subject to enrol!");
                                            printMessage("");
                                        }
                                    }
                                    else
                                    {
                                        System.out.println("Maximum credit exceeded!");
                                        System.out.println("\nThis student can only enrol in " +
                                                           "subjects with a total credit of " + 
                                                           "not more than " +
                                                           (15 - 
                                                           calculateTotalCredit(enrolledSubjects)) +
                                                           ".");
                                        printMessage("");
                                    }
                                }
                                else
                                {
                                    SubjectDatabase subjectsToUnenrol = 
                                        intersectSubjectDatabases(enrolledSubjects, 
                                        subjectsSelected);
                                    if (subjectsToUnenrol.getSize() > 0)
                                    {
                                        System.out.println("Subject(s) to unenrol from: ");
                                        subjectsToUnenrol.displaySubjectDatabaseAsResults();
                                        printMessage("");
                                        for (int index = 0; index < subjectsToUnenrol.
                                             getSize(); index++)
                                        {
                                            Subject subjectToUnenrol = 
                                                subjectsToUnenrol.getSubject(index);
                                            selectedStudent.unenrolSubject(subjectToUnenrol);
                                        }
                                        selectedStudent.displayStudent();
                                        System.out.println("Now enrolled in: ");
                                        selectedStudent.getSubjectsEnrolled().
                                            displaySubjectDatabaseAsResults();
                                        printMessage("");
                                    }
                                    else
                                    {
                                        System.out.println("No subject to unenrol from!");
                                        printMessage("");
                                    }
                                }
                            }
                        }
                        else
                        {
                            System.out.println("Suspended student cannot be enrolled/unenrolled!");
                            printMessage("");
                        }
                    }
                    else
                    {
                        printMessage("studentNotOnList");
                        printMessage("");
                    } break;
                    
                case "5":
                    stayInMenu = false;
                    break;
            
                default:
                    printMessage("invalidInput");
                    break;
            }
        }
    }
    
    /**
     * Print out various messages.
     * 
     * @param   messageOption   The type of message to be printed out.
     */    
    private void printMessage(String messageOption)
    {
        switch(messageOption)
        {
            case "chooseOption":
                System.out.print("Please choose an option: ");
                break;
            
            case "noStudentInCategory":
                System.out.println("\nThere is no student in this category.");
                break;
            
            case "noStudentInSchool":
                System.out.println("There is no student.");
                break;
            
            case "exit":
                System.out.println("Thank you for using My School Manager!");
                break;
            
            case "":
                System.out.println("");
                break;
                
            case "duplicateStudents":
                System.out.println("\nThis is an existing student!");
                break;    
            
            case "existingIdentityNumber":
                System.out.println("\nThis identity number has already been taken!");
                break;
                
            case "duplicateNames":
                System.out.println("\nThere are more than one student with the same name!");
                break;

            case "studentNotOnList":
                System.out.println("\nThere is no student who matches the name and identity " + 
                                   "number!");
                break;
            
            case "noStudentMatches":
                System.out.println("\nThere is no student who matches!");
                break;    
                
            default:
                System.out.println("Invalid input!\n");
                break;
        }
    }
    
    /**
     * Read a text file line by line, create a list of strings from the read lines, and return the 
     * list.
     * 
     * @param   readFrom    The name of the text file (including its format ".txt") from which
     *                      the data are to be read.
     * @return  The list of lines read from the text file.
     */
    private ArrayList<String> readFile(String readFrom)
    {
        ArrayList<String> textLines = new ArrayList<>();
        String filename = (readFrom);
        try
        {
            FileReader inputFile = new FileReader(filename);
            try
            {
                Scanner parser = new Scanner(inputFile);
                while (parser.hasNextLine())
                {
                    Subject subject = new Subject();
                    String subjectDetails = parser.nextLine();
                    textLines.add(subjectDetails);
                }
            }
            finally
            {
                inputFile.close();
            }
        }
        catch(FileNotFoundException e)
        {
            System.out.println(filename + " not found!");
        }
        catch(IOException e)
        {
            System.out.println("I/O error!");
        }
        return textLines;
    }
        
    /**
     *  Main School Management System menu. Option (1) displays the List students submenu, option 
     *  (2) displays the Manage students submenu, and option (3) prints out the list of all  
     *  students enrolled in the school. The menu loops until option (4) is selected. 
     */    
    public void startManagementSystem()
    {
        boolean stayInMenu = true;
        
        while (stayInMenu)
        {
            displayMainMenu();
            printMessage("chooseOption");
            String option = stringInput();
            System.out.println("");
            switch(option)
            {
                case "1":
                    listStudents();
                    break;
                
                case "2":
                    manageStudents();
                    break;
            
                case "3":
                    System.out.println("All subjects: ");
                    allSubjects.displaySubjectDatabaseAsResults();
                    printMessage("");
                    break;
                
                case "4":
                    writeStudentsFile();
                    printMessage("exit");
                    stayInMenu = false;
                    break;
            
                default:
                    printMessage("invalidInput");
                    break;
            }
        }
    }
    
    /**
     * Receive user input and return the input.
     */ 
    private String stringInput()
    {
        Scanner console = new Scanner(System.in);
        String input = console.nextLine();
        return input;
    }
      
    /**
     * Given a name (a string of words), check if the name is not empty and comprises only 
     * alphabets.
     * 
     * @param   newName The student name to be processed.
     * @return  true    If the name comprises only alphabets, false otherwise or if the name is an
     *                  empty string.
     */    
    private boolean stringIsAlphabetic(String newName)
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
     * Write the data of the list of all students to a text file called "students.txt".
     */
    private void writeStudentsFile()
    {
        String filename = ("students.txt");
        try
        {
            PrintWriter outputFile = new PrintWriter(filename);
            int index1 = 0;
            while (index1 < allStudents.getSize())
            {
                Student student = allStudents.getStudent(index1);
                String name = student.getName();
                int identityNumber = student.getIdentityNumber();
                boolean status = student.getSuspension();
                SubjectDatabase subjectsEnrolled = student.getSubjectsEnrolled();
                outputFile.print(name + "," + identityNumber + "," + status);
                int index2 = 0;
                while (index2 < subjectsEnrolled.getSize())
                {
                    Subject subjectEnrolled = subjectsEnrolled.getSubject(index2);
                    outputFile.print("," + subjectEnrolled.toString());
                    index2++;
                }
                outputFile.println("");
                index1++;
            }
            outputFile.close();
        }
        catch(FileNotFoundException e)
        {
            System.out.println(filename + " not found!");
        }
        catch(IOException e)
        {
            System.out.println("I/O error!");
        }
    }
}