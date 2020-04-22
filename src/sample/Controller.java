package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.event.ActionEvent;

import javax.swing.*;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Stack;

public class Controller {


    /*
    our FXML elements we need to grab
     */

    @FXML
    ComboBox<Vertex> selectStartVertex;

    @FXML
    ComboBox<Vertex> selectEndVertex;

    @FXML
    ComboBox<String> selectHeuristicFunction;

    @FXML
    Button printShortestPath;

    @FXML
    TextArea shortestPathTA;


    private AStarGraph graph;

    public Controller() {
        graph = AStarGraph.createGraph();
    }

    public void initialize(){
        addDataToComboBoxes();
    }

    /*
    Assigning all vertices to ComboBox "selectStartVertex"
    */
    public void addDataToComboBoxes () {
        ObservableList<Vertex> vertices = FXCollections.observableArrayList(graph.getVertices());
        selectStartVertex.setItems(vertices);
        selectStartVertex.setValue(graph.getVertices().get(0));


        selectEndVertex.setItems(vertices);
        selectEndVertex.setValue(graph.getVertices().get(0));

        ArrayList<String> heuristicFunctions = new ArrayList<String>();
        heuristicFunctions.add("Manhattan");
        heuristicFunctions.add("Euclidean");

        ObservableList<String> heuristicFunctionList = FXCollections.observableArrayList(heuristicFunctions);
        selectHeuristicFunction.setItems(heuristicFunctionList);

        selectHeuristicFunction.setValue("Manhattan");
    }

    @FXML
    public void clickBtnShortestPath(ActionEvent actionEvent){

        Vertex start = selectStartVertex.getValue();
        Vertex end = selectEndVertex.getValue();

        NumberFormat formatter = new DecimalFormat("#,##0");

        boolean useManhattan = true;

        if (selectHeuristicFunction.getValue().equals("Manhattan")){
           useManhattan = true;
        } else{
            useManhattan = false;
        }

        long startTime = System.nanoTime();

        boolean pathFound = graph.A_Star(start, end, useManhattan);

        long endTime   = System.nanoTime();
        long totalTime = endTime - startTime;
        String totalTimeString = formatter.format(totalTime);


        System.out.println("start time: "+startTime+" end time: "+endTime);

        String runningTimeText;

        if (useManhattan){
            runningTimeText = "Total running time using Manhattan: "+totalTimeString;
        } else {
            runningTimeText = "Total running time using Euclidean: "+totalTimeString;
        }
        System.out.println(runningTimeText);


        if (pathFound){
            shortestPathTA.appendText(printShortestPath(end)+"\n"+runningTimeText+"\n");

        }else {
                shortestPathTA.appendText("DID NOT FIND A PATH!! \n");

        }
        graph = AStarGraph.createGraph();

        ObservableList<Vertex> vertices = FXCollections.observableArrayList(graph.getVertices());

        selectStartVertex.setItems(vertices);
        selectStartVertex.setValue(graph.getVertices().get(0));

        selectEndVertex.setItems(vertices);
        selectEndVertex.setValue(graph.getVertices().get(0));
    }


    public String printShortestPath (Vertex endVertex){
        String result = new String();

        Vertex pvertex = endVertex;
        Stack<Vertex> Path = new Stack<>();
        int limit = 0;
        while (pvertex != null) {
            Path.push(pvertex);
            pvertex = pvertex.getPrev();
        }
        if (!Path.isEmpty())
            limit = Path.size();
        for (int i = 0; i < limit - 1; i++)
            result += Path.pop().getid() + " - > ";
        if (limit > 0)
            result += Path.pop().getid();

        return result;
    }

}



