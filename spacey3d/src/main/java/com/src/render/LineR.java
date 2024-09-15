package com.src.render;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import com.src.render.MainR;
import com.src.render.matrix.LinAlg;
import com.src.render.matrix.Tensor;
import com.src.render.render.Render;

public class LineR extends Render{

    private Tensor matrixContainer;
    private boolean showLine;  
    private Tensor translation; 
    
    public LineR(
        Canvas canvas, 
        GraphicsContext gc, 
        Color backgroundColor, 
        double[] rotation,
        double zoom,
        double[][] matrixContainer, 
        boolean showLine, 
        double[] translation){

        super(canvas, gc, backgroundColor, rotation, zoom); 
        this.matrixContainer = new Tensor(matrixContainer);
        this.showLine = showLine; 
        this.translation = new Tensor(translation);
    }

    private void constructLine(){

        this.gc.setLineWidth(3);
        this.gc.setStroke(this.backgroundColor.deriveColor(0, 0.5, 1, 0.9));

        Tensor r1 = new Tensor(this.matrixContainer.T().getRowEntry(0));

        Tensor xP = LinAlg.add(LinAlg.multiply(10, r1), this.translation); 
        Tensor xN = LinAlg.add(LinAlg.multiply(-10, r1), this.translation); 

        double[] xZP = convert(xP); 
        double[] xZN = convert(xN);

        this.gc.strokeLine(xZP[0], xZP[1], xZN[0], xZN[1]);

    }

    @Override
    public void render(){


        if (this.showLine){
            constructLine();
        }
        
    }
}
