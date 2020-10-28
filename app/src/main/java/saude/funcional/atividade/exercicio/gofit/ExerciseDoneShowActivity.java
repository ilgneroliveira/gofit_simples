package saude.funcional.atividade.exercicio.gofit;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Objects;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import saude.funcional.atividade.exercicio.gofit.Adapter.ExerciseDoneAdapter;
import saude.funcional.atividade.exercicio.gofit.Adapter.ExerciseDoneShowAdapter;
import saude.funcional.atividade.exercicio.gofit.Model.AuthenticateUser;
import saude.funcional.atividade.exercicio.gofit.Model.ExerciseDoneUser;
import saude.funcional.atividade.exercicio.gofit.Service.RetrofitService;
import saude.funcional.atividade.exercicio.gofit.Service.ServiceGenerator;

public class ExerciseDoneShowActivity extends AppCompatActivity {

    RecyclerView rvExercisesDone;
    TextView tvExerciseDone;
    String userId;
    String exerciseId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercise_done_show);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        Bundle bundle = getIntent().getExtras();
        toolbar.setTitle(bundle.getString("title"));
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        rvExercisesDone = findViewById(R.id.rvExercisesDone);
        tvExerciseDone = findViewById(R.id.tvExercisesDone);
//        tvExerciseDone = findViewById(R.id.tvExercisesDone);
        TextView tvExerciseDone = new TextView(this);
        tvExerciseDone.setText("Nenhum resultado encontrado");

        SharedPreferences sharedPref =  getApplicationContext().getSharedPreferences("user", Context.MODE_PRIVATE);
        userId = sharedPref.getString("id", null);
        exerciseId = bundle.getString("id");
        find();
    }

    public void find(){
        AuthenticateUser authenticate_user = new AuthenticateUser();
        authenticate_user.setId(userId);
        authenticate_user.setId_exercise(exerciseId);
        RetrofitService service = ServiceGenerator.createService(RetrofitService.class);
        service.findExercisesDoneUserAndExercise(authenticate_user).enqueue(new Callback<ExerciseDoneUser>() {
            @Override
            public void onResponse(Call<ExerciseDoneUser> call, Response<ExerciseDoneUser> response) {
                boolean is_exercises = false;
                if (response.isSuccessful()) {
                    ExerciseDoneUser exerciseDoneUser = response.body();
                    //verifica aqui se o corpo da resposta não é nulo
                    String title = "Nenhum resultado encontrado";
                    if (exerciseDoneUser != null) {
                        if (exerciseDoneUser.getExercisesDone() != null) {
                            rvExercisesDone.addItemDecoration(new DividerItemDecoration(getApplicationContext(), LinearLayoutManager.VERTICAL));
                            ExerciseDoneShowAdapter adapter2 = new ExerciseDoneShowAdapter(exerciseDoneUser.getExercisesDone(), getApplicationContext());
                            LinearLayoutManager verticalLayoutManager = new LinearLayoutManager(ExerciseDoneShowActivity.this, LinearLayoutManager.VERTICAL, false);
                            rvExercisesDone.setLayoutManager(verticalLayoutManager);
                            rvExercisesDone.setAdapter(adapter2);
                            adapter2.notifyDataSetChanged();

                            if (exerciseDoneUser.getExercisesDone().size() > 0) {
                                title = "Histórico";
                                tvExerciseDone.setText(title);
                                is_exercises = true;
                            }
                        }
                    } else {
                        Toast.makeText(getApplicationContext(), "Resposta nula do servidor", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    // segura os erros de requisição
                    ResponseBody errorBody = response.errorBody();
                    Log.i("Error", "post submitted to API." + errorBody.toString());
                }

                if(!is_exercises){
                    String title = "Nenhum exercício encontrado";
                    tvExerciseDone.setText(title);
//                    layout.addView(tvExerciseDone);
                }
            }

            @Override
            public void onFailure(Call<ExerciseDoneUser> call, Throwable t) {
                Log.i("Error", "post submitted to API." + call.toString());
            }
        });
    }

}
