package saude.funcional.atividade.exercicio.gofit;


import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import saude.funcional.atividade.exercicio.gofit.Adapter.SearchAdapter;
import saude.funcional.atividade.exercicio.gofit.Model.SearchExercise;
import saude.funcional.atividade.exercicio.gofit.Service.RetrofitService;
import saude.funcional.atividade.exercicio.gofit.Service.ServiceGenerator;

import static com.facebook.FacebookSdk.getApplicationContext;


/**
 * A simple {@link Fragment} subclass.
 */
public class SearchFragment extends Fragment {
    ProgressDialog dialog;
    EditText btSearch;
    GridView lvResult;
    TextView tvNoResult;
    SearchExercise exercises;

    public SearchFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_search, container, false);

        btSearch = view.findViewById(R.id.btSearch);

        btSearch.setOnEditorActionListener(
                new EditText.OnEditorActionListener() {
                    @Override
                    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                        if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                            dialog = ProgressDialog.show(getActivity(), "Buscando", "Aguarde...");
                            search();
                        }
                        return false; // pass on to other listeners.
                    }
                }
        );

        tvNoResult = view.findViewById(R.id.tvNoResult);
        lvResult = view.findViewById(R.id.lvResult);
        lvResult.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getApplicationContext(), ShowExersiceActivity.class);
                intent.putExtra("id", exercises.getExercises().get(position).getId());
                getApplicationContext().startActivity(intent);

            }
        });

        // Inflate the layout for this fragment
        return view;
    }

    private void search() {
        RetrofitService service = ServiceGenerator.createService(RetrofitService.class);

        SearchExercise search_exercise = new SearchExercise();
        search_exercise.setSearch_term(btSearch.getText().toString());

        service.searchexercises(search_exercise).enqueue(new Callback<SearchExercise>() {
            @Override
            public void onResponse(Call<SearchExercise> call, Response<SearchExercise> response) {

                if (response.isSuccessful()) {
                    exercises = response.body();

                    //verifica aqui se o corpo da resposta não é nulo
                    if (exercises != null) {
//                        List<String> names = new ArrayList<String>();
                        final SearchAdapter namesAdapter = new SearchAdapter(getApplicationContext(), exercises.getExercises());
                        lvResult.setAdapter(namesAdapter);
                        dialog.dismiss();

                        String title = "Nenhum resultado encontrado";
                        if(exercises.getExercises().size() > 0){
                            title = "Exercícios";
                        }

                        tvNoResult.setText(title);
                    } else {
                        dialog.dismiss();
                        Toast.makeText(getApplicationContext(), "Resposta nula do servidor", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(getActivity(), "Erro ao buscar Exercícios, tente novamente", Toast.LENGTH_LONG).show();
                    // segura os erros de requisição
                    ResponseBody errorBody = response.errorBody();
                    Log.i("Error", "post submitted to API." + response.body().toString());
                }

            }


            @Override
            public void onFailure(Call<SearchExercise> call, Throwable t) {
                Log.i("Error", "post submitted to API." + call.toString());
                Toast.makeText(getActivity(), "Erro ao buscar Exercícios, tente novamente", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
