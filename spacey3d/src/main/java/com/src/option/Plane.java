package com.src.option;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.TextField;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import com.src.option.option.Option;

public class Plane extends Option {

    private boolean hasPlane; 
    private boolean hasEigenspace; 
    private double[] translation; 

    public Plane(){

        super.matrixContainer = new double[3][2];
        super.optionSelectionUI = optionSelectionUIPane();
        this.hasPlane = true;
        this.hasEigenspace = false; 
        this.translation = new double[]{0, 0, 0}; 
        
    }

    private VBox translateInput(){

        VBox translateContainer = new VBox(5);

        Text transTitle = new Text("Translation");
        transTitle.setFill(Color.valueOf(getbFontColor()));
        transTitle.setFont(Font.font("System", FontWeight.BOLD, 13));

        translateContainer.getChildren().add(transTitle);

        for (int i = 0; i < 3; i++){
            TextField floatInput = new TextField();
            floatInput.setPrefWidth(50);
            
            int place = i; 
            floatInput.textProperty().addListener((observable, oldValue, newValue) -> {
                if (!newValue.matches("-?\\d*(\\.\\d*)?")) {
                    floatInput.setText(oldValue);
                }

                else{
                    try{

                        this.translation[place] = Double.parseDouble(newValue);

                    }
                    catch(Exception e){

                        this.translation[place] = 0.0;
                    }   
                }
            });

            String label = "X"; 

            if (i == 1){
                label = "Y";
            }

            else if (i == 2){
                label = "Z";
            }

            translateContainer.getChildren().add(tagAndPane("\t" + label + " :\t", floatInput));

        }   

        return translateContainer;
    }

    private VBox optionSelectionUIPane(){

        VBox optionContainer = new VBox(10);

        Text matrixTitle = new Text("Plane 3 x 2");
        matrixTitle.setFill(Color.valueOf(getbFontColor()));
        matrixTitle.setFont(Font.font("System", FontWeight.BOLD, 13));

        Pane matrixInput = matrixCreation(2);

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
        
        CheckBox checkBox1 = new CheckBox("Plane");

        checkBox1.setOnAction(event -> {
            this.hasPlane = checkBox1.isSelected(); // Update the variable with the checkbox state
        });

        checkBox1.setSelected(true);
        
        CheckBox checkBox2 = new CheckBox("Eigenspace");

        checkBox2.setOnAction(event -> {
            this.hasEigenspace = checkBox2.isSelected(); // Update the variable with the checkbox state
        });
        
        optionContainer.getChildren().addAll(matrixTitle, matrixInput, colorPick, translateInput(), viewTitle, checkBox1); //checkBox2

        return optionContainer;
    }

    public boolean getHasEigenspace(){
        return this.hasEigenspace; 
    }

    public boolean getHasPlane(){
        return this.hasPlane; 
    }

    public double[] getTranslation(){
        return this.translation; 
    }
}

