package saude.funcional.atividade.exercicio.gofit.Service;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import saude.funcional.atividade.exercicio.gofit.Model.AuthenticateUser;
import saude.funcional.atividade.exercicio.gofit.Model.Exercise;
import saude.funcional.atividade.exercicio.gofit.Model.ExerciseDoneSave;
import saude.funcional.atividade.exercicio.gofit.Model.LifestyleProfile;
import saude.funcional.atividade.exercicio.gofit.Model.Recommedation;
import saude.funcional.atividade.exercicio.gofit.Model.RegisterUser;
import saude.funcional.atividade.exercicio.gofit.Model.SearchExercise;
import saude.funcional.atividade.exercicio.gofit.Model.User;

/**
 * RetrofitService
 *
 * @author Ilgner Fagundes <ilgner552@gmail.com>
 * @version 1.0
 */
public interface RetrofitService {
    @POST("user/new")
    Call<RegisterUser> saveOrAlterUser(@Body User user);

    @POST("user/authenticate")
    Call<AuthenticateUser> authenticateUser(@Body AuthenticateUser authenticate_user);

    @POST("user/already/registered")
    Call<AuthenticateUser> isAlreadyRegistered(@Body AuthenticateUser authenticate_user);

    @GET("user/{id}/")
    Call<User> getUser(@Path("id") String id);

    @POST("/exercise/search")
    Call<SearchExercise> searchexercises(@Body SearchExercise search_exercise);

    @POST("/lifestyle/profile/new")
    Call<RegisterUser> saveLifestyleProfile(@Body LifestyleProfile lifestyle_profile);

    @POST("/lifestyle/profile")
    Call<RegisterUser> updateLifestyleProfile(@Body LifestyleProfile lifestyle_profile);

    @POST("/lifestyle/profile/already/create")
    Call<AuthenticateUser> testLifestyleProfile(@Body AuthenticateUser authenticate_user);

    @POST("/lifestyle/profile/already/create")
    Call<LifestyleProfile> getLifestyleProfileUser(@Body AuthenticateUser authenticate_user);

    @GET("/lifestyle/profile/user/{id}")
    Call<LifestyleProfile> getLifestyleProfile(@Path("id") String id);

    @POST("/exercise/recommendation")
    Call<Recommedation> recommendation(@Body AuthenticateUser authenticate_user);

    @GET("exercise/{id}")
    Call<Exercise> getExercise(@Path("id") String id);

    @POST("/exercises/done/new")
    Call<RegisterUser> saveExercisesDone(@Body ExerciseDoneSave exerciseDoneSave);
}
