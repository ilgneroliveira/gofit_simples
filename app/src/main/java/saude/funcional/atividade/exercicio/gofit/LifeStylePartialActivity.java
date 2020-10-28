package saude.funcional.atividade.exercicio.gofit;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import saude.funcional.atividade.exercicio.gofit.Model.AuthenticateUser;
import saude.funcional.atividade.exercicio.gofit.Model.LifestyleProfile;
import saude.funcional.atividade.exercicio.gofit.Model.RegisterUser;
import saude.funcional.atividade.exercicio.gofit.Service.RetrofitService;
import saude.funcional.atividade.exercicio.gofit.Service.ServiceGenerator;

public class LifeStylePartialActivity extends AppCompatActivity {
    Toolbar toolbar;
    ProgressDialog dialog;
    String userId;
    String lifestyleProfileId;
    boolean is_already_create = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_life_style_partial);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        SharedPreferences sharedPref = getApplicationContext().getSharedPreferences("user", Context.MODE_PRIVATE);
        userId = sharedPref.getString("id", null);

        carregar();

        Button btSavePartial = findViewById(R.id.btSavePartial);

        btSavePartial.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                save();
            }
        });
    }

    private void save() {
        dialog = ProgressDialog.show(LifeStylePartialActivity.this, "Salvando", "Aguarde...");

        LifestyleProfile lifestyleProfile = new LifestyleProfile();
        lifestyleProfile.setUser_id(userId);

        RadioGroup d = (RadioGroup) findViewById(R.id.d);
        int selectedIdD = d.getCheckedRadioButtonId();
        RadioButton radioDButton = (RadioButton) findViewById(selectedIdD);
        lifestyleProfile.setPhysicalActivityD(radioDButton.getText().toString());

        RadioGroup e = (RadioGroup) findViewById(R.id.e);
        int selectedIdE = e.getCheckedRadioButtonId();
        RadioButton radioEButton = (RadioButton) findViewById(selectedIdE);
        lifestyleProfile.setPhysicalActivityE(radioEButton.getText().toString());

        RadioGroup f = (RadioGroup) findViewById(R.id.f);
        int selectedIdF = f.getCheckedRadioButtonId();
        RadioButton radioFButton = (RadioButton) findViewById(selectedIdF);
        lifestyleProfile.setPhysicalActivityF(radioFButton.getText().toString());

        RetrofitService service = ServiceGenerator.createService(RetrofitService.class);

        if(is_already_create){
            service.updateLifestyleProfile(lifestyleProfileId,lifestyleProfile).enqueue(new Callback<RegisterUser>() {
                @Override
                public void onResponse(Call<RegisterUser> call, Response<RegisterUser> response) {

                    if (response.isSuccessful()) {
                        RegisterUser registerUser = response.body();
                        Log.i("SaveLifeStyleProfile", registerUser.getMessage());
                        dialog.dismiss();
                        Toast.makeText(getApplicationContext(), response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    } else {
                        Log.i("SaveLifeStyleProfile", "Erro ao salvar seu perfil.");
                        Toast.makeText(getApplicationContext(), "Erro ao salvar seu perfil.", Toast.LENGTH_SHORT).show();
                        dialog.dismiss();
                    }

                }

                @Override
                public void onFailure(Call<RegisterUser> call, Throwable t) {
                    Log.i("Error", "post submitted to API." + call.toString());
                    Toast.makeText(getApplicationContext(), "Erro ao salvar seu perfil", Toast.LENGTH_SHORT).show();
                }
            });
        }else{
            service.saveLifestyleProfile(lifestyleProfile).enqueue(new Callback<RegisterUser>() {
                @Override
                public void onResponse(Call<RegisterUser> call, Response<RegisterUser> response) {

                    if (response.isSuccessful()) {
                        RegisterUser registerUser = response.body();
                        Log.i("SaveLifeStyleProfile", registerUser.getMessage());
                        if (registerUser.getSuccess() == 1) {
                            Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
                            startActivity(intent);
                            dialog.dismiss();
                        }
                        Toast.makeText(getApplicationContext(), response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    } else {
                        Log.i("SaveLifeStyleProfile", "Erro ao salvar seu perfil.");
                        Toast.makeText(getApplicationContext(), "Erro ao salvar seu perfil.", Toast.LENGTH_SHORT).show();
                        dialog.dismiss();
                    }

                }

                @Override
                public void onFailure(Call<RegisterUser> call, Throwable t) {
                    Log.i("Error", "post submitted to API." + call.toString());
                    Toast.makeText(getApplicationContext(), "Erro ao salvar seu cadastro", Toast.LENGTH_SHORT).show();
                }
            });
        }

    }

    private void carregar() {
        dialog = ProgressDialog.show(LifeStylePartialActivity.this, "Carregando", "Aguarde...");
        RetrofitService service = ServiceGenerator.createService(RetrofitService.class);

        AuthenticateUser authenticate_user = new AuthenticateUser();
        authenticate_user.setId(userId);
        authenticate_user.setIs_get(true);

        service.getLifestyleProfileUser(authenticate_user).enqueue(new Callback<LifestyleProfile>() {
            @Override
            public void onResponse(Call<LifestyleProfile> call, Response<LifestyleProfile> response) {

                if (response.isSuccessful()) {
                    LifestyleProfile lifestyleProfile = response.body();
                    if(lifestyleProfile != null){
                        is_already_create = true;
                        lifestyleProfileId = String.valueOf(lifestyleProfile.getId());

                        if (lifestyleProfile.getPhysicalActivityD() != null) {
                            checkRadio("d" + lifestyleProfile.getPhysicalActivityD());
                        } else {
                            checkRadio("d0");
                        }

                        if (lifestyleProfile.getPhysicalActivityE() != null) {
                            checkRadio("e" + lifestyleProfile.getPhysicalActivityE());
                        } else {
                            checkRadio("e0");
                        }

                        if (lifestyleProfile.getPhysicalActivityF() != null) {
                            checkRadio("f" + lifestyleProfile.getPhysicalActivityF());
                        } else {
                            checkRadio("f0");
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

    private void checkRadio(String id) {
        int resID = getResources().getIdentifier(id, "id", getPackageName());
        RadioButton radioDButton = findViewById(resID);

        radioDButton.setChecked(true);
    }
}
