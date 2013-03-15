
public class Option {
	
	int optionNum;
	boolean isCorrect;
	String optionText;
	String optionDesc;
	
	Integer option_id = null;
	Integer id_generator = null;
	
	// getters setters
	
	public Integer getOption_id() {
		return option_id;
	}
	public void setOption_id(Integer option_id) {
		this.option_id = option_id;
	}
	public String getOption_text() {
		return optionText;
	}
	public void setOption_text(String option_text) {
		this.optionText = option_text;
	}
	public String getOption_desc() {
		return optionDesc;
	}
	public void setOption_desc(String option_desc) {
		this.optionDesc = option_desc;
	}
	
	public void generateOptionId()
	{
		if(id_generator == null)
			id_generator = 1;
		else
			id_generator++;
		
		this.setOption_id(id_generator);
			
			
	}
	
	

}
