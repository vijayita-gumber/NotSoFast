package pcsma.database.connection;




public class QuizMarks 
{
	private String id;
	
	
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	private Integer quizId;
	private String rollNo;
	private String email;
	private String subject;
	private Integer marks;
	
	public Integer getMarks() {
		return marks;
	}
	public void setMarks(Integer marks) {
		this.marks = marks;
	}
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
	
	public String toString(){
		return this.getMarks()+" "+this.getQuizId();
	}
	
	
	
	
	
	
}