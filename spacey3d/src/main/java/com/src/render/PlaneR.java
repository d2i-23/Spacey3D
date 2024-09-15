package com.src.render;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import com.src.render.MainR;
import com.src.render.matrix.LinAlg;
import com.src.render.matrix.Tensor;
import com.src.render.render.Render;

public class PlaneR extends Render {

    private Tensor matrixContainer; 
    private boolean hasTransformedGrid; 
    private boolean hasEigenspace; 
    private Tensor translation; 
    
    public PlaneR(
        Canvas canvas, 
        GraphicsContext gc, 
        Color backgroundColor, 
        double[] rotation,
        double zoom,
        double[][] matrixContainer,
        boolean hasTransformedGrid,
        boolean hasEigenspace,
        double[] translation){

        super(canvas, gc, backgroundColor, rotation, zoom); 
        this.matrixContainer = new Tensor(matrixContainer);
        this.hasTransformedGrid = hasTransformedGrid;
        this.hasEigenspace = hasEigenspace;
        this.translation = new Tensor(translation);
    }

    private void constructGrid(){
        Tensor r1 = new Tensor(this.matrixContainer.T().getRowEntry(0));
        Tensor r2 = new Tensor(this.matrixContainer.T().getRowEntry(1));

        Tensor xPos = r1;
        Tensor xNeg = LinAlg.multiply(-1, r1);

        Tensor yPos = r2;;
        Tensor yNeg = LinAlg.multiply(-1, r2);;

        this.gc.setLineWidth(3);
        this.gc.setStroke(this.backgroundColor.deriveColor(0, 0.5, 1, 0.7));

        int i = 10; 

        Tensor xP = LinAlg.add(this.translation, LinAlg.add(LinAlg.multiply(10, xPos), LinAlg.multiply(i , yPos))); 
        Tensor xN = LinAlg.add(this.translation, LinAlg.add(LinAlg.multiply(10, xNeg), LinAlg.multiply(i , yPos)));

        Tensor xPO = LinAlg.add(this.translation,LinAlg.add(LinAlg.multiply(10, xPos), LinAlg.multiply(-i , yPos))); 
        Tensor xNO = LinAlg.add(this.translation,LinAlg.add(LinAlg.multiply(10, xNeg), LinAlg.multiply(-i , yPos)));

        drawQuad(xP,  xN, xNO, xPO, this.backgroundColor.deriveColor(0, 0.7, 1, 0.8));    

    }

    @Override
    public void render(){

        if (this.hasTransformedGrid){
            constructGrid();
        }
        
    }
}
