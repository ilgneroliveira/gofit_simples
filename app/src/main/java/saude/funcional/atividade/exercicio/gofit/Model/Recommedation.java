package saude.funcional.atividade.exercicio.gofit.Model;

import java.util.List;

/**
 * Recommedation
 *
 * @author Ilgner Fagundes <ilgner552@gmail.com>
 * @version 1.0
 */
public class Recommedation {
    private List<ExerciseRecommedation> exercises;
    private List<ExerciseRecommedation> exercises_done;

    public List<ExerciseRecommedation> getExercises() {
        return exercises;
    }

    public void setExercises(List<ExerciseRecommedation> exercises) {
        this.exercises = exercises;
    }

    public List<ExerciseRecommedation> getExercises_done() {
        return exercises_done;
    }

    public void setExercises_done(List<ExerciseRecommedation> exercises_done) {
        this.exercises_done = exercises_done;
    }
}
