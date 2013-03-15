import java.sql.SQLException;
import java.util.ArrayList;


public class Course {
	private String courseId;
	private String semester;
	private String courseToken;
	private String courseName;
	private DataAccess dataAccess;
	
	public class Topic {
		String topicId = null;
		String name = null;
	}
	
	public String getCourseId() {
		return courseId;
	}
	public void setCourseId(String course_id) {
		this.courseId = course_id;
	}
	public String getCourseName() {
		return courseName;
	}
	public void setCourseName(String name) {
		this.courseName = name;
	}

	
	Course (String token, String name, String sem) throws ClassNotFoundException, SQLException {
		semester = sem;
		courseToken = token;
		courseName = name;
		dataAccess = DataAccess.getInstance();
	}
	public void Enroll (Student s) {
		dataAccess.EnrollStudent(s,this);
	}
	
	public ArrayList<Assessment> getAttemptedHomeworks(Student s) {
		return dataAccess.getAttemptedHomeworks(s,this);
		
	}
	
	public ArrayList<Assessment> getOpenHomeworks(Student s) {
		return dataAccess.getOpenHomeworks(s,this);
		
	}
	public String getSemester() {
		return semester;
	}
	public String getCourseToken() {
		return courseToken;
	}
		
}
