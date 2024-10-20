package Matrix;
public class SPLBalikan {
    public static MatrixADT solve(MatrixADT m) {
        MatrixADT A = new MatrixADT(m.nRows, m.nCols-1);
        MatrixADT B = new MatrixADT(m.nRows, 1);
        MatrixADT res = new MatrixADT(m.nRows, 1);
        for (int i = 0; i < A.nRows; i++) {
            for (int j = 0; j < A.nCols; j++) {
                A.matrix[i][j] = m.matrix[i][j];
            }
        }

        for (int i = 0; i < B.nRows; i++) {
            B.matrix[i][0] = m.matrix[i][m.nCols-1];
        }

        MatrixADT AInverse = A.inverse();
        if (AInverse == null) {
            return null;
        } else {
            res = AInverse.multiply(B);
            return res;
        }
    }
    public static void displaySolution(MatrixADT result) {
            for (int i = 1; i < result.nRows; i++) {
                System.out.printf("X" + i + ": %.6f\n", result.getElmt(i, 0));
            }
    }
}
