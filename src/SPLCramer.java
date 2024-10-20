public class SPLCramer {
    public static void cramer(MatrixADT m) {
        int rows = m.nRows;
        int cols = m.nCols;

        // Ensure matrix is square (n x n), because Cramer's rule works only for square matrices
        if (rows != cols - 1) {
            System.out.println("Cramer's Rule only applies to systems with n equations and n unknowns.");
            return;
        }

        // Step 1: Compute the determinant of the coefficient matrix
        MatrixADT coefficientMatrix = new MatrixADT(rows, cols - 1);
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols - 1; j++) {
                coefficientMatrix.matrix[i][j] = m.matrix[i][j];
            }
        }

        // Use the optimized determinant method (Gaussian elimination)
        double detCoefficient = computeDeterminant(coefficientMatrix);

        // Step 2: Solve for each variable using Cramer's Rule
        double[] res = new double[cols - 1];
        double det;
        for (int j = 0; j < cols - 1; j++) {
            // Create a temporary matrix by replacing the j-th column with the constants
            MatrixADT temp = coefficientMatrix.copyMatrix();
            for (int i = 0; i < rows; i++) {
                temp.matrix[i][j] = m.matrix[i][cols - 1];
            }

            // Compute the determinant of the modified matrix
            det = computeDeterminant(temp);

            // Solve for variable Xj
            res[j] = det / detCoefficient;

            // Check for ill-conditioned matrix issues
            // if (Double.isNaN(res[j]) || Double.isInfinite(res[j])) {
            //     System.out.println("The system appears to be ill-conditioned, leading to invalid results.");
            //     return;
            // }
        }

        // Step 3: Output the solution
        for (int i = 0; i < cols - 1; i++) {
            System.out.printf("X%d = %.6f\n", i + 1, res[i]);
        }
    }

    // Optimized determinant calculation using Gaussian elimination
    private static double computeDeterminant(MatrixADT m) {
        MatrixADT temp = m.copyMatrix();  // Create a copy to avoid modifying the original matrix
        int n = temp.nRows;
        double det = 1;
        boolean isSwapped = false;

        // Perform Gaussian elimination to reduce to upper triangular form
        for (int i = 0; i < n; i++) {
            // Find pivot (ensure the pivot is not zero)
            if (Math.abs(temp.matrix[i][i]) < 1e-9) {
                boolean swapped = false;
                for (int j = i + 1; j < n; j++) {
                    if (Math.abs(temp.matrix[j][i]) > 1e-9) {
                        swapRows(temp, i, j);
                        isSwapped = !isSwapped;
                        swapped = true;
                        break;
                    }
                }
                if (!swapped) {
                    return 0;  // The matrix is singular, determinant is zero
                }
            }

            // Perform row reduction below the pivot
            for (int j = i + 1; j < n; j++) {
                double factor = temp.matrix[j][i] / temp.matrix[i][i];
                for (int k = i; k < n; k++) {
                    temp.matrix[j][k] -= factor * temp.matrix[i][k];
                }
            }

            // Multiply the diagonal elements to compute the determinant
            det *= temp.matrix[i][i];
        }

        // Adjust determinant for row swaps
        if (isSwapped) {
            det = -det;
        }

        return det;
    }

    // Utility function to swap rows during Gaussian elimination
    private static void swapRows(MatrixADT m, int row1, int row2) {
        double[] temp = m.matrix[row1];
        m.matrix[row1] = m.matrix[row2];
        m.matrix[row2] = temp;
    }
}
