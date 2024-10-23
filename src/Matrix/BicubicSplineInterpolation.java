package Matrix;
import java.io.File;
import java.io.FileWriter;
import java.io.FileReader;
import java.io.IOException;
import java.io.BufferedReader;

public class BicubicSplineInterpolation {
    static MatrixADT BSIDefaultCoordinates = new MatrixADT(new double[][]{ {0,0}, {1,0}, {0,1}, {1,1} });
    static MatrixADT BSIDefaultMatrixInverse = bicubicSplineMatrixGenerator(BSIDefaultCoordinates).inverse();
    
    private static MatrixADT bicubicSplineMatrixGenerator(MatrixADT coordinates){
        MatrixADT res = new MatrixADT(16, 16) ;
        for (int i = 0; i < 4; i++){
            // for (int j = 0; j < 4; j++){
            //     res[i][j] = Math.pow(coordinates[i][0], j);
            // }
            res.setElmt(i, 0, 1);
            res.setElmt(i, 1, coordinates.getElmt(i, 0));
            res.setElmt(i, 2, res.getElmt(i, 1) * coordinates.getElmt(i, 0));
            res.setElmt(i, 3, res.getElmt(i,2) * coordinates.getElmt(i, 0));
            for(int j = 4; j < 16; j++) {
                res.setElmt(i, j, res.getElmt(i, j-4) * coordinates.getElmt(i, 1));
            }
            res.setElmt(i+4, 0, 0);
            for (int j = 1; j < 4; j++){
                res.setElmt(i+4, j, res.getElmt(i, j-1) * j);
            }
            for (int j = 4; j < 16; j++){
                res.setElmt(i+4, j, res.getElmt(i+4, j-4) * coordinates.getElmt(i, 1));
            }
            for (int j = 0; j < 4; j++){
                res.setElmt(i+8, j, 0);
                res.setElmt(i+12, j, 0);
            }
            for (int j = 1; j < 4; j++){
                for (int k = 0; k < 4; k++){
                    res.setElmt(i+8, 4*j + k, res.getElmt(i, k) * j * res.getElmt(i, 4*(j-1)));
                }
            }
            for (int j = 1; j < 4; j++){
                res.setElmt(i+12, 4*j, 0);
                for (int k = 1; k < 4; k++){
                    res.setElmt(i+12, 4*j + k, res.getElmt(i, k-1 + (j-1) * 4) * k * j);
                }
            }
        }
        return res;
    }
    public static MatrixADT straighten(MatrixADT input){
        MatrixADT res = new MatrixADT(16, 1);
        for (int i = 0; i < 4; i++){
            for (int j = 0; j < 4; j++){
                res.setElmt(j+4*i, 0, input.getElmt(i, j));
            }
        }
        return res;
    }
    public static MatrixADT bicubicSplineCoefs(MatrixADT values){
        return bicubicSplineMatrixGenerator(BSIDefaultCoordinates).inverse().multiply(values);
    }
    public static MatrixADT bicubicSplineCoefs(MatrixADT coordinates, MatrixADT values){
        return bicubicSplineMatrixGenerator(coordinates).inverse().multiply(values);
    }

    private static double bicubicEquation(MatrixADT coefs, double x, double y){
        double x2 = x * x, x3 = x2 * x, y2 = y * y, y3 = y2 * y;
        return (
            coefs.getElmt(0, 0)
            + coefs.getElmt(1, 0) * x
            + coefs.getElmt(2, 0) * x2
            + coefs.getElmt(3, 0) * x3
            + coefs.getElmt(4, 0) * y
            + coefs.getElmt(5, 0) * x * y
            + coefs.getElmt(6, 0) * x2 * y
            + coefs.getElmt(7, 0) * x3 * y
            + coefs.getElmt(8, 0) * y2
            + coefs.getElmt(9, 0) * x * y2
            + coefs.getElmt(10, 0) * x2 * y2
            + coefs.getElmt(11, 0) * x3 * y2
            + coefs.getElmt(12, 0) * y3
            + coefs.getElmt(13, 0) * x * y3
            + coefs.getElmt(14, 0) * x2 * y3
            + coefs.getElmt(15, 0) * x3*y3);
    }

    public static MatrixADT bicubicSplineInterpolation(MatrixADT values, MatrixADT queries){
        MatrixADT coefs = bicubicSplineCoefs(BSIDefaultCoordinates, values);
        MatrixADT res = new MatrixADT(queries.nRows, 1);
        for (int i = 0; i < res.nRows; i++){
            res.setElmt(i, 0, bicubicEquation(coefs, queries.getElmt(i, 0), queries.getElmt(i, 1)));
        }
        return res;
    } 

    public static double bicubicSplineInterpolation(MatrixADT coordinates, MatrixADT values, double x, double y){
        MatrixADT coefs = BSIDefaultMatrixInverse.multiply(values);
        return bicubicEquation(coefs, x, y);
    }

    public static void printEquation(MatrixADT coefs) {
        System.out.println("Y = " +
        coefs.getElmt(0,0) + " + " +
        coefs.getElmt(1,0) + " X + " + 
        coefs.getElmt(2,0) + " X^2 + " + 
        coefs.getElmt(3,0) + " X^3 + " +
        coefs.getElmt(4,0) + " Y + " +
        coefs.getElmt(5,0) + " XY + " +
        coefs.getElmt(6,0) + " XY^2 + " +
        coefs.getElmt(7,0) + " XY^3 + " +
        coefs.getElmt(8,0) + " Y^2 + " +
        coefs.getElmt(9,0) + " XY^2 + " +
        coefs.getElmt(10,0) + " X^2Y^2 + " +
        coefs.getElmt(11,0) + " X^3Y^2 + " +
        coefs.getElmt(12,0) + " Y^3 + " +
        coefs.getElmt(13,0) + " XY^3 + " +
        coefs.getElmt(14,0) + " X^2Y^3 + " +
        coefs.getElmt(15,0) + " X^3Y^3" 
        );
    }

    public static void safeResult(MatrixADT values, MatrixADT coefs){
        String eqn =
            "Y = " +
            Double.toString(coefs.getElmt(0,0)) + " + " +
            Double.toString(coefs.getElmt(1,0)) + " X + " + 
            Double.toString(coefs.getElmt(2,0)) + " X^2 + " + 
            Double.toString(coefs.getElmt(3,0)) + " X^3 + " +
            Double.toString(coefs.getElmt(4,0)) + " Y + " +
            Double.toString(coefs.getElmt(5,0)) + " XY + " +
            Double.toString(coefs.getElmt(6,0)) + " XY^2 + " +
            Double.toString(coefs.getElmt(7,0)) + " XY^3 + " +
            Double.toString(coefs.getElmt(8,0)) + " Y^2 + " +
            Double.toString(coefs.getElmt(9,0)) + " XY^2 + " +
            Double.toString(coefs.getElmt(10,0)) + " X^2Y^2 + " +
            Double.toString(coefs.getElmt(11,0)) + " X^3Y^2 + " +
            Double.toString(coefs.getElmt(12,0)) + " Y^3 + " +
            Double.toString(coefs.getElmt(13,0)) + " XY^3 + " +
            Double.toString(coefs.getElmt(14,0)) + " X^2Y^3 + " +
            Double.toString(coefs.getElmt(15,0)) + " X^3Y^3";
    }
}
