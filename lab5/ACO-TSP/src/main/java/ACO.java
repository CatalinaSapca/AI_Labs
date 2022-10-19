import java.util.ArrayList;
import java.util.List;

public class ACO {
    final private double Alpha;
    final private double Beta;
    final private double q0;
    final private Network network;
    final private int antsNr;
    final private double degrad;
    final private int oneAntPheromone;
    final private double[][] pheromone;
    private List<Ant> population;
    private Ant best;

    public ACO(double alpha, double beta, double q0, Network network, int antsNr, double degrad, int oneAntPheromone) {
        Alpha = alpha;
        Beta = beta;
        this.q0 = q0;
        this.network = network;
        this.antsNr = antsNr;
        this.degrad = degrad;
        this.oneAntPheromone = oneAntPheromone;
        this.pheromone = new double[network.getNoVertices()+1][network.getNoVertices()+1];
        this.population = new ArrayList<>();
        this.best = null;
    }

    public double[][] getPheromone() {
        return pheromone;
    }

    public List<Ant> getPopulation() {
        return population;
    }

    public Network getNetwork() {
        return network;
    }

    public Ant getBest(){
        return best;
    }

    public double getDegrad() {
        return degrad;
    }

    public int getOneAntPheromone() {
        return oneAntPheromone;
    }

    public void setBest(Ant best) {
        this.best = best;
    }

    public double getAlpha() {
        return Alpha;
    }

    public double getBeta() {
        return Beta;
    }

    public void initData(){
        for(int v:network.getVertices())
            for(int w:network.getVertices()) {
                pheromone[v][w] = 1;
                pheromone[w][v] = 1;
            }

        this.population = new ArrayList<>();
        for(int i=0;i<this.antsNr;i++)
            population.add(new Ant(network,this, q0));
    }

    public Ant getBestAnt(){
        Ant best = population.get(0);
        double bestDist = best.getTotalDistance();
        for (Ant ant : population)
            if (ant.getTotalDistance() < bestDist) {
                best = ant;
                bestDist = best.getTotalDistance();
            }
        return best;
    }



    public double getPheromoneOnEdge(int i,int j){
        return pheromone[i][j];
    }


    public void updatePheromoneForTheBestAnt(){
        Ant bestAnt = this.getBest();
        for(int i=0;i<network.getNoVertices();i++)
            for(int j = i+1;j<network.getNoVertices();j++){
                int first = bestAnt.getPath().get(i);
                int second = bestAnt.getPath().get(j);
                double oldPheromone = pheromone[first][second];
                pheromone[first][second] = (1 - this.degrad) * oldPheromone + this.degrad * (1/bestAnt.getTotalDistance());
                pheromone[second][first] = (1 - this.degrad) * oldPheromone + this.degrad * (1/bestAnt.getTotalDistance());
            }
        int first = bestAnt.getPath().get(bestAnt.getPath().size()-1);
        int second = bestAnt.getPath().get(0);
        double oldPheromone = pheromone[first][second];
        pheromone[first][second] = (1 - this.degrad) * oldPheromone + this.degrad * (1/bestAnt.getTotalDistance());
        pheromone[second][first] = (1 - this.degrad) * oldPheromone + this.degrad * (1/bestAnt.getTotalDistance());
    }



}
