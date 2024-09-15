package com.src;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.text.Text;
import com.src.option.option.Option;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import java.util.ArrayList;
import javafx.scene.shape.Line;
import javafx.scene.layout.Border;

public class MainInterface extends Settings{

    private VBox UI; 
    private Pane topUI; 
    private Pane graphSelectionUI; 
    private ArrayList<GraphOption> graphStorage; 
    private int graphCreated; 
    private DrawingField canvas; 

    public MainInterface(){
        this.graphStorage = new ArrayList<>();
        this.topUI = topUI();
        this.graphSelectionUI = graphSelectionUI();
        this.graphCreated = 0;
        this.UI = UI(); 
        this.canvas = new DrawingField(); 

        //Gives one graph by default
        addGraph();
    }  

    private Pane topUI(){

        Pane topUI = new Pane();

        topUI.setPrefSize(getWindowWidth()/4, 50); 

        Line bottomLine = new Line(0, 50, getWindowWidth()/4, 50);
        bottomLine.setStyle(
            "-fx-stroke: white;" 
            +  "-fx-stroke-width: 0.5;"
        );

        Button addButton = new Button();
        Text plusSign = new Text("+");
        plusSign.setFont(Font.font(null, FontWeight.BOLD, 20)); 
        plusSign.setFill(Color.WHITE); 
        addButton.setGraphic(plusSign);

        addButton.setStyle(
            "-fx-background-color: transparent;"
            + "-fx-text-fill: white;" 
            + "-fx-font-weight: bold;" 
            + "-fx-font-size: 20;" 
        );

        addButton.setLayoutX(4);
        addButton.setLayoutY(4);

        addButton.setOnAction(event -> {
            addGraph();
            this.canvas.getCanvas().requestFocus();
        });

        Button updateButton = new Button("Update");
        updateButton.setPrefSize(60, 20);
        updateButton.setStyle("-fx-background-color:transparent;" 
            + "-fx-text-fill: white;" 
            + "-fx-font-size: 12px;" 
            + "-fx-border-color: #6ab056;"
            + "-fx-border-width: 2px;"
            + "-fx-border-radius: 2px;"
            );

        updateButton.setLayoutX(210);
        updateButton.setLayoutY(13);

        updateButton.setOnAction(event -> {

            canvas.addOption(this.graphStorage);
            this.canvas.getCanvas().requestFocus();
            
        });

        addButton.setStyle(
            "-fx-background-color: transparent;"
            + "-fx-text-fill: white;" 
            + "-fx-font-weight: bold;" 
            + "-fx-font-size: 20;"
        );

        topUI.getChildren().addAll(addButton, updateButton, bottomLine);

        return topUI;
    }

    private Pane graphSelectionUI(){
        VBox graphSelection = new VBox(); 
        graphSelection.setPrefWidth(getWindowWidth()/4);

        graphSelection.setSpacing(5);

        graphSelection.setPadding(new Insets(5, 5, 5, 5));

        return graphSelection; 
    }

    private void addGraph(){
        if (this.graphStorage.size() < 10){

            Text referenceText = new Text("Graph #" + (++ graphCreated)); 
            referenceText.setFill(Color.valueOf(getfontColor())); 
            referenceText.setFont(Font.font("Arial", FontWeight.BOLD, 20));

            Button deleteButton = new Button("Delete"); 
            deleteButton.setStyle(
                "-fx-background-color: #f73939;" + 
                "-fx-text-fill: white;" + 
                "-fx-text-fill: white; -fx-font-size: 11px;"
            );

            HBox referenceUI = new HBox(105); 
            referenceUI.getChildren().addAll(referenceText, deleteButton); 
            referenceUI.setPadding(new Insets(15, 15, 10, 15));
            referenceUI.setAlignment(Pos.CENTER_LEFT); 
            referenceUI.setStyle(
                "-fx-background-color: #242323; " 
            );
    
            GraphOption newGraphOption =  new GraphOption();

            graphStorage.add(newGraphOption);
            VBox.setVgrow(newGraphOption.getSelf(), Priority.NEVER);

            VBox graphContainer = new VBox(10); 
            graphContainer.getChildren().addAll(referenceUI, newGraphOption.getSelf()); 
            graphContainer.setStyle(
                "-fx-border-radius: 3px; " + 
                "-fx-border-width: 1px; " + 
                "-fx-border-color: #8c8c8c;" 
            );

            deleteButton.setOnAction(e -> {
                this.graphSelectionUI.getChildren().remove(graphContainer); 
                this.graphStorage.remove(newGraphOption);
            });

            this.graphSelectionUI.getChildren().add(graphContainer);
            
        }
    }

    private VBox UI(){
        VBox UIPane = new VBox(); 

        UIPane.setStyle(
            "-fx-background-color:" + getUIPaneColor()
        );

        UIPane.setPrefSize(super.getWindowWidth()/4, super.getWindowHeight());

        ScrollPane scrollPane = new ScrollPane();
        scrollPane.setFitToWidth(true);
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scrollPane.setStyle(
            "-fx-background:" + getScrolLBoxColor() 
        );
        scrollPane.setPrefHeight(getWindowHeight() -50 -50);
        scrollPane.setContent(this.graphSelectionUI);

        UIPane.getChildren().addAll(this.topUI, scrollPane);

        return UIPane; 
    }

    public Scene start(){

        HBox hbox = new HBox(); 
        Canvas renderer = this.canvas.getCanvas();

        hbox.getChildren().addAll(this.UI, renderer);

        Scene scene = new Scene(hbox, 420, 300);

        return scene;

    }
}
