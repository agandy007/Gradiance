import java.sql.*;
import java.util.ArrayList;
import java.util.Iterator;


public class Professor extends User{

	String id;
	String name = null;
	DataAccess dataAccess = null;
	//TODO How to set prof_id?
	
	public Professor(String id, String name) throws ClassNotFoundException, SQLException {
		
		this.setId(id);
		this.setName(name);
		dataAccess = DataAccess.getInstance();
	}
	
	// Getters and Setters
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public ArrayList<Course> getCourses() throws SQLException 
	{
		return dataAccess.getSections(this.getId());
	}

	public void addCourse(Course course, String sem) throws SQLException
	{
		dataAccess.addCourse(course, sem, this.getId());
	}
	
	public void addHomework(Assessment assignment, String course_id, String semester) throws SQLException
	{
		dataAccess.addAssesment(assignment, course_id, semester);
	}
	
	public void editHomework(String assessment_id, String attribute, String attr_value) throws SQLException
	{
			dataAccess.editAssessment(assessment_id, attribute, attr_value);
	}
	
	public void addAssessmentQuestion(String assessment_id, String question_no) throws SQLException {
		
		dataAccess.addAssessmentQuestion(assessment_id, question_no);
	}
	
	public void editAssessmentQuestion(Integer assessment_id, Integer que_id_old, Integer que_id_new) throws SQLException
	{
		dataAccess.editAssessmentQuestion(assessment_id, que_id_old, que_id_new);
	}
	
	public void addQuestion(String topic_id, Question question) throws SQLException
	{
		dataAccess.addQuestion(topic_id, question);
	}
	
	public void addAnswer(String que_id, Option option, char is_correct) throws SQLException
	{
		dataAccess.addOption(que_id, option, is_correct);
	}
	
	public ResultSet executeUserQuery(String query) throws SQLException 
	{
		return dataAccess.executeUserQuery(query);
	}
	
	public ArrayList<Option> getTopics(Course course) throws SQLException
	{
		return dataAccess.getTopics(course);
	}
	
	public ArrayList<Question> getQuestions() throws SQLException
	{
		return dataAccess.getQuestions();
	}
	
	
}
