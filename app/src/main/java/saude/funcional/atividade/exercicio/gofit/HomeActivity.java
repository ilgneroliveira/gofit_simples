package saude.funcional.atividade.exercicio.gofit;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.view.SimpleDraweeView;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import saude.funcional.atividade.exercicio.gofit.Callback.GetUserCallback;
import saude.funcional.atividade.exercicio.gofit.Model.AuthenticateUser;
import saude.funcional.atividade.exercicio.gofit.Model.User;
import saude.funcional.atividade.exercicio.gofit.Request.UserRequest;
import saude.funcional.atividade.exercicio.gofit.Service.RetrofitService;
import saude.funcional.atividade.exercicio.gofit.Service.ServiceGenerator;

public class HomeActivity extends AppCompatActivity implements GetUserCallback.IGetUserResponse {
    Toolbar toolbar;
    String userId;
    FragmentManager fragmentManager;
    FragmentTransaction fragmentTransaction;
    SimpleDraweeView imagePerfil;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Fresco.initialize(this);
        setContentView(R.layout.activity_home);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        SharedPreferences sharedPref =  getApplicationContext().getSharedPreferences("user",Context.MODE_PRIVATE);
        userId = sharedPref.getString("id", null);

        imagePerfil = findViewById(R.id.imagePerfil);
        FrameLayout viewPager = findViewById(R.id.viewpager);
        setupViewPager();

        testLifestyleProfile();

//        setupTabIcons();

//        tabEvents();
    }

    private void testLifestyleProfile() {
        RetrofitService service = ServiceGenerator.createService(RetrofitService.class);

        AuthenticateUser authenticate_user = new AuthenticateUser();
        authenticate_user.setId(userId);

        service.testLifestyleProfile(authenticate_user).enqueue(new Callback<AuthenticateUser>() {
            @Override
            public void onResponse(Call<AuthenticateUser> call, Response<AuthenticateUser> response) {

                if (response.isSuccessful()) {
                    if (!response.body().getIs_valid()) {
                        Intent intent = new Intent(HomeActivity.this, LifeStylePartialActivity.class);
                        startActivity(intent);
                    }
                } else {
                    // segura os erros de requisição
                    ResponseBody errorBody = response.errorBody();
                    Log.i("Error", "post submitted to API." + response.body().toString());
                }

            }

            @Override
            public void onFailure(Call<AuthenticateUser> call, Throwable t) {
                Log.i("Error", "post submitted to API." + call.toString());
            }
        });
    }

    private void setupViewPager() {
        fragmentManager = getSupportFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.viewpager, new StartFragment(), "Inicio");
        fragmentTransaction.commit();
    }

    public void onClick(View v) {
        Intent intent;
        switch (v.getId()) {
            case R.id.saerch:
                intent = new Intent(getApplicationContext(), Search.class);
                startActivity(intent);
                break;
            case R.id.imagePerfil:
                intent = new Intent(getApplicationContext(), PerfilActivity.class);
                startActivity(intent);
                break;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        UserRequest.makeUserRequest(new GetUserCallback(HomeActivity.this).getCallback());
    }

    @Override
    public void onCompleted(User user) {
        imagePerfil.setImageURI(user.getPicture());
    }

    @Override
    public void onBackPressed() {
    }
}
