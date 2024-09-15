package com.src.render;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Stop;
import com.src.render.matrix.LinAlg;
import com.src.render.matrix.Tensor;
import javafx.scene.shape.Line;
import com.src.render.render.Render;

public class LinearTransformationR extends Render {

    private Tensor matrixContainer;
    private boolean hasTransformedGrid; 
    private boolean hasDeterminant; 
    private boolean hasEigenspace;
    
    public LinearTransformationR(
        Canvas canvas, 
        GraphicsContext gc, 
        Color backgroundColor, 
        double[] rotation,
        double zoom,
        double[][] matrixContainer,
        boolean hasDeterminant,
        boolean hasEigenspace, 
        boolean hasTransformedGrid){

        super(canvas, gc, backgroundColor, rotation, zoom); 
        this.matrixContainer = new Tensor(matrixContainer);
        this.hasTransformedGrid = hasTransformedGrid; 
        this.hasDeterminant = hasDeterminant; 
        this.hasEigenspace = hasEigenspace;
    }

    private void constructTransformedGrid(){

        Tensor xPos = LinAlg.dot(matrixContainer, new Tensor(new double[]{1, 0, 0}));
        Tensor xNeg = LinAlg.dot(matrixContainer, new Tensor(new double[]{-1, 0, 0}));

        Tensor yPos = LinAlg.dot(matrixContainer, new Tensor(new double[]{0, 1, 0}));
        Tensor yNeg = LinAlg.dot(matrixContainer, new Tensor(new double[]{0, -1, 0}));

        Tensor zPos = LinAlg.dot(matrixContainer, new Tensor(new double[]{0, 0, 1}));
        Tensor zNeg = LinAlg.dot(matrixContainer, new Tensor(new double[]{0, 0, -1}));

        double[] xRow = this.matrixContainer.T().getRowEntry(0);   
        double[] yRow = this.matrixContainer.T().getRowEntry(1);
        
        boolean allZerosY = true;
        boolean allZerosX = true; 

        for (double entry:xRow){
            if (entry != 0){
                allZerosX = false; 
            }
        }

        for (double entry:yRow){
            if (entry != 0){
                allZerosY = false; 
            }
        }

        if (allZerosX){
            Tensor holdP = zPos;
            Tensor holdNeg = zNeg;
            
            zPos = xPos; 
            zNeg = xNeg;

            xPos  = holdP; 
            xNeg = holdNeg; 
        }

        if (allZerosY){
            Tensor holdP = zPos;
            Tensor holdNeg = zNeg;
            
            zPos = yPos; 
            zNeg = yNeg;

            yPos  = holdP; 
            yNeg = holdNeg; 
        }
        
        this.gc.setLineWidth(3);

        double axisMultiplier = this.axisBasis[0][0];
        
        for ( double j = -axisMultiplier; j < (axisMultiplier + 1); j++){

            if (j == -axisMultiplier || j == axisMultiplier){
                this.gc.setStroke(this.backgroundColor.deriveColor(0, 0.5, 1, 0.8));
            }

            else{
                this.gc.setStroke(this.backgroundColor.deriveColor(0, 1, 1, 0.15));                
            }

            for (int i = 0; i <= (axisMultiplier); i++){

                Tensor xP = LinAlg.add(LinAlg.multiply(axisMultiplier, xPos), LinAlg.multiply(i , yPos)); 
                Tensor xN = LinAlg.add(LinAlg.multiply(axisMultiplier, xNeg), LinAlg.multiply(i , yPos));

                Tensor xPO = LinAlg.add(LinAlg.multiply(axisMultiplier, xPos), LinAlg.multiply(-i , yPos)); 
                Tensor xNO = LinAlg.add(LinAlg.multiply(axisMultiplier, xNeg), LinAlg.multiply(-i , yPos));

                Tensor yP = LinAlg.add(LinAlg.multiply(axisMultiplier, yPos), LinAlg.multiply(i, xPos)); 
                Tensor yN = LinAlg.add(LinAlg.multiply(axisMultiplier, yNeg), LinAlg.multiply(i, xPos));

                Tensor yPO = LinAlg.add(LinAlg.multiply(axisMultiplier, yPos), LinAlg.multiply(-i, xPos)); 
                Tensor yNO = LinAlg.add(LinAlg.multiply(axisMultiplier, yNeg), LinAlg.multiply(-i, xPos));

                double[] xZP = convert(LinAlg.add(xP, LinAlg.multiply(j , zPos))); 
                double[] xZN = convert(LinAlg.add(xN, LinAlg.multiply(j , zPos)));
    
                double[] xZPO = convert(LinAlg.add(xPO, LinAlg.multiply(j , zPos))); 
                double[] xZNO = convert(LinAlg.add(xNO, LinAlg.multiply(j , zPos)));
    
                double[] yZP = convert(LinAlg.add(yP, LinAlg.multiply(j, zPos))); 
                double[] yZN = convert(LinAlg.add(yN, LinAlg.multiply(j, zPos)));
    
                double[] yZPO = convert(LinAlg.add(yPO, LinAlg.multiply(j, zPos))); 
                double[] yZNO = convert(LinAlg.add(yNO, LinAlg.multiply(j, zPos)));

                if (j % 2 == 0){
                    this.gc.strokeLine(xZP[0], xZP[1], xZN[0], xZN[1]);
                    this.gc.strokeLine(xZPO[0], xZPO[1], xZNO[0], xZNO[1]);

                    this.gc.strokeLine(yZP[0], yZP[1], yZN[0], yZN[1]);
                    this.gc.strokeLine(yZPO[0], yZPO[1], yZNO[0], yZNO[1]);
                }
                
            }
        }
 
    }

    private void constructDeterminant(){

        Tensor transposed = this.matrixContainer.T(); 
        
        Tensor origin = new Tensor(new double[]{0, 0, 0});
        
        Tensor x = new Tensor(transposed.getRowEntry(0));
        Tensor y = new Tensor(transposed.getRowEntry(1));
        Tensor z = new Tensor(transposed.getRowEntry(2));
        
        Tensor xY = LinAlg.add(x, y);
        Tensor xZ = LinAlg.add(x, z);
        Tensor yZ = LinAlg.add(y, z);
        Tensor xYZ = LinAlg.add(x, yZ); 

        this.gc.setLineWidth(1);

        drawQuad(origin, x, xY, y, this.backgroundColor.deriveColor(0, 0.7, 1, 1));
        drawQuad(origin, x, xZ, z, this.backgroundColor.deriveColor(0, 0.7, 1, 1));
        drawQuad(origin, z, yZ, y, this.backgroundColor.deriveColor(0, 0.7, 1, 1));
        drawQuad(z, xZ, xYZ, yZ, this.backgroundColor.deriveColor(0, 0.7, 1, 1));
        drawQuad(y, xY, xYZ, yZ, this.backgroundColor.deriveColor(0, 0.7, 1, 1));
        drawQuad(x, xZ, xYZ, xY, this.backgroundColor.deriveColor(0, 0.7, 1, 1));

        strokeQuad(origin, x, xY, y);
        strokeQuad(origin, x, xZ, z);
        strokeQuad(origin, z, yZ, y);
        strokeQuad(z, xZ, xYZ, yZ);
        strokeQuad(y, xY, xYZ, yZ);
        strokeQuad(x, xZ, xYZ, xY);
    }

    @Override
    public void render(){
        if (this.hasTransformedGrid){
            constructTransformedGrid();
        }

        if (this.hasDeterminant){
            constructDeterminant();
        }
        
    }
}
