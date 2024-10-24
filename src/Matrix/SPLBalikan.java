package Matrix;
public class SPLBalikan {
    public static String solve(MatrixADT m) {
        MatrixADT A = new MatrixADT(m.nRows, m.nCols-1);
        MatrixADT B = new MatrixADT(m.nRows, 1);
        MatrixADT res = new MatrixADT(m.nRows, 1);
        
        if (m.nRows != m.nCols - 1) {
            System.out.println("SPL tidak dapat dikerjakan dengan metode ini.");
            return ("SPL tidak dapat dikerjakan dengan metode ini.");
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
            System.out.println("SPL tidak memiliki solusi.");
            return "SPL tidak memiliki solusi.";
        } else {
            // Mencari hasil dengan A x B
            res = AInverse.multiply(B);
            return displaySolution(res);
            
        }
    }
    public static String displaySolution(MatrixADT result) {
        String res = "";
        for (int i = 0; i < result.nRows; i++) {
            System.out.printf("X" + (i+1) + ": %.6f\n", result.getElmt(i, 0));
            res += String.format("X" + (i+1) + ": %.6f\n", result.getElmt(i, 0));
        }
        return res;
    }
}
