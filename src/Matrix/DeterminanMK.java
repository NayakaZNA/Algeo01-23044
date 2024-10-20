package Matrix;
public class DeterminanMK {
    public static double detMK(MatrixADT m) {
        if (m.nRows == 1) {
            return m.matrix[0][0];
        }
        if (m.nRows == 2) {
            return (m.matrix[0][0] * m.matrix[1][1]) - (m.matrix[0][1] * m.matrix[1][0]);
        }
        double det = 0;
        int neg = 1;

        for (int i = 0; i < m.nCols; i++) {
            MatrixADT newM = MatrixADT.matrixMinor(m, 0, i);
            det += neg * m.matrix[0][i] * detMK(newM);
            neg *= -1;
        }

        return det;
    }
}
