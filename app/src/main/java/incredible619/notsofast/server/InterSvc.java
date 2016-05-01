package incredible619.notsofast.server;

import java.util.List;

import retrofit.http.GET;
import retrofit.http.Query;

public interface InterSvc {

	@GET("/getpothole2")
	public  List<Pothole2> getpothole();

	@GET("/getpotholedouble2")
	public List<PotholeDouble2> getpotholedouble();

	@GET("/getpotholelatlngdec")
	public List<PotholeDouble2> getpotholelatlngdec(@Query("lattitude") Double latt, @Query("longitude") Double longi,@Query("dec") Double dec);
	
}