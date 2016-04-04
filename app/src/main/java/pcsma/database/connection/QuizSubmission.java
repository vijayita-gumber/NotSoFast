package pcsma.database.connection;



//@Document(collection = "quizsubmission")
public class QuizSubmission 
{
	private String id;
	
	
	private Integer quizId;
	private String rollNo;
	private String email;
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	private String subject;
	private String option;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	
	public Integer getQuizId() {
		return quizId;
	}
	public void setQuizId(Integer quizId) {
		this.quizId = quizId;
	}
	public String getRollNo() {
		return rollNo;
	}
	public void setRollNo(String rollNo) {
		this.rollNo = rollNo;
	}
	public String getSubject() {
		return subject;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}
	public String getOption() {
		return option;
	}
	public void setOption(String option) {
		this.option = option;
	}
	
	public String toString(){
		return this.getEmail()+" "+this.getQuizId()+" "+this.getSubject();
	}
	
	
	
	
	
	
	
	
}