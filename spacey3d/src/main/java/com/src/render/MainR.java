package com.src.render;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import com.src.render.matrix.LinAlg;
import com.src.render.matrix.Tensor;
import com.src.render.render.Render;

public class MainR extends Render{


    public MainR(Canvas canvas, GraphicsContext gc, Color backgroundColor, double[] rotation, double zoom){
        super(canvas, gc, backgroundColor, rotation, zoom); 
    }

    private void drawAxis(Tensor vector, String label){

        Tensor oppositeVector = LinAlg.multiply(-1, vector);

        double[] renderFlip = convert(oppositeVector); 
        double[] renderVector = convert(vector);

        this.gc.setStroke(Color.WHITE); 
        this.gc.setLineWidth(1); 
        this.gc.strokeLine(renderFlip[0], renderFlip[1], renderVector[0], renderVector[1]); 

        this.gc.setFont(Font.font("Arial", FontWeight.LIGHT, 16));

        double gridGap = this.axisBasis[0][0];
 
        //Make grid points            
        for (double i = 2.0 ; i < (gridGap + 2); i+= 2){
            Tensor line = LinAlg.multiply(i/gridGap, vector);
            Tensor lineOp = LinAlg.multiply(i/gridGap, oppositeVector);

            double[] up = {0, 0, 0}; 
            double[] down = {0, 0, 0};  

            double gridLength = 2.0;

            if (label.equals("x")){
                up[1] = gridLength/10/this.zoom; 
                down[1] = -gridLength/10/this.zoom;
            }

            else if (label.equals("y")){
                up[2] = gridLength/10/this.zoom; 
                down[2] = -gridLength/10/this.zoom;
            }

            else if (label.equals("z")){
                up[0] = gridLength/10/this.zoom; 
                down[0] = -gridLength/10/this.zoom;
            }

            
            double[] l1 = convert(LinAlg.add(line, new Tensor(up)));
            double[] l2 = convert(LinAlg.add(line,  new Tensor(down)));

            if (i < gridGap){
                double[] l1O = convert(LinAlg.add(lineOp, new Tensor(up)));
                double[] l2O = convert(LinAlg.add(lineOp, new Tensor(down)));

                this.gc.strokeLine(l1[0], l1[1], l2[0], l2[1]);
                this.gc.strokeLine(l1O[0], l1O[1], l2O[0], l2O[1]);

            }

            else{
                double[] tip = convert(LinAlg.multiply(1.1, vector));

                double[] tip1 = convert(LinAlg.add(vector, new Tensor(up)));
                double[] tip2 = convert(LinAlg.add(vector,  new Tensor(down)));

                double[] triangleX = {tip[0], tip1[0], tip2[0]};
                double[] triangleY = {tip[1], tip1[1], tip2[1]};

                this.gc.setFill(Color.WHITE);
                this.gc.fillPolygon(triangleX, triangleY, 3);
            }
        }

        this.gc.setFont(Font.font("Arial", FontWeight.LIGHT, 10));

        double[] labelLocation = convert(LinAlg.multiply(1.2, vector));
        this.gc.setFont(Font.font("Arial", FontWeight.LIGHT, 16));
        this.gc.fillText(label, labelLocation[0], labelLocation[1]);
 
    }

    private void drawCube(Tensor x, Tensor y, Tensor z){

        Tensor nx = LinAlg.multiply(-1, x);
        Tensor ny = LinAlg.multiply(-1, y);
        Tensor nz = LinAlg.multiply(-1, z);

        double[] v0 = convert(LinAlg.add(x, LinAlg.add(y, z)));
        double[] v1 = convert(LinAlg.add(x, LinAlg.add(y, nz)));
        double[] v2 = convert(LinAlg.add(x, LinAlg.add(ny, z)));
        double[] v3 = convert(LinAlg.add(x, LinAlg.add(ny, nz)));
        double[] v4 = convert(LinAlg.add(nx, LinAlg.add(y, z)));
        double[] v5 = convert(LinAlg.add(nx, LinAlg.add(y, nz)));
        double[] v6 = convert(LinAlg.add(nx, LinAlg.add(ny, z)));
        double[] v7 = convert(LinAlg.add(nx, LinAlg.add(ny, nz)));

        gc.setStroke(Color.WHITE);
        gc.setLineWidth(1);

        this.gc.strokeLine(v0[0], v0[1], v1[0], v1[1]);
        this.gc.strokeLine(v0[0], v0[1], v2[0], v2[1]);
        this.gc.strokeLine(v0[0], v0[1], v4[0], v4[1]);
        this.gc.strokeLine(v1[0], v1[1], v3[0], v3[1]);
        this.gc.strokeLine(v1[0], v1[1], v5[0], v5[1]);
        this.gc.strokeLine(v2[0], v2[1], v3[0], v3[1]);
        this.gc.strokeLine(v2[0], v2[1], v6[0], v6[1]);
        this.gc.strokeLine(v3[0], v3[1], v7[0], v7[1]);
        this.gc.strokeLine(v4[0], v4[1], v5[0], v5[1]);
        this.gc.strokeLine(v4[0], v4[1], v6[0], v6[1]);
        this.gc.strokeLine(v5[0], v5[1], v7[0], v7[1]);
        this.gc.strokeLine(v6[0], v6[1], v7[0], v7[1]);

    }

    @Override
    public void render(){
        Tensor xAxis = new Tensor(this.axisBasis[0]);
        Tensor yAxis = new Tensor(this.axisBasis[1]);
        Tensor zAxis = new Tensor(this.axisBasis[2]);
        
        //Axis 
        drawAxis(xAxis, "x");
        drawAxis(yAxis, "y");
        drawAxis(zAxis, "z");

    }

    public void renderCube(){
        Tensor xAxis = new Tensor(this.axisBasis[0]);
        Tensor yAxis = new Tensor(this.axisBasis[1]);
        Tensor zAxis = new Tensor(this.axisBasis[2]);

        //Render Axis Numbers 
        this.gc.setFill(Color.WHITE);
        this.gc.setFont(Font.font("Arial", FontWeight.LIGHT, 10));

        for (double i = 2.0; i < (this.axisBasis[0][0]); i+= 2){
            Tensor line = LinAlg.multiply(i/this.axisBasis[0][0], yAxis);
            Tensor lineOp = LinAlg.multiply(i/this.axisBasis[0][0], LinAlg.multiply(-1, yAxis));

            Tensor spacer = new Tensor(new double[]{0, 0, 0.5});
            

            double[] posText = convert(LinAlg.add(line, spacer));
            double[] negText = convert(LinAlg.add(lineOp, spacer));

            this.gc.fillText("" + ((int) i), posText[0], posText[1]);
            this.gc.fillText("-" + ((int) i), negText[0], negText[1]);
        }

        double[] originNo = convert(new Tensor(new double[]{0, -0.2, 0.2}));
        this.gc.fillText("" + 0 , originNo[0], originNo[1]);
        
        

        drawCube(xAxis, yAxis, zAxis);
    }

    

}
