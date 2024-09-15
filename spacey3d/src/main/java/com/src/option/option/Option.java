package com.src.option.option;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import com.src.Settings;


public class Option extends Settings{

    protected double[][] matrixContainer; 
    protected Node optionSelectionUI;
    protected String optionType; 
    protected Color color;

    public Option(){
        this.color = Color.BLUE;
    }
    
    protected Pane matrixCreation(int columnAllow){
        GridPane gridPane = new GridPane();
        gridPane.setPadding(new Insets(10)); 
        gridPane.setHgap(3); 
        gridPane.setVgap(3); 
        gridPane.setStyle(
            "-fx-border-color: white;" 
            + "-fx-border-width: 1px;"
        );
        gridPane.setMaxWidth(180);
        gridPane.setMinWidth(180);
        gridPane.setAlignment(Pos.CENTER);

        
        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 3; col++) {
                StackPane cellPane = new StackPane();

                Rectangle border = new Rectangle(50, 30);
                border.setStroke(Color.WHITE);
                border.setFill(Color.TRANSPARENT);

                cellPane.getChildren().add(border);

                int rowStore = row; 
                int colStore = col; 
                
                if (col <= columnAllow - 1){
                   
                    TextField textField = new TextField();
                    textField.setPrefWidth(50); 
                    textField.setPrefHeight(30); 
                    textField.setStyle("-fx-alignment: center;"); 

                    textField.textProperty().addListener((observable, oldValue, newValue) -> {
                        if (!newValue.matches("-?\\d*(\\.\\d*)?")) {
                            textField.setText(oldValue);
                        }

                        else{
                            try{

                                this.matrixContainer[rowStore][colStore] = Double.parseDouble(newValue);

                            }
                            catch(Exception e){

                                this.matrixContainer[rowStore][colStore] = 0.0;
                            }   
                        }
                    });

                    this.matrixContainer[rowStore][colStore] = 0.0;

                    cellPane.getChildren().add(textField);
                }
                
                gridPane.add(cellPane, col, row);
            }
        }
        
        return gridPane;
    }

    protected HBox tagAndPane(String label, Node pane){
        HBox box = new HBox(10); 
        Label optionLabel = new Label(label);
        optionLabel.setStyle("-fx-text-fill: white;");
        box.getChildren().addAll(optionLabel, pane);
        box.setAlignment(Pos.CENTER_LEFT); 

        return box; 
    }

    public Node getOptionSelectionUI(){
        return this.optionSelectionUI;
    }
    
    public String getOptionType(){
        return this.optionType; 
    }

    public double[][] getMatrixContainer(){
        return this.matrixContainer;
    }

    public Color getColor(){
        return this.color;
    }

}
