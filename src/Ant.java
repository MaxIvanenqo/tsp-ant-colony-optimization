import java.util.ArrayList;
import java.util.Comparator;
import java.util.Formatter;
import java.lang.Float;

class Ant {
    private ArrayList<ArrayList<CityRelationship>> roads = new ArrayList<ArrayList<CityRelationship>>();
    private static final float alpha = 1.0f;
    private static final float beta = 1.0f;
    private static final float vaporizationConst = 0.5f;
    private float length = 0.0f;

    Ant(ArrayList<ArrayList<CityRelationship>> matrix){
        this.roads = matrix;
    }

    void Run(int rounds, String startPoint){
        ArrayList<String> arrTmp = new ArrayList<>();
        for (ArrayList<CityRelationship> road : this.roads) arrTmp.add(road.get(0).cityFrom.name);
        int indexStart = arrTmp.indexOf(startPoint);
        int indexCalc = indexStart;
        do {
            choosingDirection(indexCalc, 0);
            if (++indexCalc == this.roads.size()) indexCalc = 0;
            for (ArrayList<CityRelationship> road : this.roads)
                for (CityRelationship cityRelationship : road) cityRelationship.cityTo.visited = false;
        }
        while (rounds-- > 0);
        getResult(indexStart);
    }

    private void choosingDirection(int indexStart, int n){
        if (n == this.roads.size()) return;
        calcProb(indexStart);
        double R = Math.random();
        for (int j = 0; j < this.roads.get(indexStart).size(); ++j){
            if (this.roads.get(indexStart).get(j).cityTo.visited) continue;
            if (((this.roads.get(indexStart).get(j).probability - this.roads.get(indexStart).get(this.roads.get(indexStart).size()-1).probability) - R) > 0) continue;
            this.roads.get(indexStart).get(j).cityTo.visited = true;
            this.roads.get(indexStart).get(j).pheromone +=  (1-vaporizationConst)*this.roads.get(indexStart).get(j).visibility + this.roads.get(indexStart).get(j).visibility;
            choosingDirection(this.roads.get(indexStart).get(j).cityTo.index, ++n);
        }
    }

    private void calcProb(int i){
        float denominator = 0.0f;
        for (int j = 0; j < this.roads.get(i).size(); ++j){
            denominator += (float)Math.pow(this.roads.get(i).get(j).pheromone, alpha)*Math.pow(this.roads.get(i).get(j).visibility, beta);
        }
        for (int j = 0; j < this.roads.get(i).size(); ++j){
            this.roads.get(i).get(j).probability = (float)(Math.pow(this.roads.get(i).get(j).pheromone, alpha)*Math.pow(this.roads.get(i).get(j).visibility, beta)/denominator);
        }
        for (int j = 0; j < this.roads.get(i).size(); ++j){
            this.roads.get(i).sort(new Comparator<>() {
                public int compare(CityRelationship o1, CityRelationship o2) {
                    return Float.compare(o2.probability, o1.probability);
                }
            });
        }
    }

    private void getResult(int indexStart){
        System.out.println("\nMatrix:");
        for (int i = 0; i < roads.size(); ++i){
            System.out.println("\nStart point " + this.roads.get(i).get(i%this.roads.get(i).size()).routeName.substring(0,1) + ":");
            for (int j = 0; j < this.roads.get(i).size(); ++j){
                Formatter formatter = new Formatter();
                formatter.format("\tdata for %s -> distance: %f, probability: %f, pheromone level: %f", this.roads.get(i).get(j).routeName.substring(1,2), this.roads.get(i).get(j).distance, this.roads.get(i).get(j).probability, this.roads.get(i).get(j).pheromone);
                String formattedString = formatter.toString();
                System.out.println(formattedString);
            }
        }
        System.out.print("\nOptimal route: " + this.roads.get(indexStart).get(0).cityFrom.name + "-> ");
        int i = printBest(indexStart);
        this.length+=this.roads.get(indexStart).get(i).distance;
        System.out.print(this.roads.get(indexStart).get(0).cityFrom.name + "\n" + this.length);
    }

    private int printBest(int indexStart) {
        int last = 0;
        for (int j = 0; j < this.roads.get(indexStart).size(); ++j) {
            this.roads.get(indexStart).get(j).cityFrom.visited = true;
            if (this.roads.get(indexStart).get(j).cityTo.visited) continue;
            this.length+=this.roads.get(indexStart).get(j).distance;
            System.out.print(this.roads.get(indexStart).get(j).cityTo.name + "-> ");
            last = this.roads.get(indexStart).get(j).cityTo.index;
            printBest(last);
        }
        return last;
    }
}
