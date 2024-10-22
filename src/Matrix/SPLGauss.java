package Matrix;
import java.util.Arrays;

public class SPLGauss {
    private MatrixADT matrix;
    private static final double EPSILON = 1e-9; // Threshold to treat very small values as 0

    // Constructor to initialize the matrix for the class
    public SPLGauss(MatrixADT matrix) {
        this.matrix = matrix;
    }

    // Perform Gaussian Reduction (ensure leading ones but do not zero out above pivots)
    public void gaussReduction() {
        int rows = matrix.nRows;
        int cols = matrix.nCols;

        int lead = 0;
        for (int r = 0; r < rows; r++) {
            if (lead >= cols) {
                break;  // Prevent accessing out-of-bounds columns
            }

            int i = r;
            while (Math.abs(matrix.matrix[i][lead]) < EPSILON) {
                i++;
                if (i == rows) {
                    i = r;
                    lead++;
                    if (lead == cols) {
                        break;  // Prevent accessing out-of-bounds columns
                    }
                }
            }

            // Swap rows if necessary to bring the pivot row
            if (lead < cols && Math.abs(matrix.matrix[i][lead]) >= EPSILON) {
                swapRows(i, r);

                // Ensure leading coefficient is 1 by dividing the pivot row by the leading element
                double pivotValue = matrix.matrix[r][lead];
                if (Math.abs(pivotValue) > EPSILON) {
                    for (int j = 0; j < cols; j++) {
                        matrix.matrix[r][j] /= pivotValue;
                        // Round the result to avoid floating-point errors
                        matrix.matrix[r][j] = round(matrix.matrix[r][j]);
                    }
                }

                // Eliminate rows below the pivot (same as in Gaussian elimination)
                for (int i2 = r + 1; i2 < rows; i2++) {  // Start eliminating from the row below the pivot
                    double factor = matrix.matrix[i2][lead];
                    for (int j = lead; j < cols; j++) {
                        matrix.matrix[i2][j] -= factor * matrix.matrix[r][j];
                        // Round the result to avoid floating-point errors
                        matrix.matrix[i2][j] = round(matrix.matrix[i2][j]);
                    }
                }
            }

            // Increment the lead column
            lead++;
        }
        detectSolution(); // Now call detectSolution to analyze the matrix
    }

    // Swap two rows of the matrix
    private void swapRows(int row1, int row2) {
        double[] temp = matrix.matrix[row1];
        matrix.matrix[row1] = matrix.matrix[row2];
        matrix.matrix[row2] = temp;
    }

    // Rounding function to handle small floating-point inaccuracies
    private double round(double value) {
        return Math.abs(value) < EPSILON ? 0 : value;
    }

    // Detect whether the system has a unique solution, no solution, or parametric solution
    private void detectSolution() {
        int rows = matrix.nRows;
        int cols = matrix.nCols;
        boolean noSolution = false;
        boolean parametricSolution = false;
//        System.out.println("detectSolution called");

        // Check for inconsistency (no solution)
        for (int i = 0; i < rows; i++) {
            boolean allZeroes = true;
            for (int j = 0; j < cols - 1; j++) {
                if (Math.abs(matrix.matrix[i][j]) > EPSILON) {
                    allZeroes = false;
                    break;
                }
            }
            // If all variables are zero but the constant term is not, there is no solution
            if (allZeroes && Math.abs(matrix.matrix[i][cols - 1]) > EPSILON) {
                noSolution = true;
                break;
            }
        }

        if (noSolution) {
            System.out.println("No Solution");
        } else {
            // Check for parametric solutions (free variables)
            boolean[] isPivotColumn = new boolean[cols - 1];
            int[] pivotRow = new int[cols - 1]; // Track the row of the pivot in each column
            Arrays.fill(pivotRow, -1);

            // Identify pivot columns and pivot rows
            for (int i = 0; i < rows; i++) {
                for (int j = 0; j < cols - 1; j++) {
                    if (Math.abs(matrix.matrix[i][j]) == 1 && pivotRow[j] == -1) {
                        isPivotColumn[j] = true;
                        pivotRow[j] = i;
                        break;
                    }
                }
            }

            // Check if there are any free variables (parametric solution)
            parametricSolution = false;
            for (int i = 0; i < cols - 1; i++) {
                if (!isPivotColumn[i]) {
                    parametricSolution = true;
                    break;
                }
            }

            if (parametricSolution) {
                // System.out.println("Parametric solution detected");
                printParametricSolution(isPivotColumn, pivotRow);
            } else {
                // If no free variables, we have a unique solution
                printUniqueSolution(pivotRow);
            }
        }
    }

    // Print the unique solution for the system
    private void printUniqueSolution(int[] pivotRow) {
        int cols = matrix.nCols;
        int rows = matrix.nRows;
        double[] solution = new double[cols - 1];  // Store solutions

        // Back substitution to find the correct values of variables
        for (int i = rows - 1; i >= 0; i--) {
            int pivotIndex = -1;
            for (int j = 0; j < cols - 1; j++) {
                if (Math.abs(matrix.matrix[i][j]) == 1) {
                    pivotIndex = j;
                    break;
                }
            }

            if (pivotIndex != -1) {
                // Solve for the pivot variable
                solution[pivotIndex] = matrix.matrix[i][cols - 1];
                for (int j = pivotIndex + 1; j < cols - 1; j++) {
                    solution[pivotIndex] -= matrix.matrix[i][j] * solution[j];
                }
            }
        }

        // Print the solution
        System.out.println("Unique Solution:");
        for (int i = 0; i < cols - 1; i++) {
            System.out.printf("X%d = %.6f\n", i + 1, solution[i]);
        }
    }

    // Print the parametric solution if the system has free variables
    private void printParametricSolution(boolean[] isPivotColumn, int[] pivotRow) {
        int cols = matrix.nCols;
        System.out.println("Parametric Solution:");

        for (int j = 0; j < cols - 1; j++) {
            if (isPivotColumn[j]) {
                // Dependent variable (can be expressed in terms of other variables)
                System.out.printf("X%d = %.6f", j + 1, matrix.matrix[pivotRow[j]][cols - 1]);
                for (int k = j + 1; k < cols - 1; k++) {
                    if (Math.abs(matrix.matrix[pivotRow[j]][k]) > EPSILON) {
                        System.out.printf(" + %.6fa%d", -matrix.matrix[pivotRow[j]][k], k + 1);
                    }
                }
                System.out.println();
            } else {
                // Free variable
                System.out.printf("X%d = a%d\n", j + 1, j + 1);
            }
        }
    }
}
