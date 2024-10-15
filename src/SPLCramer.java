public class SPLCramer {
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
}
