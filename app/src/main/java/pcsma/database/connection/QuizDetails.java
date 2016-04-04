package pcsma.database.connection;





//@Document(collection = "quizdetails")
public class QuizDetails 
{
	//@Id
	private String id;
	
	//@Column(unique=true)
	private Integer quizId;
	public String getAnswer() {
		return answer;
	}

	public void setAnswer(String answer) {
		this.answer = answer;
	}

	private Long creationTime;
	private Long endingTime;
	private Integer maxTime;
	private String question;
	private String subject;
	private String answer;
	
	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
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

	public Long getCreationTime() {
		return creationTime;
	}

	public void setCreationTime(Long creationTime) {
		this.creationTime = creationTime;
	}

	public Long getEndingTime() {
		return endingTime;
	}

	public void setEndingTime(Long endingTime) {
		this.endingTime = endingTime;
	}

	public Integer getMaxTime() {
		return maxTime;
	}

	public void setMaxTime(Integer maxTime) {
		this.maxTime = maxTime;
	}

	public String getQuestion() {
		return question;
	}

	public void setQuestion(String question) {
		this.question = question;
	}

	public QuizDetails(){
		
	}
	
	public String toString()
	{
	   StringBuilder builder = new StringBuilder();
	   builder.append(this.getQuestion())
		      .append(", ")
		      .append(this.getQuizId())
		      .append(", ")
		      .append(this.getMaxTime())
		      .append(", ")
		      .append(this.getSubject())
		      .append(", ")
		      .append(this.getEndingTime())
		      .append(", ")
		      .append(this.getCreationTime());
		      //.append(", ")
		      //.append(this.getSubjects().toString());
	   
	   return builder.toString();
	}
	
	
}
