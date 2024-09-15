package com.src.render.matrix;

public class LinAlg {
    
    public static Tensor add(Tensor matrix1, Tensor matrix2){

        assert matrix1.getRow() == matrix2.getRow() && matrix1.getColumn() == matrix2.getColumn(); 

        int row = matrix1.getRow(); 
        int column = matrix1.getColumn();

        double[][] m1 = matrix1.getTensor(); 
        double[][] m2 = matrix2.getTensor(); 

        double[][] array = new double[row][column];

        for (int i = 0; i < row; i++){
            for (int j = 0; j < column; j++){
                array[i][j] = m1[i][j] + m2[i][j]; 
            }
        }

        return new Tensor(array);

    }

    public static Tensor minus(Tensor matrix1, Tensor matrix2){

        assert matrix1.getRow() == matrix2.getRow() && matrix1.getColumn() == matrix2.getColumn(); 

        int row = matrix1.getRow(); 
        int column = matrix1.getColumn();

        double[][] m1 = matrix1.getTensor(); 
        double[][] m2 = matrix2.getTensor(); 

        double[][] array = new double[row][column];

        for (int i = 0; i < row; i++){
            for (int j = 0; j < column; j++){
                array[i][j] = m1[i][j] - m2[i][j]; 
            }
        }

        return new Tensor(array);

    }

    public static Tensor multiply(double number, Tensor matrix1){

        int row = matrix1.getRow(); 
        int column = matrix1.getColumn();

        double[][] m1 = matrix1.getTensor(); 

        double[][] array = new double[row][column];

        for (int i = 0; i < row; i++){
            for (int j = 0; j < column; j++){
                array[i][j] = m1[i][j] * number; 
            }
        }

        return new Tensor(array);

    }

    public static Tensor dot(Tensor matrix1, Tensor matrix2){

        assert matrix1.getColumn() == matrix2.getRow(); 

        int row = matrix1.getRow(); 
        int column = matrix2.getColumn();
        int dotAlong = matrix1.getColumn();

        double[][] m1 = matrix1.getTensor(); 
        double[][] m2 = matrix2.getTensor(); 

        double[][] array = new double[row][column];

        for (int i = 0; i < row; i++){
            for (int j = 0; j < column; j++){

                array[i][j] = 0.0;
                
                for (int k = 0; k < dotAlong; k++){
                    array[i][j] += m1[i][k] * m2[k][j]; 
                }
            }
        }

        return new Tensor(array);

    }
}

