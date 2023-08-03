public class Toy {
    int id;
    String title;
    double weight;

    public Toy(int id, String title, double weight) {
        this.id = id;
        this.title = title;
        this.weight = weight;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(float weight) {
        this.weight = weight;
    }

    @Override
    public String toString() {
        return  id + ") " + title + "(" + weight + ")";
    }
}
