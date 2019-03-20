package saude.funcional.atividade.exercicio.gofit;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import saude.funcional.atividade.exercicio.gofit.Adapter.ExerciseDoneAdapter;
import saude.funcional.atividade.exercicio.gofit.Adapter.StartAdapter;
import saude.funcional.atividade.exercicio.gofit.Model.AuthenticateUser;
import saude.funcional.atividade.exercicio.gofit.Model.ExerciseDoneUser;
import saude.funcional.atividade.exercicio.gofit.Model.ExerciseDoneUser;
import saude.funcional.atividade.exercicio.gofit.Service.RetrofitService;
import saude.funcional.atividade.exercicio.gofit.Service.ServiceGenerator;

public class ExercisesDoneActivity extends AppCompatActivity {
    RecyclerView rvExercisesDone;
    TextView tvExerciseDone;
    String userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_exercises_done);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        rvExercisesDone = findViewById(R.id.rvExercisesDone);
//        tvExerciseDone = findViewById(R.id.tvExercisesDone);
//        tvExerciseDone = findViewById(R.id.tvExercisesDone);
        TextView tvExerciseDone = new TextView(this);
        tvExerciseDone.setText("Nenhum resultado encontrado");

        SharedPreferences sharedPref =  getApplicationContext().getSharedPreferences("user", Context.MODE_PRIVATE);
        userId = sharedPref.getString("id", null);

        find();
    }

    public void find(){
        AuthenticateUser authenticate_user = new AuthenticateUser();
        authenticate_user.setId(userId);
        RetrofitService service = ServiceGenerator.createService(RetrofitService.class);
        service.findExercisesDoneUser(authenticate_user).enqueue(new Callback<ExerciseDoneUser>() {
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
                            ExerciseDoneAdapter adapter2 = new ExerciseDoneAdapter(exerciseDoneUser.getExercisesDone(), getApplicationContext());
                            LinearLayoutManager horizontalLayoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false);
                            rvExercisesDone.setLayoutManager(horizontalLayoutManager);
                            rvExercisesDone.setAdapter(adapter2);
                            adapter2.notifyDataSetChanged();

                            if (exerciseDoneUser.getExercisesDone().size() > 0) {
//                                title = "";
//                                tvExerciseDone.setText(title);
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

    @Override
    public boolean onOptionsItemSelected(MenuItem item) { //Botão adicional na ToolBar
        switch (item.getItemId()) {
            case android.R.id.home:  //ID do seu botão (gerado automaticamente pelo android, usando como está, deve funcionar
                finish();  //Método para matar a activity e não deixa-lá indexada na pilhagem
                break;
            default:break;
        }
        return true;
    }
}
