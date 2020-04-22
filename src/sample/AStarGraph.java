package sample;

import java.util.ArrayList;
import java.util.PriorityQueue;

public class AStarGraph {

    private ArrayList<Vertex> vertices;
    public AStarGraph() {
        vertices = new ArrayList<Vertex>();
    }

    public void addvertex(Vertex v) {
        vertices.add(v);
    }
    public void newconnection(Vertex v1, Vertex v2, Double dist) {
        v1.addOutEdge(v2,dist);
        v2.addOutEdge(v1,dist);
    }



    public boolean A_Star(Vertex start, Vertex destination, boolean useManhattan)
    {

        if (start==null || destination==null)
        return false;
        PriorityQueue<Vertex> Openlist = new PriorityQueue<Vertex>();
        ArrayList<Vertex> Closedlist = new ArrayList<Vertex>();
        Openlist.offer(start);
        Vertex Current;
        ArrayList<Vertex> CurrentNeighbors;
        Vertex Neighbor;

        /*
        Initialize h with chosen heuristic
        Use either Manhattan or Euclidean as heuristic function
         */

        if (useManhattan){
            manhattanHeuristic(destination);
        } else {
            euclideanHeuristic(destination);
        }

        start.setg(0.0);
        start.calculatef();
        //System.out.println("Start Algorithm");
        /*
        Implement the A* algorithm
        Start the While loop
         */
        while (!Openlist.isEmpty()){

            Current = Openlist.poll();
            if (Current == destination) {
                return true;
            }
            Closedlist.add(Current);
            for (int i=0; i<Current.getNeighbours().size(); i++) {
                Double tempGOfV = Current.getg() + Current.getNeighbourDistance().get(i);
                /*
                tempV = neighbours to current
                 */
                Vertex tempV = Current.getNeighbours().get(i);
                if (tempGOfV < tempV.getg()) {
                    tempV.setPrev(Current);
                    tempV.setg(tempGOfV);
                    tempV.calculatef();

                    if (!Closedlist.contains(tempV) && !Openlist.contains(tempV)){
                        Openlist.add(tempV);
                    }
                }
            }

        }

        return false;
    }


    public void manhattanHeuristic(Vertex destination){
        for (int i=0; i<vertices.size();i++){
            vertices.get(i).seth(Manhattan(vertices.get(i),destination));
            /*
            Print this, to see the different h value, when using the different heuristic functions.
             */
            //System.out.println(vertices.get(i).getid() + ".h: " + vertices.get(i).geth());
        }
    }

    public void euclideanHeuristic(Vertex destination){
        for (int i=0; i<vertices.size();i++){
            vertices.get(i).seth(Euclidean(vertices.get(i),destination));
            /*
            Print this, to see the different h value, when using the different heuristic functions.
             */
            //System.out.println(vertices.get(i).getid() + ".h: " + vertices.get(i).geth());
        }
    }


    public Double Manhattan(Vertex currentVertex ,Vertex destination){
        int h = Math.abs(currentVertex.getx() - destination.getx()) + Math.abs(currentVertex.gety() - destination.gety());
        return Double.valueOf(h);

    }

    public Double Euclidean( Vertex currentVertex,Vertex destination){
        double h = Math.pow((currentVertex.getx() - destination.getx()),2.0) + Math.pow((currentVertex.gety() - destination.gety()),2.0);
        return Math.sqrt(h);

    }

    public ArrayList<Vertex> getVertices() {
        return vertices;
    }

    public static AStarGraph createGraph() {

        AStarGraph MyMaze = new AStarGraph();

        Vertex A = new Vertex("A", 0, 4);
        Vertex B = new Vertex("B", 1, 7);
        Vertex C = new Vertex("C", 4, 0);
        Vertex D = new Vertex("D", 3, 7);
        Vertex E = new Vertex("E", 3, 3);
        Vertex F = new Vertex("F", 6, 6);
        Vertex G = new Vertex("G", 7, 2);
        Vertex H = new Vertex("H", 8, 7);
        Vertex I = new Vertex("I", 9, 2);
        Vertex J = new Vertex("J", 11, 5);

        /*
        add vertices to MyMaze, using addvertex()
         */
        MyMaze.addvertex(A);
        MyMaze.addvertex(B);
        MyMaze.addvertex(C);
        MyMaze.addvertex(D);
        MyMaze.addvertex(E);
        MyMaze.addvertex(F);
        MyMaze.addvertex(G);
        MyMaze.addvertex(H);
        MyMaze.addvertex(I);
        MyMaze.addvertex(J);

        /*
        add distance to vertices
         */
        MyMaze.newconnection(A, B, 3.41);
        MyMaze.newconnection(A, C, 6.82);

        MyMaze.newconnection(B, D, 2.0);

        MyMaze.newconnection(C, G, 4.41);
        MyMaze.newconnection(C, I, 4.82);

        MyMaze.newconnection(D, E, 4.0);

        MyMaze.newconnection(E, F, 6.23);

        MyMaze.newconnection(F, G, 4.41);
        MyMaze.newconnection(F, H, 3.82);

        MyMaze.newconnection(G, H, 5.41);
        MyMaze.newconnection(G, I, 2.82);

        MyMaze.newconnection(H, J, 4.41);

        MyMaze.newconnection(I, J, 3.82);


        return MyMaze;
    }

} // End of AStarGraph class


// Comparable because we want to find the f with the minimum value
class Vertex implements Comparable<Vertex>{
    private String id;
    private ArrayList<Vertex> Neighbours = new ArrayList<Vertex>();
    private ArrayList<Double> NeighbourDistance = new ArrayList<Double>();
    private Double f; //f = total estimated cost/how far it is from the origin to goal.
    private Double g; //g = the cost so far. The distance from origin to the current vertex. Always true.
    private Double h; //h = estimate form current vertex to the end. g+h=f
    private Integer x; //coordinates of our vertex - used to calculate h
    private Integer y; //coordinates of our vertex - used to calculate h

    private Vertex prev =null;

    /*
    Constructor for setting start and end vertex.
     */
    public Vertex(){
    }

    /*
    Constructor for vertex.
     */
    public Vertex(String id, int x_cor,int y_cor){
        this.id = id;
        this.x = x_cor;
        this.y = y_cor;
        f=Double.POSITIVE_INFINITY;
        g=Double.POSITIVE_INFINITY;
        h=0.0; // Will change when running A*, depending on which estimated function (Manhattan/Euclidean)
    }
    public void addOutEdge(Vertex toV, Double dist){
        Neighbours.add(toV);
        NeighbourDistance.add(dist);
    }
    public ArrayList<Vertex> getNeighbours(){
        return Neighbours;
    }
    public ArrayList<Double> getNeighbourDistance(){
        return NeighbourDistance;
    }
    public String getid(){ return id; };
    public Integer getx(){ return x; }
    public Integer gety(){return y; }
    public Double getf() { return f; }
    public void calculatef(){ f=g+h; }

    public Double getg() { return g; }

    public void setg(Double newg){ g=newg; }
    public Double geth(){ return h; }
    public void seth(Double estimate){ h=estimate; }
    public Vertex getPrev() { return prev; }
    public void setPrev(Vertex v){prev=v;}
    public void printVertex(){
        System.out.println(id + " g: "+g+ ", h: "+h+", f: "+f);
    }

    @Override
    public int compareTo(Vertex o) {
        if (f == o.f){
            return 0;
        }
        else if (f > o.f){
            return 1;
        }
        else  {
            return -1;
        }
    }
/*
Used to display vertex to string in ComboBox
 */
    @Override
    public String toString() {
        return id;
    }

}
