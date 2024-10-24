package Matrix;
public class SPLCramer {
    public static MatrixADT solve(MatrixADT m) {
        int rows = m.nRows;
        int cols = m.nCols;

        // Melakukan validasi dimensi matriks n x (n+1)
        if (rows != cols - 1) {
            System.out.println("SPL tidak dapat dikerjakan dengan metode ini.");
            return null;
        }

        // Mencari determnian dari matriks koefisien
        MatrixADT coefficientMatrix = new MatrixADT(rows, cols - 1);
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols - 1; j++) {
                coefficientMatrix.matrix[i][j] = m.matrix[i][j];
            }
        }

        // Mencari determinan
        double detCoefficient = coefficientMatrix.determinant();

        // Jika det 0, maka solusi tidak dapat ditemukan
        if (detCoefficient == 0) {
            System.out.println("SPL tidak memiliki solusi.");
            return null;
        }

        // Mengganti setiap kolom matriks koefisien untuk mendapatkan hasil
        double[][] res = new double[cols - 1][1];
        double det;
        for (int j = 0; j < cols - 1; j++) {
            MatrixADT temp = coefficientMatrix.copyMatrix();
            for (int i = 0; i < rows; i++) {
                temp.matrix[i][j] = m.matrix[i][cols - 1];
            }

            // Mencari determinan matriks sebagai solusi
            det = computeDeterminant(temp);

            // Menyelesaikan Xj
            res[j][0] = det / detCoefficient;
        }

        // Output
        for (int i = 0; i < cols - 1; i++) {
            System.out.printf("X%d = %.6f\n", i + 1, res[i][0]);
        }
        return new MatrixADT(res);
    }

    // Mencari determinan
    private static double computeDeterminant(MatrixADT m) {
        MatrixADT temp = m.copyMatrix();
        int n = temp.nRows;
        double det = 1;
        boolean isSwapped = false;

        // Melakukan Determinan menggunakan Gauss
        for (int i = 0; i < n; i++) {
            // Mencari pivot
            if (Math.abs(temp.matrix[i][i]) < 1e-9) {
                boolean swapped = false;
                for (int j = i + 1; j < n; j++) {
                    if (Math.abs(temp.matrix[j][i]) > 1e-9) {
                        swapRows(temp, i, j);
                        isSwapped = !isSwapped;
                        swapped = true;
                        break;
                    }
                }
                if (!swapped) {
                    return 0;  // Matriks singular
                }
            }

            // Reduksi baris
            for (int j = i + 1; j < n; j++) {
                double factor = temp.matrix[j][i] / temp.matrix[i][i];
                for (int k = i; k < n; k++) {
                    temp.matrix[j][k] -= factor * temp.matrix[i][k];
                }
            }

            det *= temp.matrix[i][i];
        }

        // Menyesuaikan tanda negatif
        if (isSwapped) {
            det = -det;
        }

        return det;
    }

    private static void swapRows(MatrixADT m, int row1, int row2) {
        double[] temp = m.matrix[row1];
        m.matrix[row1] = m.matrix[row2];
        m.matrix[row2] = temp;
    }
}
