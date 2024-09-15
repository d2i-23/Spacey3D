package com.src.render.matrix;

public class Tensor {

    private double[][] tensor; 
    private int row; 
    private int column; 
    
    public Tensor(double[][] tensor){
        this.tensor = tensor;
        this.row = tensor.length; 
        this.column = tensor[0].length;  
    }

    public Tensor(double[] tensor){

        this.tensor = new double[tensor.length][1];

        for (int i = 0; i < tensor.length; i ++){
            this.tensor[i][0] = tensor[i]; 
        }
        
        this.row = tensor.length; 
        this.column = 1; 
    }

    public Tensor T(){

        double[][] array = new double[this.column][this.row];

        for (int i = 0; i < this.row; i++){
            for (int j = 0; j < this.column; j++){
                array[j][i] = this.tensor[i][j];
            }
        }

        return new Tensor(array); 
    }

    public int[] shape(){

        int[] shape = {this.row, this.column};

        return shape; 
    }

    public int getRow(){
        return this.row; 
    }

    public int getColumn(){
        return this.column; 
    }

    public double[][] getTensor(){
        return this.tensor; 
    }

    public double valueOf(int row, int column){
        return this.tensor[row][column]; 
    }

    public double[] getRowEntry(int row){
        return this.tensor[row];
    }

    @Override
    public String toString(){

        String sum = ""; 
        for (int i = 0; i < this.tensor.length; i++) {
            // Loop through each column in the current row
            for (int j = 0; j < this.tensor[0].length; j++) {
                // Print the element at position (i, j)
                sum += this.tensor[i][j] + " ";
            }
            // Print a newline after each row
            sum += "\n";
        }

        return sum; 
    }
}
