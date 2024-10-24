package Matrix;
public class InverseAdjoin {
    public static MatrixADT inverseAdj(MatrixADT m) {
        double det = DeterminanMK.detMK(m);

        // Jika det = 0, maka tidak memiliki inverse
        if (det == 0) {
            return null;
        }

        // Mencari kofaktor dari matriks
        MatrixADT cofactorMatrix = new MatrixADT(m.nRows, m.nCols);
        for (int i = 0; i < m.nRows; i++) {
            for (int j = 0; j < m.nCols; j++) {
                cofactorMatrix.matrix[i][j] = cofactor(m, i, j);
            }
        }

        // Melakukan transpose untuk membuat adjoin
        MatrixADT adjoinMatrix = adjoin(cofactorMatrix);

        // Inverse = 1/det * Adj(M)
        MatrixADT inverseMatrix = new MatrixADT(m.nRows, m.nCols);
        for (int i = 0; i < m.nRows; i++) {
            for (int j = 0; j < m.nCols; j++) {
                inverseMatrix.matrix[i][j] = adjoinMatrix.matrix[i][j] / det;
            }
        }
        return inverseMatrix;
    }

    // Algoritma kofaktor
    private static double cofactor(MatrixADT m, int i, int j) {
        MatrixADT minor = MatrixADT.matrixMinor(m, i, j);
        double det = DeterminanMK.detMK(minor);
        int neg = (i + j) % 2 == 0 ? 1 : -1;
        return neg * det;
    }

    // Algoritma transpose
    private static MatrixADT adjoin(MatrixADT m) {
        MatrixADT temp = m.copyMatrix();
        for (int i = 0; i < m.nRows; i++) {
            for (int j = 0; j < m.nCols; j++) {
                temp.matrix[j][i] = m.matrix[i][j];
            }
        }
        return temp;
    }
}
