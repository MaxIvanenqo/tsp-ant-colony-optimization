import java.util.ArrayList;

class CityRelationship {
    float pheromone;
    float distance, visibility, probability;
    City cityFrom, cityTo;
    String routeName;
    ArrayList<ArrayList<CityRelationship>> matrix = new ArrayList<ArrayList<CityRelationship>>();
    private CityRelationship(float x1, float x2, float y1, float y2, String name1, String name2, float probability, City city1, City city2){
        this.distance = (float)Math.sqrt(Math.pow((x1 - x2), 2) + Math.pow((y1 - y2), 2));
        this.visibility = 1/this.distance;
        this.pheromone = 0.01f;
        this.routeName = name1 + name2;
        this.probability = probability;
        this.cityFrom = city1;
        this.cityTo = city2;
    }
    CityRelationship(ArrayList<City> arrCity){
        arrCity.forEach((i)->{
            ArrayList<CityRelationship> row = new ArrayList<CityRelationship>();
            arrCity.forEach((j)->{
                if (i == j) return;
                CityRelationship tmp = new CityRelationship(arrCity.get(i.index).x, arrCity.get(j.index).x, arrCity.get(i.index).y, arrCity.get(j.index).y, arrCity.get(i.index).name, arrCity.get(j.index).name, 0.0f, arrCity.get(i.index), arrCity.get(j.index));
                row.add(tmp);
            });
            this.matrix.add(row);
        });
    }
}
