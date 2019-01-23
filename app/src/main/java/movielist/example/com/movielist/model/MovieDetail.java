package movielist.example.com.movielist.model;

public class MovieDetail {
    private String name;
    private float score;
    private Actor[] actors;
    private String description;

    public MovieDetail() {

    }

    public Actor[] getActors() {
        return actors;
    }

    public String getDescription() {
        return description;
    }

    public float getScore() {
        return score;
    }

    public void setActors(Actor[] actors) {
        this.actors = actors;
    }
}
