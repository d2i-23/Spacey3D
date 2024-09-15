package com.src.option;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ColorPicker;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import com.src.option.option.Option;


public class LinearTransformation extends Option {

    private boolean hasTransformedGrid; 
    private boolean hasDeterminant; 
    private boolean hasEigenspace; 

    public LinearTransformation(){
        super.matrixContainer = new double[3][3];
        super.optionSelectionUI = optionSelectionUIPane();

        this.hasTransformedGrid = true; 
        this.hasDeterminant = false; 
        this.hasEigenspace = false;
    }

    private VBox optionSelectionUIPane(){

        VBox optionContainer = new VBox(10);

        Text matrixTitle = new Text("Matrix 3 x 3");
        matrixTitle.setFill(Color.valueOf(getbFontColor()));
        matrixTitle.setFont(Font.font("System", FontWeight.BOLD, 13));

        Pane matrixInput = matrixCreation(3);

        ColorPicker colorPicker = new ColorPicker();
        colorPicker.setValue(Color.BLUE);
        colorPicker.getStyleClass().add("combo-box");
        HBox colorPick = tagAndPane("Color: ", colorPicker);

        colorPicker.setOnAction(event -> {
            super.color = colorPicker.getValue(); // Update the variable with the selected color
        });

        Text viewTitle = new Text("View Option");
        viewTitle.setFill(Color.valueOf(getbFontColor()));
        viewTitle.setFont(Font.font("System", FontWeight.BOLD, 13));
        
        CheckBox checkBox1 = new CheckBox("Transformed Grid");

        checkBox1.setOnAction(event -> {
            this.hasTransformedGrid = checkBox1.isSelected(); // Update the variable with the checkbox state
        });

        checkBox1.setSelected(true);

        CheckBox checkBox2 = new CheckBox("Determinant");

        checkBox2.setOnAction(event -> {
            this.hasDeterminant = checkBox2.isSelected(); // Update the variable with the checkbox state
        });

        CheckBox checkBox3 = new CheckBox("Eigenspace(s)");
        
        checkBox3.setOnAction(event -> {
            this.hasEigenspace = checkBox3.isSelected(); // Update the variable with the checkbox state
        });

        optionContainer.getChildren().addAll(matrixTitle, matrixInput, colorPick, viewTitle, checkBox1, checkBox2); //checkBox3

        return optionContainer;
    }

    public boolean getHasDeterminant(){
        return this.hasDeterminant; 
    }

    public boolean getHasEigenspace(){
        return this.hasEigenspace; 
    }

    public boolean getHasTransformedGrid(){
        return this.hasTransformedGrid; 
    }
}   
