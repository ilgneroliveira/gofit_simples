package saude.funcional.atividade.exercicio.gofit.Model;

import java.util.List;

/**
 * SearchExercise
 *
 * @author Ilgner Fagundes <ilgner552@gmail.com>
 * @version 1.0
 */
public class SearchExercise {

    private String search_term;
    private List<Exercise> exercises;

    public String getSearch_term() {
        return search_term;
    }

    public void setSearch_term(String search_term) {
        this.search_term = search_term;
    }

    public List<Exercise> getExercises() {
        return exercises;
    }

    public void setExercises(List<Exercise> exercises) {
        this.exercises = exercises;
    }
}
