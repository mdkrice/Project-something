import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * 
 * Student Center abstraction for our simulation. Execution starts here.
 * 
 * @author CS367
 *
 */
public class StudentCenter
	{

	private static int DEFAULT_POINTS = 100;
	// Global lists of all courses and students
	private static List<Course> courseList = new ArrayList<Course>();
	private static List<Student> studentList = new ArrayList<Student>();

	public static void main(String[] args)
		{
		if(args.length != 3)
			{
			System.err.println("Bad invocation! Correct usage: " + "java StudentCentre <StudentCourseData file>" + "<CourseRosters File> + <StudentCourseAssignments File>");
			System.exit(1);
			}

		boolean didInitialize = readData(args[0]);

		if(!didInitialize)
			{
			System.err.println("Failed to initialize the application!");
			System.exit(1);
			}

		generateAndWriteResults(args[1], args[2]);
		}

	/**
	 * 
	 * @param fileName
	 *            - input file. Has 3 sections - #Points/Student, #Courses, and
	 *            multiple #Student sections. See P3 page for more details.
	 * @return true on success, false on failure.
	 * 
	 */
	public static boolean readData(String fileName) {
		try	{
			// TODO parse the input file as described in the P3 specification.
			// make sure to call course.addStudent() appropriately.
			File inputFile = new File(fileName);
			Scanner scan = new Scanner(inputFile);
			int pointsPerStudent = 0;
			String nextLine = scan.nextLine();
//			while(pointsPerStudent == 0)	{
//				nextLine = scan.nextLine();
//				if(nextLine.contains("#"))	{
//					pointsPerStudent = new Integer(scan.next().trim());
//				}
//			}
			
			pointsPerStudent = scan.nextInt();
			
			while(!nextLine.contains("#Courses"))	{
				nextLine = scan.nextLine();
			}
			
			nextLine = scan.nextLine();
			while(!nextLine.contains("#Student"))	{
				String[] courseInfo = nextLine.split(" ");
				courseList.add(new Course(courseInfo[0].trim(), courseInfo[1].trim(), 
						new Integer(courseInfo[2].trim())));
				nextLine = scan.nextLine();
			}
			
			//nextLine = scan.nextLine();
			
			while(scan.hasNext())	{
				StudentCenter.createStudent(scan, pointsPerStudent);
			}
		}
		catch(Exception e)	{
			e.printStackTrace();
			System.out.println("File Parse Error");
			return false;
		}
		return true;
	}
	
	private static void createStudent(Scanner scan, int pointsPerStudent)	{
		String name = scan.nextLine().trim();
		if(name.equals(""))	{
			name = scan.nextLine().trim();
		}
		String id = scan.nextLine().trim();
		String nextLine = "";
		Student studentToAdd = new Student(name, id, pointsPerStudent);
		StudentCenter.studentList.add(studentToAdd);
		while(scan.hasNext())	{
			String courseID = scan.next().trim();
			if(courseID.contains("#"))	{
				return;
			}
			int coinsForClass = new Integer(scan.next().trim());
			scan.nextLine();
			Course courseToAddTo = StudentCenter.getCourseFromCourseList(courseID);
			if(studentToAdd.getPoints() >= coinsForClass)	{
				courseToAddTo.addStudent(studentToAdd, coinsForClass);
				studentToAdd.deductCoins(coinsForClass);
			}
			//nextLine = scan.nextLine();
			
		}
		
	}

	/**
	 * 
	 * @param fileName1
	 *            - output file with a line for each course
	 * @param fileName2
	 *            - output file with a line for each student
	 */
	public static void generateAndWriteResults(String fileName1, String fileName2)
		{
		try
			{
			// List Students enrolled in each course
			PrintWriter writer = new PrintWriter(new File(fileName1));
			for (Course c : courseList)
				{
				writer.println("-----" + c.getCourseCode() + " " + c.getCourseName() + "-----");

				// Core functionality
				c.processRegistrationList();

				// List students enrolled in each course
				int count = 1;
				for (Student s : c.getCourseRegister())
					{
					writer.println(count + ". " + s.getid() + "\t" + s.getName());
					s.enrollCourse(c);
					count++;
					}
				writer.println();
				}
			writer.close();

			// List courses each student gets
			writer = new PrintWriter(new File(fileName2));
			for (Student s : studentList)
				{
				writer.println("-----" + s.getid() + " " + s.getName() + "-----");
				int count = 1;
				for (Course c : s.getEnrolledCourses())
					{
					writer.println(count + ". " + c.getCourseCode() + "\t" + c.getCourseName());
					count++;
					}
				writer.println();
				}
			writer.close();
			}
		catch(FileNotFoundException e)
			{
			e.printStackTrace();
			}
		}

	/**
	 * Look up Course from classCode
	 * 
	 * @param courseCode
	 * @return Course object
	 */
	private static Course getCourseFromCourseList(String courseCode)
		{
		for (Course c : courseList)
			{
			if(c.getCourseCode().equals(courseCode))
				{
				return c;
				}
			}
		return null;
		}
	}