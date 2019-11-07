import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        ArrayList<City> cities = new ArrayList<>();
        cities.add(new City(1.0f, 1.0f, "A"));
        cities.add(new City(3.0f, 1.0f, "B"));
        cities.add(new City(5.0f, 3.0f, "C"));
        cities.add(new City(2.0f, 8.0f, "D"));
        cities.add(new City(8.0f, 8.0f, "E"));

        CityRelationship relMatrix = new CityRelationship(cities);

        Ant ants = new Ant(relMatrix.matrix);
        ants.Run(100000, "B");
    }
}
