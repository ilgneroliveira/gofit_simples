package saude.funcional.atividade.exercicio.gofit;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import saude.funcional.atividade.exercicio.gofit.Control.ExerciseControl;

import static com.facebook.FacebookSdk.getApplicationContext;

/**
 * A simple {@link Fragment} subclass.
 */
public class StartFragment extends Fragment {

    String userId;
    ProgressDialog dialog;

    public StartFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_start, container, false);

        SharedPreferences sharedPref = getApplicationContext().getSharedPreferences("user", Context.MODE_PRIVATE);
        userId = sharedPref.getString("id", null);

        dialog = ProgressDialog.show(inflater.getContext(), "Carregando", "Aguarde...");
        ExerciseControl.recommendation(view,userId,getApplicationContext());
        dialog.dismiss();
        return view;
    }

}
