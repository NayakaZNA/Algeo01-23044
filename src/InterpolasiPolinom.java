import java.util.Scanner;
import java.lang.Math;

public class InterpolasiPolinom {
    public static void scanPoints(int n, int[][] points) {
        Scanner scanner = new Scanner(System.in);
        String line;
        for (int i = 0; i < n; i++) {
            line = scanner.nextLine();
            String[] lineContents = line.split(" ");
            points[i][0] = Integer.parseInt(lineContents[0]);
            points[i][1] = Integer.parseInt(lineContents[1]);
        }
        scanner.close();
    }

    public static MatrixADT SubstitutePoints(int[][] points) {
        int n = points.length - 1;
        MatrixADT vandermonde = new MatrixADT(n+1, n+2);
        for (int i = 0; i < n+1; i++) {
            for (int j = 0; j < n+1; j++) {
                vandermonde.setElmt(i, j, Math.pow(points[i][0], (n-j)));
            }
            vandermonde.setElmt(i, n+1, points[i][1]);
        }
        return vandermonde;
    }

    public static double[] cramer(MatrixADT m) {
        int rows = m.nRows;
        int cols = m.nCols;

        MatrixADT coefficientMatrix = new MatrixADT(rows, cols - 1);
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols - 1; j++) {
                coefficientMatrix.matrix[i][j] = m.matrix[i][j];
            }
        }

        double detCoefficient = DeterminanMK.detMK(coefficientMatrix);

        if (detCoefficient == 0) {
            System.out.println("Determinan dari matriks adalah 0, tidak bisa menggunakan Cramer untuk menemukan solusi.");
            return null;
        }

        double[] res = new double[cols - 1];
        double det;
        for (int j = 0; j < cols-1; j++) {
            MatrixADT temp = coefficientMatrix.copyMatrix();
            for (int i = 0; i < rows; i++) {
                temp.matrix[i][j] = m.matrix[i][cols - 1];
            }
            det = DeterminanMK.detMK(temp);
            res[j] = det / detCoefficient;
        }

        return res;
    }

    public static double[] PolynomialCoefficients(MatrixADT vandermonde) {
        return cramer(vandermonde);
    }
}

