package com.src.render.render;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import com.src.render.matrix.LinAlg;
import com.src.render.matrix.Tensor;

public class Render {
    
    protected Canvas canvas;
    protected GraphicsContext gc; 
    protected Color backgroundColor;
    protected double[] rotation; 
    protected Tensor rotationMatrix; 
    protected Tensor projectionMatrix;
    protected Tensor translation; 
    protected double zoom; 
    protected double[][] axisBasis;

    public Render(Canvas canvas, GraphicsContext gc, Color backgroundColor, double[] rotation, double zoom){
        this.canvas = canvas; 
        this.gc = gc; 
        this.backgroundColor = backgroundColor; 
        updateRotatonMatrix(rotation);
        this.projectionMatrix = new Tensor(new double[][]{
            {1, 0, 0},
            {0, 1, 0},
        });
        this.axisBasis = new double[][] {
            {10, 0, 0},
            {0, 10, 0},
            {0, 0, 10}
        };
        this.translation = new Tensor(new double[]{canvas.getWidth()/ 2.0, canvas.getHeight()/2.0});
        this.zoom = zoom; 
    }

    public void updateRotatonMatrix(double[] rotation){

        this.rotation = rotation; 
        
        double[][] xY = {
            {1, 0, 0},
            {0, Math.cos(this.rotation[0]), -Math.sin(this.rotation[0])},
            {0, Math.sin(this.rotation[0]), Math.cos(this.rotation[0])}
        };

        double[][] yZ = {
            {Math.cos(this.rotation[1]), 0, -Math.sin(this.rotation[1])},
            {0, 1, 0},
            {Math.sin(this.rotation[1]), 0, Math.cos(this.rotation[1])},
        };

        this.rotationMatrix = LinAlg.dot(new Tensor(xY), new Tensor(yZ));

    }

    protected double[] convert(Tensor vector){

        double axisMultiplier = 50 * 5/axisBasis[0][0];

        vector = LinAlg.multiply(axisMultiplier * this.zoom, vector);
        
        Tensor transformation = LinAlg.dot(this.projectionMatrix, this.rotationMatrix);
        
        transformation = LinAlg.dot(transformation, vector);

        double[] transformationArray = transformation.T().getRowEntry(0);
        
        transformationArray[1] *= -1; 

        transformation = new Tensor(transformationArray);

        double[] coordinates = LinAlg.add(transformation, this.translation).T().getTensor()[0];

        
        return coordinates; 
    }

    protected void drawQuad(Tensor s1, Tensor s2, Tensor s3, Tensor s4, Color color ){

        double[] ss1 = convert(s1); 
        double[] ss2 = convert(s2); 
        double[] ss3 = convert(s3); 
        double[] ss4 = convert(s4); 

        gc.setFill(color);

        this.gc.fillPolygon(new double[]{ss1[0], ss2[0], ss3[0], ss4[0]}, 
                                new double[]{ss1[1], ss2[1], ss3[1], ss4[1]}, 
                                4);

    }

    protected void strokeQuad(Tensor s1, Tensor s2, Tensor s3, Tensor s4){
        double[] ss1 = convert(s1); 
        double[] ss2 = convert(s2); 
        double[] ss3 = convert(s3); 
        double[] ss4 = convert(s4); 

        gc.setStroke(Color.valueOf("#a0a1a3"));

        this.gc.strokePolygon(new double[]{ss1[0], ss2[0], ss3[0], ss4[0]}, 
                                new double[]{ss1[1], ss2[1], ss3[1], ss4[1]}, 
                                4);
    }

    public void updateZoom(double zoom){

        this.zoom = zoom;

        
    }

    public  void render(){

    }

    

}
