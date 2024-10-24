package Matrix;
import java.util.Arrays;

public class SPLGauss {
    private MatrixADT matrix;
    private static final double EPSILON = 1e-9; // Untuk nilai sangat kecil

    public SPLGauss(MatrixADT matrix) {
        this.matrix = matrix;
    }

    // Eliminasi Gauss
    public void gaussReduction() {
        int rows = matrix.nRows;
        int cols = matrix.nCols;

        int lead = 0;
        // Mencari leading one
        for (int r = 0; r < rows; r++) {
            if (lead >= cols) {
                break;  // Menghindari error out of bounds
            }

            int i = r;
            while (Math.abs(matrix.matrix[i][lead]) < EPSILON) {
                i++;
                if (i == rows) {
                    i = r;
                    lead++;
                    if (lead == cols) {
                        break;  // Menghindari error out of bounds
                    }
                }
            }

            // Swap row jika ditemukan elemen bukan 0 sebagai leading one
            if (lead < cols && Math.abs(matrix.matrix[i][lead]) >= EPSILON) {
                swapRows(i, r);

                // Menentukan pivot
                double pivotValue = matrix.matrix[r][lead];
                if (Math.abs(pivotValue) > EPSILON) {
                    for (int j = 0; j < cols; j++) {
                        matrix.matrix[r][j] /= pivotValue;
                        // Round the result to avoid floating-point errors
                        matrix.matrix[r][j] = round(matrix.matrix[r][j]);
                    }
                }

                // Eliminasi baris pada leading one
                for (int i2 = r + 1; i2 < rows; i2++) { 
                    double factor = matrix.matrix[i2][lead];
                    for (int j = lead; j < cols; j++) {
                        matrix.matrix[i2][j] -= factor * matrix.matrix[r][j];
                        // Melakukan pembulatan agar menghasilkan hasil yang lebih baik
                        matrix.matrix[i2][j] = round(matrix.matrix[i2][j]);
                    }
                }
            }
            lead++;
        }
        detectSolution(); // Deteksi hasil solusi
    }

    private void swapRows(int row1, int row2) {
        double[] temp = matrix.matrix[row1];
        matrix.matrix[row1] = matrix.matrix[row2];
        matrix.matrix[row2] = temp;
    }

    // Pembulatan angka sangat kecil
    private double round(double value) {
        return Math.abs(value) < EPSILON ? 0 : value;
    }

    // Menentukan solusi unik, paramterik, atau tidak ada solusi
    private void detectSolution() {
        int rows = matrix.nRows;
        int cols = matrix.nCols;
        boolean noSolution = false;
        boolean parametricSolution = false;
//        System.out.println("Deteksi solusi");

        // Cek jika solusi tidak ada
        for (int i = 0; i < rows; i++) {
            boolean allZeroes = true;
            for (int j = 0; j < cols - 1; j++) {
                if (Math.abs(matrix.matrix[i][j]) > EPSILON) {
                    allZeroes = false;
                    break;
                }
            }
            // Jika seluruh variabel 0 kecuali kolom paling kanan, maka tidak ada solusi
            if (allZeroes && Math.abs(matrix.matrix[i][cols - 1]) > EPSILON) {
                noSolution = true;
                break;
            }
        }

        if (noSolution) {
            System.out.println("SPL tidak memiliki solusi");
        } else {
            // Cek jika solusi parametrik
            boolean[] isPivotColumn = new boolean[cols - 1];
            int[] pivotRow = new int[cols - 1]; // Kumpulan row dari pivot
            Arrays.fill(pivotRow, -1); //Placeholder pada pivotRow

            // Mencari row dan column pivot
            for (int i = 0; i < rows; i++) {
                for (int j = 0; j < cols - 1; j++) {
                    if (Math.abs(matrix.matrix[i][j]) == 1 && pivotRow[j] == -1) {
                        isPivotColumn[j] = true;
                        pivotRow[j] = i;
                        break;
                    }
                }
            }

            // Cek variabel bebas
            parametricSolution = false;
            for (int i = 0; i < cols - 1; i++) {
                if (!isPivotColumn[i]) {
                    parametricSolution = true;
                    break;
                }
            }

            if (parametricSolution) {
                // System.out.println("Parametric solution");
                printParametricSolution(isPivotColumn, pivotRow);
            } else {
                // Jika tidak ada variabel bebas, maka solusi unik
                printUniqueSolution(pivotRow);
            }
        }
    }

    // Print solusi unik
    private void printUniqueSolution(int[] pivotRow) {
        int cols = matrix.nCols;
        int rows = matrix.nRows;
        double[] solution = new double[cols - 1];  // Solusi

        // Back substitution
        for (int i = rows - 1; i >= 0; i--) {
            int pivotIndex = -1;
            for (int j = 0; j < cols - 1; j++) {
                if (Math.abs(matrix.matrix[i][j]) == 1) {
                    pivotIndex = j;
                    break;
                }
            }

            if (pivotIndex != -1) {
                // Menyelesaikan solusi untuk pivot
                solution[pivotIndex] = matrix.matrix[i][cols - 1];
                for (int j = pivotIndex + 1; j < cols - 1; j++) {
                    solution[pivotIndex] -= matrix.matrix[i][j] * solution[j];
                }
            }
        }

        // Print solusi
        // System.out.println("Unique Solution:");
        for (int i = 0; i < cols - 1; i++) {
            System.out.printf("X%d = %.6f\n", i + 1, solution[i]);
        }
    }

    // Print solusi parametrik
    private void printParametricSolution(boolean[] isPivotColumn, int[] pivotRow) {
        int cols = matrix.nCols;
        // System.out.println("Parametric Solution:");

        for (int j = 0; j < cols - 1; j++) {
            if (isPivotColumn[j]) {
                // Menyatakan X yang memiliki nilai
                System.out.printf("X%d = %.6f", j + 1, matrix.matrix[pivotRow[j]][cols - 1]);
                for (int k = j + 1; k < cols - 1; k++) {
                    if (Math.abs(matrix.matrix[pivotRow[j]][k]) > EPSILON) {
                        System.out.printf(" + %.6fa%d", -matrix.matrix[pivotRow[j]][k], k + 1);
                    }
                }
                System.out.println();
            } else {
                // Menyatakan X yang memiliki variabel bebas
                System.out.printf("X%d = a%d\n", j + 1, j + 1);
            }
        }
    }
}
