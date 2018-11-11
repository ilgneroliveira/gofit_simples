package saude.funcional.atividade.exercicio.gofit.Model;

/**
 * ExerciseDone
 *
 * @author Ilgner Fagundes <ilgner552@gmail.com>
 * @version 1.0
 */
public class ExerciseDoneSave {
    private String id;
    private String exercise_id;
    private String user_id;
    private String time_execute;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getExercise_id() {
        return exercise_id;
    }

    public void setExercise_id(String exercise_id) {
        this.exercise_id = exercise_id;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getTime_execute() {
        return time_execute;
    }

    public void setTime_execute(String time_execute) {
        this.time_execute = time_execute;
    }
}
