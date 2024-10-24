package Matrix;
import java.util.Scanner;
import java.lang.Math;
public class InterpolasiPolinom {
    public static MatrixADT scanPoints(int n) {
        Scanner scanner = new Scanner(System.in);
        MatrixADT points = new MatrixADT(n, 2);
        String line;
        for (int i = 0; i < n; i++) {
            line = scanner.nextLine();
            String[] lineContents = line.split(" ");
            points.setElmt(i, 0, Double.parseDouble(lineContents[0]));
            points.setElmt(i, 1, Double.parseDouble(lineContents[1]));
        }
        scanner.close();
        return points;
    }

    public static MatrixADT SubstitutePoints(MatrixADT points) {
        int n = points.getRows();
        MatrixADT vandermonde = new MatrixADT(n, n+1);
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                vandermonde.setElmt(i, j, Math.pow(points.getElmt(i, 0), (j)));
            }
            vandermonde.setElmt(i, n, points.getElmt(i, 1));
        }
        return vandermonde;
    }

    public static MatrixADT PolynomialCoefficients(MatrixADT points) {
        return SPLBalikan.solve(SubstitutePoints(points));
    }

    public static void displaySolution(MatrixADT coefs) {
        double epsilon = 1e-10;
    
        double firstCoef = coefs.getElmt(0, 0);
        if (Math.abs(firstCoef) > epsilon) {
            System.out.printf("Y = %.5f ", firstCoef);
        } else {
            System.out.print("Y = 0.00000 "); 
        }
    
        for (int i = 1; i < coefs.getRows(); i++) {
            double coef = coefs.getElmt(i, 0);
            if (Math.abs(coef) > epsilon) { 
                if (coef < 0) {
                    System.out.print("- ");
                } else {
                    System.out.print("+ ");
                }
                if (i == 1) System.out.printf("%.5f X ", Math.abs(coef));
                else System.out.printf("%.5f X^%d ", Math.abs(coef), i);
            }
        }
        System.out.println();
    }

    public static double predict(MatrixADT coefs, double x) {
        double retval = 0;
        for (int i = 0; i < coefs.getRows(); i++) {
            retval += coefs.getElmt(i, 0) * Math.pow(x, i);
        }
        return retval;
    }
}