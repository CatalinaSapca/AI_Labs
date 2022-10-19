import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static double euclidianDistance(int ax, int ay, int bx, int by){
        return Math.sqrt(Math.pow(ax-bx,2) + Math.pow(ay-by,2));
    }

    public static void writeToFile(List<String> lines) throws IOException {
        BufferedWriter writer = new BufferedWriter(new FileWriter("data/out.txt"));
        for(String ss : lines)
            writer.write(ss);

        writer.close();
    }

    public static void run(ACO aco) throws IOException {
        int i = 0;
        List<String> lines = new ArrayList<>();
        while(true){
            i++;
            aco.initData();
            for(Ant ant : aco.getPopulation()) {
                for (int j = 1; j <= aco.getNetwork().getNoVertices() - 1; j++) {
                    ant.goToNext();
                }
            }
            Ant potentialBest = aco.getBestAnt();
            if(aco.getBest()==null || potentialBest.getTotalDistance()<aco.getBest().getTotalDistance())
                aco.setBest(potentialBest);

            //aco.updatePheromoneForTheBestAnt();

            String out="iter : "+ i +" cost: "+aco.getBest().getTotalDistance() + " path:" + aco.getBest().getPath() + "\n";
            lines.add(out);
            System.out.println(aco.getBest().getTotalDistance());


            if(lines.size()>1000) {
                writeToFile(lines);
                lines.clear();
            }

        }
    }

    public static void generate(Network network,double alpha, double beta, double q0, double degrad, int antsNr, int oneAntPheromone) throws IOException {
        StringBuilder output= new StringBuilder();
        ACO aco = new ACO(alpha, beta, q0, network, antsNr, degrad, oneAntPheromone);
        run(aco);
    }

    public static Network readNetwork(){
        Network graph = new Network();

        BufferedReader br = null;
        try{
            br = new BufferedReader(new FileReader("data/mediumF.txt"));
            int noVertices= Integer.parseInt(br.readLine());
            for(int i=0;i<noVertices;i++){
                graph.addVertex(i);

                String line= br.readLine();
                String[] arguments = line.split(",");

                for(int j=0;j<noVertices;j++)
                    graph.addEdge(i, j, Double.parseDouble(arguments[j]));
            }
        }
        catch (IOException ioe)
        {
            ioe.printStackTrace();
        }
        return graph;
    }

    public static Network readNetworkByCoordonates(){
        Network graph = new Network();

        //citirea grafului cu coordonate carteziene
        List<Integer> x = new ArrayList<>();
        List<Integer> y = new ArrayList<>();
        int noVertices=0;

        BufferedReader br1 = null;
        try{
            br1 = new BufferedReader(new FileReader("data/hardE.txt"));
            noVertices= Integer.parseInt(br1.readLine());
            for(int i=0;i<noVertices;i++){
                graph.addVertex(i);

                String line= br1.readLine();
                String[] arguments = line.split(" ");
                x.add(Integer.parseInt(arguments[1]));
                y.add(Integer.parseInt(arguments[2]));
            }
        }
        catch (IOException ioe)
        {
            ioe.printStackTrace();
        }

        for(int i=0;i<noVertices;i++)
            for(int j=0;j<noVertices;j++)
                graph.addEdge(i,j,euclidianDistance(x.get(i),y.get(i),x.get(j),y.get(j)));
        return graph;
    }

    public static void main(String[] args) throws IOException {
        System.out.println("Buna!");

        //---citirea grafului
        Network graph = readNetwork();

        //citirea grafului cu coordonate carteziene
        //Network graph = readNetworkByCoordonates();

        //----generare
        generate(graph,0.5, 0.5, 0.5,  0.2, 20, 15);
    }
}
