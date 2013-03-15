import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream.GetField;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Iterator;
import java.sql.ResultSet;

public class Gradiance {
	
	static String userInput = null;
	static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	static Professor professor = null;
	static Gradiance gradience = null;
	
	public static void main(String[] args) throws ClassNotFoundException, SQLException, IOException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, InstantiationException, NoSuchMethodException
	{
		
		Gradiance.initializeDatabase();	
		System.out.println("WELCOME TO GRADIANCE");
		
		// LOGIN if Professor:
		String id = "404";
		String name = "Ogan";
		Gradiance.professor = new Professor(id, name);
		Gradiance.getProfessorMenu(); 
		
	}
	
	public static void initializeDatabase() throws SQLException, ClassNotFoundException
	{
		DataAccess dataAccess = DataAccess.getInstance();
		//Class.forName("DataAccess");
	}
	
	public static void getProfessorMenu() throws IOException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException
	{
		System.out.println("1.Select Course \n2.Add Course \n3.Back");
		Gradiance.userInput = Gradiance.br.readLine();
		// TODO handle back option
		try {
			
			Method method = Gradiance.class.getMethod("getMenu1"+Gradiance.userInput, null);
			method.invoke(gradience, null);
			
		} catch (NoSuchMethodException e) {
			System.out.println("Invalid Choice. Re-enter:");
			Gradiance.getFirstMenu();
			
		}	
	}
	
	public static void getMenu11() throws SQLException, IOException // select course
, IllegalAccessException, IllegalArgumentException, InvocationTargetException
	{
		ArrayList<Course> courses = new ArrayList<Course>();
		courses = professor.getCourses();
		Course course = null;
		int number = 1;
		Iterator iterator = courses.iterator();
		
		while(iterator.hasNext())
		{
			course = (Course) iterator.next();
			System.out.println(number+". "+course.getCourseName());
			number++;
		}
			System.out.println(number+". Back");
			Gradiance.userInput = Gradiance.br.readLine();
			
		//courses.get(number-1);
			
			Gradiance.getCourseMenu(courses.get(Integer.parseInt(Gradiance.userInput)-1));	
		
		
		
	}
	
	public static void getMenu12() throws IOException	//add course
, SQLException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException
	{
		Course course = null;
		try {
			course = new Course(userInput, userInput, userInput);
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("Enter Course ID:");
		Gradiance.userInput = Gradiance.br.readLine();
		course.setCourseId(Gradiance.userInput);
		
		System.out.println("Enter Course name:");
		Gradiance.userInput = Gradiance.br.readLine();
		course.setCourseName(Gradiance.userInput);
		
		System.out.println("Enter Semester:");
		Gradiance.userInput = Gradiance.br.readLine();
		
		professor.addCourse(course, Gradiance.userInput);
		
		System.out.println("Course added successfully \n");
		Gradiance.getFirstMenu();
		
	}
	
	public static void getMenu13()	//back
	{
		// TODO back to login?
	}
	
	public static void getCourseMenu(Course course) throws IOException, IllegalAccessException, IllegalArgumentException, InvocationTargetException
	{
		
		System.out.println("For "+course.getCourseId());
		System.out.println(" 1. Add Homework \n 2. Edit Homework \n 3. Add question \n 4. Add answer \n 5. Report \n 6. Back ");
		
		Gradiance.userInput = Gradiance.br.readLine();
		// TODO handle back option
		try {
			
			Method method = Gradiance.class.getMethod("getMenu2"+Gradiance.userInput,Course.class);
			method.invoke(gradience, course);
			
		} catch (NoSuchMethodException e) {
			System.out.println("Invalid Choice. Re-enter:");
			Gradiance.getCourseMenu(course);	
		}
		
	}
	
	public static void getMenu21(Course course) throws IOException // Add homework
, ParseException, SQLException, IllegalAccessException, IllegalArgumentException, InvocationTargetException
	{
		Assessment homework = null;
		try {
			homework = new Assessment();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("For the new Homework - ");
		System.out.println("Enter start date:");
		Gradiance.userInput = Gradiance.br.readLine();
		homework.setStart_date(Gradiance.userInput);
		
		System.out.println("Enter end date:");
		Gradiance.userInput = Gradiance.br.readLine();
		homework.setEndDate(Gradiance.userInput);
		
		System.out.println("Enter number of attempts:");
		Gradiance.userInput = Gradiance.br.readLine();
		homework.setRetries(Integer.parseInt(Gradiance.userInput));
		
		System.out.println("Enter score selection scheme:");
		Gradiance.userInput = Gradiance.br.readLine();
		homework.setScoreSelectionMethod(Integer.parseInt(Gradiance.userInput));
		
		System.out.println("Enter correct answer points:");
		Gradiance.userInput = Gradiance.br.readLine();
		homework.setPointsForCorrAns(Integer.parseInt(Gradiance.userInput));
		
		System.out.println("Enter incorrect answer points deduction:");
		Gradiance.userInput = Gradiance.br.readLine();
		homework.setPointsForWrongAns(Integer.parseInt(Gradiance.userInput));
		
		System.out.println("Enter current semester:");
		Gradiance.userInput = Gradiance.br.readLine();
		String sem = new String(Gradiance.userInput);
		
		homework.generateAssessmentId();
		professor.addHomework(homework, course.getCourseId(), sem);
		
		System.out.println("Enter question numbers. Enter -1 to end: ");
		Gradiance.userInput = Gradiance.br.readLine();
		while(Gradiance.userInput!= "-1")
		{
			professor.addAssessmentQuestion(homework.getAssessmentId(), Gradiance.userInput);
			Gradiance.userInput = Gradiance.br.readLine();
		}
		
		Gradiance.getCourseMenu(course);
		
	}
	
	public static void getMenu22(Course course) // Edit Homework
	{
		System.out.println("Select a Homework to edit");
		
	}
	
	public static void getMenu23(Course course) throws SQLException // Add question
, IOException, IllegalAccessException, IllegalArgumentException, InvocationTargetException
	{
		System.out.println("Select a topicID to add question to:");
		
		Question question = new Question();
		Option option = new Option();
		ArrayList<Option> topics = new ArrayList<Option>();
		topics = professor.getTopics(course);
		Option topic = null;
		int number = 1;
		Iterator iterator = topics.iterator();
		
		while(iterator.hasNext())
		{
			topic = (Option) iterator.next();
			System.out.println(number+". "+topic.getCourseName());
			number++;
		}
			System.out.println(number+". Back");
			
			Gradiance.userInput = Gradiance.br.readLine();
			if(Integer.parseInt(Gradiance.userInput)==number)
			{
				Gradiance.getCourseMenu(course);
			}
			else
			{
				topic = topics.get(Integer.parseInt(Gradiance.userInput) - 1);
				question = Gradiance.frameQuestion();
				professor.addQuestion(topic.getTopic_id(), question);
				
				System.out.println("Enter one correct answer:");
				option = Gradiance.frameOption(question.getQue_id());
				professor.addAnswer(question.getQue_id(), option, 'y');
				
				for(number = 1; number < 4; number++)
				{
					System.out.println("Enter incorrect answer:");
					option = Gradiance.frameOption(question.getQue_id());
					professor.addAnswer(question.getQue_id(), option, 'n');
				}
				
				Gradiance.getMenu23(course);
			}
			
		
	}
	
	public static void getMenu24(Course course) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, IOException // Add answer
, SQLException
	{
		System.out.println("Select a question");
		
		Question question = new Question();
		Option option = new Option();
		ArrayList<Question> questions = new ArrayList<Question>();
		questions = professor.getQuestions();
		int number = 1;
		Iterator iterator = questions.iterator();
		char c;
		while(iterator.hasNext())
		{
			question = (Question) iterator.next();
			System.out.println(number+". "+question.getQue_id());
			number++;
		}
			System.out.println(number+". Back");
			
			Gradiance.userInput = Gradiance.br.readLine();
			if(Integer.parseInt(Gradiance.userInput)==number)
			{
				Gradiance.getCourseMenu(course);
			}
			else
			{
				question = questions.get(Integer.parseInt(Gradiance.userInput) -1);
				
				System.out.println("Enter Answer type: \n 1. Correct \n 2. Incorrect");
				Gradiance.userInput = Gradiance.br.readLine();
				int is_correct = Integer.parseInt(Gradiance.userInput);
				if(is_correct == 1)
				{
					c = 'y';
				}
				else
				{
					c = 'n';
				}
				
				option = Gradiance.frameOption(question.getQue_id());
				
				professor.addAnswer(question.getQue_id(), option, c);
				Gradiance.getMenu24(course);
			}
	}
	
	public static void getMenu25(Course course) throws IOException // Report
, SQLException, IllegalAccessException, IllegalArgumentException, InvocationTargetException
	{
		ResultSet rs = null; 
		System.out.println("Enter your Query: ");
		Gradiance.userInput = Gradiance.br.readLine();
		rs = professor.executeUserQuery(Gradiance.userInput);
		java.sql.ResultSetMetaData rsmd = rs.getMetaData();
		int NumCols = rsmd.getColumnCount();
		int i;
		while(rs.next())
		{
			for(i=0; i < NumCols; i++)
			{
			System.out.print(rs.getString(i)+"\t");
			}
			System.out.println("");
		}
		
		Gradiance.getCourseMenu(course);
	}
	
	
	public static void getMenu26(Course course) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, SQLException, IOException  // Back
	{
		Gradiance.getMenu11();
	}
	
	public static Question frameQuestion() throws IOException
	{
		Question question = new Question();
		question.generateQuestionId();

		System.out.println("Enter question text:");
		Gradiance.userInput = Gradiance.br.readLine();
		question.setQuestion_text(Gradiance.userInput);
		
		return question;
				
	}
	
	public static Option frameOption(String que_id) throws IOException
	{
		Option option = new Option();
		option.generateOptionId();
		
		System.out.println("Enter answer text:");
		Gradiance.userInput = Gradiance.br.readLine();
		option.setOption_text(Gradiance.userInput);
		
		return option;
	}
}
