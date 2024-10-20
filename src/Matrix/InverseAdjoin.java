package Matrix;
public class InverseAdjoin {
    public static MatrixADT inverseAdj(MatrixADT m) {
        double det = DeterminanMK.detMK(m);
        if (det == 0) {
            return null;
        }
        MatrixADT cofactorMatrix = new MatrixADT(m.nRows, m.nCols);
        for (int i = 0; i < m.nRows; i++) {
            for (int j = 0; j < m.nCols; j++) {
                cofactorMatrix.matrix[i][j] = cofactor(m, i, j);
            }
        }
        MatrixADT adjoinMatrix = adjoin(cofactorMatrix);

        MatrixADT inverseMatrix = new MatrixADT(m.nRows, m.nCols);
        for (int i = 0; i < m.nRows; i++) {
            for (int j = 0; j < m.nCols; j++) {
                inverseMatrix.matrix[i][j] = adjoinMatrix.matrix[i][j] / det;
            }
        }
        return inverseMatrix;
    }

    private static double cofactor(MatrixADT m, int i, int j) {
        MatrixADT minor = MatrixADT.matrixMinor(m, i, j);
        double det = DeterminanMK.detMK(minor);
        int neg = (i + j) % 2 == 0 ? 1 : -1;
        return neg * det;
    }

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
