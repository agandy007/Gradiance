import java.util.ArrayList;

public class Question {
	private String questionId;
	private String questionText;
	private String hint;
	private String answerDesc;
	private Integer correctOption;
	private Integer selectedOption;
	private String justification;
	private Integer difficulty = 1;

	
	private ArrayList<Option> optionList;
	
	static Integer id_generator = null;
	// getters setters
	
	public String getQue_id() {
		return questionId;
	}
	public void setQue_id(String que_id) {
		this.questionId = que_id;
	}
	public String getQuestion_text() {
		return questionText;
	}
	public void setQuestion_text(String question_text) {
		this.questionText = question_text;
	}
	public Integer getDifficulty() {
		return difficulty;
	}
	public void setDifficulty(Integer difficulty) {
		this.difficulty = difficulty;
	}
	public String getHint() {
		return hint;
	}
	public void setHint(String hint) {
		this.hint = hint;
	}
	public String getAnswer_desc() {
		return answerDesc;
	}
	public void setAnswer_desc(String answer_desc) {
		this.answerDesc = answer_desc;
	}
	
	public void generateQuestionId()
	{
		if(id_generator == null)
			id_generator = 1;
		else
			id_generator++;
		
		this.setQue_id("Q"+id_generator);
					
	}

}
