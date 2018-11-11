package saude.funcional.atividade.exercicio.gofit.Model;

/**
 * Exercise
 *
 * @author Ilgner Fagundes <ilgner552@gmail.com>
 * @version 1.0
 */
public class Exercise {
    private String id;
    private String title;
    private String description;
    private String amount_executed;
    private String media_url;
    private String media_type;
    private String question_one;
    private String weight_question_one;
    private String question_tw;
    private String weight_question_two;
    private String question_three;
    private String weight_question_three;
    private String featuredImageUrl;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAmount_executed() {
        return amount_executed;
    }

    public void setAmount_executed(String amount_executed) {
        this.amount_executed = amount_executed;
    }

    public String getMedia_url() {
        return media_url;
    }

    public void setMedia_url(String media_url) {
        this.media_url = media_url;
    }

    public String getMedia_type() {
        return media_type;
    }

    public void setMedia_type(String media_type) {
        this.media_type = media_type;
    }

    public String getQuestion_one() {
        return question_one;
    }

    public void setQuestion_one(String question_one) {
        this.question_one = question_one;
    }

    public String getWeight_question_one() {
        return weight_question_one;
    }

    public void setWeight_question_one(String weight_question_one) {
        this.weight_question_one = weight_question_one;
    }

    public String getQuestion_tw() {
        return question_tw;
    }

    public void setQuestion_tw(String question_tw) {
        this.question_tw = question_tw;
    }

    public String getWeight_question_two() {
        return weight_question_two;
    }

    public void setWeight_question_two(String weight_question_two) {
        this.weight_question_two = weight_question_two;
    }

    public String getQuestion_three() {
        return question_three;
    }

    public void setQuestion_three(String question_three) {
        this.question_three = question_three;
    }

    public String getWeight_question_three() {
        return weight_question_three;
    }

    public void setWeight_question_three(String weight_question_three) {
        this.weight_question_three = weight_question_three;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFeaturedImageUrl() {
        return featuredImageUrl;
    }

    public void setFeaturedImageUrl(String featuredImageUrl) {
        this.featuredImageUrl = featuredImageUrl;
    }
}
