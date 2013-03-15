import java.io.IOException;
import java.util.HashMap;

public class Student extends User {
	private String student_id;
	private DataAccess dataAccess;

	public HashMap<String, Course> getCourseList(){
		return dataAccess.getCourseListFromDB(this);
	}
	
	public Course addCourse(HashMap<String,Course> courseList) {
		String token = null;
		System.out.print("Enter the course token: ");
		try {
			token = ProjectUtils.ReadLine();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Course c = courseList.get(token);
		c.Enroll(this);
		return c;
	}

	public String getStudentId() {
		return student_id;
	}
}
