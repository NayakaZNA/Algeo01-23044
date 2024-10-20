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

    public static void displaySolution(MatrixADT coefs){
        System.out.print("Y = " + coefs.getElmt(0, 0) + " ");
        if (coefs.getRows() > 1){
            // System.out.print("+ " + coefs.getElmt(1, 0) + "X" + " ");
            System.out.printf("+ %.3f X ", coefs.getElmt(1, 0));
            for (int i = 2; i < coefs.getRows(); i++){
                // System.out.print("+ " + coefs.getElmt(i, 0) + "X^" + i + " ");
                System.out.printf("+ %.3f X^%d ", coefs.getElmt(i, 0), i);
            }
        }
        // System.out.println("\n");
    }
}