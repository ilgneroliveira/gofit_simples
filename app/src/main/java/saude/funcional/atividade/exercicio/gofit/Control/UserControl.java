package saude.funcional.atividade.exercicio.gofit.Control;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.widget.EditText;
import android.widget.Toast;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import saude.funcional.atividade.exercicio.gofit.HomeActivity;
import saude.funcional.atividade.exercicio.gofit.Login;
import saude.funcional.atividade.exercicio.gofit.Model.AuthenticateUser;
import saude.funcional.atividade.exercicio.gofit.Model.RegisterUser;
import saude.funcional.atividade.exercicio.gofit.Model.User;
import saude.funcional.atividade.exercicio.gofit.R;
import saude.funcional.atividade.exercicio.gofit.Service.RetrofitService;
import saude.funcional.atividade.exercicio.gofit.Service.ServiceGenerator;

import static com.facebook.FacebookSdk.getApplicationContext;

public class UserControl {
    public static void saveUser(final User user, final Context context, final boolean show_message) {
        RetrofitService service = ServiceGenerator.createService(RetrofitService.class);

        service.saveOrAlterUser(user).enqueue(new Callback<RegisterUser>() {
            @Override
            public void onResponse(Call<RegisterUser> call, Response<RegisterUser> response) {

                if (response.isSuccessful()) {
                    if(show_message) {
                        RegisterUser registerUser = response.body();
                        Log.i("SaveUser", registerUser.getMessage());
                        if(registerUser.getSuccess() == 1){
                            SharedPreferences sharedPref =  getApplicationContext().getSharedPreferences("user",Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor = sharedPref.edit();
                            editor.putString("id", user.getLogin());
                            editor.apply();

                            Intent intent = new Intent(context, HomeActivity.class);
                            context.startActivity(intent);
                        }
                        Toast.makeText(context, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Log.i("SaveUser", "Erro ao salvar seu cadastro." + response.body().toString());
                }

            }

            @Override
            public void onFailure(Call<RegisterUser> call, Throwable t) {
                Log.i("Error", "post submitted to API." + call.toString());
                Toast.makeText(context, "Erro ao salvar seu cadastro", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public static void saveUser(final User user, final Context context, final boolean show_message, final ProgressDialog dialog, final EditText edEmail) {
        RetrofitService service = ServiceGenerator.createService(RetrofitService.class);

        service.saveOrAlterUser(user).enqueue(new Callback<RegisterUser>() {
            @Override
            public void onResponse(Call<RegisterUser> call, Response<RegisterUser> response) {

                if (response.isSuccessful()) {
                    if(show_message) {
                        dialog.dismiss();
                        RegisterUser registerUser = response.body();
                        Log.i("SaveUser", response.body().getMessage());
                        Toast.makeText(context, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                        if(registerUser.getSuccess() == 1){
                            SharedPreferences sharedPref =  getApplicationContext().getSharedPreferences("user",Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor = sharedPref.edit();
                            editor.putString("id", user.getLogin());
                            editor.apply();

                            Intent intent = new Intent(context, HomeActivity.class);
                            context.startActivity(intent);
                        }

                        edEmail.setBackgroundResource(R.drawable.edittext_border);
                    }
                } else {
                    Log.i("SaveUser", "Erro ao salvar seu cadastro." + response.body().toString());
                }

            }

            @Override
            public void onFailure(Call<RegisterUser> call, Throwable t) {
                Log.i("Error", "post submitted to API." + call.toString());
                dialog.dismiss();
                Toast.makeText(context, "Erro ao salvar seu cadastro", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
