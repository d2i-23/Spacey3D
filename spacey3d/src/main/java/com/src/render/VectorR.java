package com.src.render;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import com.src.render.MainR;
import com.src.render.matrix.LinAlg;
import com.src.render.matrix.Tensor;
import com.src.render.render.Render;

public class VectorR extends Render{

    private Tensor vector;
    private boolean hasVector;   
    
    public VectorR(
        Canvas canvas, 
        GraphicsContext gc, 
        Color backgroundColor, 
        double[] rotation,
        double zoom,
        double[] vector,
        boolean hasVector){

        super(canvas, gc, backgroundColor, rotation, zoom); 
        this.vector = new Tensor(vector);
        this.hasVector = hasVector; 
        
    }

    private void constructVector(){

        double[] location = convert(this.vector); 

        double[] sphereTipLocation = convert(new Tensor(new double[]{0, 0.3, 0}));
        double sphereLength = Math.sqrt(sphereTipLocation[0]*sphereTipLocation[0] + sphereTipLocation[1]*sphereTipLocation[1]);
        double radius = Math.abs(zoom * sphereLength / 60);

        gc.setFill(this.backgroundColor.deriveColor(0, 0.7, 1, 1));
        gc.fillOval(location[0] - radius, location[1] - radius, 2 * radius, 2 * radius);
    }

    @Override
    public void render(){

        if (this.hasVector){
            constructVector();
        }
        
    }
}
