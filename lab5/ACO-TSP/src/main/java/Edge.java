public class Edge {
    private int x;
    private int y;
    private double cost;

    public Edge(int x, int y, double cost){
        this.x=x;
        this.y=y;
        this.cost=cost;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public double getCost() {
        return cost;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }
}
