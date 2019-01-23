package movielist.example.com.movielist.model;

public class Actor {
    private String name;
    private int age;

    private String imageUrl;

    public Actor() {

    }

    public Actor(String name, int age) {
        this.name = name;
        this.age = age;
        imageUrl = "";
    }

    public Actor(String name, int age, String imageUrl) {
        this.name = name;
        this.age = age;
        this.imageUrl = imageUrl;
    }

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    public String getImageUrl() {
        return imageUrl;
    }
}
