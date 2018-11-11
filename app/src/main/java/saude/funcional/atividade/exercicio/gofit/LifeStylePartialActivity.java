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

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import saude.funcional.atividade.exercicio.gofit.Model.LifestyleProfile;
import saude.funcional.atividade.exercicio.gofit.Model.RegisterUser;
import saude.funcional.atividade.exercicio.gofit.Service.RetrofitService;
import saude.funcional.atividade.exercicio.gofit.Service.ServiceGenerator;

public class LifeStylePartialActivity extends AppCompatActivity {
    Toolbar toolbar;
    ProgressDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_life_style_partial);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

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

        SharedPreferences sharedPref =  getApplicationContext().getSharedPreferences("user",Context.MODE_PRIVATE);
        String userId = sharedPref.getString("id", null);
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

    @Override
    public void onBackPressed() {
    }
}
