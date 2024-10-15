import java.util.Scanner;

public class MatrixADT {
    public double[][] matrix;
    public int nRows;
    public int nCols;

    public MatrixADT(int nRows, int nCols) {
        this.nRows = nRows;
        this.nCols = nCols;
        this.matrix = new double[nRows][nCols];
    }

    public int getRows() {
        return this.nRows;
    }

    public int getCols() {
        return this.nCols;
    }

    public double getElmt(int row, int col) {
        return this.matrix[row][col];
    }

    public void setElmt(int row, int col, double value) {
        this.matrix[row][col] = value;
    }

    public void printMatrix() {
        for (int i = 0; i < nRows; i++) {
            for (int j = 0; j < nCols; j++) {
                System.out.printf("%.6f", matrix[i][j]);

                if (j != nCols - 1) {
                    System.out.print(" ");
                }
            }
            if (i != nRows - 1) {
                System.out.println();
            }
        }
        System.out.println();
    }

    public void readMatrix(int row, int col) {
        double data;
        System.out.println("Masukkan Matriks, pisahkan baris dengan newline dan kolom dengan spasi");
        Scanner scan = new Scanner(System.in);
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < col; j++) {
                try {
                    data = scan.nextDouble();
                    this.matrix[i][j] = data;
                } catch (Exception e) {
                    System.out.println("Input tidak valid");
                    scan.nextLine();
                }
            }
        }
        scan.close();
    }

    public static MatrixADT matrixMinor(MatrixADT m, int i, int j) {
        MatrixADT temp = new MatrixADT(m.nRows - 1, m.nCols - 1);
        int minorRow = 0, minorCol;

        for (int a = 0; a < m.nRows; a++) {
            if (a == i) {
                continue;
            }

            minorCol = 0;
            for (int b = 0; b < m.nCols; b++) {
                if (b == j) {
                    continue;
                }

                temp.matrix[minorRow][minorCol] = m.matrix[a][b];
                minorCol++;
            }
            minorRow++;
        }

        return temp;
    }

    public MatrixADT copyMatrix() {
        MatrixADT copyMatrix = new MatrixADT(this.nRows, this.nCols);
        for (int i = 0; i < this.nRows; i++) {
            for (int j = 0; j < this.nCols; j++) {
                copyMatrix.matrix[i][j] = this.matrix[i][j];
            }
        }
        return copyMatrix;
    }
}
