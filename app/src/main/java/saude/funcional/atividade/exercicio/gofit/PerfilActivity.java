package saude.funcional.atividade.exercicio.gofit;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.devs.vectorchildfinder.VectorChildFinder;
import com.devs.vectorchildfinder.VectorDrawableCompat;
import com.facebook.AccessToken;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.view.SimpleDraweeView;
import com.facebook.imagepipeline.common.ResizeOptions;
import com.facebook.imagepipeline.request.ImageRequest;
import com.facebook.imagepipeline.request.ImageRequestBuilder;
import com.facebook.login.LoginManager;

import java.util.Objects;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import saude.funcional.atividade.exercicio.gofit.Adapter.ExerciseDoneAdapter;
import saude.funcional.atividade.exercicio.gofit.Callback.GetUserCallback;
import saude.funcional.atividade.exercicio.gofit.Model.AuthenticateUser;
import saude.funcional.atividade.exercicio.gofit.Model.ExerciseDoneUser;
import saude.funcional.atividade.exercicio.gofit.Model.LifestyleProfile;
import saude.funcional.atividade.exercicio.gofit.Model.User;
import saude.funcional.atividade.exercicio.gofit.Request.UserRequest;
import saude.funcional.atividade.exercicio.gofit.Service.RetrofitService;
import saude.funcional.atividade.exercicio.gofit.Service.ServiceGenerator;

public class PerfilActivity extends AppCompatActivity implements GetUserCallback.IGetUserResponse {
    Toolbar toolbar;
    TextView tvName;
    SimpleDraweeView imagePerfil;
    Button btSair;
    String userId;
    RecyclerView rvExercisesDone;
    LifestyleProfile lifestyleProfile;
    ImageView star;
    ProgressDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Fresco.initialize(this);
        setContentView(R.layout.activity_perfil);
        toolbar = findViewById(R.id.toolbarPerfil);

        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        tvName = findViewById(R.id.tvName);
        imagePerfil = findViewById(R.id.imagePerfil);
        btSair = findViewById(R.id.sair);
        btSair.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logout();
            }
        });

        rvExercisesDone = findViewById(R.id.rvExercisesDone);
        SharedPreferences sharedPref = getApplicationContext().getSharedPreferences("user", Context.MODE_PRIVATE);
        userId = sharedPref.getString("id", null);
        findExercisesDone();
        star = findViewById(R.id.star);

        TextView pevComplete = findViewById(R.id.complete);
        pevComplete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PerfilActivity.this, LifestyleProfileActivity.class);
                startActivity(intent);
            }
        });

        TextView pevPartial = findViewById(R.id.partial);
        pevPartial.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PerfilActivity.this, LifeStylePartialActivity.class);
                intent.putExtra("redirect", false);
                startActivity(intent);
            }
        });
        carregar();
    }

    private void carregar() {
        dialog = ProgressDialog.show(PerfilActivity.this, "Carregando", "Aguarde...");
        RetrofitService service = ServiceGenerator.createService(RetrofitService.class);

        AuthenticateUser authenticate_user = new AuthenticateUser();
        authenticate_user.setId(userId);
        authenticate_user.setIs_get(true);

        service.getLifestyleProfileUser(authenticate_user).enqueue(new Callback<LifestyleProfile>() {
            @Override
            public void onResponse(Call<LifestyleProfile> call, Response<LifestyleProfile> response) {

                if (response.isSuccessful()) {
                    LifestyleProfile lifestyleProfile = response.body();
                    VectorChildFinder vector = new VectorChildFinder(PerfilActivity.this, R.drawable.ic_novo, star);
//                    VectorDrawableCompat.VFullPath path1 = vector.findPathByName("a1");
//                    path1.setFillColor(Color.RED);

                    VectorDrawableCompat.VFullPath path1;
                    VectorDrawableCompat.VFullPath path2;
                    VectorDrawableCompat.VFullPath path3;

                    if (lifestyleProfile != null) {
                        if (lifestyleProfile.getNutritionA() != null) {

                            switch (lifestyleProfile.getNutritionA()) {
                                case "1":
                                    path1 = vector.findPathByName("a1");
                                    path1.setFillColor(Color.YELLOW);
                                    break;
                                case "2":
                                    path1 = vector.findPathByName("a1");
                                    path1.setFillColor(Color.YELLOW);
                                    path2 = vector.findPathByName("a2");
                                    path2.setFillColor(Color.YELLOW);
                                    break;
                                case "3":
                                    path1 = vector.findPathByName("a1");
                                    path1.setFillColor(Color.YELLOW);
                                    path2 = vector.findPathByName("a2");
                                    path2.setFillColor(Color.YELLOW);
                                    path3 = vector.findPathByName("a3");
                                    path3.setFillColor(Color.YELLOW);
                                    break;
                            }
                        }

                        if (lifestyleProfile.getNutritionB() != null) {
                            switch (lifestyleProfile.getNutritionB()) {
                                case "1":
                                    path1 = vector.findPathByName("b1");
                                    path1.setFillColor(Color.YELLOW);
                                    break;
                                case "2":
                                    path1 = vector.findPathByName("b1");
                                    path1.setFillColor(Color.YELLOW);
                                    path2 = vector.findPathByName("b2");
                                    path2.setFillColor(Color.YELLOW);
                                    break;
                                case "3":
                                    path1 = vector.findPathByName("b1");
                                    path1.setFillColor(Color.YELLOW);
                                    path2 = vector.findPathByName("b2");
                                    path2.setFillColor(Color.YELLOW);
                                    path3 = vector.findPathByName("b3");
                                    path3.setFillColor(Color.YELLOW);
                                    break;
                            }
                        }

                        if (lifestyleProfile.getNutritionC() != null) {
                            switch (lifestyleProfile.getNutritionC()) {
                                case "1":
                                    path1 = vector.findPathByName("c1");
                                    path1.setFillColor(Color.YELLOW);
                                    break;
                                case "2":
                                    path1 = vector.findPathByName("c1");
                                    path1.setFillColor(Color.YELLOW);
                                    path2 = vector.findPathByName("c2");
                                    path2.setFillColor(Color.YELLOW);
                                    break;
                                case "3":
                                    path1 = vector.findPathByName("c1");
                                    path1.setFillColor(Color.YELLOW);
                                    path2 = vector.findPathByName("c2");
                                    path2.setFillColor(Color.YELLOW);
                                    path3 = vector.findPathByName("c3");
                                    path3.setFillColor(Color.YELLOW);
                                    break;
                            }
                        }

                        if (lifestyleProfile.getPhysicalActivityD() != null) {
                            switch (lifestyleProfile.getPhysicalActivityD()) {
                                case "1":
                                    path1 = vector.findPathByName("d1");
                                    path1.setFillColor(Color.BLUE);
                                    break;
                                case "2":
                                    path1 = vector.findPathByName("d1");
                                    path1.setFillColor(Color.BLUE);
                                    path2 = vector.findPathByName("d2");
                                    path2.setFillColor(Color.BLUE);
                                    break;
                                case "3":
                                    path1 = vector.findPathByName("d1");
                                    path1.setFillColor(Color.BLUE);
                                    path2 = vector.findPathByName("d2");
                                    path2.setFillColor(Color.BLUE);
                                    path3 = vector.findPathByName("d3");
                                    path3.setFillColor(Color.BLUE);
                                    break;
                            }
                        }

                        if (lifestyleProfile.getPhysicalActivityE() != null) {
                            switch (lifestyleProfile.getPhysicalActivityE()) {
                                case "1":
                                    path1 = vector.findPathByName("e1");
                                    path1.setFillColor(Color.BLUE);
                                    break;
                                case "2":
                                    path1 = vector.findPathByName("e1");
                                    path1.setFillColor(Color.BLUE);
                                    path2 = vector.findPathByName("e2");
                                    path2.setFillColor(Color.BLUE);
                                    break;
                                case "3":
                                    path1 = vector.findPathByName("e1");
                                    path1.setFillColor(Color.BLUE);
                                    path2 = vector.findPathByName("e2");
                                    path2.setFillColor(Color.BLUE);
                                    path3 = vector.findPathByName("e3");
                                    path3.setFillColor(Color.BLUE);
                                    break;
                            }
                        }

                        if (lifestyleProfile.getPhysicalActivityF() != null) {
                            switch (lifestyleProfile.getPhysicalActivityF()) {
                                case "1":
                                    path1 = vector.findPathByName("f1");
                                    path1.setFillColor(Color.BLUE);
                                    break;
                                case "2":
                                    path1 = vector.findPathByName("f1");
                                    path1.setFillColor(Color.BLUE);
                                    path2 = vector.findPathByName("f2");
                                    path2.setFillColor(Color.BLUE);
                                    break;
                                case "3":
                                    path1 = vector.findPathByName("f1");
                                    path1.setFillColor(Color.BLUE);
                                    path2 = vector.findPathByName("f2");
                                    path2.setFillColor(Color.BLUE);
                                    path3 = vector.findPathByName("f3");
                                    path3.setFillColor(Color.BLUE);
                                    break;
                            }
                        }

                        if (lifestyleProfile.getPreventiveBehaviorG() != null) {
                            switch (lifestyleProfile.getPreventiveBehaviorG()) {
                                case "1":
                                    path1 = vector.findPathByName("g1");
                                    path1.setFillColor(Color.RED);
                                    break;
                                case "2":
                                    path1 = vector.findPathByName("g1");
                                    path1.setFillColor(Color.RED);
                                    path2 = vector.findPathByName("g2");
                                    path2.setFillColor(Color.RED);
                                    break;
                                case "3":
                                    path1 = vector.findPathByName("g1");
                                    path1.setFillColor(Color.RED);
                                    path2 = vector.findPathByName("g2");
                                    path2.setFillColor(Color.RED);
                                    path3 = vector.findPathByName("g3");
                                    path3.setFillColor(Color.RED);
                                    break;
                            }
                        }

                        if (lifestyleProfile.getPreventiveBehaviorH() != null) {
                            switch (lifestyleProfile.getPreventiveBehaviorH()) {
                                case "1":
                                    path1 = vector.findPathByName("h1");
                                    path1.setFillColor(Color.RED);
                                    break;
                                case "2":
                                    path1 = vector.findPathByName("h1");
                                    path1.setFillColor(Color.RED);
                                    path2 = vector.findPathByName("h2");
                                    path2.setFillColor(Color.RED);
                                    break;
                                case "3":
                                    path1 = vector.findPathByName("h1");
                                    path1.setFillColor(Color.RED);
                                    path2 = vector.findPathByName("h2");
                                    path2.setFillColor(Color.RED);
                                    path3 = vector.findPathByName("h3");
                                    path3.setFillColor(Color.RED);
                                    break;
                            }
                        }

                        if (lifestyleProfile.getPreventiveBehaviorI() != null) {
                            switch (lifestyleProfile.getPreventiveBehaviorI()) {
                                case "1":
                                    path1 = vector.findPathByName("i1");
                                    path1.setFillColor(Color.RED);
                                    break;
                                case "2":
                                    path1 = vector.findPathByName("i1");
                                    path1.setFillColor(Color.RED);
                                    path2 = vector.findPathByName("i2");
                                    path2.setFillColor(Color.RED);
                                    break;
                                case "3":
                                    path1 = vector.findPathByName("i1");
                                    path1.setFillColor(Color.RED);
                                    path2 = vector.findPathByName("i2");
                                    path2.setFillColor(Color.RED);
                                    path3 = vector.findPathByName("i3");
                                    path3.setFillColor(Color.RED);
                                    break;
                            }
                        }

                        if (lifestyleProfile.getRelationshipsJ() != null) {
                            switch (lifestyleProfile.getRelationshipsJ()) {
                                case "1":
                                    path1 = vector.findPathByName("j1");
                                    path1.setFillColor(Color.GREEN);
                                    break;
                                case "2":
                                    path1 = vector.findPathByName("j1");
                                    path1.setFillColor(Color.GREEN);
                                    path2 = vector.findPathByName("j2");
                                    path2.setFillColor(Color.GREEN);
                                    break;
                                case "3":
                                    path1 = vector.findPathByName("j1");
                                    path1.setFillColor(Color.GREEN);
                                    path2 = vector.findPathByName("j2");
                                    path2.setFillColor(Color.GREEN);
                                    path3 = vector.findPathByName("j3");
                                    path3.setFillColor(Color.GREEN);
                                    break;
                            }
                        }

                        if (lifestyleProfile.getRelationshipsK() != null) {
                            switch (lifestyleProfile.getRelationshipsK()) {
                                case "1":
                                    path1 = vector.findPathByName("k1");
                                    path1.setFillColor(Color.GREEN);
                                    break;
                                case "2":
                                    path1 = vector.findPathByName("k1");
                                    path1.setFillColor(Color.GREEN);
                                    path2 = vector.findPathByName("k2");
                                    path2.setFillColor(Color.GREEN);
                                    break;
                                case "3":
                                    path1 = vector.findPathByName("k1");
                                    path1.setFillColor(Color.GREEN);
                                    path2 = vector.findPathByName("k2");
                                    path2.setFillColor(Color.GREEN);
                                    path3 = vector.findPathByName("k3");
                                    path3.setFillColor(Color.GREEN);
                                    break;
                            }
                        }

                        if (lifestyleProfile.getRelationshipsL() != null) {
                            switch (lifestyleProfile.getRelationshipsL()) {
                                case "1":
                                    path1 = vector.findPathByName("l1");
                                    path1.setFillColor(Color.GREEN);
                                    break;
                                case "2":
                                    path1 = vector.findPathByName("l1");
                                    path1.setFillColor(Color.GREEN);
                                    path2 = vector.findPathByName("l2");
                                    path2.setFillColor(Color.GREEN);
                                    break;
                                case "3":
                                    path1 = vector.findPathByName("l1");
                                    path1.setFillColor(Color.GREEN);
                                    path2 = vector.findPathByName("l2");
                                    path2.setFillColor(Color.GREEN);
                                    path3 = vector.findPathByName("l3");
                                    path3.setFillColor(Color.GREEN);
                                    break;
                            }
                        }

                        if (lifestyleProfile.getStressManagementM() != null) {
                            switch (lifestyleProfile.getStressManagementM()) {
                                case "1":
                                    path1 = vector.findPathByName("m1");
                                    path1.setFillColor(Color.MAGENTA);
                                    break;
                                case "2":
                                    path1 = vector.findPathByName("m1");
                                    path1.setFillColor(Color.MAGENTA);
                                    path2 = vector.findPathByName("m2");
                                    path2.setFillColor(Color.MAGENTA);
                                    break;
                                case "3":
                                    path1 = vector.findPathByName("m1");
                                    path1.setFillColor(Color.MAGENTA);
                                    path2 = vector.findPathByName("m2");
                                    path2.setFillColor(Color.MAGENTA);
                                    path3 = vector.findPathByName("m3");
                                    path3.setFillColor(Color.MAGENTA);
                                    break;
                            }
                        }

                        if (lifestyleProfile.getStressManagementN() != null) {
                            switch (lifestyleProfile.getStressManagementN()) {
                                case "1":
                                    path1 = vector.findPathByName("m1");
                                    path1.setFillColor(Color.MAGENTA);
                                    break;
                                case "2":
                                    path1 = vector.findPathByName("n1");
                                    path1.setFillColor(Color.MAGENTA);
                                    path2 = vector.findPathByName("n2");
                                    path2.setFillColor(Color.MAGENTA);
                                    break;
                                case "3":
                                    path1 = vector.findPathByName("n1");
                                    path1.setFillColor(Color.MAGENTA);
                                    path2 = vector.findPathByName("n2");
                                    path2.setFillColor(Color.MAGENTA);
                                    path3 = vector.findPathByName("n3");
                                    path3.setFillColor(Color.MAGENTA);
                                    break;
                            }
                        }

                        if (lifestyleProfile.getStressManagementO() != null) {
                            switch (lifestyleProfile.getStressManagementO()) {
                                case "1":
                                    path1 = vector.findPathByName("o1");
                                    path1.setFillColor(Color.MAGENTA);
                                    break;
                                case "2":
                                    path1 = vector.findPathByName("o1");
                                    path1.setFillColor(Color.MAGENTA);
                                    path2 = vector.findPathByName("o2");
                                    path2.setFillColor(Color.MAGENTA);
                                    break;
                                case "3":
                                    path1 = vector.findPathByName("o1");
                                    path1.setFillColor(Color.MAGENTA);
                                    path2 = vector.findPathByName("o2");
                                    path2.setFillColor(Color.MAGENTA);
                                    path3 = vector.findPathByName("o3");
                                    path3.setFillColor(Color.MAGENTA);
                                    break;
                            }
                        }
                    }

                    dialog.dismiss();
                } else {
                    Log.i("LifeStyleProfile", "Erro ao recuperar seu perfil de estilo de vida.");
                    Toast.makeText(getApplicationContext(), "Erro ao recuperar seu perfil de estilo de vida.", Toast.LENGTH_SHORT).show();
                    dialog.dismiss();
                }

            }

            @Override
            public void onFailure(Call<LifestyleProfile> call, Throwable t) {
                Log.i("Error", "post submitted to API." + call.toString());
                Toast.makeText(getApplicationContext(), "Erro ao recuperar seu perfil de estilo de vida", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void findExercisesDone() {
        AuthenticateUser authenticate_user = new AuthenticateUser();
        authenticate_user.setId(userId);
        RetrofitService service = ServiceGenerator.createService(RetrofitService.class);
        service.findExercisesDoneUser(authenticate_user).enqueue(new Callback<ExerciseDoneUser>() {
            @Override
            public void onResponse(Call<ExerciseDoneUser> call, Response<ExerciseDoneUser> response) {
                if (response.isSuccessful()) {
                    ExerciseDoneUser exerciseDoneUser = response.body();
                    //verifica aqui se o corpo da resposta não é nulo
                    if (exerciseDoneUser != null && exerciseDoneUser.getExercisesDone() != null) {
                        ExerciseDoneAdapter adapter2 = new ExerciseDoneAdapter(exerciseDoneUser.getExercisesDone(), getApplicationContext());
                        LinearLayoutManager horizontalLayoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL, false);
                        rvExercisesDone.setLayoutManager(horizontalLayoutManager);
                        rvExercisesDone.setAdapter(adapter2);
                        adapter2.notifyDataSetChanged();
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
            public void onFailure(Call<ExerciseDoneUser> call, Throwable t) {
                Log.i("Error", "post submitted to API." + call.toString());
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        UserRequest.makeUserRequest(new GetUserCallback(PerfilActivity.this).getCallback());
    }

    @Override
    public void onCompleted(User user) {
        tvName.setText(user.getName());
        ImageRequest request = ImageRequestBuilder.newBuilderWithSource(user.getPicture())
                .setResizeOptions(new ResizeOptions(120, 120))
                .build();
        imagePerfil.setController(
                Fresco.newDraweeControllerBuilder()
                        .setOldController(imagePerfil.getController())
                        .setImageRequest(request)
                        .build());
    }

    @Override
    public void onBackPressed() {
    }

    public void logout() {
        ProgressDialog dialog = ProgressDialog.show(PerfilActivity.this, "Aguarde", "Saindo...");

        AccessToken accessToken = AccessToken.getCurrentAccessToken();
        boolean isLoggedIn = accessToken != null && !accessToken.isExpired();
        if (isLoggedIn) {
            LoginManager lg = LoginManager.getInstance();
            lg.logOut();
        }

        SharedPreferences sharedPref = getApplicationContext().getSharedPreferences("user", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString("id", null);
        editor.apply();

        Intent intent = new Intent(getApplicationContext(), Login.class);
        startActivity(intent);
        dialog.dismiss();
    }
}
