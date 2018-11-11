package saude.funcional.atividade.exercicio.gofit.Model;

/**
 * User
 *
 * @author Ilgner Fagundes <ilgner552@gmail.com>
 * @version 1.0
 */
public class AuthenticateUser {
    private String id;
    private String email;
    private String password;
    private boolean is_valid;
    private boolean is_get;

    public boolean getIs_valid() {
        return is_valid;
    }

    public void setIs_valid(boolean is_valid) {
        this.is_valid = is_valid;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public boolean isIs_get() {
        return is_get;
    }

    public void setIs_get(boolean is_get) {
        this.is_get = is_get;
    }
}
