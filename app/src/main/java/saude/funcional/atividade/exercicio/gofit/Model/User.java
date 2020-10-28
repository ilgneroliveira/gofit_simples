package saude.funcional.atividade.exercicio.gofit.Model;

import android.net.Uri;

/**
 * User
 *
 * @author Ilgner Fagundes <ilgner552@gmail.com>
 * @version 1.0
 */
public class User {

    private String id;

    private String name;

    private String kind;

    private String image;

    private String birthDate;

    private String weight;

    private String height;

    private String availableTime;

    private String email;

    private String password;

    private String login;

    private Uri picture;

    private Uri public_profile;

    private String permissions;

    public User(Uri picture, String name,
                String id, String email, String permissions) {
//        this.public_profile = public_profile;
        this.picture = picture;
        this.name = name;
        this.id = id;
        this.email = email;
        this.permissions = permissions;
    }

    public User(){

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getKind() {
        return kind;
    }

    public void setKind(String kind) {
        this.kind = kind;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(String birth_date) {
        this.birthDate = birth_date;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public String getHeight() {
        return height;
    }

    public void setHeight(String height) {
        this.height = height;
    }

    public String getAvailableTime() {
        return availableTime;
    }

    public void setAvailableTime(String available_time) {
        this.availableTime = available_time;
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

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public Uri getPicture() {
        return picture;
    }

    public String getPermissions() {
        return permissions;
    }

    public void setPicture(Uri picture) {
        this.picture = picture;
    }

    public Uri getPublic_profile() {
        return public_profile;
    }

    public void setPublic_profile(Uri public_profile) {
        this.public_profile = public_profile;
    }

    public void setPermissions(String permissions) {
        this.permissions = permissions;
    }
}
