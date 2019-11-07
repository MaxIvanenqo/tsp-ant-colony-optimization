class City {
    String name;
    boolean visited;
    float x, y;
    int index;
    private static int indexStatic = 0;
    City(float x, float y, String name){
        this.x = x;
        this.y = y;
        this.name = name;
        this.visited = false;
        this.index = indexStatic++;
    }
}
