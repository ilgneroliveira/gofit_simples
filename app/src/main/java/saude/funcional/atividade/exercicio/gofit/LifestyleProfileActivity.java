package saude.funcional.atividade.exercicio.gofit;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import saude.funcional.atividade.exercicio.gofit.Model.AuthenticateUser;
import saude.funcional.atividade.exercicio.gofit.Model.LifestyleProfile;
import saude.funcional.atividade.exercicio.gofit.Model.RegisterUser;
import saude.funcional.atividade.exercicio.gofit.Service.RetrofitService;
import saude.funcional.atividade.exercicio.gofit.Service.ServiceGenerator;

public class LifestyleProfileActivity extends AppCompatActivity {
    Toolbar toolbar;
    String userId;
    String lifestyleProfileId;
    ProgressDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lifestyle_profile);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        SharedPreferences sharedPref = getApplicationContext().getSharedPreferences("user", Context.MODE_PRIVATE);
        userId = sharedPref.getString("id", null);

        carregar();

        Button btSave = findViewById(R.id.btSave);
        btSave.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                save();
            }
        });
    }

    private void save() {
        dialog = ProgressDialog.show(LifestyleProfileActivity.this, "Salvando", "Aguarde...");

        LifestyleProfile lifestyleProfile = new LifestyleProfile();

        SharedPreferences sharedPref = getApplicationContext().getSharedPreferences("user", Context.MODE_PRIVATE);
        String userId = sharedPref.getString("id", null);
        lifestyleProfile.setUser_id(userId);

        RadioGroup a = findViewById(R.id.a);
        lifestyleProfile.setNutritionA(getRadioButtonText(a));

        RadioGroup b = findViewById(R.id.b);
        lifestyleProfile.setNutritionB(getRadioButtonText(b));

        RadioGroup c = findViewById(R.id.c);
        lifestyleProfile.setNutritionC(getRadioButtonText(c));

        RadioGroup d = findViewById(R.id.d);
        lifestyleProfile.setPhysicalActivityD(getRadioButtonText(d));

        RadioGroup e = findViewById(R.id.e);
        lifestyleProfile.setPhysicalActivityE(getRadioButtonText(e));

        RadioGroup f = findViewById(R.id.f);
        lifestyleProfile.setPhysicalActivityF(getRadioButtonText(f));

        RadioGroup g = findViewById(R.id.g);
        lifestyleProfile.setPreventiveBehaviorG(getRadioButtonText(g));

        RadioGroup h = findViewById(R.id.h);
        lifestyleProfile.setPreventiveBehaviorH(getRadioButtonText(h));

        RadioGroup i = findViewById(R.id.i);
        lifestyleProfile.setPreventiveBehaviorI(getRadioButtonText(i));

        RadioGroup j = findViewById(R.id.j);
        lifestyleProfile.setRelationshipsJ(getRadioButtonText(j));

        RadioGroup k = findViewById(R.id.k);
        lifestyleProfile.setRelationshipsK(getRadioButtonText(k));

        RadioGroup l = findViewById(R.id.l);
        lifestyleProfile.setRelationshipsL(getRadioButtonText(l));

        RadioGroup m = findViewById(R.id.m);
        lifestyleProfile.setStressManagementM(getRadioButtonText(m));

        RadioGroup n = findViewById(R.id.n);
        lifestyleProfile.setStressManagementN(getRadioButtonText(n));

        RadioGroup o = findViewById(R.id.o);
        lifestyleProfile.setStressManagementO(getRadioButtonText(o));

        lifestyleProfile.setUser_id(userId);

        RetrofitService service = ServiceGenerator.createService(RetrofitService.class);

        service.updateLifestyleProfile(lifestyleProfileId,lifestyleProfile).enqueue(new Callback<RegisterUser>() {
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
                Toast.makeText(getApplicationContext(), "Erro ao salvar seu perfil", Toast.LENGTH_SHORT).show();
            }
        });

    }

    private String getRadioButtonText(RadioGroup rb) {
        int selectedId = rb.getCheckedRadioButtonId();
        RadioButton radioButton = findViewById(selectedId);
        return radioButton.getText().toString();
    }

    private void carregar() {
        dialog = ProgressDialog.show(LifestyleProfileActivity.this, "Carregando", "Aguarde...");
        RetrofitService service = ServiceGenerator.createService(RetrofitService.class);

        AuthenticateUser authenticate_user = new AuthenticateUser();
        authenticate_user.setId(userId);
        authenticate_user.setIs_get(true);

        service.getLifestyleProfileUser(authenticate_user).enqueue(new Callback<LifestyleProfile>() {
            @Override
            public void onResponse(Call<LifestyleProfile> call, Response<LifestyleProfile> response) {

                if (response.isSuccessful()) {
                    LifestyleProfile lifestyleProfile = response.body();
                    assert lifestyleProfile != null;
                    lifestyleProfileId = String.valueOf(lifestyleProfile.getId());

                    if (lifestyleProfile.getNutritionA() != null) {
                        checkRadio("a" + lifestyleProfile.getNutritionA());
                    } else {
                        checkRadio("a0");
                    }

                    if (lifestyleProfile.getNutritionB() != null) {
                        checkRadio("b" + lifestyleProfile.getNutritionB());
                    } else {
                        checkRadio("b0");
                    }

                    if (lifestyleProfile.getNutritionC() != null) {
                        checkRadio("c" + lifestyleProfile.getNutritionC());
                    } else {
                        checkRadio("c0");
                    }

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

                    if (lifestyleProfile.getPreventiveBehaviorG() != null) {
                        checkRadio("g" + lifestyleProfile.getPreventiveBehaviorG());
                    } else {
                        checkRadio("g0");
                    }

                    if (lifestyleProfile.getPreventiveBehaviorH() != null) {
                        checkRadio("h" + lifestyleProfile.getPreventiveBehaviorH());
                    } else {
                        checkRadio("h0");
                    }

                    if (lifestyleProfile.getPreventiveBehaviorI() != null) {
                        checkRadio("i" + lifestyleProfile.getPreventiveBehaviorI());
                    } else {
                        checkRadio("i0");
                    }

                    if (lifestyleProfile.getRelationshipsJ() != null) {
                        checkRadio("j" + lifestyleProfile.getRelationshipsJ());
                    } else {
                        checkRadio("j0");
                    }

                    if (lifestyleProfile.getRelationshipsK() != null) {
                        checkRadio("k" + lifestyleProfile.getRelationshipsK());
                    } else {
                        checkRadio("k0");
                    }

                    if (lifestyleProfile.getRelationshipsL() != null) {
                        checkRadio("l" + lifestyleProfile.getRelationshipsL());
                    } else {
                        checkRadio("l0");
                    }

                    if (lifestyleProfile.getStressManagementM() != null) {
                        checkRadio("m" + lifestyleProfile.getStressManagementM());
                    } else {
                        checkRadio("m0");
                    }

                    if (lifestyleProfile.getStressManagementN() != null) {
                        checkRadio("n" + lifestyleProfile.getStressManagementN());
                    } else {
                        checkRadio("n0");
                    }

                    if (lifestyleProfile.getStressManagementO() != null) {
                        checkRadio("o" + lifestyleProfile.getStressManagementO());
                    } else {
                        checkRadio("o0");
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
