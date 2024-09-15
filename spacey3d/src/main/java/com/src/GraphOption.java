package com.src;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import com.src.option.Line;
import com.src.option.LinearTransformation;
import com.src.option.Plane;
import com.src.option.option.Option;

import com.src.option.Vector;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;


public class GraphOption extends Settings{
    
    private VBox self;
    private Option selectedOption; 
    
    public GraphOption(){
        this.self = selfPane();
    }

    private HBox tagAndPane(String label, Node pane){
        HBox box = new HBox(10); 
        Label optionLabel = new Label(label);
        optionLabel.setStyle("-fx-text-fill: white;");
        box.getChildren().addAll(optionLabel, pane);
        box.setAlignment(Pos.CENTER_LEFT); 

        return box; 
    }

    private VBox selfPane(){
        
        VBox selfVPane = new VBox(); 
                
        selfVPane.setPadding(new Insets(7, 15, 20, 15));
        selfVPane.setSpacing(10);
        //selfVPane.setMinHeight(150);

        ComboBox<String> comboBox = new ComboBox<>();
        comboBox.getItems().addAll("Linear Transformation", "Plane", "Line", "Vector");
        comboBox.getStyleClass().add("combo-box");
        
        comboBox.setValue("Linear Transformation");
        HBox option = tagAndPane("Type: ", comboBox);

        Pane changingBox = new Pane(); 

        comboBox.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {

            changingBox.getChildren().remove(this.selectedOption.getOptionSelectionUI());

            if (newValue.equals("Linear Transformation")){
                this.selectedOption = new LinearTransformation(); 
            }

            else if (newValue.equals("Plane")){
                this.selectedOption = new Plane(); 
            }

            else if (newValue.equals("Line")){
                this.selectedOption = new Line(); 
            }

            else if (newValue.equals("Vector")){
                this.selectedOption = new Vector();
            }

            changingBox.getChildren().add(this.selectedOption.getOptionSelectionUI());
        });

        selfVPane.getChildren().addAll(option, changingBox);
        
        this.selectedOption = new LinearTransformation(); 
        changingBox.getChildren().add(this.selectedOption.getOptionSelectionUI());

        return selfVPane; 
    }

    public Option getSelectedOption(){
        return this.selectedOption; 
    }

    public Pane getSelf(){
        return this.self; 
    }
}
