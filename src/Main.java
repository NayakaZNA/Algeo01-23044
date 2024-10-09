//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Masukkan jumlah baris matriks : ");
        int rows = scanner.nextInt();
        System.out.println("Masukkan jumlah kolom matriks : ");
        int cols = scanner.nextInt();

        MatrixADT matrix = new MatrixADT(rows, cols);

        matrix.readMatrix(rows, cols);

        System.out.println("Matriks yang terbentuk adalah");

        matrix.printMatrix();

        if (matrix.nCols == matrix.nRows) {
            double det = DeterminanMK.detMK(matrix);
            double det2 = DeterminanReduksi.detRB(matrix);
            System.out.printf("Determinan matriks dengan MK adalah : %.6f\n", det);
            System.out.printf("Determinan matriks dengan RB adalah : %.6f\n", det2);
            MatrixADT inverseAdjMatrix = matrix.copyMatrix();
            inverseAdjMatrix = InverseAdjoin.inverseAdj(matrix);
            if (inverseAdjMatrix != null) {
                System.out.println("InverseAdjMatrix adalah : ");
                inverseAdjMatrix.printMatrix();
            }
            MatrixADT inverseGaussJMatrix = matrix.copyMatrix();
            inverseGaussJMatrix = InverseGaussJ.inverseGaussJ(matrix);
            if (inverseGaussJMatrix != null) {
                System.out.println("InverseGaussJMatrix adalah : ");
                inverseGaussJMatrix.printMatrix();
            }
        } else {
            System.out.println("Determinan matriks tidak ada");
        }

        if (matrix.nCols-1 == matrix.nRows) {
            System.out.println("SPL Cramer adalah : ");
            SPLCramer.cramer(matrix);
            System.out.println();
            System.out.println("SPL Balikan adalah : ");
            SPLBalikan.balikan(matrix);
        }
        scanner.close();
    }
}