import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.BufferedReader;

public class txtIO {
    public static MatrixADT readTXT(String filename) {
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            // Menentukan dimensi matriks
            int i = 0, m = 0, n = 0;
            while (br.readLine() != null) {
                m = m + 1;
                if (m == 1) {
                    n = br.readLine().split(" ").length;
                }
            }

            // Membuat matriks
            MatrixADT inputMatrix = new MatrixADT(m, n);
            
            // Mengisi matriks
            while (br.readLine() != null) {
                String[] temp = br.readLine().split(" ");
                // Mengisi baris ke-i
                for (int j = 0; j < temp.length; j++) {
                    inputMatrix.setElmt(i, j, Double.valueOf(temp[j]));
                }
                i++;
            }
                return inputMatrix;
                
        } catch (IOException e) {
            // Jika file kosong atau tidak ada, kembalikan null
            return null;
        }

    }
    public static void writeTXT(String filename, MatrixADT outputMatrix) {
        
    }
}
