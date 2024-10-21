package Matrix;
public class SPLCramer {
    public static MatrixADT solve(MatrixADT m) {
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
            return null;
        }

        double[][] res = new double[cols - 1][1];
        double det;
        for (int j = 0; j < cols-1; j++) {
            MatrixADT temp = coefficientMatrix.copyMatrix();
            for (int i = 0; i < rows; i++) {
                temp.matrix[i][j] = m.matrix[i][cols - 1];
            }
            det = DeterminanMK.detMK(temp);
            res[j][0] = det / detCoefficient;
        }
        return new MatrixADT(res);
    }
}
