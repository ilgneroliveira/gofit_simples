package saude.funcional.atividade.exercicio.gofit;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.Profile;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import saude.funcional.atividade.exercicio.gofit.Control.UserControl;
import saude.funcional.atividade.exercicio.gofit.Model.AuthenticateUser;
import saude.funcional.atividade.exercicio.gofit.Model.User;
import saude.funcional.atividade.exercicio.gofit.Service.RetrofitService;
import saude.funcional.atividade.exercicio.gofit.Service.ServiceGenerator;

public class Login extends AppCompatActivity {
    private CallbackManager callbackManager;
    ProgressDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

//        TextView signup = findViewById(R.id.signup);
//
//        signup.setOnClickListener(new View.OnClickListener() {
//            public void onClick(View v) {
//                Intent intent = new Intent(Login.this, RegisterActivity.class);
//                startActivity(intent);
//            }
//        });
//
//        TextView entrar = findViewById(R.id.entrar);
//
//        entrar.setOnClickListener(new View.OnClickListener() {
//            public void onClick(View v) {
//                authenticate();
//            }
//        });


        AccessToken accessToken = AccessToken.getCurrentAccessToken();
        boolean isLoggedIn = accessToken != null && !accessToken.isExpired();

        if (isLoggedIn) {
            SharedPreferences sharedPref =  getApplicationContext().getSharedPreferences("user",Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPref.edit();
            editor.putString("id", accessToken.getUserId());
            editor.apply();

            Intent intent = new Intent(Login.this, HomeActivity.class);
            startActivity(intent);
        }

        LoginButton loginButton = findViewById(R.id.login_button);
        List<String> facebookPermitions = Arrays.asList("user_photos ","email", "public_profile", "user_age_range", "user_birthday", "user_gender");
        loginButton.setReadPermissions(facebookPermitions);
        callbackManager = CallbackManager.Factory.create();
        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {

            @Override
            public void onSuccess(LoginResult loginResult) {
                dialog = ProgressDialog.show(Login.this, "Carregando", "Aguarde...");
                GraphRequest request = GraphRequest.newMeRequest(loginResult.getAccessToken(), new GraphRequest.GraphJSONObjectCallback() {
                    @Override
                    public void onCompleted(JSONObject object, GraphResponse response) {

                        try {
                            String id = object.getString("id");
                            String name = object.getString("name");
                            String email = object.getString("email");
                            String kind = object.getString("gender");
                            String birthday = object.getString("birthday");

                            @SuppressLint("SimpleDateFormat") SimpleDateFormat formato = new SimpleDateFormat("MM/dd/yyyy");
                            @SuppressLint("SimpleDateFormat") SimpleDateFormat formatoWebService = new SimpleDateFormat("dd/MM/yyyy");
                            Date data = formato.parse(birthday);

                            User user = new User();
                            user.setEmail(email);
                            user.setName(name);
                            user.setBirthDate(formatoWebService.format(data));
                            user.setKind(kind);
                            if (Profile.getCurrentProfile() != null) {
                                user.setImage(Profile.getCurrentProfile().getProfilePictureUri(200, 200).toString());
                            }

                            user.setLogin(id);
                            isAlreadyRegistered(user);
                        } catch (JSONException | ParseException e) {
                            e.printStackTrace();
                        }
                    }
                });

                Bundle parameters = new Bundle();
                parameters.putString("fields", "id,name,email,gender, birthday");
                request.setParameters(parameters);
                request.executeAsync();
            }

            @Override
            public void onCancel() {
                Log.i("TAG", "onCancel");
            }

            @Override
            public void onError(FacebookException error) {
                Log.i("TAG", "onError");
            }
        });
    }

    public void isAlreadyRegistered(final User user) {
        RetrofitService service = ServiceGenerator.createService(RetrofitService.class);
        AuthenticateUser authenticate_user = new AuthenticateUser();
        authenticate_user.setId(user.getLogin());

        service.isAlreadyRegistered(authenticate_user).enqueue(new Callback<AuthenticateUser>() {
            @Override
            public void onResponse(Call<AuthenticateUser> call, Response<AuthenticateUser> response) {

                if (response.isSuccessful()) {
                    if (!response.body().getIs_valid()) {
                        UserControl.saveUser(user, Login.this, false);
                    }else{
                        SharedPreferences sharedPref =  getApplicationContext().getSharedPreferences("user",Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPref.edit();
                        editor.putString("id", user.getLogin());
                        editor.apply();
                    }

                    dialog.dismiss();
                    Intent intent = new Intent(Login.this, HomeActivity.class);
                    startActivity(intent);
                } else {
                    // segura os erros de requisição
                    ResponseBody errorBody = response.errorBody();
                    Log.i("SaveUser", "Erro ao consultar usuario." + response.body().toString());

                    Toast.makeText(getApplicationContext(), "Erro ao salvar cadastro, tente novamente", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<AuthenticateUser> call, Throwable t) {
                Log.i("Error", "post submitted to API." + call.toString());
            }
        });
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }

//    private void authenticate() {
//        RetrofitService service = ServiceGenerator.createService(RetrofitService.class);
//
//        final EditText email = findViewById(R.id.email);
//        final EditText password = findViewById(R.id.password);
//
//        AuthenticateUser authenticate_user = new AuthenticateUser();
//        authenticate_user.setEmail(email.getText().toString());
//        authenticate_user.setPassword(password.getText().toString());
//
//        service.authenticateUser(authenticate_user).enqueue(new Callback<AuthenticateUser>() {
//            @Override
//            public void onResponse(Call<AuthenticateUser> call, Response<AuthenticateUser> response) {
//
//                if (response.isSuccessful()) {
//                    if (response.body().getIs_valid()) {
//                        SharedPreferences sharedPref =  getApplicationContext().getSharedPreferences("user",Context.MODE_PRIVATE);
//                        SharedPreferences.Editor editor = sharedPref.edit();
//                        editor.putString("id", response.body().getId());
//                        editor.apply();
//
//                        Intent intent = new Intent(Login.this, HomeActivity.class);
//                        startActivity(intent);
//                    } else {
//                        email.setBackgroundResource(R.drawable.edittext_border);
//                        password.setBackgroundResource(R.drawable.edittext_border);
//                        Toast.makeText(getApplicationContext(), "E-mail e senha fornecidos não encontrados. Verifique-as e tente novamente.", Toast.LENGTH_LONG).show();
//                    }
//                } else {
//                    Toast.makeText(getApplicationContext(), "Erro ao fazer login, tente novamente", Toast.LENGTH_LONG).show();
//                    // segura os erros de requisição
//                    ResponseBody errorBody = response.errorBody();
//                    Log.i("Error", "post submitted to API." + response.body().toString());
//                }
//
//            }
//
//
//            @Override
//            public void onFailure(Call<AuthenticateUser> call, Throwable t) {
//                Log.i("Error", "post submitted to API." + call.toString());
//                Toast.makeText(getApplicationContext(), "Erro ao fazer login, tente novamente", Toast.LENGTH_SHORT).show();
//            }
//        });
//    }

    @Override
    public void onBackPressed() {
        finish();
    }
}
