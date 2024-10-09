public class SPLBalikan {
    public static void balikan(MatrixADT m) {
        MatrixADT A = new MatrixADT(m.nRows, m.nCols-1);
        MatrixADT B = new MatrixADT(m.nRows, 1);
        double[] res = new double[m.nRows];
        for (int i = 0; i < A.nRows; i++) {
            for (int j = 0; j < A.nCols; j++) {
                A.matrix[i][j] = m.matrix[i][j];
            }
        }

        for (int i = 0; i < B.nRows; i++) {
            B.matrix[i][0] = m.matrix[i][m.nCols-1];
        }

        MatrixADT AInverse = InverseAdjoin.inverseAdj(A);
        if (AInverse == null) {
            System.out.println("Determinan dari matriks adalah 0, tidak bisa menggunakan Balikan untuk menemukan solusi.");
            return;
        } else {
            for (int i = 0; i < AInverse.nRows; i++) {
                for (int j = 0; j < AInverse.nCols; j++) {
                    res[i] += (AInverse.matrix[i][j] * B.matrix[j][0]);
                }
            }

            for (int i = 1; i < m.nCols; i++) {
                System.out.printf("X" + i + ": %.6f\n", res[i - 1]);
            }
        }
    }
}
