package Matrix;
public class DeterminanMK {
    public static double detMK(MatrixADT m) {
        // Jika hanya tersisa matriks 1x1, maka kembalikan nilai
        if (m.nRows == 1) {
            return m.matrix[0][0];
        }
        // Jika matriks 2x2, maka det += (a*d - b*c)
        if (m.nRows == 2) {
            return (m.matrix[0][0] * m.matrix[1][1]) - (m.matrix[0][1] * m.matrix[1][0]);
        }
        // Set nilai determinan dan tanda negatif
        double det = 0;
        int neg = 1;

        // Menghitung determinan dengan minor kofaktor pada baris pertama
        for (int i = 0; i < m.nCols; i++) {
            MatrixADT newM = MatrixADT.matrixMinor(m, 0, i);
            det += neg * m.matrix[0][i] * detMK(newM);
            // Mengubah nilai negatif
            neg *= -1;
        }

        return det;
    }
}
