package Matrix;
public class SPLBalikan {
    public static MatrixADT solve(MatrixADT m) {
        MatrixADT A = new MatrixADT(m.nRows, m.nCols-1);
        MatrixADT B = new MatrixADT(m.nRows, 1);
        MatrixADT res = new MatrixADT(m.nRows, 1);

        // Melakukan validasi dimensi matriks n x (n+1)
        if (m.nRows != m.nCols - 1) {
            return null;
        }

        // Mengambil matriks n x n dari m
        for (int i = 0; i < A.nRows; i++) {
            for (int j = 0; j < A.nCols; j++) {
                A.matrix[i][j] = m.matrix[i][j];
            }
        }

        // Mengambil matriks n x 1 dari m
        for (int i = 0; i < B.nRows; i++) {
            B.matrix[i][0] = m.matrix[i][m.nCols-1];
        }

        // Melakukan inverse pada A
        MatrixADT AInverse = A.inverse();
        if (AInverse == null) {
            return null;
        } else {
            // Mencari hasil dengan A x B
            res = AInverse.multiply(B);
            return res;
        }
    }
    public static void displaySolution(MatrixADT result) {
        if (result == null) {
            System.out.println("Solusi tidak dapat ditentukan");
            return;
        }
        for (int i = 0; i < result.nRows; i++) {
            System.out.printf("X" + (i+1) + ": %.6f\n", result.getElmt(i, 0));
        }
    }
}
