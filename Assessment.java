import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class Assessment {
	
	private String assessmentId = null;
	private Integer retries = null;
	private Date startDate, endDate;
	private Integer pointsForCorrAns;
	private Integer pointsForWrongAns;
	private Integer seed;
	private Integer currentScore;
	private ArrayList<Attempt> attemptList;
	private Attempt incompleteAttempt;
	private Integer scoreSelectionMethod;
	
	private DataAccess dataAccess;
	
	static Integer id_generator = null;
	
	public Assessment() throws ClassNotFoundException, SQLException {
		 dataAccess = DataAccess.getInstance();
	}
	
	// getter setters
	
	public String getAssessmentId() {
		return assessmentId;
	}
	public void setAssessmentId(String assessment_id) {
		this.assessmentId = assessment_id;
	}
	public Integer getRetries() {
		return retries;
	}
	public void setRetries(Integer retries) {
		this.retries = retries;
	}
	public Date getStartDate() {
		return startDate;
	}
	public void setStart_date(String start_date) throws ParseException {
		
		DateFormat formatter = new SimpleDateFormat("DD-MM-YYYY");
		Date date ;
		date = (Date)formatter.parse(start_date);
		this.startDate = date;
	}
	
	public Date getEndDate() {
		return endDate;
	}
	
	public void setEndDate(String end_date) throws ParseException {
		
		DateFormat formatter = new SimpleDateFormat("DD-MM-YYYY");
		Date date ;
		date = (Date)formatter.parse(end_date);
		this.endDate = date;
	}
	
	public Integer getPointsForCorrAns() {
		return pointsForCorrAns;
	}
	
	public void setPointsForCorrAns(Integer points_for_corr_ans) {
		this.pointsForCorrAns = points_for_corr_ans;
	}
	
	public Integer getPointsForWrongAns() {
		return pointsForWrongAns;
	}
	
	public void setPointsForWrongAns(Integer points_for_wrong_ans) {
		this.pointsForWrongAns = points_for_wrong_ans;
	}
	
	public Integer getSeed() {
		return seed;
	}
	public void setSeed(Integer seed) {
		this.seed = seed;
	}
	public Integer getScoreSelectionMethod() {
		return scoreSelectionMethod;
	}
	public void setScoreSelectionMethod(Integer score_selection_method) {
		this.scoreSelectionMethod = score_selection_method;
	}

	public void generateAssessmentId()
	{
		if(id_generator == null)
			id_generator = 1;
		else
			id_generator++;
		
		this.setAssessmentId("HW"+id_generator);
			
			
	}
		
	int getScore(Student s) {
		return dataAccess.getScore(s,this);
	}
	
	void AttemptHomework() {
	}
	
	public ArrayList<Question> getQuestionList(Student s) {
		return dataAccess.getQuestionList(s,this);
	}
}
