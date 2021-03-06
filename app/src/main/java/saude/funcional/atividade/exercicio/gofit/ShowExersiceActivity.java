package saude.funcional.atividade.exercicio.gofit;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerSupportFragment;
import com.google.android.youtube.player.YouTubePlayerView;
import com.loopj.android.image.SmartImageView;

import java.util.Objects;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import saude.funcional.atividade.exercicio.gofit.Model.Exercise;
import saude.funcional.atividade.exercicio.gofit.Model.ExerciseDoneSave;
import saude.funcional.atividade.exercicio.gofit.Model.RegisterUser;
import saude.funcional.atividade.exercicio.gofit.Service.RetrofitService;
import saude.funcional.atividade.exercicio.gofit.Service.ServiceGenerator;

public class ShowExersiceActivity extends AppCompatActivity implements YouTubePlayer.OnInitializedListener     {
    SmartImageView ivExerciseShow;
    TextView tvDescription;
    Button button2;
    Toolbar toolbar;
    private static TextView section_label;
    private static long initialTime;
    private static Handler handler;
    private static boolean isRunning;
    private static final long MILLIS_IN_SEC = 1000L;
    private static final int SECS_IN_MIN = 60;
    private static final int SECS_IN_HORA = 3600;
    private static FloatingActionButton fab;
    public static ExerciseDoneSave exerciseDone;
    public static ProgressDialog dialog;
    public static Context context;
    public static final String API_KEY = "AIzaSyBubbCd9PMfj89eJvZReeaTpGjPE97CMaI";
    public static String VIDEO_ID = "";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_exersice);

        toolbar = findViewById(R.id.toolbarShowExercicise);

        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        context = getApplicationContext();
        section_label = findViewById(R.id.section_label);
        fab = findViewById(R.id.fab);
        exerciseDone = new ExerciseDoneSave();
        SharedPreferences sharedPref = getApplicationContext().getSharedPreferences("user", Context.MODE_PRIVATE);
        String userId = sharedPref.getString("id", null);
        exerciseDone.setUser_id(userId);
        setupStopwatch();

        tvDescription = findViewById(R.id.tvDescription);
        button2 = findViewById(R.id.button2);
        ivExerciseShow = findViewById(R.id.ivExerciseShow);

        RetrofitService service = ServiceGenerator.createService(RetrofitService.class);

        Bundle extras = getIntent().getExtras();

        Intent iin = getIntent();
        Bundle b = iin.getExtras();
        String exerciseId = "";
        if (b != null) {
            exerciseId = b.getString("id");
        }
        service.getExercise(exerciseId).enqueue(new Callback<Exercise>() {
            @Override
            public void onResponse(Call<Exercise> call, Response<Exercise> response) {

                if (response.isSuccessful()) {
                    final Exercise exercise = response.body();
                    exerciseDone.setExercise_id(exercise.getId());
                    //verifica aqui se o corpo da resposta não é nulo
                    if (exercise != null) {
                        toolbar.setTitle(exercise.getTitle());
                        tvDescription.setText(exercise.getDescription());
                        ivExerciseShow.setImageUrl(exercise.getFeaturedImageUrl());
                        ivExerciseShow.setScaleType(ImageView.ScaleType.CENTER_CROP);

                        // Initializing YouTube player view
//                        ShowExersiceActivity.VIDEO_ID = exercise.getMediaUrl();
//                        YouTubePlayerView youTubePlayerView = findViewById(R.id.youtube_player_view);

//                        YouTubePlayerSupportFragment frag =
//                                (YouTubePlayerSupportFragment) getSupportFragmentManager().findFragmentById(R.id.youtube_player_view);
//                        frag.initialize(API_KEY, ShowExersiceActivity.this);

                        button2.setOnClickListener(new View.OnClickListener() {
                            public void onClick(View v) {
                                String url = exercise.getMediaUrl();
                                Intent i = new Intent(Intent.ACTION_VIEW);
                                i.setData(Uri.parse(url));
                                startActivity(i);
                            }
                        });
                    } else {
                        Toast.makeText(getApplicationContext(), "Resposta nula do servidor", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    // segura os erros de requisição
                    ResponseBody errorBody = response.errorBody();
                    Log.i("Error", "post submitted to API." + errorBody.toString());
                }
            }

            @Override
            public void onFailure(Call<Exercise> call, Throwable t) {
                Log.i("Error", "post submitted to API." + call.toString());
            }
        });
    }

    private final static Runnable runnable = new Runnable() {
        @Override
        public void run() {
            if (isRunning) {
                long seconds = (System.currentTimeMillis() - initialTime) / MILLIS_IN_SEC;
                long input = seconds;// 1h : 30 min : 18 seg
                long horas = input / 3600;
                long minutos = (input - (horas * 3600)) / 60;
                long segundos = input - (horas * 3600) - (minutos * 60);
                section_label.setText(String.format("%dh %dm %ds", horas, minutos, segundos));
                handler.postDelayed(runnable, MILLIS_IN_SEC);
            }
        }
    };

    private void setupStopwatch() {
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FloatingActionButton fab = (FloatingActionButton) view;
                if (!isRunning) {
                    isRunning = true;
                    initialTime = System.currentTimeMillis();
                    fab.setImageResource(android.R.drawable.ic_media_pause);
                    handler.postDelayed(runnable, MILLIS_IN_SEC);
                } else {
                    dialog = ProgressDialog.show(ShowExersiceActivity.this, "Salvando Histórico", "Aguarde...");
                    isRunning = false;
                    fab.setImageResource(android.R.drawable.ic_media_play);
                    handler.removeCallbacks(runnable);

                    exerciseDone.setTime_execute(section_label.getText().toString());

                    RetrofitService service = ServiceGenerator.createService(RetrofitService.class);

                    service.saveExercisesDone(exerciseDone).enqueue(new Callback<RegisterUser>() {
                        @Override
                        public void onResponse(Call<RegisterUser> call, Response<RegisterUser> response) {

                            if (response.isSuccessful()) {
                                RegisterUser registerUser = response.body();
                                Log.i("SaveExercisesDone", registerUser.getMessage());
                                if (registerUser.getSuccess() == 1) {
                                    dialog.dismiss();
                                    Toast.makeText(context, "Histórico salvo.", Toast.LENGTH_SHORT).show();
                                } else {
                                    Toast.makeText(context, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            } else {
                                Log.i("SaveExercisesDone", "Erro ao salvar seu Histórico.");
                                Toast.makeText(context, "Erro ao salvar seu Histórico.", Toast.LENGTH_SHORT).show();
                                dialog.dismiss();
                            }

                        }

                        @Override
                        public void onFailure(Call<RegisterUser> call, Throwable t) {
                            Log.i("Error", "post submitted to API." + call.toString());
                            Toast.makeText(context, "Erro ao salvar seu cadastro", Toast.LENGTH_SHORT).show();
                        }
                    });

                    section_label.setText("0h 0m 0s");
                }
            }
        });

        handler = new Handler();
    }

    @Override
    public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean b) {
        if(null== youTubePlayer) return;

        // Start buffering
        if (!b) {
            youTubePlayer.cueVideo(VIDEO_ID);
        }
    }

    @Override
    public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {
        Toast.makeText(this, "Failed to initialize.", Toast.LENGTH_LONG).show();
    }
}
