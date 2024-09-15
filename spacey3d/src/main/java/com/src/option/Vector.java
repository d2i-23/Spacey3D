package com.src.option;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.TextField;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import com.src.option.option.Option;


public class Vector extends Option {

    private double[] vector; 
    private boolean hasVector; 

    public Vector(){
        this.vector = new double[]{0, 0, 0};
        super.optionSelectionUI = optionSelectionUIPane();
        this.hasVector = true; 
    }

    private Pane vectorInput(){

        GridPane gridPane = new GridPane();
        gridPane.setPadding(new Insets(5)); 
        gridPane.setHgap(5); 
        gridPane.setVgap(3); 
        gridPane.setStyle(
            "-fx-border-color: white;" 
            + "-fx-border-width: 1px;"
        );
        gridPane.setMaxWidth(180);
        gridPane.setMinWidth(180);
        gridPane.setAlignment(Pos.CENTER);

        
        for (int row = 0; row < 3; row++) {
            
            StackPane cellPane = new StackPane();

            Rectangle border = new Rectangle(50, 30);
            border.setStroke(Color.WHITE);
            border.setFill(Color.TRANSPARENT);

            cellPane.getChildren().add(border);

            int rowStore = row; 
            
                
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

                        this.vector[rowStore] = Double.parseDouble(newValue);

                    }
                    catch(Exception e){

                        this.vector[rowStore] = 0.0;
                    }   
                }
            });

            this.vector[rowStore] = 0.0;

            cellPane.getChildren().add(textField);
            
            
            gridPane.add(cellPane, row, 0);
            
        }
        
        return gridPane;
    }


    private VBox optionSelectionUIPane(){

        VBox optionContainer = new VBox(10);

        Text matrixTitle = new Text("Vector 1 x 3");
        matrixTitle.setFill(Color.valueOf(getbFontColor()));
        matrixTitle.setFont(Font.font("System", FontWeight.BOLD, 13));

        Pane matrixInput = vectorInput();

        ColorPicker colorPicker = new ColorPicker();
        colorPicker.setValue(Color.BLUE);
        colorPicker.getStyleClass().add("combo-box");
        HBox colorPick = tagAndPane("Pick Color: ", colorPicker);
        
        colorPicker.setOnAction(event -> {
            super.color = colorPicker.getValue(); // Update the variable with the selected color
        });

        Text viewTitle = new Text("View Option");
        viewTitle.setFill(Color.valueOf(getbFontColor()));
        viewTitle.setFont(Font.font("System", FontWeight.BOLD, 13));
        
        CheckBox checkBox1 = new CheckBox("Vector");

        checkBox1.setOnAction(event -> {
            this.hasVector = checkBox1.isSelected(); // Update the variable with the checkbox state
        });

        checkBox1.setSelected(true);
        
        
        optionContainer.getChildren().addAll(matrixTitle, matrixInput, colorPick, viewTitle, checkBox1); //checkBox2

        return optionContainer;
    }

    public double[] getVector(){
        return this.vector; 
    }

    public boolean getHasVector(){
        return this.hasVector; 
    }    
    
}
