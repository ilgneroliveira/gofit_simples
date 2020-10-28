package saude.funcional.atividade.exercicio.gofit.Control;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.image.SmartImageView;

import java.util.List;
import java.util.Random;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import saude.funcional.atividade.exercicio.gofit.Adapter.StartAdapter;
import saude.funcional.atividade.exercicio.gofit.Model.AuthenticateUser;
import saude.funcional.atividade.exercicio.gofit.Model.Exercise;
import saude.funcional.atividade.exercicio.gofit.Model.ExerciseRecommedation;
import saude.funcional.atividade.exercicio.gofit.Model.Recommedation;
import saude.funcional.atividade.exercicio.gofit.R;
import saude.funcional.atividade.exercicio.gofit.Service.RetrofitService;
import saude.funcional.atividade.exercicio.gofit.Service.ServiceGenerator;
import saude.funcional.atividade.exercicio.gofit.ShowExersiceActivity;

import static com.facebook.FacebookSdk.getApplicationContext;

public class ExerciseControl {

    private static List<ExerciseRecommedation> recomedation_exercises;

    public static void recommendation(final View view, String userId, final Context applicationContext) {
        final RecyclerView lvResult1;
        final RecyclerView lvResult2;
        final RecyclerView lvResult3;
        final TextView tvResult1;
        final TextView tvResult2;
        final TextView tvResult4;
        final SmartImageView imageView;
        final TextView txtview;

        lvResult1 = view.findViewById(R.id.lvResult1);
        lvResult2 = view.findViewById(R.id.lvResult2);
        tvResult4 = view.findViewById(R.id.tvResult4);
//        lvResult3 = view.findViewById(R.id.lvResult3);
        tvResult1 = view.findViewById(R.id.tvResult1);
        tvResult2 = view.findViewById(R.id.tvResult2);
        imageView = view.findViewById(R.id.idImage);
        txtview = view.findViewById(R.id.idName);
//        lvResult.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                Intent intent = new Intent(applicationContext, ShowExersiceActivity.class);
//                intent.putExtra("id", recomedation_exercises.get(position).getExercise().getId());
//                applicationContext.startActivity(intent);
//
//            }
//        });

        RetrofitService service = ServiceGenerator.createService(RetrofitService.class);

        AuthenticateUser authenticate_user = new AuthenticateUser();
        authenticate_user.setId(userId);

        service.recommendation(authenticate_user).enqueue(new Callback<Recommedation>() {
            @Override
            public void onResponse(Call<Recommedation> call, Response<Recommedation> response) {
                boolean is_exercises = false;
                if (response.isSuccessful()) {
                    Recommedation recommedation = response.body();
                    //verifica aqui se o corpo da resposta não é nulo
                    String title = "Nenhum resultado encontrado";
                    if (recommedation != null) {
                        if (recommedation.getExercises() != null) {
                            final ExerciseRecommedation exercise = recommedation.getExercises().get(0);
                            recommedation.getExercises().remove(exercise);
                            imageView.setImageUrl(exercise.getExercise().getFeaturedImageUrl());
                            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
                            txtview.setText(exercise.getExercise().getTitle());
                            txtview.setBackgroundColor(applicationContext.getResources().getColor(R.color.cinza));
                            imageView.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    Intent intent = new Intent(getApplicationContext(), ShowExersiceActivity.class);
                                    intent.putExtra("id", exercise.getExercise().getId());
                                    getApplicationContext().startActivity(intent);
                                }
                            });
                            txtview.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    Intent intent = new Intent(getApplicationContext(), ShowExersiceActivity.class);
                                    intent.putExtra("id", exercise.getExercise().getId());
                                    getApplicationContext().startActivity(intent);
                                }
                            });


//                            lvResult2.addItemDecoration(new DividerItemDecoration(applicationContext, LinearLayoutManager.HORIZONTAL));
                            StartAdapter adapter2 = new StartAdapter(recommedation.getExercises(), getApplicationContext());
                            LinearLayoutManager horizontalLayoutManager = new LinearLayoutManager(applicationContext, LinearLayoutManager.HORIZONTAL, false);
                            lvResult1.setLayoutManager(horizontalLayoutManager);
                            lvResult1.setAdapter(adapter2);
                            adapter2.notifyDataSetChanged();

                            if (recommedation.getExercises().size() > 0) {
                                title = "Recomendados para você";
                                tvResult1.setText(title);

                                title = "Melhor para hoje";
                                tvResult4.setText(title);
                                is_exercises = true;
                            }
                        }
                        if (recommedation.getExercises_done() != null) {
//                            lvResult1.addItemDecoration(new DividerItemDecoration(applicationContext, LinearLayoutManager.HORIZONTAL));
                            StartAdapter adapter1 = new StartAdapter(recommedation.getExercises_done(), getApplicationContext());
                            LinearLayoutManager horizontalLayoutManager = new LinearLayoutManager(applicationContext, LinearLayoutManager.HORIZONTAL, false);
                            lvResult2.setLayoutManager(horizontalLayoutManager);
                            lvResult2.setAdapter(adapter1);
                            adapter1.notifyDataSetChanged();

                            if (recommedation.getExercises_done().size() > 0) {
                                title = "Exercícios Praticados";
                                tvResult2.setText(title);
                                is_exercises = true;
                            }
                        }
                    } else {
                        Toast.makeText(applicationContext, "Resposta nula do servidor", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    // segura os erros de requisição
                    ResponseBody errorBody = response.errorBody();
                    Log.i("Error", "post submitted to API." + errorBody.toString());
                }

                if(!is_exercises){
                    String title = "Nenhum exercício encontrado";
                    tvResult4.setText(title);
                }
            }

            @Override
            public void onFailure(Call<Recommedation> call, Throwable t) {
                Log.i("Error", "post submitted to API." + call.toString());
            }
        });
    }
}
