import com.google.common.collect.Multimap;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class Network {

    private final List<Integer> vertices;
    private final List<Edge> edges;
    //private final Multimap<Integer, Integer> edges;
    private Multimap<Integer, Integer> invertedEdges;

    public Network() {
        this.vertices = new LinkedList<>();
        // this.edges = LinkedListMultimap.create();
        this.edges = new ArrayList<>();
    }

//    public void finishConstruction() {
//        this.invertedEdges = Multimaps.invertFrom(edges, ArrayListMultimap.<Integer, Integer>create());
//    }

    //returns the list of all nodes/vertices
    public List<Integer> getVertices() {
        return vertices;
    }

    //no of nodes/vertices
    public Integer getNoVertices() {
        return vertices.size();
    }

    public List<Edge> getEdges() {
        return edges;
    }

    public void addVertex(int v) {
        this.vertices.add(v);
    }

    public void addEdge(int a, int b, double cost) {
        Edge edge = new Edge(a,b,cost);
        this.edges.add(edge);
    }

    public double getDistanceBetween(int fromCity, int toCity) {
        for(Edge edge : edges)
            if(edge.getX()==fromCity && edge.getY()==toCity)
                return edge.getCost();
            else if(edge.getX()==toCity && edge.getY()==fromCity)
                return edge.getCost();

        return 0;
    }

    //retunrs the degree of a node/vertex
//    public int calculateDegree(int vertex) {
//        int degree = 0;
//
//        degree += edges.get(vertex).size();
//        degree += invertedEdges.get(vertex).size();
//
//        return degree;
//    }

    //checks if exists Edge from vertexA to vertexB
//    public int existsEdge(int vertexA, int vertexB) {
//        if (edges.get(vertexA) != null) {
//            for (Integer v : edges.get(vertexA)) {
//                if (v == vertexB) {
//                    return 1;
//                }
//            }
//        }
//
//        if (invertedEdges.get(vertexA) != null) {
//            for (Integer v : invertedEdges.get(vertexA)) {
//                if (v == vertexB) {
//                    return 1;
//                }
//            }
//        }
//
//        return 0;
//    }

//    public int getNumberOfEdges() {
//        int aux = 0;
//        for (int i = 0; i < vertices.size(); ++i) {
//            if (edges.get(i) != null) {
//                aux += edges.get(i).size();
//            }
//        }
//
//        return aux;
//    }



}
