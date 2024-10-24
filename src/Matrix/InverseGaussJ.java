package Matrix;
public class InverseGaussJ {
    public static MatrixADT inverseGaussJ(MatrixADT m) {

        // Melakukan OBE menjadi matriks identitas
        MatrixADT temp = OBEGaussJ(convertMatrix(m));
        if (temp == null) {
            return null;
        } else {
            // Memasukkan matriks inverse
            MatrixADT res = new MatrixADT(m.nRows, m.nCols);
            for (int i = 0; i < m.nRows; i++) {
                for (int j = 0; j < m.nCols; j++) {
                    res.matrix[i][j] = temp.matrix[i][m.nCols + j];
                }
            }
            return res;
        }
    }

    // Membuat matriks augmented dengan matriks identitas
    private static MatrixADT convertMatrix(MatrixADT m) {
        MatrixADT temp = new MatrixADT(m.nRows, m.nCols * 2);
        for (int i = 0; i < m.nRows; i++) {
            for (int j = 0; j < m.nCols; j++) {
                temp.matrix[i][j] = m.matrix[i][j];
            }
        }
        for (int i = 0; i < m.nRows; i++) {
            for (int j = 0; j < m.nCols; j++) {
                if (i != j) {
                    temp.matrix[i][m.nCols + j] = 0;
                } else {
                    temp.matrix[i][m.nCols + j] = 1;
                }
            }
        }
        return temp;
    }

    private static MatrixADT OBEGaussJ(MatrixADT m) {
        // Mencari leading one
        for (int i = 0; i < m.nRows; i++) {
            if (m.matrix[i][i] == 0) {
                boolean swapped = false;
                for (int j = i + 1; j < m.nRows; j++) {
                    if (m.matrix[j][i] != 0) {
                        swapRows(m, i, j);
                        swapped = true;
                        break;
                    }
                }
                // Jika tidak ada leading one
                if (!swapped) {
                    return null;
                }
            }

            double pivot = m.matrix[i][i];
            for (int j = 0; j < m.nCols; j++) {
                m.matrix[i][j] /= pivot;
            }

            // Menjadikan 0 pada kolom yang memiliki leading one
            for (int j = 0; j < m.nRows; j++) {
                if (j != i) {
                    double factor = m.matrix[j][i];
                    for (int k = 0; k < m.nCols; k++) {
                        m.matrix[j][k] -= factor * m.matrix[i][k];
                    }
                }
            }
        }
        return m;
    }

    private static void swapRows(MatrixADT m, int row1, int row2) {
        double[] temp = m.matrix[row1];
        m.matrix[row1] = m.matrix[row2];
        m.matrix[row2] = temp;
    }
}
