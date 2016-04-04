package pcsma.database.connection;


import java.util.ArrayList;
import java.util.List;


import retrofit.http.Body;
import retrofit.http.GET;
import retrofit.http.POST;
import retrofit.http.Path;
import retrofit.http.Query;

public interface QuizSvc {
	@POST("/submitquiz")
	public QuizSubmission submitQuiz(@Body QuizSubmission quizsub);
	
	@GET("/activequiz")
	public QuizDetails greetingForm(@Query("email") String email);
	
	@GET("/getmarks")
	public ArrayList<QuizMarks> getMarks(@Query("subject") String subject
			, @Query("email") String email);

	@GET("/isdone")
	public Boolean isDone(@Query("email") String email);

	@GET("/getaverage")
	public Float getDone(@Query("subject") String subject);

}
