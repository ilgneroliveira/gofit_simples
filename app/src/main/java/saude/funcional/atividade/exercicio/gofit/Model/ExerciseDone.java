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
    private String executeAt;
    private String timeExecute;

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
        return executeAt;
    }

    public void setExecute_at(String execute_at) {
        this.executeAt = execute_at;
    }

    public String getTimeExecute() {
        return timeExecute;
    }

    public void setTimeExecute(String timeExecute) {
        this.timeExecute = timeExecute;
    }
}
