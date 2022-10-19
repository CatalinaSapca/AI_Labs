import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Ant {
    private final ACO colony;
    private final List<Integer> path;
    private final List<Integer> visited;
    private final Network distanceGraph;
    private double total;
    private final double q0;

    public Ant(Network distanceGraph, ACO colony, double q0) {
        this.path = new ArrayList<>();
        this.visited = new ArrayList<>();
        this.distanceGraph = distanceGraph;
        this.total = 0D;
        this.q0 = q0;
        this.colony = colony;

        //plasare furnica intr un oras aleator
        //se adauga nodul de start in vectorul de noduri vizitate
        Random r = new Random();
        int start = r.nextInt(distanceGraph.getNoVertices());
        visited.add(start);
        path.add(start);
    }

    public void goToNext(){
        double q = Math.random();
        int nextNode;
        if(q < this.q0)
            nextNode = this.calculateNextNode();
        else
            nextNode = this.calculateProbability();
        this.path.add(nextNode);
        this.visited.add(nextNode);
        int first = path.get(path.size()-2);
        int second = path.get(path.size()-1);
        this.total = this.total + this.distanceGraph.getDistanceBetween(first,second);

        double oldPheromone = colony.getPheromone()[first][second];

        //actualizare feromon dupa deplasarea cu o muchie
        colony.getPheromone()[first][second] = (1-colony.getDegrad()) * oldPheromone + colony.getDegrad() * colony.getOneAntPheromone();

    }

    public int calculateNextNode(){
        int nextNode = -1;
        double maxi = -1.0;
        int last = this.path.get(path.size()-1);
        for(int i = 0;i<distanceGraph.getNoVertices();i++)
            if(!visited.contains(i)) {
                double rez = Math.pow(colony.getPheromoneOnEdge(last,i), colony.getAlpha())*Math.pow(1/distanceGraph.getDistanceBetween(last,i),colony.getBeta());
                if(rez > maxi){
                     maxi = rez;
                     nextNode = i;
                }
            }

        return nextNode;
    }

    public int calculateProbability(){
        int nextNode = -1;
        int maxi = -1;
        double summ = 0;
        int last = path.get(path.size()-1);
        for(int i = 0;i<distanceGraph.getNoVertices() ;i++)
            if(!visited.contains(i))
                summ = summ + Math.pow(colony.getPheromoneOnEdge(last,i), colony.getAlpha())*Math.pow(1/distanceGraph.getDistanceBetween(last,i),colony.getBeta());

        List<Double> partialProbs = new ArrayList<>();
        List<Integer> nodes = new ArrayList<>();
        partialProbs.add(0D);
        nodes.add(-1);

        for(int i=0;i<distanceGraph.getNoVertices();i++)
            if(!visited.contains(i)){
                double up = Math.pow(colony.getPheromoneOnEdge(last,i), colony.getAlpha())*Math.pow(1/distanceGraph.getDistanceBetween(last,i),colony.getBeta());
                double rez = up/summ;
                nodes.add(i);
                partialProbs.add(partialProbs.get(partialProbs.size()-1)+rez);
            }

        Random random = new Random();
        double r = random.nextDouble()*(partialProbs.get(partialProbs.size()-1));
        int index = 0;
        while(index !=partialProbs.size() && r>partialProbs.get(index))
            index++;

        if(index==partialProbs.size())
            index--;

        return nodes.get(index);

    }

    public List<Integer> getPath(){
        return this.path;
    }
     public double getTotalDistance(){
        return this.total + this.distanceGraph.getDistanceBetween(path.get(path.size()-1), path.get(0));
     }

    public Network getDistanceGraph() {
        return distanceGraph;
    }

    public List<Integer> getVisited() {
        return visited;
    }
}
