package saude.funcional.atividade.exercicio.gofit;


import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.facebook.AccessToken;
import com.facebook.login.LoginManager;

import static com.facebook.FacebookSdk.getApplicationContext;


/**
 * A simple {@link Fragment} subclass.
 */
public class MenuFragment extends Fragment {


    public MenuFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_menu, container, false);

        TextView tvSair = view.findViewById(R.id.tvSair);
        tvSair.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logout();
            }
        });

        TextView tvPerfilEstiloVida = view.findViewById(R.id.tvPerfilEstiloVida);
        tvPerfilEstiloVida.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                perfilEstiloVida();
            }
        });


        return view;
    }

    public void perfilEstiloVida() {
        Intent intent = new Intent(getActivity(), LifestyleProfileActivity.class);
        startActivity(intent);
    }

    public void logout() {
        ProgressDialog dialog = ProgressDialog.show(getActivity(), "Aguarde", "Saindo...");

        AccessToken accessToken = AccessToken.getCurrentAccessToken();
        boolean isLoggedIn = accessToken != null && !accessToken.isExpired();
        if (isLoggedIn) {
            LoginManager lg = LoginManager.getInstance();
            lg.logOut();
        }

        SharedPreferences sharedPref =  getApplicationContext().getSharedPreferences("user", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString("id", null);
        editor.apply();

        Intent intent = new Intent(getActivity(), Login.class);
        startActivity(intent);
        dialog.dismiss();
    }

}
