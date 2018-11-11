package saude.funcional.atividade.exercicio.gofit.Model;

/**
 * ExerciseDone
 *
 * @author Ilgner Fagundes <ilgner552@gmail.com>
 * @version 1.0
 */
public class ExerciseDone {
    private String id;
    private Exercise exercise;
    private User user;
    private String execute_at;
    private String time_execute;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Exercise getExercise() {
        return exercise;
    }

    public void setExercise(Exercise exercise) {
        this.exercise = exercise;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getExecute_at() {
        return execute_at;
    }

    public void setExecute_at(String execute_at) {
        this.execute_at = execute_at;
    }

    public String getTime_execute() {
        return time_execute;
    }

    public void setTime_execute(String time_execute) {
        this.time_execute = time_execute;
    }
}
