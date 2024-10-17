import java.io.File;
import java.io.FileWriter;
import java.io.FileReader;
import java.io.IOException;
import java.io.BufferedReader;

public class txtIO {
    public static MatrixADT readTXT(String filename) {
        try {
            BufferedReader br = new BufferedReader(new FileReader(filename));
            // Menentukan dimensi matriks
            int i = 0, m = 0, n = 0;
            String line;
            while ((line = br.readLine()) != null) {
                m++;
                if (m == 1) {
                    n = line.split(" ").length;
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
            br.close();
            return inputMatrix;
                
        } catch (IOException e) {
            // Jika file kosong atau tidak ada, kembalikan pesan error dan null
            System.out.println("Sebuah error terjadi saat membaca file.");
            e.printStackTrace();
            return null;
        }

    }
    public static void writeTXT(String filename, MatrixADT outputMatrix) {
        File outputTXT = new File(filename);
        try {
            System.out.println("Memeriksa keberadaan " + filename +"...");
            if (!outputTXT.exists()) {
                System.out.println(filename + " tidak ada! Membuat " + filename + ".");
                outputTXT.createNewFile();
                System.out.println(filename + " berhasil dibuat.");
            } else {System.out.println(filename + " ditemukan!");}
            try (FileWriter writer = new FileWriter(outputTXT, false)) {
                for (int i = 0; i < outputMatrix.getRows(); i++) {
                    String line = "";
                    for (int j = 0; j < outputMatrix.getCols(); j++) {
                        if (j == 0) {
                            line += Double.toString(outputMatrix.getElmt(i, j));
                        } else {
                            line += " " + Double.toString(outputMatrix.getElmt(i, j));
                        }
                    }
                    writer.write(line);
                    writer.write("\n");
                }
            System.out.println(filename + " berhasil disimpan!");
            }
        }
        catch (IOException e) {
            System.out.println("Sebuah error terjadi ketika membuat atau menyimpan file.");
            e.printStackTrace();
        }
    }
}
