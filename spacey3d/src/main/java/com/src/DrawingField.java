package com.src;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.scene.paint.Color;
import com.src.option.Line;
import com.src.option.LinearTransformation;
import com.src.option.Plane;
import com.src.option.Vector;
import com.src.option.option.Option;
import com.src.render.LineR;
import com.src.render.LinearTransformationR;
import com.src.render.MainR;
import com.src.render.PlaneR;
import com.src.render.VectorR;
import com.src.render.render.Render;
import java.util.ArrayList;

public class DrawingField extends Settings {

    public Canvas canvas; 
    private GraphicsContext gc; 
    private double[] rotation; 
    private double mouseX; 
    private double mouseY; 
    private double keySpeed; 
    private ArrayList<Render> optionStorage; 
    private double zoom; 
    private Color canvasColor; 

    private MainR mainRender; 

    public DrawingField() {
        this.canvas = canvas(); //this.gc gets its value alongside 
        this.keySpeed = Math.PI/100;
        this.rotation = new double[]{-0.373, -0.628, 0.0};
        this.zoom = 1; 
        this.canvasColor = Color.valueOf(getCanvasColor()); 
        this.mainRender = new MainR(
                            this.canvas, 
                            this.gc, 
                            this.canvasColor, 
                            this.rotation, 
                            this.zoom
                            );
        this.optionStorage = new ArrayList(); 


        updateRender();
    }

    private boolean isInCanvas(Canvas canva, double x, double y){
        return x >= 0 && x <= canvas.getWidth() && y >= 0 && y <= canvas.getHeight();
    }

    private Canvas canvas(){
        Canvas canvas = new Canvas(getWindowWidth()/4 * 3, getWindowHeight()); 

        canvas.setOnMousePressed(event -> {
            this.mouseX = event.getX();
            this.mouseY = event.getY();
        });

        canvas.setOnMouseDragged(event -> {

            double x = event.getX(); 
            double y = event.getY(); 

            if (isInCanvas(canvas, x, y)){
                double moveX = x-this.mouseX; 
                double moveY = y-this.mouseY;
                
                if (moveX > 0){
                    this.rotation[1] += this.keySpeed * Math.sqrt(Math.abs(moveX) * 0.5); 
                }

                else if (moveX < 0) {
                    this.rotation[1] -= this.keySpeed * Math.sqrt(Math.abs(moveX) * 0.5); 
                }

                if (moveY > 0){
                    this.rotation[0] -= this.keySpeed * 0.66 * Math.sqrt(Math.abs(moveY)); 
                }

                else if (moveY < 0){
                    this.rotation[0] += this.keySpeed * 0.66 *  Math.sqrt(Math.abs(moveY)); 
                }

                for (int i = 0; i < 3; i ++){
                    if (this.rotation[i] > 2 * Math.PI || this.rotation[i] < -2 * Math.PI){
                        this.rotation[i] = 0.0;
                    }
                }

                this.mouseX = x; 
                this.mouseY = y; 

                updateRender();
                //System.out.println("\rCounter " + rotation[0] + " " + rotation[1]);
            }
        });

        canvas.setOnScroll(event -> {
            
            double deltaY = event.getDeltaY();
            
            if (deltaY > 0){
                this.zoom /= 0.88;
            }

            else{
                this.zoom *= 0.88;
            }

            mainRender.updateZoom(this.zoom);

            for (Render option: optionStorage){
                option.updateZoom(this.zoom);
            }

            updateRender();
        });

        canvas.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.SPACE) {
                this.rotation[0] = -0.373;
                this.rotation[1] = -0.628;
                this.rotation[2] = 0;

                this.zoom = 1;

                mainRender.updateZoom(this.zoom);
                for (Render option: this.optionStorage){
                    option.updateZoom(this.zoom);
                }

                updateRender();
            }
        });

        canvas.setFocusTraversable(true);

        this.gc = canvas.getGraphicsContext2D();

        return canvas; 
    }

    public void addOption(ArrayList<GraphOption> graphStorage){

        this.optionStorage.clear(); 

        for (GraphOption option: graphStorage){
            Option optionType = option.getSelectedOption(); 

            if (optionType instanceof LinearTransformation){
                LinearTransformation linearTransform = (LinearTransformation) optionType; 

                this.optionStorage.add(new LinearTransformationR(
                                            canvas, 
                                            gc, 
                                            linearTransform.getColor(), 
                                            rotation, 
                                            this.zoom,
                                            linearTransform.getMatrixContainer(), 
                                            linearTransform.getHasDeterminant(), 
                                            linearTransform.getHasEigenspace(), 
                                            linearTransform.getHasTransformedGrid()
                                        ));
            }

            else if (optionType instanceof Plane){

                Plane plane = (Plane) optionType;
                
                this.optionStorage.add(new PlaneR(
                                            canvas, 
                                            gc, 
                                            plane.getColor(), 
                                            rotation, 
                                            this.zoom,
                                            plane.getMatrixContainer(), 
                                            plane.getHasPlane(), 
                                            plane.getHasEigenspace(),
                                            plane.getTranslation()
                                            ));
            }

            else if (optionType instanceof Line){

                Line line = (Line) optionType;

                this.optionStorage.add(new LineR(
                                            canvas,
                                            gc,
                                            line.getColor(),
                                            rotation,
                                            this.zoom,
                                            line.getMatrixContainer(),
                                            line.gethasLine(),
                                            line.getTranslation()
                                        ));
            }

            else if (optionType instanceof Vector){

                Vector vector = (Vector) optionType;

                this.optionStorage.add(new VectorR(
                                            canvas,
                                            gc,
                                            vector.getColor(),
                                            rotation,
                                            this.zoom,
                                            vector.getVector(),
                                            vector.getHasVector()
                                        ));
            }
        }

        updateRender();
    }

    public void updateRender(){
        this.gc.clearRect(0, 0, this.canvas.getWidth(), this.canvas.getHeight());
        this.gc.setFill(this.canvasColor);
        this.gc.fillRect(0, 0, this.canvas.getWidth(), this.canvas.getHeight());
        
        mainRender.updateRotatonMatrix(this.rotation);
        mainRender.render();

        for (Render option: this.optionStorage){
            option.updateRotatonMatrix(this.rotation);
            option.render(); 
        }

        mainRender.renderCube();

        this.canvas.requestFocus();
    }

    public Canvas getCanvas(){
        return this.canvas;
    }
}
