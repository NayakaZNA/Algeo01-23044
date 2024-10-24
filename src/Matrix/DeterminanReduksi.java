package Matrix;
public class DeterminanReduksi {
    public static double detRB(MatrixADT m) {
        MatrixADT temp = m.copyMatrix();

        double det = 1;
        int n = temp.nRows;
        boolean isSwapped = false; //Menentukan nilai negatif

        // Membuat matriks segitiga atas
        for (int i = 0; i < n; i++) {
            if (temp.matrix[i][i] == 0) {
                // Jika pada M[i][i] = 0, maka swap
                boolean swapped = false;
                for (int j = i + 1; j < n; j++) {
                    if (temp.matrix[j][i] != 0) {
                        swapRows(temp, i, j);
                        isSwapped = !isSwapped;
                        swapped = true;
                        break;
                    }
                }
                // Jika seluruh baris pada kolom yang sama bernilai 0, maka det = 0
                if (!swapped) {
                    return 0.0;
                }
            }
            det *= temp.matrix[i][i];

            // Melakukan OBE
            for (int j = i + 1; j < n; j++) {
                double factor = temp.matrix[j][i] / temp.matrix[i][i];
                for (int k = i; k < n; k++) {
                    temp.matrix[j][k] -= factor * temp.matrix[i][k];
                }
            }
        }
        // Jika melakukan swap ganjil-kali, maka negatif
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
