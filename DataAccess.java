import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;

public class DataAccess {
	
	private Connection conn = null;
	private Statement stmt = null;
	private String sql = null;
	private ResultSet rs = null;
	
	private final String jdbcURL = "jdbc:mysql://localhost/test";
	private String user = "apurva";	// For example, "jsmith"
	private String passwd = "me@gemini268";	// Your 9 digit student ID number
	
	private static DataAccess instance = null;
	
	public static DataAccess getInstance() throws ClassNotFoundException, SQLException
	{
		if(instance == null)
		{
			instance = new DataAccess();
		}
		return instance;
	}
	
	protected DataAccess() throws ClassNotFoundException, SQLException {
		// TODO Auto-generated constructor stub
		
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection(jdbcURL, user, passwd);
			stmt = conn.createStatement();
	}
	
	public ArrayList<Course> getSections(String id) throws SQLException, ClassNotFoundException
	{
		ArrayList<Course> courses = new ArrayList<Course>();
		sql = "SELECT * FROM COURSE WHERE course_id in (SELECT course_id FROM SECTION WHERE semester = 'fall12' AND prof_id = \'"+id+"\');";
		rs = stmt.executeQuery(sql);
		while(rs.next())
		{
			Course c = new Course(id, id, id);
			c.setCourseId(rs.getString("course_id"));
			c.setCourseName(rs.getString("name"));
			courses.add(c);
		}
		
		return courses;
	}
	
	public void addCourse(Course course, String sem, String prof_id) throws SQLException
	{
		sql = "INSERT INTO COURSE VALUES(\'"+course.getCourseId()+"\', \'"+course.getCourseName()+"\');";
		System.out.println(sql);
		stmt.executeUpdate(sql);
		
		sql = "INSERT INTO SECTION VALUES(\'"+course.getCourseId()+sem+"\', "+"\'"+course.getCourseId()+"\', \'"+ sem + "\', " +"\'"+ prof_id+"\')";
		System.out.println(sql);
		stmt.executeUpdate(sql);
	}
	
	public void addAssesment(Assessment assessment, String course_id, String semester) throws SQLException
	{
		// ASSESSMENT AND SECTION_ASSESSMENT
		sql = "INSERT INTO ASSESSMENT VALUES("+ 
				assessment.getAssessmentId()+", "+
				assessment.getRetries()+", "+
				assessment.getStartDate()+", "+
				assessment.getEndDate()+", "+
				assessment.getPointsForCorrAns()+", "+
				assessment.getPointsForWrongAns()+", "+
				assessment.getSeed()+", "+
				assessment.getScoreSelectionMethod()+")";
		
		stmt.executeUpdate(sql);
		
		sql = "INSERT INTO section_assesment VALUES(\'"+assessment.getAssessmentId()+
				"\', \'"+course_id+"\', \'"+semester+"\')";
		
		 stmt.executeUpdate(sql);
	}
	
	public void editAssessment(String assessment_id, String attribute, String attr_value) throws SQLException
	{
		// TODO date as string. Rest as integer
		if(attribute.contains("date"))
		{
			sql = "UPDATE ASSESSMENT SET "+attribute+" = \'"+attr_value+
					"\' WHERE "+"assessment_id = \'"+assessment_id+"\';";
		}
		else
		{
			sql = "UPDATE ASSESSMENT SET "+attribute+" = "+attr_value+
				" WHERE "+"assessment_id = \'"+assessment_id+"\'";
		}
		stmt.executeUpdate(sql);
		
	}
	
	// from here
	
	public void addAssessmentQuestion(String assessment_id, String que_id) throws SQLException
	{
		sql = "INSERT INTO assessment_ques VALUES("+que_id+", "+assessment_id+")";
		
		stmt.executeUpdate(sql);
	}
	
	public void editAssessmentQuestion(Integer assessment_id, Integer que_id_old, Integer que_id_new) throws SQLException
	{
		sql = "UPDATE assessment_ques SET que_id = "+que_id_new+
				" WHERE assessment_id = "+assessment_id+" AND que_id = "+que_id_old;
		
		stmt.executeUpdate(sql);
	}
	
	public void addQuestion(String topic_id, Question question) throws SQLException
	{
		// TABLE QUESTIONS AND TOPIC-QUESTION
		sql = "INSERT INTO QUESTIONS VALUES( "+question.getQue_id()+", "+
				question.getQuestion_text()+", "+ question.getDifficulty()+", "+
				question.getHint()+", "+question.getAnswer_desc()+")";
		
		stmt.executeUpdate(sql);
		
		sql = "INSERT INTO topic_questions VALUES( "+ question.getQue_id()+", "+
				topic_id+")";
		stmt.executeUpdate(sql);
	}
	
	public void addOption(String que_id, Option option, char is_correct) throws SQLException
	{
		// TABLE OPTIONS QUESTION_OPTION
		
		sql = "INSERT INTO options VALUES( "+option.getOption_id()+", "+
				option.getOption_text()+", "+option.getOption_desc()+")";
		
		stmt.executeUpdate(sql);
		
		sql = "INSERT INTO question_option VALUES( "+que_id+", "+option.getOption_id()+", "+
				is_correct+")";
		
		stmt.executeUpdate(sql);
		
	} 
	
	public ResultSet executeUserQuery(String query) throws SQLException
	{
		rs = stmt.executeQuery(query);
		return rs;
	}
	
	public ArrayList<Topic> getTopics(Course course) throws SQLException
	{
		ArrayList<Topic> topics = new ArrayList<Topic>();
		sql = "SELECT * FROM TOPIC WHERE topic_id in " +
				"(SELECT topic_id FROM course_content WHERE course_id = "+course.getCourseId()+");";
		rs = stmt.executeQuery(sql);
		while(rs.next())
		{
			Topic t = new Topic();
			t.setTopic_id(rs.getString("topic_id"));
			t.setName(rs.getString("name"));
			topics.add(t);
		}
		
		return topics;
	}
	
	public ArrayList<Question> getQuestions() throws SQLException
	{
		ArrayList<Question> questions = new ArrayList<Question>();
		sql = "SELECT * FROM QUESTION;";
		rs = stmt.executeQuery(sql);
		
		while(rs.next())
		{
			Question q = new Question();
			q.setQue_id(rs.getString("que_id"));
			q.setQuestion_text(rs.getString("question_text"));
			q.setDifficulty(rs.getInt("difficulty"));
			q.setHint(rs.getString("hint"));
			q.setAnswer_desc(rs.getString("answer_desc"));
		}
		
		return questions;
	}

	
	//partition

	
	public HashMap<String,Course> getCourseListFromDB(Student s) throws ClassNotFoundException, SQLException {
		String student_id = s.getStudentId();
		Course c;
		String course_token = null;
		String course_name = null;
		String course_id = null;
		//TODO modify the query to include semester
		String query = "select course_token, name from course" +
				"where course_token in " +
				"(select course_token from student_enrollment " +
				"where student_id like '%" + student_id + "%' and semester in " +
						"(select semester_id from semester " +
						"where start_date >= SYSDATE and end_date <= SYSDATE))";
		
		HashMap<String,Course> courseList = new HashMap<String,Course>();
		while(true) {
			c = new Course(course_token,course_name, course_id);
			courseList.put(course_token, c);
		}
		//return courseList;
	}

	public void EnrollStudent(Student s, Course course) {
		String query = "insert into student_enrollment values('" +
				s.getStudentId() + "','" + course.getCourseToken() + "','" +
				course.getSemester() + "')";
		//execute query
	}

	public ArrayList<Assessment> getAttemptedAssessments(Student s,
			Course course) throws ClassNotFoundException, SQLException {
		String assessment_id = null;
		
		String query = "select assessment_id, retries, start_date, end_date, seed, " +
				"points_for_corr_ans, points_for_wrong_ans, score_selection_method from assessment where" +
				"assessment_id in (select assessment_id from attempt" +
				"where student_id like '" + s.getStudentId() + "' and time_of_completion is not null" +
				"and assessment_id in (select assessment_id from section_assessment" +
				"where course_token = '" + course.getCourseToken() + "' and semester = '" + course.getSemester() + "'))";
		
		Assessment a = new Assessment();
		ArrayList<Assessment> assessList = null;
		
		String query1 = "attempt_id, attempt_num, total_points from attempt" +
				"where student_id like '" + s.getStudentId() + "' and assessment_id like '%" + assessment_id + "%'";
				
		return assessList;
	}

	public ArrayList<Assessment> getOpenAssessments(Student s,
			Course course) {
		String assessment_id = null;
		String query = "select assessment_id, retries, start_date, end_date, seed," +
				"points_for_corr_ans, points_for_wrong_ans, score_selection_method from assessment " +
				"where start_date >= SYSDATE and end_date <= SYSDATE " +
				"and assessment_id in (select assessment_id from section_assessment" +
				"where course_token = '" + course.getCourseToken() + "')";

		String query1 = "attempt_id, attempt_num, total_points from attempt" +
				"where student_id like '" + s.getStudentId() + "' and assessment_id like '%" + assessment_id + "%'";

		
		return null;
	}

	public ArrayList<Question> getQuestionList(Student s,
			Assessment assessment) {
		//query to get the questions.
		ArrayList<Question> queList = null;
		Question q = null;
		constructQuestion(q);
		queList.add(q);
		return queList;
	}

	private Question constructQuestion(Question q) {
		//query to select options
		//add the options to the questions
		return q;	
	}

	public int getScore(Student s, Assessment assessment) {
		// TODO Auto-generated method stub
		return 0;
	}

	public ArrayList<Assessment> getAttemptedHomeworks(Student s, Course course) {
		// TODO Auto-generated method stub
		return null;
	}

	public ArrayList<Assessment> getOpenHomeworks(Student s, Course course) {
		// TODO Auto-generated method stub
		return null;
	}
	
}

